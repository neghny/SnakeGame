package kernel;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class KeyListenerKernel implements java.awt.event.KeyListener {
    private final LinkedList<Objet> objets;
    private boolean left,up,down,right;

    public KeyListenerKernel(LinkedList<Objet> objets) {
        this.objets = objets;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressed = e.getKeyCode();

        for (Objet objet : objets) {
            if (keyPressed == KeyEvent.VK_RIGHT) {
                right = true;
                objet.setRotation(2*Math.PI);
            } else if (keyPressed == KeyEvent.VK_LEFT) {
                left = true;
                objet.setRotation(Math.PI);
            } else if (keyPressed == KeyEvent.VK_UP) {
                up = true;
                objet.setRotation(3*Math.PI/2);
            } else if (keyPressed == KeyEvent.VK_DOWN) {
                down = true;
                objet.setRotation(Math.PI/2);
            }
            objet.move(left,up,down,right);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyReleased = e.getKeyCode();
        if (keyReleased == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

        for (Objet objet : objets) {
            if (keyReleased == KeyEvent.VK_RIGHT) {
                right = false;
            } else if (keyReleased == KeyEvent.VK_LEFT) {
                left = false;
            } else if (keyReleased == KeyEvent.VK_UP) {
                up = false;
            } else if (keyReleased == KeyEvent.VK_DOWN) {
                down = false;
            }
            objet.move(left,up,down,right);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
