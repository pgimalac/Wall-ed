package fr.telecom.wall_ed;


import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FragmentManager fragmentManager;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
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
            /*case R.id.enregistrement_button:
                Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show();
                break;*/
        }
    }

}