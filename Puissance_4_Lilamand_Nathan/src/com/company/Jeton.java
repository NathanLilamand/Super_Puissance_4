package com.company;

public class Jeton {
    String Couleur;

    public Jeton(String UneCouleur) {

        Couleur = UneCouleur;
    }

    public String lireCouleur() {

        return Couleur;

    }
    public String afficherCouleur(){
        if (Couleur.equals("rouge")){
            return "R";
        }else {
            return "J";
        }
    }

    public void afficherLaCouleur() {
        System.out.println(" la couleur du jeton est :  " + Couleur);
    }
}

