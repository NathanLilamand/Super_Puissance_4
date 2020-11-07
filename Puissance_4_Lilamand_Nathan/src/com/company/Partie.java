package com.company;

import java.util.Random;
import java.util.Scanner;

public class Partie {
    Joueur[] ListeJoueur = new Joueur[2];
    Joueur joueurCourent;
    int d; // on va l'utiliser dans le deroulement de la parte, permet de sauter une partie du code au premier tour dans le do while (l'inverseur de joueur pour le joueur actif)

    public void initialiserPartie() {
        Grille newgrid = new Grille(); // on creer une nouvelle grille de reference newgrid
        // on attribue les troues noir
        for (int i = 0; i < 6; i++) {
            int r = new Random().nextInt(7); // on choisi une colones au hasard
            int l = new Random().nextInt(6); // on choisi une ligne au hasard
            if (newgrid.tab[r][l].presenceTrouNoir()) {
                i--; // si la case contient deja un trou noir on désincremente i pour refaire un tour de for
            } else {
                newgrid.tab[r][l].placerTrouNoir();
            }
        }

        // on positione aleatoirement 3 desintegrateurs Visible
        for (int i = 0; i < 4; i++) {
            int r = new Random().nextInt(7); // on choisi une colones au hasard
            int l = new Random().nextInt(6); // on choisi une ligne au hasard
            if (newgrid.tab[r][l].presenceTrouNoir()) {
                i--; // si la case contient deja un trou noir on désincremente i pour refaire un tour de for
            } else {
                newgrid.tab[r][l].placerDesintegrateur();
            }
        }

        //on positione aleatoirement 2 desintegrateur non visible
        for (int i = 0; i < 2; i++) { // on reprend la meme methode qu'au dessus
            int r = new Random().nextInt(7); // on choisi une colones au hasard
            int l = new Random().nextInt(6); // on choisi une ligne au hasard
            if (!newgrid.tab[r][l].presenceTrouNoir()) {//on inverse la condition
                i--; // si la case ne contient pas un trou noir on désincremente i pour refaire un tour de for
            } else {
                newgrid.tab[r][l].placerDesintegrateur();// a ameliorer imperativement!!!! car par malchance ca peut prendre une eternité
            }
        }
        //on attribue les nom aux joueur en les demendant sur la console
        System.out.println("Quel est le nom du 1er joueur?");
        Scanner sc = new Scanner(System.in);
        String nomjoueur1 = sc.nextLine();
        System.out.println("Quel est le nom du 2eme joueur?");
        String nomjoueur2 = sc.nextLine();


        //les joueurs se voit assigner une couleur aleatoire, j'utilise ma methode attribuerCouleursAuxJoueurs()
        String[] colors = attribuerCouleursAuxJoueurs(); // on recupere un tableau soit jaune rouge ou rouge noir
        ListeJoueur[0] = new Joueur(nomjoueur1, colors[0]); // on recupere le nom du scanner nomjoueur 1 et on prend la premiere case d'un des deux tableau des 2 couleurs
        ListeJoueur[1] = new Joueur(nomjoueur2, colors[1]);

        // chaque joueur se voit se faire remettre 21 jetons
        for (int i = 0; i < 2; i++) {
            ListeJoueur[i].genererJeton();
        }

        // quel est le 1er joueur qui commence la partie?
        int a = new Random().nextInt(2);
        Joueur joueuractif, joueurnonactif;
        if (a == 0) {
            joueuractif = ListeJoueur[0];
            joueurnonactif = ListeJoueur[1];
        } else {
            joueuractif = ListeJoueur[1];
            joueurnonactif = ListeJoueur[0];
        }
        System.out.println(joueuractif.Nom + " commence!");


        // deroulement de la partie:

        do {
            if (d == 1) {
                if (joueuractif.equals(ListeJoueur[0])) {
                    joueuractif = ListeJoueur[1];
                    joueurnonactif = ListeJoueur[0];
                } else {
                    joueuractif = ListeJoueur[0];
                    joueurnonactif = ListeJoueur[1];
                }
            }
            d = 1; // grace a ca, au premier tour je ne vais pas echanger celui qui est entrain de jouer des le 1er tour
            newgrid.afficherGrillesurConsoleEnLigne();

            // création d'un menu avec plusieurs choix possible
            System.out.println(joueuractif.Nom + ", que voulez vous faire?");
            System.out.println("vous avez " + joueuractif.nombreDesintegrateurs + " désintégrateur");
            System.out.println();
            System.out.println("1 - jouer un jeton");
            System.out.println("2 - Récupérer un jeton");
            System.out.println("3 - Désintegration d'un jeton adverse");
            System.out.println("veuillez entrer un chiffre correspondant à une action:");
            int actionchoisie = Integer.parseInt(sc.next());
            while (actionchoisie <= 0 || actionchoisie > 3) { // si l'utilisateur se trompe
                System.out.println("veuillez entrer un chiffre dans la plage 1 - 3 correspondant a une action");
                actionchoisie = Integer.parseInt(sc.next());
            }

            // mis en place d'un switch case pour diffrencier les actions du joueur actif:
            switch (actionchoisie) {
                case 1: {
                    System.out.println(joueuractif.Nom + ", dans quel colonne voulez vous placez votre jeton?");
                    int colonnechoisie = Integer.parseInt(sc.next()); // permet de demender un entier (convertir une chaine de caractere en entier)
                    Jeton jeto = joueuractif.ListeJeton[joueuractif.positionDernierDuJetonNonNull()];
                    joueuractif.ListeJeton[joueuractif.positionDernierDuJetonNonNull()] = null; // on lui enleve un jeton de sa liste (enfin je croi a voir quand on recupere les jeton

                    // on va verifier si la cellules qu'il a choisi contient un trou noir
                    if (!newgrid.tab[colonnechoisie][newgrid.derniereLigneDisponible(colonnechoisie)].presenceTrouNoir()) {
                        System.out.println(newgrid.derniereLigneDisponible(colonnechoisie));
                        if (!newgrid.ajouterJetonDansColone(jeto, colonnechoisie)){
                            System.out.println("il n'y avait plus de place dans cette colone, soyez plus attentif la prochaine fois! vous passer votre tour");
                        }


                    } else {
                        newgrid.tab[colonnechoisie][newgrid.derniereLigneDisponible(colonnechoisie)].suprimerTrouNoir();
                    }

                    // on va verifier si la cellules qu'il a choisi contient un desintegrateur
                    if (newgrid.tab[colonnechoisie][newgrid.derniereLigneDisponible(colonnechoisie)].presenceDesintegrateur()) {
                        newgrid.tab[colonnechoisie][newgrid.derniereLigneDisponible(colonnechoisie)].suprimerDesintegrateur();
                        joueuractif.nombreDesintegrateurs++;
                    }


                }
                break;
                case 2: {
                    int colone;
                    int ligne;
                    do {
                        System.out.println("quel jeton voulez vous récupérer ?");
                        System.out.println("entrer la colone:");
                        colone = Integer.parseInt(sc.next());
                        System.out.println("entrer la ligne");
                        ligne = Integer.parseInt(sc.next());
                    } while (!newgrid.tab[colone][ligne].lireCouleurDuJeton().equals(joueuractif.Couleur));
                    joueuractif.ajouterJetonDansSaListeJeton(newgrid.tab[colone][ligne].jetonCourant); // on sauvegarde le jeton dans sa liste
                    newgrid.tab[colone][ligne].suprimerJeton(); // on suprimme son jeton de la grille

                    // on tasse la grille
                    tasserGrille2(newgrid, colone);

                }
                break;
                case 3: {
                    if (joueuractif.nombreDesintegrateurs > 0) {
                        joueuractif.nombreDesintegrateurs--;
                        int column, row;
                        do {
                            System.out.println("le jeton adverse que vous voulais désintegrer se trouve dans quel colone ?");
                            column = Integer.parseInt(sc.next());
                            System.out.println("le jeton adverse que vous voulais désintegrer se trouve à quel ligne ?");
                            row = Integer.parseInt(sc.next());
                        } while (newgrid.tab[column][row].lireCouleurDuJeton().equals(joueuractif.Couleur));
                        newgrid.tab[column][row].suprimerJeton();
                        tasserGrille2(newgrid, column);


                    } else {
                        System.out.println("il ne te reser plus de désintegrateur, soit attentif pour la prochaine fois !");
                        System.out.println("tu passe ton tour");
                    }

                }
                break;

                // on verifie si le joueur actif est gagnant dans le while


            }
        }
        while (!newgrid.etreGagnantePourJoueur(joueuractif) || newgrid.etreRemplie() || !newgrid.etreGagnantePourJoueur(joueurnonactif))
                ;
        if (newgrid.etreRemplie()) {
            System.out.println("Partie nul");
        }
        if (newgrid.etreGagnantePourJoueur(joueuractif) == newgrid.etreGagnantePourJoueur(joueurnonactif)) {
            System.out.println("le joueur: " + joueurnonactif.Nom + " a gagner pour cause d'une faute de jeu causé par " + joueuractif.Nom);
        } else {
            System.out.println("le joueur: " + joueuractif.Nom + " a gagner");
        }

    }

