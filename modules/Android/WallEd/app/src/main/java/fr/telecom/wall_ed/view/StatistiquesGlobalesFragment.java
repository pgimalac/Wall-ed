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


public class StatistiquesGlobalesFragment extends Fragment {

    private InterfaceStatsMaster mStatsMaster;
    public TextView nombreDechet;
    public TextView poubelleNormale ;
    public TextView poubelleCarton ;
    public TextView poubellePlastique;
    public TextView poubelleMetal ;
    public TextView poubelleVerre ;
    public TextView tauxReussite ;

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
        View view = inflater.inflate(R.layout.fragment_statistiques_globales4, container, false);
        nombreDechet = (TextView) view.findViewById(R.id.nbre_dechets_global);
        poubelleNormale = (TextView) view.findViewById(R.id.poubelleNormaleG);
        poubellePlastique = (TextView) view.findViewById(R.id.poubellePlastiqueG);
        poubelleCarton = (TextView) view.findViewById(R.id.poubelleCartonG);
        poubelleVerre = (TextView) view.findViewById(R.id.poubelleVerreG);
        poubelleMetal = (TextView) view.findViewById(R.id.poubelleMétauxG);
        tauxReussite = (TextView) view.findViewById(R.id.tauxRéussiteG);
        updateContent();
        return view;
    }

    private void updateContent(){
        nombreDechet.setText(String.valueOf(mStatsMaster.getTotal()));
        poubelleNormale.setText(String.valueOf(mStatsMaster.getCorrectByType("trash")));
        poubellePlastique.setText(String.valueOf(mStatsMaster.getCorrectByType("plastic")));
        poubelleCarton.setText(String.valueOf(mStatsMaster.getCorrectByType("cardboard")));
        poubelleVerre.setText(String.valueOf(mStatsMaster.getCorrectByType("glass")));
        poubelleMetal.setText(String.valueOf(mStatsMaster.getCorrectByType("metal")));
        tauxReussite.setText(String.valueOf(mStatsMaster.getTotalScore()));
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
