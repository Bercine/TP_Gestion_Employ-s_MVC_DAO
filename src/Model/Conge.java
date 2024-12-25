package Model;
import java.time.LocalDate;

public class Conge { 
    private int id_conge;
    private int id_employe;
    private String nom_employe;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private TypeConge typeConge;

    //setter
    public void  setConge(int id_conge, int id_employe,String nom_employe, LocalDate dateDebut, LocalDate dateFin, TypeConge typeConge) {
        this.id_conge = id_conge;
        this.id_employe = id_employe;
        this.nom_employe=nom_employe;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.typeConge = typeConge;
    }

    //constructeur
    public Conge(int id_conge, int id_employe,String n, LocalDate dateDebut, LocalDate dateFin, TypeConge typeConge){
        this.setConge(id_conge, id_employe,n, dateDebut, dateFin, typeConge);
    }

    // Getters
    public int getId_conge() {
        return id_conge;
    }

    public int getIdEmploye() {
        return id_employe;
    }

    public String getNomEmploye() {
        return nom_employe;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public String getTypeConge() {
        return typeConge.name();
    }

}


