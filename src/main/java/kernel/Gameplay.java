package kernel;

import physique.Rectangle;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Gameplay {

    LinkedList<Objet> serpent;
    LinkedList<Objet> objets = new LinkedList<Objet>();
    Objet pomme;
    int score = 0;
    final int tailleBloc = 50;
    private final int width;
    private final int height;


    public Gameplay(int width, int height) {
        this.pomme = new Objet(0, 0, new Rectangle(tailleBloc, tailleBloc), "pomme.png", tailleBloc, tailleBloc);
        objets.add(pomme);
        this.width = width;
        this.height = height;
//        this.serpent = new LinkedList<Objet>();
        resetLevel();
    }

    public void gestionCollisions(List<Objet[]> collisions){
        Objet teteSerpent = serpent.get(0);

        for (Objet[] collision : collisions){
            if (collision[0] == teteSerpent || collision[1] == teteSerpent){
                if (collision[0] == pomme || collision[1] == pomme){
                    collisionSerpentPomme();
                } else if (
                        collision[0] == teteSerpent && serpent.contains(collision[1])
                                || collision[1] == teteSerpent && serpent.contains(collision[0])
                ) {
                    collisionSerpent();
                }
            }
        }

        if (depassementBords(teteSerpent)){
            collisionSerpentMur();
        }


        /*if (teteSerpent.percute(pomme)){
            collisionSerpentPomme();
        }
        else if (depassementBords(teteSerpent)) { // la tête du serpent dépasse les bords
            collisionSerpentMur();
        }
        else {
            for (int i = 1; i < serpent.size(); i++) {
                //if (teteSerpent.percute(gp.serpent.get(i))){
                if (teteSerpent.getXposition() == serpent.get(i).getXposition() && teteSerpent.getYposition() == serpent.get(i).getYposition()) {
                    collisionSerpent();
                }
            }
        }*/

    }

    public void mvtSnake() {
        Objet teteSerpent = serpent.get(0);
        for (int i = 1; i < serpent.size(); i++)
        {
            Objet suivant = serpent.get(i + 1);
            serpent.get(i).setPosition(suivant.getXposition(), suivant.getYposition());
        }
        // Bouger la tête du serpent...
    }

    public boolean depassementBords(Objet o){
        return (o.getXposition() + o.getSizeImageX() > width)
                || (o.getXposition() < 0)
                || (o.getYposition() + o.getSizeImageY() > height)
                || (o.getYposition() < 0);
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
        gameOver();
    }

    public void resetLevel() { // si on a en parallele une liste objets, est ce qu'il faut pas supprimer les anciens membres du serpent de cette liste ?
        score = 0;
        // On réinitialise le serpent.
        Objet bloc1 = createBlocSerpent(5*tailleBloc, 5*tailleBloc);
        Objet bloc2 = createBlocSerpent(4*tailleBloc, 5*tailleBloc);
        Objet bloc3 = createBlocSerpent(3*tailleBloc, 5*tailleBloc);
        bloc1.setSpeed(tailleBloc); //c'était pour tester si le serpent bouge (c'est bien le cas mais pas correctement pour l'instant)
        bloc2.setSpeed(tailleBloc);
        bloc3.setSpeed(tailleBloc);
        serpent = new LinkedList<>();
        addObjSerpent(bloc1);
        addObjSerpent(bloc2);
        addObjSerpent(bloc3);
        // On réinitialise la pomme.
        replacerPomme();
    }

    public Objet createBlocSerpent(int x, int y) {
        return new Objet(x, y, new Rectangle(tailleBloc, tailleBloc), "bloc.png", tailleBloc, tailleBloc); // Mettre chemin de l'image.
    }

    public void addObjSerpent(Objet o) {
        serpent.add(0, o);  // le nouvel élément ajouté à la place de la pomme est la nouvelle tête du serpent je pense
        addObj(o);
    }

    public void addObj(Objet o) {
        objets.add(o);
    }

    /**
     * Replace la pomme à une position aléatoire, en prenant en compte la position du serpent, la taille de la fenêtre
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
        } while (!verifPosition);
        pomme.setPosition(newWidth, newHeight);
    }

    public void gameOver() {
        System.out.println("Game Over!");
        System.out.println("Score final : " + score);
        //on réinitialise le jeu
        resetLevel();



    }


    public LinkedList<Objet> getSerpent() {
        return serpent;
    }

    public LinkedList<Objet> getObjets() {
        return objets;
    }

    public Objet getPomme() {
        return pomme;
    }

    public int getScore() {
        return score;
    }

    public int getTailleBloc() {
        return tailleBloc;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}