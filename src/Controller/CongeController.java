
package Controller;


import Model.*;
import View.CongeView;


public class CongeController {
      //attributs
        private CongeModel cmodel;
        private CongeView cview; 
        private EmployeModel emodel;

    //Constructeur
    public CongeController(CongeModel m,CongeView v){
        this.cmodel=m;
        this.cview=v;
        this.cview.ajouter.addActionListener(aj ->ajouterConge());
        this.cview.modifier.addActionListener(modif ->modifierConge());
       this.cview.supprimer.addActionListener(s -> supprimerCongeParSonId());
       this.cview.afficher.addActionListener(af -> afficherConge());
    }

    //ajouterEmploye()
    public void ajouterConge(){
        int id_employe=cview.getSelectedEmployeId();
        
        //Une variable pour vérifier la validité des informations
        boolean ajoutReussi=cmodel.ajoutConge( cview,id_employe);
        
        if(ajoutReussi){
            cview.afficherMessageSucces("Conge ajoute avec succes !");
        }else{
            cview.afficherMessageErreur("Echec de l'ajout du conge. Verifiez les donnees.");
        }
            afficherConge();
    }

    //modifier un employe
    public void modifierConge(){
        //récupérer l'id du congé sélectionné au niveau de view
        int id=cview.getSelectedCongeId();

        //modifier l'employe grace à la méthode modifierEmployeParSonId(int id) du model
        boolean modificationReussi=cmodel.modifierCongeParSonId(cview,id);

        //messages pour informer sur l'etat de la modification
        if(modificationReussi){
            cview.afficherMessageSucces("Modifie");
        }else{
            cview.afficherMessageErreur("Erreur de modification");
        }
        afficherConge();
    }

    //supprimer l'employé dont l'id a été retourné
    public void supprimerCongeParSonId(){
        //récupérer l'id au niveau de view
        int id=cview.getSelectedCongeId();

        //supprimer l'employe grace à la méthode supprimerEmployeParSonId(int id) du model
        boolean suppressionReussie=cmodel.supprimerCongeParSonId(id);

        //messages pour informer de la suppression
       if(suppressionReussie){
         cview.afficherMessageSucces("Supprime");
       }else{
        cview.afficherMessageErreur("Erreur de supression");
       }

       emodel.recuperer_liste_employe();
       afficherConge();
    }

    //afficher les employés
    public void afficherConge(){
            cview.afficherTableau();
    }

    
     
}