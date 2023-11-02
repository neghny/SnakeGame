package kernel;

import physique.Rectangle;

import java.util.ArrayList;

public class Gameplay {

    ArrayList<Objet> serpent;
    Objet pomme;
    int score = 0;
    final int tailleBloc = 20;

    public Gameplay(Objet pomme) {
        this.pomme = pomme;
        this.serpent = new ArrayList<Objet>();
    }

    public void collisionSerpentPomme(){}

    public void collisionSerpent(){
        resetLevel();
    }

    public void collisionSerpentMur(){
        resetLevel();
    }

    public void resetLevel() {
        score = 0;
        for (Objet s : serpent)
            ctrl.destroy(s); // ctrl est la classe Control
        Objet bloc1 = createBlocSerpent(5*tailleBloc, 5*tailleBloc);
        Objet bloc2 = createBlocSerpent(4*tailleBloc, 5*tailleBloc);
        Objet bloc3 = createBlocSerpent(3*tailleBloc, 5*tailleBloc);
        serpent = new ArrayList<>();
        addObj(bloc1);
        addObj(bloc2);
        addObj(bloc3);
    }

    public Objet createBlocSerpent(int x, int y) {
        return new Objet(x, y, new Rectangle(tailleBloc, tailleBloc), "", tailleBloc, tailleBloc); // Mettre chemin de l'image.
    }

    public void addObj(Objet o) {
        serpent.add(o);
        ctrl.addObj(o); // Nouvelle méthode à créer.
    }

    public void replacerPomme(){}

}
