package fr.telecom.wall_ed.model;

public class Dechet {

    private final int dechetID;
    private final int braceletID;
    private final String type;
    private final String typePropose;
    private final boolean reponseEleve;
    private final String heureRamassage;

    public Dechet(int dechetID, int braceletID, String type, String typePropose, boolean reponseEleve, String heureRamassage) {
        this.dechetID = dechetID ;
        this.braceletID = braceletID ;
        this.type = type ;
        this.typePropose = typePropose ;
        this.heureRamassage = heureRamassage ;
        this.reponseEleve = reponseEleve ;
    }

    public int getDechetID() {
        return this.dechetID;
    }

    public int getBraceletID() {
        return this.braceletID;
    }

    public String getType() {
        return this.type;
    }

    public String getTypeEleve() {
        return this.typePropose;
    }

    public String getHeureRamassage() {
        return this.heureRamassage;
    }

    public boolean getReponseEleve() { return this.reponseEleve; }
}
