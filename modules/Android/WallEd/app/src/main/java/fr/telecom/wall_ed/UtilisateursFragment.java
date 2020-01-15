package fr.telecom.wall_ed;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import fr.telecom.pact32.wall_ed.model.Utilisateur;

/**
 * A simple {@link Fragment} subclass.
 */
public class UtilisateursFragment extends Fragment  {

    private ListView mListView ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_utilisateurs, container, false);
        ListView mlistView = result.findViewById(R.id.listViewUtilisateurs);

        Utilisateur adrien = new Utilisateur("Adrien", "Maes le S", "CP", "id1");
        Utilisateur nicolas = new Utilisateur("Nicolas", "Jow le beau", "CE1", "id2") ;

        Utilisateur[] eleves = new Utilisateur[]{adrien, nicolas} ;

        ArrayAdapter<Utilisateur> arrayAdapter = new ArrayAdapter<Utilisateur>(getContext(), android.R.layout.simple_list_item_1, eleves);

        mlistView.setAdapter(arrayAdapter);
    }


}









