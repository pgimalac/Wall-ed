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


public class AjoutUtilisateurFragment extends Fragment implements View.OnClickListener {

    private View.OnClickListener mOnClickListenerCallback;

    private Button mAjoutButton;
    private Button mPhotoButton;
    private EditText mPrenomEditText;
    private EditText mNomEditText;
    private EditText mClasseEditText;

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
        return result;
    }

    @Override
    public void onAttach(Context context) {
        Log.i("PACT32_DEBUG", "CheckPoint (AjoutUtilisateurFragment) : entrée dans onAttach");
        super.onAttach(context);
        createCallbackToParentActivity();
    }

    @Override
    public void onClick(View v) {
        Log.i("PACT32_DEBUG", "CheckPoint (AjoutUtilisateurFragment) : entrée dans onClick (" + v.getId() + ")");

        switch (v.getId()){
            case R.id.enregistrement_button:
                getActivity().getIntent().putExtra("firstName", mPrenomEditText.getText().toString());
                getActivity().getIntent().putExtra("name", mNomEditText.getText().toString());
                getActivity().getIntent().putExtra("group", mClasseEditText.getText().toString());
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


}
