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
        moteurGraphique.getMainFrame().addKeyListener(new KeyListenerKernel(objets));
    }
    public void run() {
        long startTime = System.currentTimeMillis();
        long expectedRestart = startTime + 33;

        while (running && startTime < expectedRestart) {
            startTime = System.currentTimeMillis();
            expectedRestart = startTime + 33;

            for (Objet o1 : objets) {
                if (o1.getXposition() + o1.getSizeImageX() > 1000 || o1.getXposition() < 0){ // todo mettre width et height de la fenêtre dans des attributs
                    System.out.println("dépassement sur l'axe X");
                }
                if (o1.getYposition() + o1.getSizeImageY() > 1000 || o1.getYposition() < 0){
                    System.out.println("dépassement sur l'axe y");
                }
                for (Objet o2 : objets) {
                    if (o1 != o2 && o1.percute(o2)) {
                        o1.eventCollision(o2);
                    }
                }
            }
            // Dessiner
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
        Objet pacman = new Objet(25, 25, new Cercle(60), "pacman.png", 120, 120);
        c.addObjet(pacman);
        pacman.setSpeed(10);
        Objet monstre = new Objet(250, 0, new Rectangle(120, 120), "pink_ghost.png", 120, 120);
        c.addObjet(monstre);
        c.getMoteurGraphique().init_display(c.getObjets());
        c.run();
    }
}

