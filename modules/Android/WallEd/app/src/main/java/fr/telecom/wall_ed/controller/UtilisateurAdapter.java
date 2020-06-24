package fr.telecom.wall_ed.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import fr.telecom.wall_ed.R;
import fr.telecom.wall_ed.model.Utilisateur;

/**
 * Adaptateur entre la liste des utilisateurs et l'affichage de celle-ci
 */

public class UtilisateurAdapter extends ArrayAdapter<Utilisateur> {

    private List<Utilisateur> listeUtilisateurs ;
    private Context context ;

    public UtilisateurAdapter(List<Utilisateur> listeUtilisateurs, Context context) {
        super(context, R.layout.single_listview_item, listeUtilisateurs);
        Log.i("PACT32_DEBUG", "CheckPoint (UtilisateurAdaptater) : UtilisateurAdapter");
        this.listeUtilisateurs = listeUtilisateurs ;
        this.context = context ;
    }

    public static class UtilisateurHolder {
        public TextView userName ;
        public TextView userSurname ;
        public TextView userClasse ;
        public CheckBox userCheckBox ;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Log.i("PACT32_DEBUG", "CheckPoint (UtilisateurAdaptater) : getView");
        View v = convertView ;
        UtilisateurHolder holder = new UtilisateurHolder() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.single_listview_item, null);
        holder.userName = (TextView) v.findViewById(R.id.name_txt);
        holder.userSurname = (TextView) v.findViewById(R.id.surname_txt);
        holder.userClasse = (TextView) v.findViewById(R.id.classe_txt);
        holder.userCheckBox = (CheckBox) v.findViewById(R.id.checkBox);

        holder.userCheckBox.setOnCheckedChangeListener((MainActivity) context);

        Utilisateur u = listeUtilisateurs.get(position);
        holder.userName.setText(u.getPrenom());
        holder.userSurname.setText(u.getNom());
        holder.userClasse.setText(u.getClasse());
        holder.userCheckBox.setTag(u);

        return v ;
    }

}
