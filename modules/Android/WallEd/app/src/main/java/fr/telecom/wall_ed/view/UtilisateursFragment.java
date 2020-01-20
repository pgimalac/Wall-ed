package fr.telecom.wall_ed.view;


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
import fr.telecom.wall_ed.R;
import fr.telecom.wall_ed.model.InterfaceGestionUtilisateurs;

/**
 * A simple {@link Fragment} subclass.
 */
public class UtilisateursFragment extends Fragment  implements View.OnClickListener, View.OnLongClickListener {

    private ListView mListView ;

    private InterfaceGestionUtilisateurs callBackUtilisateur ;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createCallbackToParentActivity() ;
    }

    private void createCallbackToParentActivity() {
        try {
            //Parent activity will automatically subscribe to callback
            callBackUtilisateur = (InterfaceGestionUtilisateurs) getActivity();
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

        ArrayAdapter<Utilisateur> arrayAdapter = new ArrayAdapter<Utilisateur>(getContext(), android.R.layout.simple_list_item_1, callBackUtilisateur.getUser());

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

}



