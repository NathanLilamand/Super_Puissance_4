package com.company;


class Main {
    public static void main(String args[]) {
        System.out.println(Math.pow(2,3));
        double resultatSerie = 0;
        for(int i = 1; i<=3; i++){
            resultatSerie = resultatSerie + 1/Math.pow(2,i);
            System.out.println(resultatSerie);
        }
        Partie newpartie = new Partie();
        newpartie.initialiserPartie();

    }

}