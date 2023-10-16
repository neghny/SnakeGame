package kernel;

import graphique.MoteurGraphique;

import java.util.ArrayList;

public class Control {
    boolean running = true;
    MoteurGraphique mg = new MoteurGraphique();
    public Control(ArrayList<Objet> objs) {
        while (running) {
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
        }
    }

    public void stop() {
        running = false;
    }
}
