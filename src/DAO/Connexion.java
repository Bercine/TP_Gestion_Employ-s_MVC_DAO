package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    //Attributs
    private static final String url="jdbc:mysql://localhost:3306/BDEmployee";
    private static final String user="root";
    private static final String password="";
    static Connection conn=null;

    //méthode pour récupérer la connection
    public static Connection getConnexion(){
        if(conn==null){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
            throw new RuntimeException("Erreur de connexion");
        }
    }
    return conn;
    }

}
