package graphique;

import kernel.Objet;

import java.io.IOException;
import java.util.List;

public class MoteurGraphique {
    Frame mainFrame;

    public MoteurGraphique(Frame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void displayObject(Object o){ // affiche un objet

    }

    public void display(List<Objet> objects){ // affiche tout
        for (Objet o : objects){
            displayObject(o);
        }
    }



}
