package kernel;

import graphique.MoteurGraphique;
import physique.Cercle;

import java.util.LinkedList;

public class Control {
    boolean running = true;
    MoteurGraphique mg = new MoteurGraphique();
    LinkedList<Objet> objs = new LinkedList<>();
    public Control() {
        while (running) {
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
            mg.display();
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
        c.addObj(new Objet(10, 10, new Cercle(20)));
    }
}
