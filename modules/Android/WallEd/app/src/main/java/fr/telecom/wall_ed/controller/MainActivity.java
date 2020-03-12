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
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.CheckBox;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fr.telecom.wall_ed.model.Utilisateur;
import fr.telecom.wall_ed.model.InterfaceGestionUtilisateurs;
import fr.telecom.wall_ed.model.Serveur;
import fr.telecom.wall_ed.view.AjoutUtilisateurFragment;
import fr.telecom.wall_ed.view.MainFragment;
import fr.telecom.wall_ed.R;
import fr.telecom.wall_ed.view.SettingsFragment;
import fr.telecom.wall_ed.view.Statistiques_globales;
import fr.telecom.wall_ed.view.UtilisateursFragment;


public class MainActivity extends AppCompatActivity implements InterfaceGestionUtilisateurs, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, android.widget.CompoundButton.OnCheckedChangeListener {

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

        serveur = new Serveur();
        mPrefs = getPreferences(MODE_PRIVATE);
        loadUsers();
        //TODO: supprimer la ligne ci-dessus lorsque la ligne ci-dessous aura été implémentée
        //TODO: mUsers = serveur.getUsers();

        utAdapter = new UtilisateurAdapter(mUsers,this);
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ListView lv = findViewById(R.id.LU);
        int pos = lv.getPositionForView(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            Utilisateur u = mUsers.get(pos);
            u.setSelected(isChecked);
            Toast.makeText( this, "clicked on User" + u.getPrenom() + ". State is " + isChecked, Toast.LENGTH_SHORT).show() ;
        }
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

  */

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }*/

    private void loadUsers(){
        Gson gson = new Gson();
        String json = mPrefs.getString("mUsers", "");
        Type listType = new TypeToken<ArrayList<Utilisateur>>(){}.getType();
        mUsers = gson.fromJson(json, listType);
    }

    private void saveUsers(){
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUsers);
        prefsEditor.putString("mUsers", json);
        prefsEditor.apply();
    }

    private void addUser(Utilisateur user){
        if (mUsers==null){
            mUsers = new ArrayList<>();
        }
        mUsers.add(user);
        this.saveUsers();
    }

    public ArrayList<Utilisateur> getUsers(){
        return mUsers;
    }

    public ArrayList<Utilisateur> getUser () {
        return (mUsers);
    }

    @Override
    public UtilisateurAdapter getUserAdaptateur() {
        return utAdapter;
    }

    // ==================== MENU ====================

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_users:
                Fragment listeUtilisateurs = new UtilisateursFragment();
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, listeUtilisateurs)
                        .addToBackStack(null).commit();
                break;
            case R.id.menu_add_user:
                Fragment ajoutUtilisateurFragment = new AjoutUtilisateurFragment();
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, ajoutUtilisateurFragment)
                        .addToBackStack(null).commit();
                break;
            case R.id.menu_statistiques:
                Fragment statistiques_globales = new Statistiques_globales();
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, statistiques_globales)
                        .addToBackStack(null).commit();
                break;
            case R.id.menu_params:
                Fragment settings_fragment = new SettingsFragment();
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
        Fragment utilisateursFragment;
        switch (viewId){
            case R.id.demarrer_button:
                utilisateursFragment = new UtilisateursFragment();
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, utilisateursFragment)
                        .addToBackStack(null).commit();
                break;
            case R.id.enregistrement_button:
                addUser(new Utilisateur(getIntent().getExtras().getString("firstName"), getIntent().getExtras().getString("name"), getIntent().getExtras().getString("group"), "0"));
                utilisateursFragment = new UtilisateursFragment();
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
                mFragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, utilisateursFragment)
                        .addToBackStack(null).commit();
                break;
        }
    }


    // ==================== CAMERA ====================

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
        super.onActivityResult(requestCode, resultCode, data);
        /*ImageView imageViewPhoto = findViewById(R.id.imageView_photo);
        if (resultCode == RESULT_OK){
            imageViewPhoto.setImageURI(image_uri);
        }*/
    }

    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image bracelet");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image d'un bracelet d'identification");
        mImage_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImage_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

}