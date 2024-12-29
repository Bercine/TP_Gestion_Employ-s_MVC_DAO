
package View;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import Controller.*;
import DAO.*;
import Model.*;

public class MainView extends JFrame {
    // Créer un JTabbedPane
    JTabbedPane tabbedPane = new JTabbedPane();
    // Ajouter les deux vues dans des onglets
    EmployeView employeView = new EmployeView();
    CongeView congeView = new CongeView();
    //deux dao et model
    EmployeDAOimpl dao=new EmployeDAOimpl();
    EmployeModel model=new EmployeModel(dao);
    //deux dao et model
    CongeDAOimpl cdao=new CongeDAOimpl();
    CongeModel cmodel=new CongeModel(cdao);
    
    public MainView() {
        
        new EmployeContoller(model, employeView);

        new CongeController(cmodel, congeView);
        
        tabbedPane.addTab("Employés", employeView.getContentPane());
        tabbedPane.addTab("Congés", congeView.getContentPane());

        // Ajouter le JTabbedPane à la fenêtre principale
        add(tabbedPane);

        // Paramètres de la fenêtre principale
        setTitle("Gestion des Employés et Congés");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrer la fenêtre
    }
}
