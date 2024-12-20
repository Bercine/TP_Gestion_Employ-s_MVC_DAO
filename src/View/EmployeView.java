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
import java.awt.FlowLayout;
import java.awt.GridLayout;

import Model.Employe;
import DAO.EmployeDAOimpl;
import Model.Poste;
import Model.Role;

public class EmployeView extends JFrame {
    // Attributs
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
    Role[] roles = Role.values();
    Poste[] postes = Poste.values();

    // ComboBox pour les rôles et postes
    JComboBox<Role> listeRoles = new JComboBox<>(roles);
    JComboBox<Poste> listePostes = new JComboBox<>(postes);

    // JTable
    DefaultTableModel tableModel;
    JTable table;
    JScrollPane scrollPane;  // Déclarer un seul JScrollPane

    // Boutons
    public JButton ajouter = new JButton("Ajouter");
    public JButton modifier = new JButton("Modifier");
    public JButton supprimer = new JButton("Supprimer");
    public JButton afficher = new JButton("Afficher");

    // Panneaux
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    public JPanel p3 = new JPanel();  // Panneau contenant la table
    JPanel p4 = new JPanel();

    boolean isTableCollapsed = false;  // Variable pour suivre l'état de la table

    // Constructeur
    public EmployeView() {
        // p2 - Champs de saisie
        p2.setLayout(new GridLayout(7, 2));
        p2.add(Lnom); p2.add(saisirNom);
        p2.add(Lprenom); p2.add(saisirPrenom);
        p2.add(Lemail); p2.add(saisirEmail);
        p2.add(Ltelephone); p2.add(saisirTelephone);
        p2.add(Lsalaire); p2.add(saisirSalaire);
        p2.add(Lrole); p2.add(listeRoles);
        p2.add(Lposte); p2.add(listePostes);

        // p3 - Contient la table
        p3.setLayout(new GridLayout(2, 1));
        JLabel tableTitle = new JLabel("Liste des employés");  // Titre de la table
        p3.add(tableTitle);

        // p4 - Boutons
        p4.setLayout(new FlowLayout());
        p4.add(ajouter);
        p4.add(modifier);
        p4.add(supprimer);
        p4.add(afficher);

        // Layout global
        setLayout(new GridLayout(3, 1));
        add(p2);
        add(p3);
        add(p4);

        setTitle("****Gestion des employés****");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Actions des boutons
        ajouter.addActionListener(e -> ajouterEmploye());
        modifier.addActionListener(e -> modifierEmploye());
        supprimer.addActionListener(e -> supprimerEmploye());
        afficher.addActionListener(e -> afficher());
    }

    // Méthodes pour récupérer les textes saisis dans les champs
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
        return (Role) (listeRoles.getSelectedItem());
    }

    public Poste getPoste() {
        return (Poste) (listePostes.getSelectedItem());
    }

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

    // Méthode pour afficher ou replier la table
    public void afficher() {
        // Créer la liste des colonnes pour le tableau
        String[] colonnes = {"ID", "Nom", "Prenom", "Email", "Salaire"};

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
            data[i][4] = emp.getSalaire();
        }

        // Créer le modèle de table et la table à chaque fois
        tableModel = new DefaultTableModel(data, colonnes);
        table = new JTable(tableModel);

        // Initialiser le JScrollPane avec la table
        scrollPane = new JScrollPane(table);

        // Ajouter la table dans p3
        p3.removeAll();  // Supprimer tout le contenu actuel
        JLabel tableTitle = new JLabel("Liste des employés");
        p3.add(tableTitle);
        p3.add(scrollPane);  // Ajouter le JScrollPane avec la table

        // Gérer l'affichage de la table (pliée ou dépliée)
        if (isTableCollapsed) {
            scrollPane.setVisible(false);  // Table repliée
        } else {
            scrollPane.setVisible(true);  // Table dépliée
        }

        // Revalidate pour que les changements prennent effet
        revalidate();
        repaint();
    }

    // Méthode pour ajouter un employé
    public void ajouterEmploye() {
        // Ajoutez ici la logique pour ajouter un employé
        // Après l'ajout, on appelle afficher pour mettre à jour la table
        afficherMessageSucces("Employé ajouté avec succès.");
        // Réinitialiser les champs de saisie
        resetFields();
        afficher();  // Mettre à jour la table après l'ajout
    }

    // Méthode pour modifier un employé
    public void modifierEmploye() {
        // Ajoutez ici la logique pour modifier un employé
        // Après la modification, on appelle afficher pour mettre à jour la table
        afficherMessageSucces("Employé modifié avec succès.");
        // Réinitialiser les champs de saisie
        resetFields();
        afficher();  // Mettre à jour la table après la modification
    }

    // Méthode pour supprimer un employé
    public void supprimerEmploye() {
        int id = getSelectedEmployeeId();
        if (id != -1) {
            // Ajoutez ici la logique pour supprimer l'employé
            afficherMessageSucces("Employé supprimé avec succès.");
            // Réinitialiser les champs de saisie
            resetFields();
            afficher();  // Mettre à jour la table après la suppression
        }
    }

    // Méthode pour réinitialiser les champs de saisie
    public void resetFields() {
        saisirNom.setText("");
        saisirPrenom.setText("");
        saisirEmail.setText("");
        saisirTelephone.setText("");
        saisirSalaire.setText("");
        listeRoles.setSelectedIndex(0);  // Réinitialiser la sélection des rôles
        listePostes.setSelectedIndex(0);  // Réinitialiser la sélection des postes
    }

    // Méthodes pour afficher les messages d'erreur et de succès
    public void afficherMessageErreur(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void afficherMessageSucces(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
