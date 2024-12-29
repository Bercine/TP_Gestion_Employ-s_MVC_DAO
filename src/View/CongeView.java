package View;

import java.util.List;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.ZoneId;

import DAO.CongeDAOimpl;
import DAO.EmployeDAOimpl;
import Model.TypeConge;
import Model.Conge;
import Model.Employe;

public class CongeView extends JFrame {
    
    // Attributs
    
    // JLabels
    JLabel Lnom = new JLabel("Nom de l'employe: ");
    JLabel Ltype = new JLabel("Type: ");
    JLabel Ldate_debut = new JLabel("Date de debut: ");
    JLabel Ldate_fin = new JLabel("Date de fin: ");

    // JButtons
    public JButton ajouter = new JButton("Ajouter");
    public JButton supprimer = new JButton("Supprimer");
    public JButton modifier = new JButton("Modifier");
    public JButton afficher = new JButton("Afficher");

    // JPanels
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();

    // Un nouveau CongeDAOimpl
    CongeDAOimpl cdao = new CongeDAOimpl();
    List<TypeConge> TypeCongeListe = cdao.recuperer_liste_TypeConges();
    
    // JComboBox
    JComboBox<TypeConge> listeTypes = new JComboBox<>(TypeCongeListe.toArray(new TypeConge[0]));

    // Un nouveau EmployeDAOimpl
    EmployeDAOimpl dao = new EmployeDAOimpl();
    List<Employe> EmployeListe = dao.recuperer_liste_employe();
    
    // JComboBox
    JComboBox<String> listeEmployes = new JComboBox<>();  // Utilisation de String pour les noms des employés

    // JTable
    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;

    // JSpinners pour la sélection des dates
    JSpinner dateDebutSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
    JSpinner dateFinSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));

    boolean isTableCollapsed = false; // Variable pour suivre l'état de la table

    public void afficherTableau() {
        // Créer la liste des colonnes pour le tableau
        String[] colonnes = {"ID", "Employé", "Date de debut", "Date de fin", "Type"};

        // Créer un objet de type CongeDAOimpl pour récupérer la liste des congés
        List<Conge> conges = cdao.recuperer_liste_conge();

        // Remplir les données du tableau
        Object[][] data = new Object[conges.size()][colonnes.length];
        for (int i = 0; i < conges.size(); i++) {
            Conge cong = conges.get(i);
            data[i][0] = cong.getId_conge();
            data[i][1] = cong.getNomEmploye();
            data[i][2] = cong.getDateDebut();
            data[i][3] = cong.getDateFin();
            data[i][4] = cong.getTypeConge();
        }

        // Créer le modèle de table et la table à chaque fois
        tableModel = new DefaultTableModel(data, colonnes);
        table = new JTable(tableModel);

        // Initialiser le JScrollPane avec la table
        scrollPane = new JScrollPane(table);

        // Ajouter la table dans p3
        p3.removeAll();  // Supprimer tout le contenu actuel
        JLabel tableTitle = new JLabel("Liste des Congés");
        p3.setLayout(new BorderLayout());
        p3.add(tableTitle, BorderLayout.NORTH);
        p3.add(scrollPane, BorderLayout.CENTER);  // Ajouter le JScrollPane avec la table

        // Gérer l'affichage de la table (pliée ou dépliée)
        if (isTableCollapsed) {
            scrollPane.setVisible(false);  // Table repliée
        } else {
            scrollPane.setVisible(true);  // Table dépliée
        }
    }

    public CongeView() {
        // Initialisation des panels avec des layouts spécifiques
        p1.setLayout(new GridLayout(4, 2));
        p2.setLayout(new FlowLayout());

        System.out.println("Hello world");

        // Remplissage de la JComboBox des employes avec leurs noms uniquement
        listeEmployes.removeAllItems();  // Eviter les doublons
        for (Employe e : EmployeListe) {
            listeEmployes.addItem(e.getNom());  // Ajouter uniquement le nom de l'employé
        }

        p1.add(Lnom); p1.add(listeEmployes);
        p1.add(Ltype); p1.add(listeTypes);
        p1.add(Ldate_debut); p1.add(dateDebutSpinner);
        p1.add(Ldate_fin); p1.add(dateFinSpinner);

        // Ajouter les boutons à la p2 (panel des boutons)
        p2.add(ajouter);
        p2.add(supprimer);
        p2.add(modifier);
        p2.add(afficher);

        // Ajouter les panels à la fenêtre principale
        setLayout(new BorderLayout());
        add(p1, BorderLayout.NORTH);  // Formulaire en haut
        add(p3, BorderLayout.CENTER);  // Table au centre
        add(p2, BorderLayout.SOUTH);  // Boutons en bas

        // Affichage initial du tableau
        afficherTableau();

        // Création d'une fenêtre pour contenir le tout
        setTitle("Gestion des Congés");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
    }

    public CongeView(List<Employe> l){
        this.EmployeListe=l;
        // Remplissage de la JComboBox des employes avec leurs noms uniquement
        listeEmployes.removeAllItems();  // Eviter les doublons
        for (Employe e : l) {
            listeEmployes.addItem(e.getNom());  // Ajouter uniquement le nom de l'employé
        }
    }
    // Méthode pour récupérer l'ID de la ligne sélectionnée dans le tableau
    public int getSelectedCongeId() {
        int selectedRow = table.getSelectedRow(); // Récupérer la ligne sélectionnée
        if (selectedRow != -1) {
            return (int) table.getValueAt(selectedRow, 0); // Retourner l'ID de la ligne (colonne 0)
        }
        return -1; // Retourner -1 si aucune ligne n'est sélectionnée
    }

    // Méthode pour récupérer l'ID de l'employé sélectionné dans le JComboBox
    public int getSelectedEmployeId() { 
        String selectedEmployeNom = (String) listeEmployes.getSelectedItem(); // Récupérer le nom de l'employé sélectionné
        if (selectedEmployeNom != null) {
            for (Employe e : EmployeListe) {
                if (e.getNom().equals(selectedEmployeNom)) {
                    return e.getId(); // Retourner l'ID de l'employé
                }
            }
        }
        return -1; // Retourner -1 si aucun employé n'est sélectionné
    }

    // Méthode pour récupérer le type de congé sélectionné dans le JComboBox
    public TypeConge getSelectedTypeConge() {
        TypeConge selectedTypeConge = (TypeConge) listeTypes.getSelectedItem(); // Récupérer le type de congé sélectionné
        return selectedTypeConge; // Retourner l'objet TypeConge sélectionné
    }

    // Méthode pour récupérer la date de début sélectionnée
    public LocalDate getDateDebut() {
        Date dateDebut = (Date) dateDebutSpinner.getValue();  // Récupérer la date de début sélectionnée
        return dateDebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();  // Convertir en LocalDate
    }

    // Méthode pour récupérer la date de fin sélectionnée
    public LocalDate getDateFin() {
        Date dateFin = (Date) dateFinSpinner.getValue();  // Récupérer la date de fin sélectionnée
        return dateFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();  // Convertir en LocalDate
    }

    // Méthode pour récupérer le nom de l'employé sélectionné
    public String getSelectedEmployeNom() {
        String selectedEmployeNom = (String) listeEmployes.getSelectedItem(); // Récupérer le nom de l'employé sélectionné
        return selectedEmployeNom; // Retourner le nom de l'employé
    }

    // Méthodes pour afficher les messages d'erreur et de succès
    public void afficherMessageErreur(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void afficherMessageSucces(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
}
