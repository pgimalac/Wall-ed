package fr.telecom.wall_ed.controller;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import fr.telecom.wall_ed.R;

class Utilisateur {
    String name ;
    String surname ;
    String classe ;
    boolean selected = false ;

    public Utilisateur (String name, String surname, String  classe) {
        super();
        this.name = name ;
        this.surname = surname ;
        this.classe = classe ;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTheName() {
        return name;
    }

    public void setTheName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}

public class UtilisateurAdapter extends ArrayAdapter<Utilisateur> {

    private List<Utilisateur> listeUtilisateurs ;
    private Context context ;

    public UtilisateurAdapter(List<Utilisateur> listeUtilisateurs, Context context) {
        super(context, R.layout.single_listview_item, listeUtilisateurs);
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
        View v = convertView ;
        UtilisateurHolder holder = new UtilisateurHolder() ;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.single_listview_item, null);
            holder.userName = (TextView) v.findViewById(R.id.name_txt);
            holder.userSurname = (TextView) v.findViewById(R.id.surname_txt);
            holder.userClasse = (TextView) v.findViewById(R.id.classe_txt);
            holder.userCheckBox = (CheckBox) v.findViewById(R.id.checkBox);

            holder.userCheckBox.setOnCheckedChangeListener((MainActivity) context);

        } else {
            holder = (UtilisateurHolder) v.getTag();
        }

        Utilisateur u = listeUtilisateurs.get(position);
        holder.userName.setText(u.getTheName());
        holder.userSurname.setText(u.getSurname());
        holder.userName.setText(u.getTheName());
        holder.userCheckBox.setTag(u);


        return v ;
    }

}
