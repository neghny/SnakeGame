package kernel;

import graphique.MoteurGraphique;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Control est le contrôle-commande du jeu-vidéo.
 * Il permet de mettre ensemble les différentes parties du jeu (physique, graphique).
 * Il est responsable de faire tourner le jeu.
 */
public class Control {
    private static boolean running;

    public Control() {
        running = true;
    }

    public void run() {
        long startTime;
        long expectedRestart;

        while (running) {
            startTime = System.currentTimeMillis();
            expectedRestart = startTime + 100;
            Gameplay.getInstance().mvtSnake();
            List<Objet[]> collisions = new ArrayList<>();
            for (int i = 0; i < Gameplay.getInstance().getObjets().size(); i ++) {
                Objet o1 = Gameplay.getInstance().getObjets().get(i);
                for (int j = i + 1; j < Gameplay.getInstance().getObjets().size(); j++) {
                    Objet o2 = Gameplay.getInstance().getObjets().get(j);
                    if (o1 != o2 && o1.percute(o2)) {
                        // o1.eventCollision(o2);
                        collisions.add(new Objet[]{o1, o2});
                    }
                }
            }
            Gameplay.getInstance().gestionCollisions(collisions);

            //Affichage contenant liste Serpent
            System.out.println("--------------------------------");
            for (Objet k: Gameplay.getInstance().getSerpent()){
                System.out.println(k.getXposition() + " " + k.getYposition());
            }
            System.out.println("--------------------------------");

            if (expectedRestart > System.currentTimeMillis()) {
                try {
                    Thread.sleep(expectedRestart - System.currentTimeMillis()); } catch (InterruptedException ignored) {}
            }
            MoteurGraphique.getInstance().display(Gameplay.getInstance().getObjets());
        }
        System.exit(0);
    }

    public static void setRunning(boolean running) {
        Control.running = running;
    }

    public static void main(String[] args) {
        var c = new Control();
        MoteurGraphique.getInstance().init_display(Gameplay.getInstance().getObjets());
        c.run();
    }
}

