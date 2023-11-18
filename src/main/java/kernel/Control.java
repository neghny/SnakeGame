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
    private final MoteurGraphique mg;
    private final int width;
    private final int height;
    private final Gameplay gp;


    public Control(int width, int height) {
        this.width = width;
        this.height = height;
        running = true;
        mg = new MoteurGraphique(width, height, "Frame");
        gp = new Gameplay(width, height);
        mg.getMainFrame().addKeyListener(new KeyListenerKernel(gp)); // est ce que ici c'est pas gp.sepent plutot ? la pomme s'en fout  du clavier ?
    }

    /**
     * Fonction principale du jeu. Elle gère le passage d'une frame à la suivante, en effectuant les opérations
     * nécessaires au bon fonctionnement du jeu : demander la mise à jour de l'emplacement du serpent, détecter les
     * collisions et les envoyer au moteur gameplay, mettre à jour l'affichage
     */
    public void run() {
        long startTime;
        long expectedRestart;

        while (running) {
            startTime = System.currentTimeMillis();
            expectedRestart = startTime + 100;
            gp.mvtSnake(); // je pense qu'on va avoir la remarque que c'est propre au snake et donc que ça devrait pas être dans Control
            List<Objet[]> collisions = new ArrayList<>();
            for (int i = 0; i < gp.objets.size(); i ++) {
                Objet o1 = gp.objets.get(i);
                for (int j = i + 1; j < gp.objets.size(); j++) {
                    Objet o2 = gp.objets.get(j);
                    if (o1 != o2 && o1.percute(o2)) {
                        // o1.eventCollision(o2);
                        collisions.add(new Objet[]{o1, o2});
                    }
                }
            }
            gp.gestionCollisions(collisions);

            //Affichage contenant liste Serpent
            System.out.println("--------------------------------");
            for (Objet k:gp.serpent){
                System.out.println(k.getXposition() + " " + k.getYposition() + "");
            }
            System.out.println("--------------------------------");

            if (expectedRestart > System.currentTimeMillis()) {
                try {
                    Thread.sleep(expectedRestart - System.currentTimeMillis()); } catch (InterruptedException ignored) {}
            }
            mg.display(gp.objets);
        }
        System.exit(0);
    }

    public void addObjet(Objet o) {
        gp.addObj(o);
    }

    public LinkedList<Objet> getObjets() {
        return gp.objets;
    }

    public MoteurGraphique getMoteurGraphique() {
        return mg;
    }

    public static void setRunning(boolean running) {
        Control.running = running;
    }

    public static void main(String[] args) {
        var c = new Control(1000, 1000);
        c.getMoteurGraphique().init_display(c.gp.objets);
        c.run();
    }
}

