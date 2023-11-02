package kernel;

import java.util.ArrayList;

public class Gameplay {

    ArrayList<Objet> serpent;
    Objet pomme;


    public Gameplay(Objet pomme) {
        this.pomme = pomme;
        this.serpent = new ArrayList<Objet>();
    }

    public void collisionSerpentPomme(){}

    public void collisionSerpent(){}

    public void collisionSerpentMur(){}

    public void replacerPomme(){}

}
