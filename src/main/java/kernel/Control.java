package kernel;

import graphique.MoteurGraphique;
import physique.Cercle;
import physique.Rectangle;

import java.util.LinkedList;

/* Control est le contrôle-commande du jeu-vidéo.
 * Il permet de mettre ensemble les différentes parties du jeu (physique, graphique).
 * Il est responsable de faire tourner le jeu.
 */

public class Control {
    boolean running = true;
    LinkedList<Objet> objs = new LinkedList<>();
    MoteurGraphique mg = new MoteurGraphique(1000,1000,"Frame", new KeyListenerKernel(objs));
    public Control() {}
    public void run() {
        while (running) {
            long startTime = System.currentTimeMillis();
            // 33 pour 30 frames par seconde.
            long expectedRestart = startTime + 33;
            // Gérer collisions
            for (Objet o1 : objs) {
                if (o1.getXposition() + o1.sizeImageX > 1000 || o1.getXposition() < 0){ // todo mettre width et height de la fenêtre dans des attributs
                    System.out.println("dépassement sur l'axe X");
                }
                if (o1.getYposition() + o1.sizeImageY > 1000 || o1.getYposition() < 0){
                    System.out.println("dépassement sur l'axe y");
                }
                for (Objet o2 : objs) {
                    if (o1 != o2 && o1.percute(o2)) {
                        o1.eventCollision(o2);
                    }
                }
            }
            // Dessiner
            mg.display(objs);
            long endTime = System.currentTimeMillis();
            if (endTime < expectedRestart)
                try { Thread.sleep(expectedRestart - endTime); } catch (InterruptedException e) { throw new RuntimeException(e); }
        }
    }
    public void addObj(Objet o) {
        objs.add(o);
    }

    public void stop() {
        running = false;
    }

    public static void main(String[] args) {
        var c = new Control();
        Objet pacman = new Objet(25, 25, new Cercle(60), "pacman.png", 120, 120);
        c.addObj(pacman);
        pacman.setSpeed(10);
        /*Objet monstre = new Objet(250, 0, new Rectangle(120, 120), "pink_ghost.png", 120, 120);
        c.addObj(monstre);*/
        for (int i = 710; i < 1000; i+=50){
            c.addObj(new Objet(i+100, 500, new Cercle(10), "ball.png", 20, 20));
            c.addObj(new Objet(i, 20, new Cercle(10), "ball.png", 20, 20));
            c.addObj(new Objet(100, i-150, new Cercle(10), "ball.png", 20, 20));
            c.addObj(new Objet(i-700, 300, new Cercle(10), "ball.png", 20, 20));
        }
        Objet monstreRose = new Objet(500, 50, new Rectangle(120, 120), "pink_ghost.png", 120, 120);
        c.addObj(monstreRose);
        Objet monstreBleu = new Objet(800, 300, new Rectangle(120, 120), "blue_ghost.png", 120, 120);
        c.addObj(monstreBleu);
        Objet monstreRouge = new Objet(400, 550, new Rectangle(120, 120), "red_ghost.png", 120, 120);
        c.addObj(monstreRouge);
        c.mg.init_display(c.objs);
        c.run();
    }
}

