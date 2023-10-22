package kernel;

import graphique.Frame;
import graphique.MoteurGraphique;
import physique.Cercle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

/**
 * Control est le contrôle-commande du jeu-vidéo.
 * Il permet de mettre ensemble les différentes parties du jeu (physique, graphique).
 * Il est responsable de faire tourner le jeu.
 */
public class Control implements KeyListener {
    boolean running = true;
    MoteurGraphique mg = new MoteurGraphique(new Frame(1280, 720, "Test", true));
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
        c.addObj(new Objet(10, 10, new Cercle(20)));
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressed = e.getKeyCode();

        if (keyPressed == KeyEvent.VK_RIGHT) {
            System.out.println("Right arrow pressed");
        } else if (keyPressed == KeyEvent.VK_LEFT) {
            System.out.println("Left arrow pressed");
        } else if (keyPressed == KeyEvent.VK_ESCAPE) {
            System.out.println("Escape key pressed, exiting");
            System.exit(0);
        } else if (keyPressed == KeyEvent.VK_D) {
            System.out.println("D key pressed");
        } else {
            System.out.println(keyPressed);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Do nothing
    }
    @Override
    public void keyTyped(KeyEvent e) {
        int keyTyped = e.getKeyCode();

        if (keyTyped == KeyEvent.VK_SPACE) {
            System.out.println("JUMP!");
        }
    }
}
