
package Model;
import DAO.EmployeDAOimpl;
import View.EmployeView;


public class EmployeModel {
    //attribut
    private EmployeDAOimpl dao;

    //constructeur
    public EmployeModel(EmployeDAOimpl dao){this.dao=dao;}

    //Logique Métier
    //On vérifie ici la validité des informations avant ajout
    public boolean ajouterEmploye(String n,String p,String e,String t,double s,Role r,Poste poste){
        if(n==null || p==null || e==null || t==null || s<0 || r==null || poste==null)
            return false;
           
        //Les validations sont correctes donc on crée un Employe
        Employe emp=new Employe(0,n, p, e, t, s, r, poste);
        //et on insere l'employé
        dao.inserer(emp);
        return true;
    }

    //supprimer l'employé dont l'id est passé en paramètre
    public boolean supprimerEmployeParSonId(int id){
        if(id<0)return false;
        dao.supprimer(id);
        return true;
    }

    //modifier l'employé dont l'id est passé en paramètre
    public boolean modifierEmployeParSonId(int id,EmployeView e){
        if(id<0)return false;
        dao.modifier(e);
        return true;
    }
}
