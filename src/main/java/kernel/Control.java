package kernel;

import graphique.MoteurGraphique;

/**
 * Le core-kernel est le cœur du moteur de jeu. Il agit comme une couche intermédiaire entre les moteurs graphique et
 * physique, coordonnant l'ensemble des modules du jeu. Ses principales responsabilités comprennent la réception des
 * entrées clavier provenant de l'interface utilisateur et les collisions du moteur physique.
 */
public class Control {
    private static Control INSTANCE;
    private final MoteurGraphique moteurGraphique;
    private final Gameplay gameplay;
    private boolean running;
    public int frameLength = 100;

    /**
     * @author Matteo
     */
    private Control() {
        running = true;
        moteurGraphique = MoteurGraphique.getInstance();
        gameplay = Gameplay.getInstance();
    }

    /**
     * @author Matteo
     */
    public static Control getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Control();
        }
        return INSTANCE;
    }

    /**
     * Boucle du jeu.
     * Chaque pas a une durée déterminée : il commence au temps startTime et se termine au temps expectedRestart.
     * Dans chaque pas, le mouvement du Snake, les collisions, l'affichage des objets.
     */
    public void run() {
        long startTime;
        long expectedRestart;

        gameplay.showMainMenu();

        while (running) {
            startTime = System.currentTimeMillis();
            expectedRestart = startTime + frameLength;


            gameplay.moveSnake();
            gameplay.handleCollision();

            if (expectedRestart > System.currentTimeMillis()) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(expectedRestart - System.currentTimeMillis());
                } catch (InterruptedException ignored) {
                }
            }
            moteurGraphique.display(gameplay.getObjets());
            gameplay.updateScore();
        }
        System.exit(0);
    }

    /**
     * @author Matteo
     */
    public void exitGame() {
        this.running = false;
    }

    /**
     * C'est l'entrée principale du jeu.
     * @author Julien
     */
    public static void main(String[] args) {
        Control.getInstance().run();
    }
}

