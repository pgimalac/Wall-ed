package fr.telecom.pact32.wall_ed.controller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.telecom.pact32.wall_ed.R;

public class AjoutUtilisateur extends AppCompatActivity {

    private TextView enteteText ;
    private EditText prenomInput ;
    private EditText nomInput ;
    private EditText classeInput ;
    private Button okEnregistrer ;
    private ImageButton prendrePhoto ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_utilisateur);

        enteteText = (TextView) findViewById(R.id.textView2);
        prenomInput = (EditText) findViewById(R.id.prenom_txt);
        nomInput = (EditText) findViewById(R.id.Nom_txt);
        classeInput = (EditText) findViewById(R.id.classe_txt);
        okEnregistrer = (Button) findViewById(R.id.enregistrement_button);
        prendrePhoto = (ImageButton) findViewById(R.id.imageButton3);

    }
}
