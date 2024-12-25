
package View;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import Model.Employe;
import DAO.EmployeDAOimpl;
import Model.Poste;
import Model.Role;

public class EmployeView extends JFrame {
    // *****************************************************************************Attributs*******************************************************
    JLabel Lnom = new JLabel("Nom: ");
    JLabel Lprenom = new JLabel("Prenom: ");
    JLabel Lemail = new JLabel("Email: ");
    JLabel Ltelephone = new JLabel("Telephone: ");
    JLabel Lsalaire = new JLabel("Salaire: ");
    JLabel Lrole = new JLabel("Role: ");
    JLabel Lposte = new JLabel("Poste: ");

    // Champs de saisie
    JTextField saisirNom = new JTextField();
    JTextField saisirPrenom = new JTextField();
    JTextField saisirEmail = new JTextField();
    JTextField saisirTelephone = new JTextField();
    JTextField saisirSalaire = new JTextField();

    // Récupérer la liste des postes et des rôles
    EmployeDAOimpl dao = new EmployeDAOimpl();
    List<Role> rolesList = dao.findAllRole();
    List<Poste> postesList = dao.findAllPoste();

    // ComboBox pour les rôles et postes
    JComboBox<Role> listeRoles = new JComboBox<>(rolesList.toArray(new Role[0]));
    JComboBox<Poste> listePostes = new JComboBox<>(postesList.toArray(new Poste[0]));

    // JTable
    DefaultTableModel tableModel;
    JTable table;
    JScrollPane scrollPane;

    // Boutons
    public JButton ajouter = new JButton("Ajouter");
    public JButton modifier = new JButton("Modifier");
    public JButton supprimer = new JButton("Supprimer");
    public JButton afficher = new JButton("Afficher");

    // Panneaux
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    public JPanel p3 = new JPanel(); // Panneau contenant la table
    JPanel p4 = new JPanel();

    boolean isTableCollapsed = false; // Variable pour suivre l'état de la table
    //*****************************************************************************************Fin attributs*****************************************

