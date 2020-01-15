package fr.telecom.wall_ed;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class AjoutUtilisateurFragment extends Fragment implements View.OnClickListener {

    private View.OnClickListener onClickListenerCallback;

    private Button ajoutButton;
    private EditText prenomEditText;
    private EditText nomEditText;
    private EditText classeEditText;

    public AjoutUtilisateurFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_ajout_utilisateur, container, false);
        ajoutButton = result.findViewById(R.id.enregistrement_button);
        prenomEditText = result.findViewById(R.id.prenom_txt);
        nomEditText = result.findViewById(R.id.Nom_txt);
        classeEditText = result.findViewById(R.id.classe_txt);
        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createCallbackToParentActivity();
    }

    @Override
    public void onClick(View v) {
        onClickListenerCallback.onClick(v);
    }

    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            onClickListenerCallback = (View.OnClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " View.OnClickListener");
        }
    }
}
