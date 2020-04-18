package fr.telecom.wall_ed.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.telecom.wall_ed.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatistiquesIndividuellesFragment extends Fragment {


    public StatistiquesIndividuellesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistiques_individuelles2, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("PACT32_DEBUG", "CheckPoint (StatistiquesIndividuellesFragment) : detached");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("PACT32_DEBUG", "CheckPoint (StatistiquesIndividuellesFragment) : destroyed");
    }

}
