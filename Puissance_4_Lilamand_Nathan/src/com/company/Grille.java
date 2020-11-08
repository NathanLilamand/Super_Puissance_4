package com.company;

/*
import sun.tools.jconsole.Tab;
*/
public class Grille {
    Cellule[][] tab = new Cellule[7][6];

    public Grille() { // permet de remplir les case de type cellule avec des objets cellule
        for (int i = 0; i < 7; i++) {
            for (int y = 0; y < 6; y++) {
                tab[i][y] = new Cellule();
            }
        }
    }

    public int derniereLigneDisponible(int numeroColonne) { // permet de connaitre la derniere case vide en partant du bas
        int var = -1;
        for (int i = 0; i < 6; i++) {
            if (tab[numeroColonne][i].recupererJeton() == null) {
                var = i;
                break;
            }
        }
        return var;
    }

    public boolean ajouterJetonDansColone(Jeton unjeton, int numeroColonne) {
        if (derniereLigneDisponible(numeroColonne) == -1) {
            return false;
        } else {
            tab[numeroColonne][derniereLigneDisponible(numeroColonne)].affecterJeton(unjeton);
            return true;
        }

    }

    public boolean etreRemplie() {
        for (int i = 0; i < 7; i++) {
            if (derniereLigneDisponible(i) >= 0) { // si i = -1 la colonne est pleine donc si la derniere ligne dispo est compris entre 0 et 5 on return false
                return false;
            }
        }
        return true;
    }

    public void viderGrille() {
        for (int i = 0; i < 7; i++) {
            for (int y = 0; i < 6; i++) {
                tab[i][y] = null;
                tab[i][y] = new Cellule();
            }
        }
    }

    public void afficherGrillesurConsoleEnLigne() {
        for (int i = 0; i < 7; i++) {
            for (int y = 0; y < 6; y++) {
                System.out.println("la cellule [" + i + "][" + y + "] contient un jeton: "
                        + tab[i][y].presenceJeton() + " la couleur est: " + tab[i][y].lireCouleurDuJeton()
                        + " et il y a un trou noir? : " + tab[i][y].presenceTrouNoir());
            }
        }
    }

    // show grid
    private final int gridColumns = 7, gridRows =6; // the value can't change, if you want change the grid ans add columns or rows, you don't have to change every integer in the code

    public int getGridColumnsCount(){ return gridColumns; }

    public int getGridRowsCount(){ return gridRows;}


    public  String renderGrid(Grille grid){
        int columns = grid.getGridColumnsCount();
        int rows = grid.getGridRowsCount();
        StringBuilder separation = new StringBuilder();
        for(int i = 0; i< columns + 1 + columns * 3; i++){
            separation.append("-");
        }
        String separationLine = separation.toString();

        StringBuilder gridBuild = new StringBuilder();
        gridBuild.append(separationLine).append("\n");

        for(int i = rows; i > 0 ; i--){
            for(int j = 0; i<columns ; i++){
                gridBuild.append("| ").append(getCell(j, i-1).getSymbol()).append(" ");
            }
            gridBuild.append("|\n").append(separationLine).append("\n");
        }
        return gridBuild.toString();
    }

    public Cellule getCell(int j, int i){
        return tab[j][i];
    }

    public void getSymbol(){

    }
    public void afficherGrilleSurConsoleTableau(){
        for (int i = 0; i < 7; i++) {
            for (int y = 0; y < 6; y++) {
                if (!tab[i][y].presenceJeton()){
                    System.out.println(0);
                }
                System.out.println("la cellule [" + i + "][" + y + "] contient un jeton: "
                        + tab[i][y].presenceJeton() + " la couleur est: " + tab[i][y].lireCouleurDuJeton()
                        + " et il y a un trou noir? : " + tab[i][y].presenceTrouNoir());
            }
        }
    }

    public boolean celluleOccupee(int colonne, int ligne) {
        return tab[colonne][ligne].presenceJeton();
    }

    public String lireCouleurDuJeton(int colonne, int ligne) {
        return tab[colonne][ligne].lireCouleurDuJeton();
    }



    public boolean etreGagnantePourJoueur(Joueur lejoueur) {
        // on va verifier avec un systeme de point les case
        int vainqueur = 0; // variable permetant de compter les jetons aligné
        int c; // on crée ces deux variable pour pouvoir les incrementé dans les boucle for sans touché a i et a y
        int l;
        for (int i = 0; i < 7; i++) { // incremente les colones

            for (int y = 0; y < 6; y++) { // incremente les lignes

                if (tab[i][y].presenceJeton()) { // on verifie si il y a un jeton

                    if (lireCouleurDuJeton(i, y).equals(lejoueur.Couleur)) { // si le jeton appartien au joueur
                        // on va verifier les 3 jeton du haut
                        for (int u = 1; u < 4; u++) { // on va faire 3 tour de boucle
                            if (y <= 2 && tab[i][y + u].presenceJeton()) { // si le jeton [i][y] se trouve a la ligne 3 4 5 ca sert a rien de verifier si il y en a 3 on sortira de la grille
                                if (tab[i][y + u].lireCouleurDuJeton().equals(lejoueur.Couleur)) {
                                    vainqueur++;
                                } else {
                                    vainqueur = 0;
                                    break;

                                }
                            } // si la case se trouve a y > 2 on sort de la boucle
                        }
                        if (vainqueur == 3) {
                            return true;
                        }

                        // on verifie les jeton de droite

                        for (int j = 1; j < 4; j++) { // on va faire 3 tour de boucle
                            if (i <= 3 && tab[i + j][y].presenceJeton()) {
                                if (tab[i + j][y].lireCouleurDuJeton().equals(lejoueur.Couleur)) {
                                    vainqueur++;
                                } else {
                                    vainqueur = 0;
                                    break;
                                }
                            }
                        }
                        if (vainqueur == 3) {
                            return true;
                        }
                        // on verifie les diagonales haut droite
                        for (int u = 1; u < 4; u++) { // on va faire 3 tour de boucle
                            if (y <= 2 && i <= 3 && tab[i + u][y + u].presenceJeton()) {
                                if (tab[i + 1][y + 1].lireCouleurDuJeton().equals(lejoueur.Couleur)) {
                                    vainqueur++;
                                } else {
                                    vainqueur = 0;
                                    break;
                                }
                            }
                        }
                        if (vainqueur == 3) {
                            return true;
                        }

                        // on verifie les diagonales haut gauche

                        for (int u = 1; u < 4; u++) { // on va faire 3 tour de boucle
                            if (y <= 2 && i >= 3 && tab[i - u][y + u].presenceJeton()) {
                                if (tab[i + 1][y + 1].lireCouleurDuJeton().equals(lejoueur.Couleur)) {
                                    vainqueur++;
                                } else {
                                    vainqueur = 0;
                                    break;
                                }
                            }
                        }
                        if (vainqueur == 3) {
                            return true;
                        }
                    } else { // si le jeton n'est pas de sa couleur alors on fais rien, on peux enlever le else

                    }
                } else {
                    // si il n'y a pas de jeton alligné on fais rien
                }

            }

        }
        return false;  // si la grille est vide ou il n'y a aucun jeton alligné on return false
    }
}
