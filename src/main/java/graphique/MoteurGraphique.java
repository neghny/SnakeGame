package graphique;

import kernel.KeyListenerKernel;
import kernel.Objet;

import javax.swing.*;
import java.util.LinkedList;

public class MoteurGraphique {
    private static MoteurGraphique INSTANCE;
    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;

    private final JFrame mainFrame;

    private MoteurGraphique() {
        mainFrame = new JFrame("Frame");
        init_mainFrame();
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void init_mainFrame() {
        mainFrame.setLayout(null);
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setResizable(true);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.addKeyListener(KeyListenerKernel.getInstance());
        mainFrame.setVisible(true);
    }

    public static MoteurGraphique getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MoteurGraphique();
        }
        return INSTANCE;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    /**
     * Ajoute un objet à la fenêtre
     *
     * @param objet objet à ajouter
     */
    public void addObjet(Objet objet) { // affiche un objet
        mainFrame.add(objet);
    }

    public void empty_mainFrame() {
        mainFrame.getContentPane().removeAll();
    }

    /**
     * Affiche tous les objets de la liste dans l'ordre inverse d'ajout à la liste, prenant en compte leurs tailles et positions
     *
     * @param objets liste d'objets à afficher
     */
    public void init_display(LinkedList<Objet> objets) {
        empty_mainFrame();
        for (Objet o : objets) {
            addObjet(o);
        }
        mainFrame.setVisible(true);
    }

    public void display(LinkedList<Objet> objects) {
        for (Objet o : objects) {
            o.setBounds(o.getXposition(), o.getYposition(), o.getSizeImageX(), o.getSizeImageY());
        }
    }

}

