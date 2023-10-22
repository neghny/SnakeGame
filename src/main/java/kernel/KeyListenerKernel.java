package kernel;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class KeyListenerKernel implements java.awt.event.KeyListener {
    private final LinkedList<Objet> objets;

    public KeyListenerKernel(LinkedList<Objet> objets) {
        this.objets = objets;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressed = e.getKeyCode();

        for (Objet objet : objets) {
            if (keyPressed == KeyEvent.VK_RIGHT) {
                objet.hspeed = 3;
            } else if (keyPressed == KeyEvent.VK_LEFT) {
                objet.hspeed = -3;
            } else if (keyPressed == KeyEvent.VK_UP) {
                objet.vspeed = -3;
            } else if (keyPressed == KeyEvent.VK_DOWN) {
                objet.vspeed = 3;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyReleased = e.getKeyCode();

        for (Objet objet : objets) {
            if (keyReleased == KeyEvent.VK_RIGHT || keyReleased == KeyEvent.VK_LEFT) {
                objet.hspeed = 0;
            }
            if (keyReleased == KeyEvent.VK_UP || keyReleased == KeyEvent.VK_DOWN) {
                objet.vspeed = 0;
            }
        }

        if (keyReleased == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
