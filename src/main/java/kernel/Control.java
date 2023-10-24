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
    private static boolean running;
    private final LinkedList<Objet> objets;
    private final MoteurGraphique moteurGraphique;

    public Control() {
        running = true;
        objets = new LinkedList<>();
        moteurGraphique = new MoteurGraphique(1000, 1000, "Frame");
        // Pacman has to be the first added object for now
        Objet pacman = new Objet(25, 25, new Cercle(60), "pacman.png", 120, 120);
        addObjet(pacman);
        pacman.setSpeed(10);
        moteurGraphique.getMainFrame().addKeyListener(new KeyListenerKernel(objets.get(0)));
    }

    public void run() {
        int width = 1000;
        int height = 1000;

        long startTime;
        long expectedRestart;

        while (running) {
            startTime = System.currentTimeMillis();
            expectedRestart = startTime + 33;

            for (int i = 0; i < objets.size(); i++) {
                Objet objet = objets.get(i);
                objet.move();

               objet.detectCollision(width, height);
                //for (Objet o2 : objs) {
                for (int j = i + 1; j < objets.size(); j++) {
                    Objet o2 = objets.get(j);
                    if (objet != o2 && objet.percute(o2)) {
                        objet.eventCollision(o2);
                    }
                }
            }
            if (expectedRestart > System.currentTimeMillis()) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(expectedRestart - System.currentTimeMillis());
                } catch (InterruptedException ignored) {
                }
            }
            moteurGraphique.display(objets);
        }
        System.exit(0);
    }

    public void addObjet(Objet o) {
        objets.add(o);
    }

    public LinkedList<Objet> getObjets() {
        return objets;
    }

    public MoteurGraphique getMoteurGraphique() {
        return moteurGraphique;
    }

    public static void setRunning(boolean running) {
        Control.running = running;
    }

    public static void main(String[] args) {
        var c = new Control();
        Objet monstre = new Objet(250, 0, new Rectangle(120, 120), "pink_ghost.png", 120, 120);
        c.addObjet(monstre);
        c.getMoteurGraphique().init_display(c.getObjets());
        /*Objet monstre = new Objet(250, 0, new Rectangle(120, 120), "pink_ghost.png", 120, 120);
        c.addObj(monstre);*/
        for (int i = 710; i < 1000; i += 50) {
            c.addObjet(new Objet(i, 500, new Cercle(10), "ball.png", 20, 20));
            c.addObjet(new Objet(i - 100, 20, new Cercle(10), "ball.png", 20, 20));
            c.addObjet(new Objet(100, i - 150, new Cercle(10), "ball.png", 20, 20));
            c.addObjet(new Objet(i - 700, 300, new Cercle(10), "ball.png", 20, 20));
        }
        Objet monstreRose = new Objet(500, 50, new Rectangle(120, 120), "pink_ghost.png", 120, 120);
        c.addObjet(monstreRose);
        Objet monstreBleu = new Objet(800, 300, new Rectangle(120, 120), "blue_ghost.png", 120, 120);
        c.addObjet(monstreBleu);
        Objet monstreRouge = new Objet(400, 550, new Rectangle(120, 120), "red_ghost.png", 120, 120);
        c.addObjet(monstreRouge);
        c.getMoteurGraphique().init_display(c.getObjets());
        c.run();
    }
}

