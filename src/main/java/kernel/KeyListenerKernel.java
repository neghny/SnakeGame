package kernel;

import java.awt.event.KeyEvent;

public class KeyListenerKernel implements java.awt.event.KeyListener {
    Gameplay gp;

    public KeyListenerKernel(Gameplay gameplay) {
        gp = gameplay;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gp.changerDirection(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyReleased = e.getKeyCode();

        if (keyReleased == KeyEvent.VK_ESCAPE) {
            gp.quitterJeu();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
