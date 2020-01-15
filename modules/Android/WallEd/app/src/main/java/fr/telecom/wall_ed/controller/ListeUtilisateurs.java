/* package fr.telecom.wall_ed.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import fr.telecom.wall_ed.R;
import fr.telecom.pact32.wall_ed.model.Utilisateur;


public class ListeUtilisateurs extends AppCompatActivity implements View.OnClickListener {

    private Button mBtNewUser;
    private ListView mListeUtilisateurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.UtilisateursFragments);

        mBtNewUser = (Button) findViewById(R.id.ListeUtilisateurs_BtNewUser);
        mBtNewUser.setOnClickListener(this);

        mListeUtilisateurs = (ListView) findViewById(R.id.ListeUtilisateurs_ListUsers);
        Utilisateur utilisateur1 = new Utilisateur("Lucas", "Dujardin", "CP3");
        Utilisateur utilisateur2 = new Utilisateur("Sophie", "Verdier", "CP3");
        Utilisateur[] utilisateurs = new Utilisateur[]{utilisateur1, utilisateur2};
        ArrayAdapter<Utilisateur> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, utilisateurs);
        mListeUtilisateurs.setAdapter(arrayAdapter);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ListeUtilisateurs.this, AjoutUtilisateur.class);
        startActivity(intent);
    }



} */