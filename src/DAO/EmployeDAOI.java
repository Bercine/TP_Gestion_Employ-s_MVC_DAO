package DAO;
import java.util.List;
import Model.Role;
import View.EmployeView;
import Model.Poste;
import Model.Employe;

public interface EmployeDAOI {
    public void inserer(Employe e);
    public void modifier(EmployeView eview);
    public void supprimer(int id);
    public List<Employe> recuperer_liste_employe();
    public List<Role> findAllRole();
    public List<Poste> findAllPoste();
}
