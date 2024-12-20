package Model;

public class Employe {
    //attributs
    private String nom,prenom,email,telephone;
    private double salaire;
    private Role role;
    private Poste poste;
    private int id;

    //setter
    public void setEmploye(int id,String n,String p,String e,String t,double s,Role r,Poste poste){
        this.id=id;
        this.nom=n;
        this.prenom=p;
        this.email=e;
        this.telephone=t;
        this.salaire=s;
        this.role=r;
        this.poste=poste;
    }

    //getters
    public int getId(){return id;}
    public String getNom(){return nom;}
    public String getPrenom(){return prenom;}
    public String getEmail(){return email;}
    public String getTelephone(){return telephone;}
    public double getSalaire(){return salaire;}
    public String getRole(){return role.name();}
    public String getPoste(){return poste.name();}

    //constructeur
    public Employe(int id,String n,String p,String e,String t,double s,Role r,Poste poste){
        this.setEmploye(id,n, p, e, t, s, r, poste);
    }
}

