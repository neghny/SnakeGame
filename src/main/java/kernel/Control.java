package kernel;

import graphique.MoteurGraphique;
import java.util.ArrayList;
import java.util.List;

/**
 * Control est le contrôle-commande du jeu-vidéo.
 * Il permet de mettre ensemble les différentes parties du jeu (physique, graphique).
 * Il est responsable de faire tourner le jeu.
 */
public class Control {
    private static Control INSTANCE;
    private boolean running;

    private Control() {
        running = true;
    }

    public static Control getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Control();
        }
        return INSTANCE;
    }

    public void run() {
        long startTime;
        long expectedRestart;

        MoteurGraphique.getInstance().init_display(Gameplay.getInstance().getObjets());

        while (running) {
            startTime = System.currentTimeMillis();
            expectedRestart = startTime + 100;

            Gameplay.getInstance().mvtSnake();

            List<Objet[]> collisions = new ArrayList<>();
            for (int i = 0; i < Gameplay.getInstance().getObjets().size(); i++) {
                Objet objet1 = Gameplay.getInstance().getObjets().get(i);

                for (int j = i + 1; j < Gameplay.getInstance().getObjets().size(); j++) {
                    Objet objet2 = Gameplay.getInstance().getObjets().get(j);

                    if (objet1 != objet2 && objet1.percute(objet2)) {
                        collisions.add(new Objet[]{objet1, objet2});
                    }
                }
            }
            Gameplay.getInstance().gestionCollisions(collisions);

            System.out.println("--------------------------------");
            for (Objet objet : Gameplay.getInstance().getSerpent()) {
                System.out.println(objet.getXposition() + " " + objet.getYposition());
            }
            System.out.println("--------------------------------");

            if (expectedRestart > System.currentTimeMillis()) {
                try {
                    Thread.sleep(expectedRestart - System.currentTimeMillis());
                } catch (InterruptedException ignored) {
                }
            }
            MoteurGraphique.getInstance().display(Gameplay.getInstance().getObjets());
        }
        System.exit(0);
    }

    public void exitGame() {
        this.running = false;
    }

    public static void main(String[] args) {
        Control.getInstance().run();
    }
}

