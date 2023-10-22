package kernel;

import graphique.MoteurGraphique;
import physique.Cercle;

import java.util.LinkedList;

/**
 * Control est le contrôle-commande du jeu-vidéo.
 * Il permet de mettre ensemble les différentes parties du jeu (physique, graphique).
 * Il est responsable de faire tourner le jeu.
 */
public class Control {
    boolean running = true;
    MoteurGraphique mg = new MoteurGraphique(1000,1000,"Frame");
    LinkedList<Objet> objs = new LinkedList<>();
    public Control() {}
    public void run() {
        while (running) {
            System.out.println(objs.get(0).x);
            long startTime = System.currentTimeMillis();
            // 33 pour 30 frames par seconde.
            long expectedRestart = startTime + 33;
            // Update positions
            for (Objet o : objs)
                o.updatePosition();
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
        c.run();
    }
}
