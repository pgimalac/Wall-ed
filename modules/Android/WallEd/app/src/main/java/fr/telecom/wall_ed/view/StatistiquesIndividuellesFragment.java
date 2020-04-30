package fr.telecom.wall_ed.view;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.telecom.wall_ed.R;
import fr.telecom.wall_ed.model.InterfaceStatsMaster;


public class StatistiquesIndividuellesFragment extends Fragment {

    public TextView prenomJoueur ;
    public TextView nombreDechetIndividuel ;
    public TextView poubelleNormale ;
    public TextView poubelleCarton ;
    public TextView poubellePlastique;
    public TextView poubelleMetal ;
    public TextView poubelleVerte ;
    public TextView tauxReussite ;

    public StatistiquesIndividuellesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        Log.i("PACT32_DEBUG", "CheckPoint (StatistiquesIndividuellesFragment) : attached");
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
