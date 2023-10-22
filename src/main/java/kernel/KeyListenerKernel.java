package kernel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyListenerKernel implements java.awt.event.KeyListener {

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
        } else if (keyPressed == KeyEvent.VK_SPACE) {
            // Do nothing to not interfere with keyReleased
        } else {
            System.out.println(keyPressed);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyTyped = e.getKeyCode();

        if (keyTyped == KeyEvent.VK_SPACE) {
            System.out.println("JUMP!");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // For testing purposes, will have to addKeyListener to the main game frame
    public static void main(String[] args) {

        Frame f = new Frame("Demo");
        f.setLayout(new FlowLayout());
        f.setSize(200, 200);
        Label l = new Label();
        l.setText("KeyListener Test");
        f.add(l);
        f.setVisible(true);

        KeyListenerKernel keyListener = new KeyListenerKernel();
        f.addKeyListener(keyListener);
    }
}
