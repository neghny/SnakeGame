package kernel;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * La classe KeyListenerKernel gère les événements liés au clavier et à la souris dans le contexte du noyau du jeu.
 * Elle implémente l'interface KeyListener pour la gestion des touches du clavier et MouseListener pour la gestion des clics de souris.
 * Cette classe suit le modèle de conception Singleton pour garantir une unique instance dans l'application.
 */
public class KeyListenerKernel implements java.awt.event.KeyListener, MouseListener {
    private static KeyListenerKernel INSTANCE;

    /**
     * Récupère l'instance unique de la classe KeyListenerKernel.
     *
     * @return L'instance unique de KeyListenerKernel.
     */
    public static KeyListenerKernel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KeyListenerKernel();
        }
        return INSTANCE;
    }

    private KeyListenerKernel() {
    }

    /**
     * Gère l'événement lorsqu'une touche du clavier est enfoncée.
     * Déclenche le changement de direction dans la classe Gameplay en fonction de la touche enfoncée.
     *
     * @param e Événement KeyEvent associé à la touche enfoncée.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        Gameplay.getInstance().changeDirection(e);
    }

    /**
     * Gère l'événement lorsqu'une touche du clavier est relâchée.
     * Si la touche relâchée est la touche "Escape" (VK_ESCAPE), appelle la méthode exitGame de la classe Control pour quitter le jeu.
     *
     * @param e Événement KeyEvent associé à la touche relâchée.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyReleased = e.getKeyCode();

        if (keyReleased == KeyEvent.VK_ESCAPE) {
            Control.getInstance().exitGame();
        }
    }

    /**
     * Gère l'événement lorsqu'une touche du clavier est tapée.
     * Cette méthode n'est pas utilisée dans l'implémentation actuelle.
     *
     * @param e Événement KeyEvent associé à la touche tapée.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Gère l'événement lorsqu'un clic de souris est effectué.
     * Identifie l'objet graphique cliqué et déclenche différentes actions en conséquence.
     *
     * @param mouseEvent Événement MouseEvent associé au clic de souris.
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Objet objet = (Objet) mouseEvent.getComponent();
        System.out.println(objet.pathImage);
        Gameplay gp = Gameplay.getInstance();
        if (objet.equals(gp.playButton)) gp.startGame();
        if (objet.equals(gp.tutorialButton)) gp.showInstruction();
        if (objet.equals(gp.optionButton)) gp.showOptionMenu();
        if (objet.equals(gp.leaderboardButton)) gp.showLeaderboard(gp.getScoresFilePath());
        if (objet.equals(gp.exitButton)) Control.getInstance().exitGame();;
        if (objet.equals(gp.difficultySpinner)) gp.chooseDifficultyLevel();
        if (objet.equals(gp.colorSpinner)) gp.chooseSnakeColor();
        if (objet.equals(gp.returnButton) || objet.equals(gp.gameOver)) gp.showMainMenu();
    }

    /**
     * Gère l'événement lorsqu'un bouton de la souris est enfoncé.
     * Cette méthode n'est pas utilisée dans l'implémentation actuelle.
     *
     * @param mouseEvent Événement MouseEvent associé au bouton de souris enfoncé.
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    /**
     * Gère l'événement lorsqu'un bouton de la souris est relâché.
     * Cette méthode n'est pas utilisée dans l'implémentation actuelle.
     *
     * @param mouseEvent Événement MouseEvent associé au bouton de souris relâché.
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    /**
     * Gère l'événement lorsque la souris entre dans la zone du composant.
     * Cette méthode n'est pas utilisée dans l'implémentation actuelle.
     *
     * @param mouseEvent Événement MouseEvent associé à l'entrée de la souris.
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    /**
     * Gère l'événement lorsque la souris sort de la zone du composant.
     * Cette méthode n'est pas utilisée dans l'implémentation actuelle.
     *
     * @param mouseEvent Événement MouseEvent associé à la sortie de la souris.
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
