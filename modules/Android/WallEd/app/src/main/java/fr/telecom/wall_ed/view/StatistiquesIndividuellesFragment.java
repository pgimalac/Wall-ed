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
    public TextView poubelleVerre ;
    public TextView tauxReussite ;
    private InterfaceStatsMaster mStatsMaster;



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
        View result = inflater.inflate(R.layout.fragment_statistiques_individuelles2, container, false);
        prenomJoueur = result.findViewById(R.id.textView2) ;
        nombreDechetIndividuel = result.findViewById(R.id.Nbre_dechets_individuel) ;
        poubelleNormale = result.findViewById(R.id.PoubelleNormale) ;
        poubelleCarton  = result.findViewById(R.id.PoubelleCarton) ;
        poubellePlastique = result.findViewById(R.id.Poubelleplastique) ;
        poubelleMetal = result.findViewById(R.id.Poubellemétaux) ;
        poubelleVerre = result.findViewById(R.id.Poubelleverre) ;
        tauxReussite  = result.findViewById(R.id.TauxReussite) ;
        return result ;
    }

    public void MAJ (TextView prenomJoueur, TextView nombreDechetIndividuel, TextView poubelleNormale, TextView poubelleCarton, TextView poubellePlastique, TextView poubelleMetal, TextView poubelleVerre, TextView tauxReussite ) {
        

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
