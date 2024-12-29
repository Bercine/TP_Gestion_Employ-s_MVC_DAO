
-- créer la BDD si elle n'existe pas 
CREATE DATABASE if not EXISTS BDEmployee;

-- utiliser la BDD
use BDEmployee;

-- creer la table role
CREATE TABLE role(
    nom_role VARCHAR(50) PRIMARY KEY
    );
-- insertion  
INSERT INTO role(nom_role) VALUES('ADMIN'),
                             ('EMPLOYE');

-- création de la table poste
CREATE TABLE poste(
    nom_poste VARCHAR(50) PRIMARY KEY
);
-- insertion
INSERT INTO poste(nom_poste) VALUES('INGENIEUR_ETUDE_ET_DEVELOPPEMENT'),
                                    ('TEAM_LEADER'),
                                    ('PILOTE');
-- créer la table employé
CREATE TABLE employe(
    id_employe INTEGER PRIMARY KEY AUTO_INCREMENT,
    nom_employe VARCHAR(50) NOT NULL,
    prenom_employe VARCHAR(50) NOT NULL,
    email_employe VARCHAR(50) UNIQUE NOT NULL,
    telephone_employe VARCHAR(50) NOT NULL,
    salaire_employe DOUBLE NOT NULL,
    nom_role VARCHAR(50) NOT NULL,
    nom_poste VARCHAR(50) NOT NULL,
    constraint cleRole Foreign Key (nom_role) REFERENCES role(nom_role),
    constraint clePoste Foreign Key (nom_poste) REFERENCES poste(nom_poste)
);   

-- créer une table pour les types de congé.
CREATE TABLE typeConge(
    nom_type_conge VARCHAR(50) PRIMARY KEY
);
-- insérer des valeurs dans la table des congés
INSERT INTO typeConge(nom_type_conge) values('CONGE_PAYE'),
                                            ('CONGE_MALADIE'),
                                            ('CONGE_SANS_SOLDE');
-- Ajout de la table des congés
CREATE TABLE conge (
    id_conge INTEGER PRIMARY KEY AUTO_INCREMENT,
    id_employe INTEGER,
    nom_employe VARCHAR(50) NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    nom_type_conge VARCHAR(50) NOT NULL,
    constraint cle_id_employe FOREIGN KEY (id_employe) REFERENCES employe(id_employe) ON DELETE CASCADE,
    constraint cle_type_conge FOREIGN KEY(nom_type_conge) REFERENCES typeConge(nom_type_conge) ON DELETE CASCADE
);




