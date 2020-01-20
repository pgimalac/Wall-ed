package fr.telecom.wall_ed.controller;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
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

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Type;
import java.util.ArrayList;

import fr.telecom.pact32.wall_ed.model.Utilisateur;
import fr.telecom.wall_ed.view.AjoutUtilisateurFragment;
import fr.telecom.wall_ed.view.MainFragment;
import fr.telecom.wall_ed.R;
import fr.telecom.wall_ed.view.Statistiques_globales;
import fr.telecom.wall_ed.view.UtilisateursFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FragmentManager fragmentManager = null;
    private AppBarConfiguration mAppBarConfiguration = null;
    private ArrayList<fr.telecom.pact32.wall_ed.model.Utilisateur> mUsers = null;
    private SharedPreferences mPrefs = null;

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

        NavigationView navigationView = findViewById(R.id.nav_view);1
        navigationView.setNavigationItemSelectedListener(this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_users)
                .setDrawerLayout(drawer)
                .build();

        MainFragment mainFragment = new MainFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_frame_layout, mainFragment)
                .addToBackStack(null).commit();

        mPrefs = getPreferences(MODE_PRIVATE);
        loadUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }*/

    private void loadUsers(){
        Gson gson = new Gson();
        String json = mPrefs.getString("mUsers", "");
        Type listType = new TypeToken<ArrayList<fr.telecom.pact32.wall_ed.model.Utilisateur>>(){}.getType();
        mUsers = gson.fromJson(json, listType);
    }

    private void saveUsers(){
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUsers);
        prefsEditor.putString("mUsers", json);
        prefsEditor.apply();
    }

    private void addUser(fr.telecom.pact32.wall_ed.model.Utilisateur user){
        if (mUsers==null){
            mUsers = new ArrayList<>();
        }
        mUsers.add(user);
        this.saveUsers();
    }

    public ArrayList<Utilisateur> getUsers(){
        return mUsers;
    }

    // ==================== MENU ====================

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_users:
                Fragment listeUtilisateurs = new UtilisateursFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, listeUtilisateurs)
                        .addToBackStack(null).commit();
                break;
            case R.id.menu_add_user:
                Fragment ajoutUtilisateurFragment = new AjoutUtilisateurFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, ajoutUtilisateurFragment)
                        .addToBackStack(null).commit();
                break;
            case R.id.menu_statistiques:
                Fragment statistiques_globales = new Statistiques_globales();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, statistiques_globales)
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
        switch (viewId){
            case R.id.demarrer_button:
                Fragment utilisateursFragment = new UtilisateursFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_frame_layout, utilisateursFragment)
                        .addToBackStack(null).commit();
                break;
            case R.id.enregistrement_button:
                addUser(new Utilisateur(getIntent().getExtras().getString("firstName"), getIntent().getExtras().getString("name"), getIntent().getExtras().getString("group"), "0"));
                break;
        }
    }

}