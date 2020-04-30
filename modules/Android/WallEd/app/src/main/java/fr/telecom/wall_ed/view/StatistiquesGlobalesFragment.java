package fr.telecom.wall_ed.view;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.telecom.wall_ed.R;
import fr.telecom.wall_ed.model.InterfaceStatsMaster;


public class StatistiquesGlobalesFragment extends Fragment {

    private InterfaceStatsMaster mStatsMaster;

    public StatistiquesGlobalesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        Log.i("PACT32_DEBUG", "CheckPoint (StatistiquesGlobalesFragment) : attached");
        super.onAttach(context);
        createCallbackToParentActivity();
    }

    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            mStatsMaster = (InterfaceStatsMaster) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " InterfaceStatsMaster");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistiques_globales4, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("PACT32_DEBUG", "CheckPoint (StatistiquesGlobalesFragment) : detached");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("PACT32_DEBUG", "CheckPoint (StatistiquesGlobalesFragment) : destroyed");
    }

}
