package fr.telecom.wall_ed.view;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import fr.telecom.wall_ed.controller.UtilisateurAdapter;
import fr.telecom.wall_ed.model.Utilisateur;
import fr.telecom.wall_ed.R;
import fr.telecom.wall_ed.model.InterfaceGestionUtilisateurs;


public class UtilisateursFragment extends Fragment  implements View.OnClickListener, View.OnLongClickListener {

    private ListView mListView ;
    private InterfaceGestionUtilisateurs mCallBackUtilisateur ;

    private ListView lv ;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createCallbackToParentActivity() ;
    }

    private void createCallbackToParentActivity() {
        try {
            //Parent activity will automatically subscribe to callback
            mCallBackUtilisateur = (InterfaceGestionUtilisateurs) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ "InterfaceGestionUtilisateurs");
        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_utilisateurs, container, false);
        ListView mlistView = result.findViewById(R.id.listViewUtilisateurs);

        displayListeUtilisateurs();

        /*Utilisateur adrien = new Utilisateur("Adrien", "Maes le S", "CP", "id1");
        Utilisateur nicolas = new Utilisateur("Nicolas", "Jow le beau", "CE1", "id2") ;
        Utilisateur jcd = new Utilisateur ("Jean-Claude","Dufourd", "CE2", "id3");

        Utilisateur[] eleves = new Utilisateur[]{adrien, nicolas, jcd} ;

         */

        ArrayAdapter<Utilisateur> arrayAdapter = new ArrayAdapter<Utilisateur>(getContext(), android.R.layout.simple_list_item_1, mCallBackUtilisateur.getUser());

        mlistView.setAdapter(arrayAdapter);

        return result;

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    private void displayListeUtilisateurs() {
        LU = new ArrayList<Utilisateur>();
        LU.add(new Utilisateur("Masiak", "Victor", "CP", ""));
        LU.add(new Utilisateur("Maes", "Adrien", "CE1", ""));
        LU.add(new Utilisateur("Louvet", "Romain", "CE2", ""));
        LU.add(new Utilisateur("Dufourt", "Jean-claude", "CM1", ""));

        
    }

}



