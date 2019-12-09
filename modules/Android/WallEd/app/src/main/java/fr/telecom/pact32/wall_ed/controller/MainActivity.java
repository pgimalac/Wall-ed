package fr.telecom.pact32.wall_ed.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import fr.telecom.pact32.wall_ed.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent gameActivity = new Intent(MainActivity.this, ListeUtilisateurs.class);
        startActivity(gameActivity);
    }
}
