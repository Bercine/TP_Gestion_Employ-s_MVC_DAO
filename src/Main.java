import Controller.EmployeContoller;
import DAO.EmployeDAOimpl;
import Model.EmployeModel;
import View.EmployeView;

public class Main {
    public static void main(String[] args) {

        EmployeView view=new EmployeView();
        EmployeDAOimpl dao=new EmployeDAOimpl();
        EmployeModel model=new EmployeModel(dao);
        new EmployeContoller(model, view);
        view.setVisible(true);
    }
 
}
