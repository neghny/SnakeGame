package kernel;

import graphique.MoteurGraphique;
import physique.Cercle;
import physique.Rectangle;

import java.util.LinkedList;

/**
 * Control est le contrôle-commande du jeu-vidéo.
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
            for (Objet o1 : objs)
                for (Objet o2 : objs)
                    if (o1 != o2 && o1.percute(o2))
                        o1.eventCollision(o2);
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
        pacman.setSpeed(3);
        Objet monstre = new Objet(250, 0, new Rectangle(300, 300), "pink_ghost.png", 300, 300);
        c.addObj(monstre);
        c.mg.init_display(c.objs);
        c.run();
    }
}

