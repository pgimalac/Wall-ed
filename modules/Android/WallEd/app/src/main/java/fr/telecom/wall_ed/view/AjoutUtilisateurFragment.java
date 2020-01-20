package fr.telecom.wall_ed.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.BitSet;

import fr.telecom.wall_ed.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AjoutUtilisateurFragment extends Fragment implements View.OnClickListener {

    private View.OnClickListener onClickListenerCallback;

    private Button ajoutButton;
    private Button photoButton;
    private EditText prenomEditText;
    private EditText nomEditText;
    private EditText classeEditText;
    private ImageView imageViewPhoto;

    public AjoutUtilisateurFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_ajout_utilisateur, container, false);

        ajoutButton = result.findViewById(R.id.enregistrement_button);
        ajoutButton.setOnClickListener(this);

        photoButton = result.findViewById(R.id.bt_photo);
        photoButton.setOnClickListener(this);

        prenomEditText = result.findViewById(R.id.prenom_txt);
        nomEditText = result.findViewById(R.id.nom_txt);
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

        switch (v.getId()){
            case R.id.enregistrement_button:
                getActivity().getIntent().putExtra("firstName", prenomEditText.getText().toString());
                getActivity().getIntent().putExtra("name", nomEditText.getText().toString());
                getActivity().getIntent().putExtra("group", classeEditText.getText().toString());
                onClickListenerCallback.onClick(v);
                break;
            case R.id.bt_photo:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
        }
    }

    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            onClickListenerCallback = (View.OnClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " View.OnClickListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageViewPhoto.setImageBitmap(bitmap);
    }
}
