package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Model.Conge;
import Model.TypeConge;
import View.CongeView;

public class CongeDAOimpl implements CongeDAOI {

    // Insérer un congé

    @Override
    public void inserer(CongeView cv,int employeId) {
        // Vérifier si un employé a été sélectionné
        if (employeId == -1) {
            cv.afficherMessageErreur("Veuillez sélectionner un employé.");
            return;  // Sortir de la méthode si aucun employé n'est sélectionné
        }
        
        // Récupérer le nom de l'employé à partir de la table employe en utilisant l'ID
        String nomEmploye = null;
        String nomRequete = "SELECT nom_employe FROM employe WHERE id_employe = "+employeId+"";
        
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(nomRequete)) {

            // Exécuter la requête et récupérer le nom
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nomEmploye = rs.getString("nom_employe");
            }
            
            // Si le nom est trouvé, insérer le congé dans la table conge
            if (nomEmploye != null) {
                String requete = "INSERT INTO conge(id_employe, nom_employe, nom_type_conge, date_debut, date_fin) VALUES (?, ?, ?, ?, ?)";
                
                try (PreparedStatement insertStmt = conn.prepareStatement(requete)) {
                    insertStmt.setInt(1, employeId);
                    insertStmt.setString(2, nomEmploye);  // Insérer le nom récupéré
                    insertStmt.setString(3, cv.getSelectedTypeConge().name());
                    insertStmt.setDate(4, Date.valueOf(cv.getDateDebut()));  // Conversion de LocalDate en java.sql.Date
                    insertStmt.setDate(5, Date.valueOf(cv.getDateFin()));  // Conversion de LocalDate en java.sql.Date
                    
                    // Exécuter l'insertion
                    insertStmt.executeUpdate();
                }
            } else {
                cv.afficherMessageErreur("Nom de l'employé non trouvé.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    

    // Supprimer un congé par ID
    @Override
    public void supprmier(int id) {
        String requete = "DELETE FROM conge WHERE id_conge = "+id;

        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(requete)) {

            // Exécuter la requête
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Récupérer la liste des congés
    @Override
    public List<Conge> recuperer_liste_conge() {
        List<Conge> listeConge = new ArrayList<>();
        String requete = "SELECT id_conge, id_employe, nom_employe, date_debut, date_fin, nom_type_conge FROM conge";

        try (PreparedStatement stmt = Connexion.getConnexion().prepareStatement(requete);) {
             ResultSet rs = stmt.executeQuery();

            // Parcourir les résultats de la requête
            while (rs.next()) {
                int id_conge = rs.getInt("id_conge");
                int id_employe = rs.getInt("id_employe");
                String nom_employe = rs.getString("nom_employe");
                LocalDate dateDebut = rs.getDate("date_debut").toLocalDate(); // Conversion de java.sql.Date en LocalDate
                LocalDate dateFin = rs.getDate("date_fin").toLocalDate();     // Conversion de java.sql.Date en LocalDate
                TypeConge typeConge = TypeConge.valueOf(rs.getString("nom_type_conge"));

                // Ajouter le congé à la liste
                listeConge.add(new Conge(id_conge, id_employe,nom_employe,dateDebut, dateFin, typeConge));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listeConge;
    }

    //récupérer la liste des types de congés
    @Override
    public List<TypeConge> recuperer_liste_TypeConges(){
        List<TypeConge> l=new ArrayList<>();
        //requete
        String requete="SELECT * FROM typeConge";

        try(PreparedStatement stmt =Connexion.getConnexion().prepareStatement(requete)){
                ResultSet rs= stmt.executeQuery();
                while(rs.next()){
                    String typeDuConge=rs.getString("nom_type_conge");
                    l.add(TypeConge.valueOf(typeDuConge));
                }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
            return l;
    }
    @Override
    public void modifier(CongeView cview, int id_conge) {
        int employeId = cview.getSelectedEmployeId();
        
        // Vérifier si un employé a été sélectionné
        if (employeId == -1) {
            cview.afficherMessageErreur("Veuillez sélectionner un employé.");
            return;
        }
    
        // Requête pour modifier uniquement le congé spécifié par id_conge
        String requete = "UPDATE conge SET id_employe = ?, nom_employe = (SELECT nom FROM employe WHERE id = ?), " +
                         "date_debut = ?, date_fin = ?, nom_type_conge = ? WHERE id_conge = ?";
        
        try (PreparedStatement stmt = Connexion.getConnexion().prepareStatement(requete)) {
            // Paramètres pour la requête
            stmt.setInt(1, employeId);  // ID de l'employé
            stmt.setInt(2, employeId);  // ID de l'employé pour récupérer le nom
            stmt.setString(3, cview.getDateDebut().toString());  // Date de début
            stmt.setString(4, cview.getDateFin().toString());  // Date de fin
            stmt.setString(5, cview.getSelectedTypeConge().name());  // Type de congé
            stmt.setInt(6, id_conge);  // Condition WHERE sur l'ID du congé à modifier
            
            // Exécuter la requête
            int lignesAffectees = stmt.executeUpdate();
            
            if (lignesAffectees > 0) {
                cview.afficherMessageSucces("Congé modifié avec succès.");
            } else {
                cview.afficherMessageErreur("Aucun congé trouvé avec cet ID.");
            }
        } catch (SQLException ex) {
            cview.afficherMessageErreur("Erreur lors de la modification du congé.");
            ex.printStackTrace();
        }
    }
    
    
}
