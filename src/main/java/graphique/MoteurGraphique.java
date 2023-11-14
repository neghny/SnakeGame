package graphique;

import kernel.Gameplay;
import kernel.KeyListenerKernel;
import kernel.Objet;
import javax.swing.*;
import java.util.LinkedList;

public class MoteurGraphique {
    private static MoteurGraphique INSTANCE;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    private final JFrame mainFrame;

    private MoteurGraphique() {
        this.mainFrame = new JFrame("Frame");
        mainFrame.setLayout(null);
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setResizable(true);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.addKeyListener(new KeyListenerKernel(Gameplay.getInstance()));
    }

    public static MoteurGraphique getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MoteurGraphique();
        }
        return INSTANCE;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
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
    public void display(LinkedList<Objet> objects){
        for (Objet o : objects){
            o.setBounds(o.getXposition(), o.getYposition(), o.getSizeImageX(), o.getSizeImageY());
        }
    }

}

