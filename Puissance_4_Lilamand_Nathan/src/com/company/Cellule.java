package com.company;

public class Cellule {
    Jeton jetonCourant;
    boolean trouNoir, desintegrateur;

    public Cellule() {
        jetonCourant = null; // ca sert a rien mais c'est pour moi...
        trouNoir = false;
        desintegrateur = false;
    }


    public boolean affecterJeton(Jeton nomJeton) {
        if (jetonCourant == null) {
            jetonCourant = nomJeton;
            return true;
        } else {
            return false;
        }
    }

    public Jeton recupererJeton() {
        return jetonCourant;
    }


    public boolean placerTrouNoir() {
        if (!trouNoir) {
            trouNoir = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean placerDesintegrateur() {
        if (!desintegrateur) {
            desintegrateur = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean suprimerDesintegrateur() {
        if (desintegrateur) {
            desintegrateur = false;
            return true;
        } else{
            return false;
        }
    }
    public boolean presenceTrouNoir() {
        return trouNoir;
    }

    public boolean presenceDesintegrateur() {
        return desintegrateur;
    }

    public String lireCouleurDuJeton() {
        if (jetonCourant != null) {
            return jetonCourant.lireCouleur();
        } else {
            return ""; // Si le jeton n'existe pas, j'ai renvoyé une chaine de caracteres vide.
        }
    }

    public boolean recupererDesintegrateur() {
        if (desintegrateur) {
            desintegrateur = false;
            return true;
        }
        return false; // meme chose que le else
    }

    public boolean activerTrouNoir() {
        if (trouNoir) {
            jetonCourant = null;
            trouNoir = false;
            return true;
        }
        return false;
    }

    public void suprimerTrouNoir(){
        if (trouNoir){
            trouNoir = false;
        }
    }

    public boolean presenceJeton(){
       return jetonCourant != null;
    }

    public boolean suprimerJeton(){
        if (presenceJeton()){
            jetonCourant = null;
            return true;
        }return false;
    }
    public String getSymbol(){
        if (jetonCourant == null && !trouNoir && !desintegrateur){
            return "_";
        }
        if (jetonCourant != null && !trouNoir && !desintegrateur){
            jetonCourant.afficherCouleur();
        }
        if (jetonCourant == null && trouNoir && !desintegrateur){
            return "O";
        }
        if ((jetonCourant == null && !trouNoir && desintegrateur)){
            return "X";
        }else{
            return "error revoir le methode getSymbol classe Cellule";
        }
    }
}


