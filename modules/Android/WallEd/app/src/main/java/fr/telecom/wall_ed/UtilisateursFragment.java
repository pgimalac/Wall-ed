package fr.telecom.wall_ed;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class UtilisateursFragment extends Fragment   {

    ListView mListView ;
    private Button mBtNewUser;
    private ListView mListeUtilisateurs;

    public UtilisateursFragment() {
        // Required empty public constructor
    }

    private String[] prenoms = new String[]{
            "Victor", "Pierre", "Nicolas", "Adrien", "Romain", "Florian",
            "Lola"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View result = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = (ListView) result.findViewById(R.id.listViewUtilisateurs);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity(),
                android.R.layout.simple_list_item_1, prenoms);
        mListView.setAdapter(adapter);
        return result;
    }

}






