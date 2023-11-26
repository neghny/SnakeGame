package graphique;

import kernel.KeyListenerKernel;
import kernel.Objet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Le moteur graphique permet de gérer l’affichage du jeu. Il prend en charge la représentation visuelle des objets, le
 * rendu de la scène, et la mise à jour des éléments graphiques à chaque frame. Son rôle principal est d'assurer une
 * présentation fluide du jeu.
 */
public class MoteurGraphique {
    private static MoteurGraphique INSTANCE;
    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;

    private final JFrame mainFrame;

    /**
     * Constructeur de la fenêtre principale et initialisation de ses caractéristiques
     * @author Nesrine, Kawthar, Pauline
     */
    public MoteurGraphique() {
        mainFrame = new JFrame("Jeu");
        init_mainFrame();
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * Initialisation des caractéristiques de la fenêtre principale (taille, couleur)
     * @author Nesrine, Kawthar, Pauline
     */
    public void init_mainFrame() {
        mainFrame.setLayout(null);
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setResizable(true);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.addKeyListener(KeyListenerKernel.getInstance());
        mainFrame.getContentPane().setBackground(Color.BLACK);

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
     * @param objet objet à ajouter
     * @author Nesrine, Kawthar, Pauline
     */
    public void addObjet(Objet objet) {
        mainFrame.add(objet);
        objet.setVisible(true);
    }

    /**
     * source : http://www.java2s.com/Code/Java/Swing-JFC/GetAllComponentsinacontainer.htm
     * @author Julien
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

    /**
     * @author Julien
     */
    public void empty_mainFrame() {
        for (Component comp : getAllComponents(mainFrame))
            if (comp instanceof Objet)
                comp.setVisible(false);
    }

    /**
     * @author Julien
     */
    public void emptyWith(LinkedList<Objet> l) {
        for (Component comp : l)
            comp.setVisible(false);
    }

    /**
     * Affiche tous les objets de la liste dans l'ordre inverse d'ajout à la liste, prenant en compte leurs tailles et positions
     *
     * @param objets liste d'objets à afficher
     * @author Nesrine, Kawthar, Pauline
     */
    public void init_display(LinkedList<Objet> objets) {
        empty_mainFrame();
        for (Objet o : objets) {
            addObjet(o);
        }
        display(objets);
        mainFrame.setVisible(true);
    }

    /**
     * Mise à jour des objets à l'écran
     * @author Nesrine, Kawthar, Pauline
     */
    public void display(LinkedList<Objet> objects) {
        for (Objet o : objects) {
            o.setBounds(o.getXposition(), o.getYposition(), o.getSizeImageX(), o.getSizeImageY());
        }
    }

}