    // **************************************************Méthode pour afficher ou replier la table********************************
    public void afficherTableau() {
        // Créer la liste des colonnes pour le tableau
        String[] colonnes = {"ID", "Nom", "Prenom", "Email", "Telephone", "Salaire", "Role", "Poste"};

        // Créer un objet de type EmployeDAOimpl pour récupérer la liste des employés
        EmployeDAOimpl dao = new EmployeDAOimpl();
        List<Employe> employes = dao.recuperer_liste_employe();

        // Remplir les données du tableau
        Object[][] data = new Object[employes.size()][colonnes.length];
        for (int i = 0; i < employes.size(); i++) {
            Employe emp = employes.get(i);
            data[i][0] = emp.getId();
            data[i][1] = emp.getNom();
            data[i][2] = emp.getPrenom();
            data[i][3] = emp.getEmail();
            data[i][4] = emp.getTelephone();
            data[i][5] = emp.getSalaire();
            data[i][6] = emp.getRole(); // Afficher le nom du rôle
            data[i][7] = emp.getPoste(); // Afficher le nom du poste
        }

        // Créer le modèle de table et la table à chaque fois
        tableModel = new DefaultTableModel(data, colonnes);
        table = new JTable(tableModel);

        // Initialiser le JScrollPane avec la table
        scrollPane = new JScrollPane(table);

        // Masquer les colonnes "Telephone", "Role" et "Poste" (index 4, 6 et 7)
        table.getColumnModel().getColumn(4).setMinWidth(0);  // Masquer la colonne "Poste"
        table.getColumnModel().getColumn(4).setMaxWidth(0);  // Masquer la colonne "Poste"
        table.getColumnModel().getColumn(6).setMinWidth(0);  // Masquer la colonne "Role"
        table.getColumnModel().getColumn(6).setMaxWidth(0);  // Masquer la colonne "Role"
        table.getColumnModel().getColumn(7).setMinWidth(0);  // Masquer la colonne "Poste"
        table.getColumnModel().getColumn(7).setMaxWidth(0);  // Masquer la colonne "Poste"

        // Ajouter la table dans p3
        p3.removeAll();  // Supprimer tout le contenu actuel
        JLabel tableTitle = new JLabel("Liste des employés");
        p3.setLayout(new BorderLayout());
        p3.add(tableTitle, BorderLayout.NORTH);
        p3.add(scrollPane, BorderLayout.CENTER);  // Ajouter le JScrollPane avec la table

        // Gérer l'affichage de la table (pliée ou dépliée)
        if (isTableCollapsed) {
            scrollPane.setVisible(false);  // Table repliée
        } else {
            scrollPane.setVisible(true);  // Table dépliée
        }

        // Revalidate pour que les changements prennent effet
        revalidate();
        repaint();
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setFieldsIfClicked();  // Remplit les champs en cas de clic
            }
        });
    } // ***************************************************Méthode pour afficher ou replier la table**************************************************

    // *******************************************************************Constructeur*************************************
    public EmployeView() {
        // p2 (Formulaire de saisie)
        p2.setLayout(new GridLayout(7, 2));
        p2.add(Lnom);
        p2.add(saisirNom);
        p2.add(Lprenom);
        p2.add(saisirPrenom);
        p2.add(Lemail);
        p2.add(saisirEmail);
        p2.add(Ltelephone);
        p2.add(saisirTelephone);
        p2.add(Lsalaire);
        p2.add(saisirSalaire);
        p2.add(Lrole);
        p2.add(listeRoles);
        p2.add(Lposte);
        p2.add(listePostes);

        // p4 (Boutons)
        p4.setLayout(new FlowLayout());
        p4.add(ajouter);
        p4.add(modifier);
        p4.add(supprimer);
        p4.add(afficher);

        // Layout global avec BorderLayout
        setLayout(new BorderLayout());
        add(p2, BorderLayout.NORTH);  // Formulaire en haut
        add(p3, BorderLayout.CENTER);  // Table au centre
        add(p4, BorderLayout.SOUTH);  // Boutons en bas

        setTitle("****Gestion des employés****");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } // **************************************************************************Fin constructeur**************************

    // ***************************************************Méthodes pour récupérer les textes saisis dans les champs
    public String getNom() {
        return saisirNom.getText();
    }

    public String getPrenom() {
        return saisirPrenom.getText();
    }

    public String getEmail() {
        return saisirEmail.getText();
    }

    public String getTelephone() {
        return saisirTelephone.getText();
    }

    public double getSalaire() throws NumberFormatException {
        return Double.parseDouble(saisirSalaire.getText());
    }

    public Role getRole() {
        return (Role) listeRoles.getSelectedItem();
    }

    public Poste getPoste() {
        return (Poste) listePostes.getSelectedItem();
    }
    /*************************************Fin Méthodes pour récupérer les textes saisis dans les champs *********************************/

    // Méthode pour retourner l'id de l'employé sélectionné
    public int getSelectedEmployeeId() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Récupérer l'ID à partir de la colonne 0
            return (int) tableModel.getValueAt(selectedRow, 0);
        } else {
            afficherMessageErreur("Veuillez sélectionner un employé à supprimer.");
            return -1; // Aucun employé sélectionné
        }
    }

    // Méthode pour supprimer un employé
    public void supprimerEmploye() {
        int id = getSelectedEmployeeId();
        if (id != -1) {
            // Ajoutez ici la logique pour supprimer l'employé
            afficherMessageSucces("Employé supprimé avec succès.");
            // Réinitialiser les champs de saisie
            resetFields();
            afficherTableau(); // Mettre à jour la table après la suppression
        }
    }

    // Méthode pour remplir les champs de saisi par les valeurs de l'élément à modifier
    public int setFieldsIfClicked() {
        // Récupérer la ligne sélectionnée dans le tableau
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            // Récupérer les valeurs de la ligne sélectionnée
            String nom = (String) tableModel.getValueAt(selectedRow, 1);
            String prenom = (String) tableModel.getValueAt(selectedRow, 2);
            String email = (String) tableModel.getValueAt(selectedRow, 3);
            String telephone = (String) tableModel.getValueAt(selectedRow, 4);
            double salaire = (Double) tableModel.getValueAt(selectedRow, 5);

            // Initialiser les champs de saisie avec les valeurs récupérées
            saisirNom.setText(nom);
            saisirPrenom.setText(prenom);
            saisirEmail.setText(email);
            saisirTelephone.setText(telephone);
            saisirSalaire.setText(String.valueOf(salaire));
        }
        return selectedRow;
    }

    // Méthode pour réinitialiser les champs de saisie
    public void resetFields() {
        saisirNom.setText("");
        saisirPrenom.setText("");
        saisirEmail.setText("");
        saisirTelephone.setText("");
        saisirSalaire.setText("");
        listeRoles.setSelectedIndex(0); // Réinitialiser la sélection des rôles
        listePostes.setSelectedIndex(0); // Réinitialiser la sélection des postes
    }

    // Méthodes pour afficher les messages d'erreur et de succès
    public void afficherMessageErreur(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void afficherMessageSucces(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}