package graphique;

import kernel.KeyListenerKernel;
import kernel.Objet;
import physique.Cercle;
import physique.IForme;
import physique.Rectangle;

import javax.swing.*;
import java.io.IOException;
import java.util.LinkedList;

public class MoteurGraphique {
    JFrame mainFrame;

    public MoteurGraphique(int width, int height, String title, KeyListenerKernel keyListener) {
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

        mainFrame.addKeyListener(keyListener);
    }


    /**
     * Ajoute un objet à la fenêtre
     * @param o objet à ajouter
     */
    public void addObjet(Objet o){ // affiche un objet
        mainFrame.add(o);
        //o.setBounds((int) o.getXposition(), (int) o.getYposition(), o.getWidth(), o.getHeight());
        //o.setLayout(null);

    }


    /**
     * Affiche tous les objets de la liste dans l'ordre inverse d'ajout à la liste, prenant en compte leurs tailles et positions
     * @param objects liste d'objets à afficher
     */
    public void init_display(LinkedList<Objet> objects) {
        for (Objet o : objects){
            addObjet(o);
        }
        mainFrame.setVisible(true);
    }
    public void display(LinkedList<Objet> objects){
        for (Objet o : objects){
            o.setBounds((int) o.getXposition(), (int) o.getYposition(), o.getSizeImageX(), o.getSizeImageY());
        }
    }

    public static void main(String[] args) {
        //null keyListener because the constructor doesn't work otherwise, just ignore
        MoteurGraphique myMG = new MoteurGraphique(1000,1000,"Frame", new KeyListenerKernel(null));
        //IForme rectangle = new Rectangle(800, 800);
        //Objet myO = new Objet(500, 500, rectangle, "pacman.png", 800, 800);
        IForme rGhost = new Rectangle(300, 300);
        IForme cPacman = new Cercle(60);
        IForme cBall = new Cercle(10);
        Objet pacman = new Objet(25, 25, cPacman, "pacman.png", 120, 120);
        Objet pinkGhost = new Objet(0, 0, rGhost, "pink_ghost.png", 300, 300);
        Objet blueGhost = new Objet(250, 450, rGhost, "blue_ghost.png", 300, 300);
        Objet redGhost = new Objet(600, 500, rGhost, "red_ghost.png", 300, 300);
        Objet b1 = new Objet(100, 600, cBall, "ball.png", 20, 20);
        Objet b2 = new Objet(200, 700, cBall, "ball.png", 20, 20);
        LinkedList<Objet> myObjets = new LinkedList<>();
        myObjets.add(pacman);
        myObjets.add(pinkGhost);
        myObjets.add(blueGhost);
        myObjets.add(redGhost);
        myObjets.add(b1);
        myObjets.add(b2);
        pinkGhost.setRotation(90);
        pinkGhost.repaint();
        myMG.init_display(myObjets);
        pinkGhost.setPosition(20, 10);
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

