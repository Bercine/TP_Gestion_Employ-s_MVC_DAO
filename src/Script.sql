
CREATE DATABASE BDEmployee;

use BDEmployee;
CREATE TABLE employe(

    id_employe INTEGER PRIMARY KEY auto_increment,
    nom_employe VARCHAR(20) NOT NULL,
    prenom_employe VARCHAR(20) NOT NULL,
    email_employe VARCHAR(50) UNIQUE NOT NULL,
    telephone_employe VARCHAR(20) NOT NULL,
    salaire_employe DOUBLE NOT NULL,
    role_employe VARCHAR(50) NOT NULL,
    poste_employe VARCHAR(50) NOT NULL
);