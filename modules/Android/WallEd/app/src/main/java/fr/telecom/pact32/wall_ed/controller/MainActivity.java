package fr.telecom.pact32.wall_ed.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import fr.telecom.pact32.wall_ed.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView bienvenuetxt;
    private Button demarrerbtn ;
    private ImageView logoimage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bienvenuetxt = (TextView) findViewById(R.id.bienvenue_txt);
        demarrerbtn = (Button) findViewById(R.id.demarrer_button);
        logoimage = (ImageView) findViewById(R.id.logo_imageView);

        demarrerbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, ListeUtilisateurs.class);
        startActivity(intent);
    }

}
