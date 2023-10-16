package graphique;

import kernel.Objet;

import java.util.LinkedList;

public class MoteurGraphique {
    Frame mainFrame;

    public MoteurGraphique(Frame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void displayObject(Objet o){ // affiche un objet

    }

    public void display(LinkedList<Objet> objects){ // affiche tout
        for (Objet o : objects){
            displayObject(o);
        }
    }



}
