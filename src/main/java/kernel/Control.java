package kernel;

import graphique.MoteurGraphique;

/**
 * Control est le contrôle-commande du jeu-vidéo.
 * Il permet de mettre ensemble les différentes parties du jeu (physique, graphique).
 * Il est responsable de faire tourner le jeu.
 */
public class Control {
    private static Control INSTANCE;
    private final MoteurGraphique moteurGraphique;
    private final Gameplay gameplay;
    private boolean running;

    private Control() {
        running = true;
        moteurGraphique = MoteurGraphique.getInstance();
        gameplay = Gameplay.getInstance();
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

        gameplay.startGame();
        moteurGraphique.init_display(gameplay.getObjets());

        while (running) {
            startTime = System.currentTimeMillis();
            expectedRestart = startTime + 100;

            gameplay.growSnake();
            gameplay.handleCollision();
            //gameplay.printSnakePosition();

            if (expectedRestart > System.currentTimeMillis()) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(expectedRestart - System.currentTimeMillis());
                } catch (InterruptedException ignored) {
                }
            }
            moteurGraphique.display(gameplay.getObjets());
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

