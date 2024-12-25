package DAO;
import java.util.List;

import Model.Conge;
import Model.TypeConge;
import View.CongeView;

public interface CongeDAOI {
        public void inserer(CongeView cv,int employeId);
        public void supprmier(int id);
        public void modifier(CongeView cview,int id_conge);
        public List<Conge> recuperer_liste_conge();
        public List<TypeConge> recuperer_liste_TypeConges();
}
