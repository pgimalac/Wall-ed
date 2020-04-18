package fr.telecom.wall_ed.controller;


import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.CheckBox;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fr.telecom.wall_ed.model.InterfaceServeur;
import fr.telecom.wall_ed.model.Utilisateur;
import fr.telecom.wall_ed.model.InterfaceGestionUtilisateurs;
import fr.telecom.wall_ed.model.Serveur;
import fr.telecom.wall_ed.view.AjoutUtilisateurFragment;
import fr.telecom.wall_ed.view.MainFragment;
import fr.telecom.wall_ed.R;
import fr.telecom.wall_ed.view.SettingsFragment;
import fr.telecom.wall_ed.view.Statistiques_globales;
import fr.telecom.wall_ed.view.UtilisateursFragment;


public class MainActivity extends AppCompatActivity implements InterfaceGestionUtilisateurs, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, android.widget.CompoundButton.OnCheckedChangeListener, InterfaceServeur {

    //If testing without the server, set to false; otherwise, set to true
    private static final boolean SERVER_AVAILABLE = false;

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    private Uri mImage_uri;
    private Serveur serveur;
    private FragmentManager mFragmentManager = null;
    private AppBarConfiguration mAppBarConfiguration = null;
    private ArrayList<fr.telecom.wall_ed.model.Utilisateur> mUsers = null;
    private SharedPreferences mPrefs = null;

    private UtilisateurAdapter utAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_users)
                .setDrawerLayout(drawer)
                .build();

        MainFragment mainFragment = new MainFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.main_frame_layout, mainFragment)
                .addToBackStack(null).commit();

        if (SERVER_AVAILABLE){
            serveur = new Serveur();
            Log.i("PACT32_DEBUG", "SERVER_AVAILABLE = true");
            try{
                Thread.sleep(500);
                mUsers = serveur.getUsers();
                Log.i("PACT32_DEBUG", "Déroulement nominal : mUsers récupérée sur serveur");
            }catch (Exception e){
                mUsers = new ArrayList<>();
                Log.e("PACT32_DEBUG", "Initialisation alternative : mUsers initialisée vide");
            }
        }else{
            Log.i("PACT32_DEBUG", "SERVER_AVAILABLE = false");
            mUsers = new ArrayList<>();
            Log.e("PACT32_DEBUG", "Initialisation alternative : mUsers initialisée vide");
        }
        mPrefs = getPreferences(MODE_PRIVATE);
        utAdapter = new UtilisateurAdapter(mUsers,this);
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans onCheckedChanged");
        ListView lv = findViewById(R.id.LU);
        int pos = lv.getPositionForView(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            Utilisateur u = mUsers.get(pos);
            u.setSelected(isChecked);
        }
    }

    private void loadUsers(){
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans loadUsers");
        Gson gson = new Gson();
        String json = mPrefs.getString("mUsers", "");
        Type listType = new TypeToken<ArrayList<Utilisateur>>(){}.getType();
        mUsers = gson.fromJson(json, listType);
    }

    private void saveUsers(){
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans saveUsers");
        //TODO: replace with server-oriented code
        /*SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUsers);
        prefsEditor.putString("mUsers", json);
        prefsEditor.apply();
        */
    }

    private void addUser(Utilisateur user){
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans addUser " + user.toString());
        if (mUsers==null){
            mUsers = new ArrayList<>();
        }
        mUsers.add(user);
        this.saveUsers();
    }

    public ArrayList<Utilisateur> getUser () {
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans getUser");
        return mUsers;
    }

    @Override
    public UtilisateurAdapter getUserAdaptateur() {
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans getUserAdaptateur");
        return utAdapter;
    }

    @Override
    public void startNewSession(ArrayList<Utilisateur> users) {
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans startNewSession");
        if (SERVER_AVAILABLE){
            serveur.startNewSession(users);
            Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : Session initialisée");
        }else{
            Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : Commande annulée");
        }
    }

    @Override
    public void endSession() {
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans endSession");
        if (SERVER_AVAILABLE){
            serveur.endSession();
            Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : Session terminée");
        }else{
            Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : Commande annulée");
        }
    }

    // ==================== MENU ====================

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans onNavigationItemSelected : " + item.getItemId());
        Log.i("PACT32_DEBUG", "BackStackCheck (MainActivity): " + mFragmentManager.getBackStackEntryCount() + " entries");
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_users:
                Fragment listeUtilisateurs = new UtilisateursFragment();
                mFragmentManager.popBackStack();
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, listeUtilisateurs)
                        .addToBackStack(null).commit();
                break;
            case R.id.menu_add_user:
                Fragment ajoutUtilisateurFragment = new AjoutUtilisateurFragment();
                mFragmentManager.popBackStack();
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, ajoutUtilisateurFragment)
                        .addToBackStack(null).commit();
                break;
            case R.id.menu_statistiques:
                Fragment statistiques_globales = new Statistiques_globales();
                mFragmentManager.popBackStack();
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, statistiques_globales)
                        .addToBackStack(null).commit();
                break;
            case R.id.menu_params:
                Fragment settings_fragment = new SettingsFragment();
                mFragmentManager.popBackStack();
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, settings_fragment)
                        .addToBackStack(null).commit();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // ==================== CLICK ====================

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans onClick : " + viewId);
        Fragment utilisateursFragment;
        switch (viewId){
            case R.id.demarrer_button:
                utilisateursFragment = new UtilisateursFragment();
                mFragmentManager.popBackStack();
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, utilisateursFragment)
                        .addToBackStack(null).commit();
                break;
            case R.id.enregistrement_button:
                addUser(new Utilisateur(getIntent().getExtras().getString("firstName"), getIntent().getExtras().getString("name"), getIntent().getExtras().getString("group"), getIntent().getExtras().getString("id")));
                utilisateursFragment = new UtilisateursFragment();
                mFragmentManager.popBackStack();
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, utilisateursFragment)
                        .addToBackStack(null).commit();
                break;
            case R.id.bt_photo:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }else{
                        openCamera();
                    }
                }else{
                    openCamera();
                }
            case R.id.session_bt_stop:
                serveur.endSession();
                utilisateursFragment = new UtilisateursFragment();
                mFragmentManager.popBackStack();
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, utilisateursFragment)
                        .addToBackStack(null).commit();
                break;
        }
    }


    // ==================== CAMERA ====================

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans onRequestPermissionsResult : " + requestCode);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }else{
                    Toast.makeText(this, "Permission refusée", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans onActivityResult : " + requestCode + ", " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        /*ImageView imageViewPhoto = findViewById(R.id.imageView_photo);
        if (resultCode == RESULT_OK){
            imageViewPhoto.setImageURI(image_uri);
        }*/
    }

    private void openCamera(){
        Log.i("PACT32_DEBUG", "CheckPoint (MainActivity) : entrée dans openCamera");
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image bracelet");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image d'un bracelet d'identification");
        mImage_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImage_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

}