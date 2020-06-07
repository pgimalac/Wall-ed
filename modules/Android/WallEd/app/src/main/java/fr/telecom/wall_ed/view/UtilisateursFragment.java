package fr.telecom.wall_ed.view;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
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
    private AdapterView.OnItemClickListener mCallbackOnItemClickListener;
    private View.OnClickListener mCallbackOnClickListener;

    @Override
    public void onAttach(Context context) {
        Log.i("PACT32_DEBUG", "CheckPoint (UtilisateursFragment) : attached");
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
            mCallbackOnClickListener = (View.OnClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ "OnClickListener");
        }
        try {
            mCallbackServeur = (InterfaceServeur) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ "InterfaceServeur");
        }
        try {
            mCallbackOnItemClickListener = (AdapterView.OnItemClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ "OnItemClickListener");
        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_utilisateurs, container, false);

        mListView = result.findViewById(R.id.LU);
        mListView.setAdapter(mCallBackUtilisateur.getUserAdaptateur());
        mListView.setOnItemClickListener(mCallbackOnItemClickListener);

        Button button1 = result.findViewById(R.id.users_start_new_session);
        button1.setOnClickListener(this);

        Button button2 = result.findViewById(R.id.users_end_session);
        button2.setOnClickListener(this);

        ImageButton button3 = result.findViewById(R.id.imgBt_utilisateurs_addUser);
        button3.setOnClickListener(this);

        ImageButton button4 = result.findViewById(R.id.imgBt_utilisateurs_modifyUser);
        button4.setOnClickListener(this);

        ImageButton button5 = result.findViewById(R.id.imgBt_utilisateurs_deleteUser);
        button5.setOnClickListener(this);

        addFake();

        return result;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.users_start_new_session:
                mCallbackServeur.startNewSession(mCallBackUtilisateur.getSelectedUser());
                break;
            case R.id.users_end_session:
                mCallbackServeur.endSession();
                break;
            case R.id.imgBt_utilisateurs_addUser:
            case R.id.imgBt_utilisateurs_modifyUser:
            case R.id.imgBt_utilisateurs_deleteUser:
                mCallbackOnClickListener.onClick(v);
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    private void addFake() {
        ArrayList<Utilisateur> LU = mCallBackUtilisateur.getUser();
        if (LU.size() < 1){
            Log.i("PACT32_DEBUG", "(UtilisateursFragment) fake users created");
            mCallBackUtilisateur.addUser(new Utilisateur("Masiak", "Victor", "CP", "0"));
            mCallBackUtilisateur.addUser(new Utilisateur("Maes", "Adrien", "CE1", "1"));
            mCallBackUtilisateur.addUser(new Utilisateur("Louvet", "Romain", "CE2", "2"));
            mCallBackUtilisateur.addUser(new Utilisateur("Dufourt", "Jean-claude", "CM1", "3"));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("PACT32_DEBUG", "CheckPoint (UtilisateursFragment) : detached");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("PACT32_DEBUG", "CheckPoint (UtilisateursFragment) : destroyed");
    }
}



