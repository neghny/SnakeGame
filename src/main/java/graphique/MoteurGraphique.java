package graphique;

import kernel.KeyListenerKernel;
import kernel.Objet;
import physique.IForme;
import physique.Rectangle;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

public class MoteurGraphique {
    Frame mainFrame;

    public MoteurGraphique(Frame mainFrame) {
        this.mainFrame = mainFrame;
        mainFrame.addKeyListener(new KeyListenerKernel());
    }


    /**
     * Affiche l'objet dans la fenêtre à une position donné (position de l'objet) et ajuste l'affichage salon la taille de l'objet (recensé dans la classe objet également)
     * @param o objet que l'on veut afficher
     */
    public void displayObject(Objet o){ // affiche un objet
        o.setBounds((int) o.getXposition(), (int) o.getYposition(), o.getWidth(), o.getHeight());
        mainFrame.add(o);
    }


    public void display(LinkedList<Objet> objects){ // affiche tout
        for (Objet o : objects){
            displayObject(o);
        }
    }

    public static void main(String[] args) throws IOException {
        Frame frame = new Frame(100, 100, "Frame", true);
        MoteurGraphique myMG = new MoteurGraphique(frame);
        IForme rectangle = new Rectangle(40, 40);
        Objet myO = new Objet(10, 10, rectangle, "java/graphique/objetToDisplay/pacman.png", 20, 20);
        myMG.displayObject(myO);
        frame.display();
    }


}
