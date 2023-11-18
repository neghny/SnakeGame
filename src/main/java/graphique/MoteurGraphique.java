package graphique;

import kernel.Objet;
import physique.Cercle;
import physique.IForme;
import physique.Rectangle;

import javax.swing.*;
import java.util.LinkedList;

public class MoteurGraphique {
    JFrame mainFrame;

    public MoteurGraphique(int width, int height, String title) {
        this.mainFrame = new JFrame(title);
        mainFrame.setLayout(null);
        mainFrame.setSize(width, height);
        mainFrame.setResizable(true);
        // Position de la fenêtre par défaut
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * Ajoute un objet à la fenêtre
     * @param o objet à ajouter
     */
    public void addObjet(Objet o){ // affiche un objet
        mainFrame.add(o);
    }

    public JFrame getMainFrame() {
        return mainFrame;
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

    /**
     * Met à jour les objets (position, taille, etc) sur l'écran
     * @param objects objets à mettre à jour
     */
    public void display(LinkedList<Objet> objects){
        for (Objet o : objects){
            o.setBounds(o.getXposition(), o.getYposition(), o.getSizeImageX(), o.getSizeImageY());
        }
    }

}

