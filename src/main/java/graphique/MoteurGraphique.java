package graphique;

import kernel.Objet;
import physique.Cercle;
import physique.IForme;
import physique.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;


public class MoteurGraphique {
    JFrame mainFrame;

    public MoteurGraphique(int width, int height, String title) {
        this.mainFrame = new JFrame(title);
        mainFrame.setLayout(null);
        mainFrame.setSize(width, height);
        mainFrame.setResizable(true);
        //mainFrame.setBounds(0, 0, width, height);
        // Désactive la gestion automatique de la disposition
        //mainFrame.setLayout(null);
        // Position de la fenêtre par défaut
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * Affiche l'objet dans la fenêtre à une position donné (position de l'objet) et ajuste l'affichage salon la taille de l'objet (recensé dans la classe objet également)
     * @param o objet que l'on veut afficher
     */
    public void addObjet(Objet o){ // affiche un objet
        mainFrame.add(o);
        //o.setBounds((int) o.getXposition(), (int) o.getYposition(), o.getWidth(), o.getHeight());
        //o.setLayout(null);

    }


    public void display(LinkedList<Objet> objects){ // affiche tout
        //mainFrame.setLayout(null);
        for (Objet o : objects){
            o.setBounds((int) o.getXposition(), (int) o.getYposition(), o.getSizeImageX(), o.getSizeImageY());
        }
        for (Objet o : objects){
            addObjet(o);
            System.out.println("cc");
        }

        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        MoteurGraphique myMG = new MoteurGraphique(1000,1000,"Frame");
        //IForme rectangle = new Rectangle(800, 800);
        //Objet myO = new Objet(500, 500, rectangle, "pacman.png", 800, 800);
        IForme rGhost = new Rectangle(300, 300);
        IForme rPacman = new Cercle(60);
        Objet pacman = new Objet(25, 25, rPacman, "pacman.png", 120, 120);
        Objet pinkGhost = new Objet(0, 0, rGhost, "pink_ghost.png", 300, 300);
        Objet blueGhost = new Objet(50, 30, rGhost, "blue_ghost.png", 300, 300);
        LinkedList<Objet> myObjets = new LinkedList<>();
        myObjets.add(pacman);
        myObjets.add(pinkGhost);
        myObjets.add(blueGhost);
        myMG.display(myObjets);
        //IForme rPacman = new Cercle(300);
        /*Objet pacman1 = new Objet(500, 500, rPacman, "pacman.png", 300, 300);
        Objet pacman2 = new Objet(0, 500, rPacman, "pacman.png", 300, 300);

        pacman1.setBounds(500,500,300,300);
        pacman2.setBounds(500,500,300,300);

        myMG.addObjet(pacman1);
        myMG.addObjet(pacman2);
        myMG.mainFrame.setVisible(true);*/
        //myMG.mainFrame.setVisible(true);
//        frame.add(myO);
//        frame.setSize(1000,1000);
        //frame.setVisible(true);
//        frame.display();
    }


}
