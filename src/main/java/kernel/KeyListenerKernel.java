package kernel;

import java.awt.event.KeyEvent;
import java.sql.SQLOutput;
import java.util.LinkedList;

public class KeyListenerKernel implements java.awt.event.KeyListener {
    private LinkedList<Objet> objets;

    public KeyListenerKernel(LinkedList<Objet> objets) {
        this.objets = objets;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressed = e.getKeyCode();

        for (Objet objet : objets) {
            if (keyPressed == KeyEvent.VK_RIGHT) {
                objet.moveRight();
            } else if (keyPressed == KeyEvent.VK_LEFT) {
                objet.moveLeft();
            } else if (keyPressed == KeyEvent.VK_UP) {
                objet.moveUp();
            } else if (keyPressed == KeyEvent.VK_DOWN) {
                objet.moveDown();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyTyped = e.getKeyCode();

        if (keyTyped == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
