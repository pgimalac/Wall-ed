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
        return result ;
    }


}


 






