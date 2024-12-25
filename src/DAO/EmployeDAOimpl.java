
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import Model.Employe;
import Model.Role;
import View.EmployeView;
import Model.Poste;

public class EmployeDAOimpl implements EmployeDAOI {

    // Insérer un employé
    @Override
    public void inserer(Employe e) {
        // Requête d'insertion
        String requete = "INSERT INTO employe(nom_employe, prenom_employe, email_employe, telephone_employe, salaire_employe, nom_role, nom_poste) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Préparer et exécuter la requête
        try (PreparedStatement stmt = Connexion.getConnexion().prepareStatement(requete)) {
            stmt.setString(1, e.getNom());
            stmt.setString(2, e.getPrenom());
            stmt.setString(3, e.getEmail());
            stmt.setString(4, e.getTelephone());
            stmt.setDouble(5, e.getSalaire());
            stmt.setString(6, e.getRole());
            stmt.setString(7, e.getPoste());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Modifier un employé
    @Override
    public void modifier(EmployeView eview) {
        // Requête de mise à jour en utilisant l'ID comme identifiant unique
        String requete = "UPDATE employe SET nom_employe = ?, prenom_employe = ?, email_employe = ?, telephone_employe = ?, salaire_employe = ?, nom_role = ?, nom_poste = ? WHERE id_employe = ?";

        // Préparer et exécuter la requête
        try (PreparedStatement stmt = Connexion.getConnexion().prepareStatement(requete)) {
            stmt.setString(1, eview.getNom());
            stmt.setString(2, eview.getPrenom());
            stmt.setString(3, eview.getEmail());
            stmt.setString(4, eview.getTelephone());
            stmt.setDouble(5, eview.getSalaire());
            stmt.setString(6, eview.getRole().name());
            stmt.setString(7, eview.getPoste().name());
            stmt.setInt(8, eview.getSelectedEmployeeId()); // Utilisation de l'ID comme identifiant
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Supprimer un employé
    @Override
    public void supprimer(int id) {
        // Requête de suppression en utilisant l'ID comme identifiant unique
        String requete = "DELETE FROM employe WHERE id_employe = ?";

        // Préparer et exécuter la requête
        try (PreparedStatement stmt = Connexion.getConnexion().prepareStatement(requete)) {
            stmt.setInt(1, id); // Utilisation de l'ID comme identifiant
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Afficher la liste des employés
    @Override
    public List<Employe> recuperer_liste_employe() {
        // Requête pour tout sélectionner
        String requete = "SELECT * FROM employe";

        // Créer une liste pour stocker toutes les lignes de la BDD
        List<Employe> l = new ArrayList<>();

        // Préparer et exécuter la requête
        try (PreparedStatement stmt = Connexion.getConnexion().prepareStatement(requete)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_employe");
                String nom = rs.getString("nom_employe");
                String prenom = rs.getString("prenom_employe");
                String email = rs.getString("email_employe");
                String telephone = rs.getString("telephone_employe");
                double salaire = rs.getDouble("salaire_employe");
                Role role = Role.valueOf(rs.getString("nom_role"));
                Poste poste = Poste.valueOf(rs.getString("nom_poste"));

                // Ajouter un nouvel Employé à la liste
                l.add(new Employe(id, nom, prenom, email, telephone, salaire, role, poste));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return l;
    }

    // Méthode pour récupérer les rôles
    @Override
    public List<Role> findAllRole() {
        List<Role> roles = new ArrayList<>();
        //requete pour sélectionner les valeurs depuis la base de données
        String requete="SELECT * FROM role";
        try(PreparedStatement stmt = Connexion.getConnexion().prepareStatement(requete)) {
            ResultSet rs=stmt.executeQuery();
            while (rs.next()) {
                String nom_role=rs.getString("nom_role");
                roles.add(Role.valueOf(nom_role));
            }
        } catch (SQLException ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        return roles;
    }

    // Méthode pour récupérer les rôles
    @Override
    public List<Poste> findAllPoste() {
        List<Poste> postes = new ArrayList<>();
        //requete pour sélectionner les valeurs depuis la base de données
        String requete="SELECT * FROM poste";
        try(PreparedStatement stmt = Connexion.getConnexion().prepareStatement(requete)) {
            ResultSet rs=stmt.executeQuery();
            while (rs.next()) {
                String nom_poste=rs.getString("nom_poste");
                postes.add(Poste.valueOf(nom_poste));
            }
        } catch (SQLException ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        return postes;
    }

    
}
