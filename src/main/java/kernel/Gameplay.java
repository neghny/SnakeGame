package kernel;

import physique.Rectangle;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Gameplay {

    LinkedList<Objet> serpent;
    LinkedList<Objet> objets;
    Objet pomme;
    int score = 0;
    final int tailleBloc = 50;
    private final int width;
    private final int height;
    private boolean growSnake = false;


    public Gameplay(int width, int height) {
        this.pomme = new Objet(0, 0, new Rectangle(tailleBloc - 2, tailleBloc - 2), "pomme.png", tailleBloc, tailleBloc);
        this.width = width;
        this.height = height;
//        this.serpent = new LinkedList<>();
        resetLevel();
    }

    public void gestionCollisions(List<Objet[]> collisions){
        Objet teteSerpent = getTeteSerpent();

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
    }
/*

*/

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

    public Objet getTeteSerpent() {
        return serpent.get(0);
    }

    public void mvtSnake() {
        Objet teteSerpent = getTeteSerpent();
        if (teteSerpent.getSpeed() > 0) {
            if (growSnake) {
                for (Objet o : objets)
                    System.out.println("Sprite :" + o.pathImage + "; X Obj : " + o.getXposition() + "; X affich :" + o.getX() + "; Y Obj : " + o.getYposition() + "; Y affich :" + o.getY());
                Objet dernier = serpent.getLast();
                addObjSerpent(createBlocSerpent(dernier.getXposition(), dernier.getYposition()));
                growSnake = false;
            }
            for (int i = serpent.size() - 1; i > 0; i--) {
                Objet suivant = serpent.get(i - 1);
                serpent.get(i).setPosition(suivant.getXposition(), suivant.getYposition());
            }
        }
        // Bouger la tête du serpent avec hspeed et vspeed.
        teteSerpent.updatePosition();
    }

    public void changerDirection(KeyEvent e) {
        int keyPressed = e.getKeyCode();
        Objet teteSerpent = getTeteSerpent();
        if (keyPressed == KeyEvent.VK_RIGHT) {
            teteSerpent.hspeed = tailleBloc;
            teteSerpent.vspeed = 0;
        } else if (keyPressed == KeyEvent.VK_LEFT) {
            teteSerpent.hspeed = -tailleBloc;
            teteSerpent.vspeed = 0;
        } else if (keyPressed == KeyEvent.VK_UP) {
            teteSerpent.vspeed = -tailleBloc;
            teteSerpent.hspeed = 0;
        } else if (keyPressed == KeyEvent.VK_DOWN) {
            teteSerpent.vspeed = tailleBloc;
            teteSerpent.hspeed = 0;
        }
    }

    public boolean depassementBords(Objet o){
        return (o.getXposition() + o.getSizeImageX() > width)
                || (o.getXposition() < 0)
                || (o.getYposition() + o.getSizeImageY() > height)
                || (o.getYposition() < 0);
    }

    public void collisionSerpentPomme(){
        score += 1;
        //Objet dernierSerpent = serpent.get(serpent.size()-1);
        //Objet nouveauBloc = createBlocSerpent(dernierSerpent.getXposition()+50, dernierSerpent.getYposition()+50);
        //addObjSerpent(nouveauBloc);
        growSnake = true;
        replacerPomme();
    }

    /*public void collisionSerpentPomme(){
        score += 1;
        growSnake = true;
        //Objet teteSerpent = getTeteSerpent();
        //addObjSerpent(createBlocSerpent(pomme.getX() + teteSerpent.hspeed, pomme.getY() + teteSerpent.vspeed));
        replacerPomme();
    }*/

    public void collisionSerpent(){
        System.out.println("Collision Serpent");
        gameOver();
    }

    public void collisionSerpentMur(){
        System.out.println("Collision Mur");
        gameOver();
    }

    public void resetLevel() { // si on a en parallele une liste objets, est ce qu'il faut pas supprimer les anciens membres du serpent de cette liste ?
        score = 0;
        // On réinitialise le serpent.
        Objet bloc1 = createBlocSerpent(5*tailleBloc, 5*tailleBloc);
        Objet bloc2 = createBlocSerpent(4*tailleBloc, 5*tailleBloc);
        Objet bloc3 = createBlocSerpent(3*tailleBloc, 5*tailleBloc);
        objets = new LinkedList<>();
        addObj(pomme);
        serpent = new LinkedList<>();
        addObjSerpent(bloc1);
        addObjSerpent(bloc2);
        addObjSerpent(bloc3);
        // On réinitialise la pomme.
        replacerPomme();
    }

    public Objet createBlocSerpent(int x, int y) {
        return new Objet(x, y, new Rectangle(tailleBloc - 2, tailleBloc - 2), "bloc.png", tailleBloc, tailleBloc);
    }

    public void addObjSerpent(Objet o) {
        //serpent.add(0, o);  // le nouvel élément ajouté à la place de la pomme est la nouvelle tête du serpent je pense
        serpent.add(o); // le nouvel élément est ajouté à la queue du serpent.
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

    public void mangerPomme(){
        collisionSerpentPomme();
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