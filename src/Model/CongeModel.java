package Model;


import DAO.CongeDAOimpl;
import View.CongeView;

public class CongeModel {
    private CongeDAOimpl dao;

    public CongeModel(CongeDAOimpl dao){this.dao=dao;}

    //Méthodes pour la logique métier; pour vérifier la validité des informations.

    //vérifier les valeurs avant ajout des congés
    public boolean ajoutConge(CongeView c, int id_employe){
            if(id_employe<0)return false;
            //on appelle le dao pour l'insertion
            dao.inserer(c,id_employe);
        return true;
    }

    //supprimer un congé par son ID
    public boolean supprimerCongeParSonId(int id){
            if(id<0)return false;
            dao.supprmier(id);
            return true;
    }

    //modifier le congé dont l'id est passé en paramètre
    public boolean modifierCongeParSonId(CongeView cv,int id){
        if(id<0)return false;
        dao.modifier(cv,id);
        return true;
    }
}