    public String[] attribuerCouleursAuxJoueurs() {
        int a = new Random().nextInt(2);
        if (a == 0) {
            return new String[]{"Jaune", "Rouge"};
        } else {
            return new String[]{"Rouge", "Jaune"};
        }

    }

    public void tasserGrille(Grille newgrid, int colone) {
        int derligndispo = newgrid.derniereLigneDisponible(colone);
        for (int i = 5 - derligndispo; i < 5; i++) {
            if (newgrid.tab[colone][newgrid.derniereLigneDisponible(colone) + 1].presenceJeton()) { // si il existe un jeton dans la cellule du dessus alors:
                newgrid.tab[colone][newgrid.derniereLigneDisponible(colone)] = newgrid.tab[colone][newgrid.derniereLigneDisponible(colone) + 1];
                newgrid.tab[colone][newgrid.derniereLigneDisponible(colone) + 1].suprimerJeton();
            } else {
                break;
            }
        }
    }

    private void tasserGrille2(Grille newgrid, int colone) {
        for (int i = 0; i < 5; i++) {
            if (!newgrid.tab[colone][i].presenceJeton()){
                Jeton token = newgrid.tab[colone][i+1].jetonCourant;
                newgrid.tab[colone][i].affecterJeton(token);
                newgrid.tab[colone][i+1].suprimerJeton();
            }else {
                break;
            }
        }
    }
}
