package graphique;

import kernel.KeyListenerKernel;
import kernel.Objet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class MoteurGraphique {
    private static MoteurGraphique INSTANCE;
    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;

    private final JFrame mainFrame;

    public MoteurGraphique() {
        mainFrame = new JFrame("Jeu");
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
        objet.setVisible(true);
    }

    /**
     * source : http://www.java2s.com/Code/Java/Swing-JFC/GetAllComponentsinacontainer.htm
     */
    public static ArrayList<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        ArrayList<Component> compList = new ArrayList<>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container)
                compList.addAll(getAllComponents((Container) comp));
        }
        return compList;
    }

    public void empty_mainFrame() {
        // mainFrame.getContentPane().removeAll();
        for (Component comp : getAllComponents(mainFrame))
            if (comp instanceof Objet)
                comp.setVisible(false);
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
        display(objets);
        mainFrame.setVisible(true);
    }

    public void display(LinkedList<Objet> objects) {
        for (Objet o : objects) {
            o.setBounds(o.getXposition(), o.getYposition(), o.getSizeImageX(), o.getSizeImageY());
        }
    }

}

