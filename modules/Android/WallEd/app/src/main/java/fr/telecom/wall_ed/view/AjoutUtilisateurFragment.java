package fr.telecom.wall_ed.view;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import fr.telecom.wall_ed.R;
import fr.telecom.wall_ed.model.Eleve;
import fr.telecom.wall_ed.model.Utilisateur;

/**
 * Ce fragment gère les ajoûts des élèves : chaque élève doit renseigner leur nom, prénom, classe et
 * prendre une photo de leur bracelet qui permettra de les identifier auprès du robot.
 */

public class AjoutUtilisateurFragment extends Fragment implements View.OnClickListener {

    private View.OnClickListener mOnClickListenerCallback;

    private Button mAjoutButton;
    private Button mPhotoButton;
    private EditText mPrenomEditText;
    private EditText mNomEditText;
    private EditText mClasseEditText;
    private EditText mIdEditText;
    private String prenom="";
    private String nom="";
    private String classe="";
    private String id="";

    public AjoutUtilisateurFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("PACT32_DEBUG", "CheckPoint (AjoutUtilisateurFragment) : entrée dans onCreateView");
        View result = inflater.inflate(R.layout.fragment_ajout_utilisateur, container, false);

        mAjoutButton = result.findViewById(R.id.enregistrement_button);
        mAjoutButton.setOnClickListener(this);

        mPhotoButton = result.findViewById(R.id.bt_photo);
        mPhotoButton.setOnClickListener(this);

        mPrenomEditText = result.findViewById(R.id.prenom_txt);
        mNomEditText = result.findViewById(R.id.nom_txt);
        mClasseEditText = result.findViewById(R.id.classe_txt);
        mIdEditText = result.findViewById(R.id.id_txt);

        mPrenomEditText.setText(prenom);
        mNomEditText.setText(nom);
        mClasseEditText.setText(classe);
        mIdEditText.setText(id);

        return result;
    }

    @Override
    public void onAttach(Context context) {
        Log.i("PACT32_DEBUG", "CheckPoint (AjoutUtilisateurFragment) : entrée dans onAttach");
        super.onAttach(context);
        createCallbackToParentActivity();
    }
    
    public void preset(Utilisateur utilisateur){
        prenom = utilisateur.getPrenom();
        nom = utilisateur.getNom();
        classe = utilisateur.getClasse();
        id = utilisateur.getId();
    }

    @Override
    public void onClick(View v) {
        Log.i("PACT32_DEBUG", "CheckPoint (AjoutUtilisateurFragment) : entrée dans onClick (" + v.getId() + ")");

        switch (v.getId()){
            case R.id.enregistrement_button:
                getActivity().getIntent().putExtra("firstName", mPrenomEditText.getText().toString());
                getActivity().getIntent().putExtra("name", mNomEditText.getText().toString());
                getActivity().getIntent().putExtra("group", mClasseEditText.getText().toString());
                getActivity().getIntent().putExtra("id", mIdEditText.getText().toString());
                mOnClickListenerCallback.onClick(v);
                break;
            case R.id.bt_photo:
                mOnClickListenerCallback.onClick(v);
                break;
        }
    }

    private void createCallbackToParentActivity(){
        Log.i("PACT32_DEBUG", "CheckPoint (AjoutUtilisateurFragment) : entrée dans createCallbackToParentActivity");
        try {
            //Parent activity will automatically subscribe to callback
            mOnClickListenerCallback = (View.OnClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " View.OnClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("PACT32_DEBUG", "CheckPoint (AjoutUtilisateurFragment) : detached");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("PACT32_DEBUG", "CheckPoint (AjoutUtilisateurFragment) : destroyed");
    }

}
