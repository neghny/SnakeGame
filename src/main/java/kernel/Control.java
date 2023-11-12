package kernel;

import graphique.MoteurGraphique;
import physique.Cercle;
import physique.Rectangle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* Control est le contrôle-commande du jeu-vidéo.
 * Il permet de mettre ensemble les différentes parties du jeu (physique, graphique).
 * Il est responsable de faire tourner le jeu.
 */

public class Control {
    private static boolean running;
    private final MoteurGraphique moteurGraphique;
    private final int width;
    private final int height;
    private Gameplay gp;


    public Control(int width, int height) {
        this.width = width;
        this.height = height;
        running = true;
        moteurGraphique = new MoteurGraphique(width, height, "Frame");
        gp = new Gameplay(width, height);
        moteurGraphique.getMainFrame().addKeyListener(new KeyListenerKernel(gp.objets)); // est ce que ici c'est pas gp.sepent plutot ? la pomme s'en fout  du clavier ?
    }

    public boolean depassementBords(Objet o){
        return (o.getXposition() + o.getSizeImageX() > width)
                || (o.getXposition() < 0)
                || (o.getYposition() + o.getSizeImageY() > height)
                || (o.getYposition() < 0);
    }
    /*public void run() {
        *//*int width = 1000;
        int height = 1000;*//*

        long startTime;
        long expectedRestart;

        while (running) {
            startTime = System.currentTimeMillis();
            //expectedRestart = startTime + 33;
            expectedRestart = startTime + 100;

            *//*for (int i = 0; i < gp.objets.size(); i ++) {
                Objet o1 = gp.objets.get(i);
                if (o1.getXposition() + o1.getSizeImageX() > width) {
                    System.out.println("dépassement sur la droite de l'axe X");
                    o1.setPosition(width - o1.getSizeImageX(), o1.getYposition());

                }
                if (o1.getXposition() < 0) {
                    System.out.println("dépassement sur la gauche de l'axe X");
                    o1.setPosition(0, o1.getYposition());
                }
                if (o1.getYposition() + o1.getSizeImageY() > height){
                    System.out.println("dépassement sur le bas de l'axe Y");
                    o1.setPosition(o1.getXposition(), height-o1.getSizeImageY());
                }
                if (o1.getYposition() < 0){
                    System.out.println("dépassement sur le haut de l'axe Y");
                    o1.setPosition(o1.getXposition(), 0);
                }

                for (int j = i + 1; j < gp.objets.size(); j++) {
                    Objet o2 = gp.objets.get(j);
                    if (o1 != o2 && o1.percute(o2)) {
                        o1.eventCollision(o2);
                    }
                }
            }*//*
            *//*Objet teteSerpent = gp.serpent.get(0);

            if (teteSerpent.percute(gp.pomme)){
                gp.collisionSerpentPomme();
            }
            else if (depassementBords(teteSerpent)) { // la tête du serpent dépasse les bords
                gp.collisionSerpentMur();
            }
            else {
                for (int i = 1; i < gp.serpent.size(); i++) {
                    //if (teteSerpent.percute(gp.serpent.get(i))){
                    if (teteSerpent.getXposition() == gp.serpent.get(i).getXposition() && teteSerpent.getYposition() == gp.serpent.get(i).getYposition()) {
                        gp.collisionSerpent();
                    }
                }
            }*//*
            List<Objet[]> collisions = new ArrayList<>();
            for (int i = 0; i < gp.objets.size(); i ++) {
                Objet o1 = gp.objets.get(i);
                for (int j = i + 1; j < gp.objets.size(); j++) {
                    Objet o2 = gp.objets.get(j);
                    if (o1 != o2 && o1.percute(o2)) {
                        // o1.eventCollision(o2);
                        collisions.add(new Objet[]{o1, o2});
                    }
                }
            }
            gp.gestionCollisions(collisions);

            if (expectedRestart > System.currentTimeMillis()) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(expectedRestart - System.currentTimeMillis()); } catch (InterruptedException ignored) {}
            }
            moteurGraphique.display(gp.objets);
        }
        System.exit(0);
    }*/

    public void run() {
        long startTime;
        long expectedRestart;

        while (running) {
            startTime = System.currentTimeMillis();
            expectedRestart = startTime + 100;
            gp.gestionCollisions();
            System.out.println("--------------------------------");
            for (Objet k:gp.serpent){
                System.out.println(k.getXposition() + " " + k.getYposition() + "");
            }
            System.out.println("--------------------------------");
            if (expectedRestart > System.currentTimeMillis()) {
                try {
                    Thread.sleep(expectedRestart - System.currentTimeMillis()); } catch (InterruptedException ignored) {}
            }
            moteurGraphique.display(gp.objets);
        }
        System.exit(0);
    }

    public void addObjet(Objet o) {
        gp.addObj(o);
    }

    public LinkedList<Objet> getObjets() {
        return gp.objets;
    }

    public MoteurGraphique getMoteurGraphique() {
        return moteurGraphique;
    }

    public static void setRunning(boolean running) {
        Control.running = running;
    }

    public static void main(String[] args) {
        var c = new Control(1000, 1000);
//        Objet pacman = new Objet(25, 25, new Cercle(60), "pacman.png", 120, 120);
//        c.addObjet(pacman);
//        pacman.setSpeed(10);
//        Objet monstre = new Objet(250, 0, new Rectangle(120, 120), "pink_ghost.png", 120, 120);
//        c.addObjet(monstre);
//        c.getMoteurGraphique().init_display(c.getObjets());
        /*Objet monstre = new Objet(250, 0, new Rectangle(120, 120), "pink_ghost.png", 120, 120);
        c.addObj(monstre);*/
        c.getMoteurGraphique().init_display(c.gp.objets);
//        for (int i = 710; i < 1000; i+=50){
//            c.addObjet(new Objet(i, 500, new Cercle(10), "ball.png", 20, 20));
//            c.addObjet(new Objet(i-100, 20, new Cercle(10), "ball.png", 20, 20));
//            c.addObjet(new Objet(100, i-150, new Cercle(10), "ball.png", 20, 20));
//            c.addObjet(new Objet(i-700, 300, new Cercle(10), "ball.png", 20, 20));
//        }
//        Objet monstreRose = new Objet(500, 50, new Rectangle(120, 120), "pink_ghost.png", 120, 120);
//        c.addObjet(monstreRose);
//        Objet monstreBleu = new Objet(800, 300, new Rectangle(120, 120), "blue_ghost.png", 120, 120);
//        c.addObjet(monstreBleu);
//        Objet monstreRouge = new Objet(400, 550, new Rectangle(120, 120), "red_ghost.png", 120, 120);
//        c.addObjet(monstreRouge);
//        c.getMoteurGraphique().init_display(c.getObjets());
        c.run();
    }
}

