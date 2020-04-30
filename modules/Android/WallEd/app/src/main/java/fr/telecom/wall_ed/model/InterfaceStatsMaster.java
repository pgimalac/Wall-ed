package fr.telecom.wall_ed.model;

public interface InterfaceStatsMaster {

    public int getTotal();
    public int getTotalByStudent(Eleve eleve);
    public int getTotalByType(String type);
    public int getTotalByTypeAndStudent(String type, Eleve eleve);
    public int getCorrect();
    public int getCorrectByStudent(Eleve eleve);
    public int getCorrectByType(String type);
    public int getCorrectByTypeAndStudent(String type, Eleve eleve);

}
