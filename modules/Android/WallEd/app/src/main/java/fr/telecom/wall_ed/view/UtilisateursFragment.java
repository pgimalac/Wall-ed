package fr.telecom.wall_ed.view;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import fr.telecom.wall_ed.model.InterfaceServeur;
import fr.telecom.wall_ed.model.Utilisateur;
import fr.telecom.wall_ed.R;
import fr.telecom.wall_ed.model.InterfaceGestionUtilisateurs;


public class UtilisateursFragment extends Fragment  implements View.OnClickListener, View.OnLongClickListener {

    private ListView mListView ;
    private InterfaceGestionUtilisateurs mCallBackUtilisateur ;
    private InterfaceServeur mCallbackServeur;

    private ListView lv ;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createCallbackToParentActivity() ;
    }

    private void createCallbackToParentActivity() {
        try {
            mCallBackUtilisateur = (InterfaceGestionUtilisateurs) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ "InterfaceGestionUtilisateurs");
        }
        try {
            mCallbackServeur = (InterfaceServeur) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ "InterfaceServeur");
        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_utilisateurs, container, false);

        ListView listView = result.findViewById(R.id.LU);
        listView.setAdapter(mCallBackUtilisateur.getUserAdaptateur());

        Button button1 = result.findViewById(R.id.users_start_new_session);
        button1.setOnClickListener(this);

        Button button2 = result.findViewById(R.id.users_end_session);
        button2.setOnClickListener(this);

        //displayListeUtilisateurs();

        return result;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.users_start_new_session:
                mCallbackServeur.startNewSession(mCallBackUtilisateur.getUser());
                break;
            case R.id.users_end_session:
                mCallbackServeur.endSession();
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    private void displayListeUtilisateurs() {
        ArrayList<Utilisateur> LU = mCallBackUtilisateur.getUser();
        LU.add(new Utilisateur("Masiak", "Victor", "CP", "0"));
        LU.add(new Utilisateur("Maes", "Adrien", "CE1", "0"));
        LU.add(new Utilisateur("Louvet", "Romain", "CE2", "0"));
        LU.add(new Utilisateur("Dufourt", "Jean-claude", "CM1", "0"));
    }
}



