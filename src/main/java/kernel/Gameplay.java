package kernel;

import physique.Rectangle;

import java.util.LinkedList;
import java.util.Random;

public class Gameplay {

    LinkedList<Objet> serpent;
    LinkedList<Objet> objets = new LinkedList<Objet>();
    Objet pomme;
    int score = 0;
    final int tailleBloc = 20;
    private final int width;
    private final int height;


    public Gameplay(int width, int height) {
        this.pomme = new Objet(0, 0, new Rectangle(tailleBloc, tailleBloc), "", tailleBloc, tailleBloc);
        this.width = width;
        this.height = height;
        this.serpent = new LinkedList<Objet>();
    }

    public void collisionSerpentPomme(){
        score += 1;
        addObjSerpent(createBlocSerpent(pomme.getX(), pomme.getY()));
        replacerPomme();
    }

    public void collisionSerpent(){
        gameOver();

        }



    public void collisionSerpentMur(){
        resetLevel();
    }

    public void resetLevel() {
        score = 0;
        // On réinitialise le serpent.
        Objet bloc1 = createBlocSerpent(5*tailleBloc, 5*tailleBloc);
        Objet bloc2 = createBlocSerpent(4*tailleBloc, 5*tailleBloc);
        Objet bloc3 = createBlocSerpent(3*tailleBloc, 5*tailleBloc);
        serpent = new LinkedList<>();
        addObjSerpent(bloc1);
        addObjSerpent(bloc2);
        addObjSerpent(bloc3);
        // On réinitialise la pomme.
        replacerPomme();
    }

    public Objet createBlocSerpent(int x, int y) {
        return new Objet(x, y, new Rectangle(tailleBloc, tailleBloc), "", tailleBloc, tailleBloc); // Mettre chemin de l'image.
    }

    public void addObjSerpent(Objet o) {
        serpent.add(o);
        addObj(o);
    }

    public void addObj(Objet o) {
        objets.add(o);
    }

    /**
     * Replace la pomme à une position aléatoire, en prenant en compte la position du serpent, la taille de la fenête
     */
    public void replacerPomme() {
        int newWidth;
        int newHeight;
        boolean verifPosition;
        do {
            Random random = new Random();
            newWidth = random.nextInt(width / tailleBloc) * tailleBloc;
            newHeight = random.nextInt(height / tailleBloc) * tailleBloc;
            verifPosition = true;

            for (Objet element : serpent) {
                if (element.getXposition() == newWidth && element.getYposition() == newHeight) {
                    verifPosition = false;
                }
            }
        }
        while (!verifPosition);

    }

    public void gameOver() {
        System.out.println("Game Over!");
        System.out.println("Score final : " + score);
        //on réinitialise le jeu
        resetLevel();



    }


}