package kernel;

import java.awt.event.KeyEvent;

public class KeyListenerKernel implements java.awt.event.KeyListener {
    private final Objet objet;

    public KeyListenerKernel(Objet objets) {
        this.objet = objets;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressed = e.getKeyCode();

        if (keyPressed == KeyEvent.VK_RIGHT) {
            objet.setDirection(Objet.Direction.RIGHT);

        } else if (keyPressed == KeyEvent.VK_LEFT) {
            objet.setDirection(Objet.Direction.LEFT);

        } else if (keyPressed == KeyEvent.VK_UP) {
            objet.setDirection(Objet.Direction.UP);

        } else if (keyPressed == KeyEvent.VK_DOWN) {
            objet.setDirection(Objet.Direction.DOWN);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyReleased = e.getKeyCode();

        if (keyReleased == KeyEvent.VK_ESCAPE) {
            Control.setRunning(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
