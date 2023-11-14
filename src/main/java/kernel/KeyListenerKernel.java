package kernel;

import java.awt.event.KeyEvent;

public class KeyListenerKernel implements java.awt.event.KeyListener {
    private static KeyListenerKernel INSTANCE;

    public static KeyListenerKernel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KeyListenerKernel();
        }
        return INSTANCE;
    }

    private KeyListenerKernel() {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Gameplay.getInstance().changerDirection(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyReleased = e.getKeyCode();

        if (keyReleased == KeyEvent.VK_ESCAPE) {
            Control.getInstance().exitGame();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
