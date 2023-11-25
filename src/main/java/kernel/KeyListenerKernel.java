package kernel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyListenerKernel implements java.awt.event.KeyListener, MouseListener {
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
        Gameplay.getInstance().changeDirection(e);
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

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Objet objet = (Objet) mouseEvent.getComponent();
        System.out.println(objet.pathImage);
        Gameplay gp = Gameplay.getInstance();
        if (objet.equals(gp.playButton)) gp.startGame();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
