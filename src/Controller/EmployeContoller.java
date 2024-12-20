
package Controller;

import Model.*;
import View.EmployeView;

public class EmployeContoller {
        //attributs
        private EmployeModel model;
        private EmployeView view;

        //Constructeur
        public EmployeContoller(EmployeModel m,EmployeView v){
            this.model=m;
            this.view=v;
            this.view.ajouter.addActionListener(aj ->ajouterEmploye());
            this.view.modifier.addActionListener(modif ->modifierEmploye());
           this.view.supprimer.addActionListener(s -> supprimerEmployeParSonId());
           this.view.afficher.addActionListener(af -> afficherEmploye());
        }

        //ajouterEmploye()
        public void ajouterEmploye(){
            String n=view.getNom();
            String p=view.getPrenom();
            String e=view.getEmail();
            String t=view.getTelephone();
            Role role=view.getRole();
            Poste poste=view.getPoste();
            double s; 

            try {
                s=view.getSalaire();
            } catch (NumberFormatException ex) {
                // TODO: handle exception
                view.afficherMessageErreur("Le salaire doit etre un nombre valide");
                return;
            } 
            //Une variable pour vérifier la validité des informations
            boolean ajoutReussi=model.ajouterEmploye(n,p,e,t,s,role,poste);

            if(ajoutReussi){
                view.afficherMessageSucces("Employe ajoute avec succes !");
            }else{
                view.afficherMessageErreur("Echec de l'ajout de l'employe. Verifiez les donnees.");
            }
        }

        //modifier un employe
        public void modifierEmploye(){
            //récupérer l'id de l'employé sélectionné au niveau de view
            int id=view.getSelectedEmployeeId();

            //modifier l'employe grace à la méthode modifierEmployeParSonId(int id) du model
            boolean modificationReussi=model.modifierEmployeParSonId(id,view);

            //messages pour informer sur l'etat de la modification
            if(modificationReussi){
                view.afficherMessageSucces("MOdifie");
            }else{
                view.afficherMessageErreur("Erreur de modification");
            }
        }

        //supprimer l'employé dont l'id a été retourné
        public void supprimerEmployeParSonId(){
            //récupérer l'id au niveau de view
            int id=view.getSelectedEmployeeId();

            //supprimer l'employe grace à la méthode supprimerEmployeParSonId(int id) du model
            boolean suppressionReussie=model.supprimerEmployeParSonId(id);

            //messages pour informer de la suppression
           if(suppressionReussie){
             view.afficherMessageSucces("Supprime");
           }else{
            view.afficherMessageErreur("Erreur de supression");
           }
        }

        //afficher les employés
        public void afficherEmploye(){
                view.afficher();
        }

        
        
}
