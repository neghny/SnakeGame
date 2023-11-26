package kernel;

import graphique.MoteurGraphique;
import physique.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.*;
import java.util.List;

import static java.awt.event.KeyEvent.*;
import static java.lang.Math.min;
import static java.lang.Thread.sleep;

public class Gameplay {
    private static Gameplay INSTANCE;
    private final MoteurGraphique moteurGraphique = MoteurGraphique.getInstance();
    int buttonWidth = 200;
    int buttonHeight = 100;

    Objet playButton = new Objet(400, 100, new Rectangle(buttonWidth, buttonHeight), "btnJouer.png", buttonWidth, buttonHeight);
    Objet tutorialButton = new Objet(400, 250, new Rectangle(buttonWidth, buttonHeight), "btnDidacticiel.png", buttonWidth, buttonHeight);
    Objet optionButton = new Objet(400, 400, new Rectangle(buttonWidth, buttonHeight), "btnOptions.png", buttonWidth, buttonHeight);
    Objet leaderboardButton = new Objet(400, 550, new Rectangle(buttonWidth, buttonHeight), "btnLeaderboard.png", buttonWidth, buttonHeight);
    Objet exitButton = new Objet(400, 700, new Rectangle(buttonWidth, buttonHeight), "btnQuitter.png", buttonWidth, buttonHeight);

    Objet[] mainMenu = new Objet[]{playButton, tutorialButton, optionButton, leaderboardButton, exitButton};

    // Créer spinner difficulté
    Objet difficultySpinner = new Objet(400, 350, new Rectangle(buttonWidth, buttonHeight), "btnDifficulte.png", buttonWidth, buttonHeight);
    // Créer spinner couleur
    Objet colorSpinner = new Objet(400, 550, new Rectangle(buttonWidth, buttonHeight), "btnCouleur.png", buttonWidth, buttonHeight);
    Objet returnButton = new Objet(400, 750, new Rectangle(buttonWidth, buttonHeight), "btnRetour.png", buttonWidth, buttonHeight);

    Objet[] optionMenu = new Objet[]{difficultySpinner, colorSpinner, returnButton};
    Objet gameOver = new Objet(MoteurGraphique.getInstance().getWidth() / 2, moteurGraphique.getHeight() / 2, new Rectangle(300, 300), "game_over.png", 300, 300);

    Objet leaderboardTitle = new Objet(100, 100, new Rectangle(200,100), "btnLeaderboard.png", 200, 100);
    Objet[] leaderboard = new Objet[7];
    String pseudo;

    Font font;

    private final String fichierScores = "src/main/java/kernel/scores.txt";

    private final LinkedList<Objet> snake;
    private final LinkedList<Objet> objets;
    private Objet apple;
    private int score;
    private final int BLOCKSIZE = 50;
    /**
     * Décide si le serpent doit grandir au prochain appel de mvtSnake.
     * Voir Javadoc de isGrowSnake.
     */
    private boolean growSnake;

    /**
     * Source : https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html section Getting the User's Input from a Dialog
     * @return le pseudo
     */
    public String enterPseudo() {
        for (;;) {
            String s = (String)JOptionPane.showInputDialog(
                    moteurGraphique.getMainFrame(),
                    "Entrez votre nom :",
                    "Dialogue utilisateur",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "pseudo");

            //If a string was returned, say so.
            if ((s != null) && (s.length() > 0))
                return s;

            //If you're here, the return value was null/empty.
            JOptionPane.showMessageDialog(moteurGraphique.getMainFrame(), "Entre un nom !");
        }
    }

    private Gameplay(){
        pseudo = enterPseudo();
        growSnake = false;
        score = 0;
        snake = new LinkedList<>();
        objets = new LinkedList<>();
        for (Objet b : mainMenu)
            addObjet(b);
        for (Objet b : optionMenu)
            addObjet(b);
        addObjet(gameOver);

        leaderboard[0] = returnButton;
        leaderboard[1] = leaderboardTitle;
        //leaderboard[1].add(new JLabel("Leaderboard"));
        for (int i = 0; i < 5; i++){
            leaderboard[i+2] = new Objet(100, (i+2)*100, new Rectangle(400, 100), "blank.png", 400, 100);
        }
        for (int i = 1; i < 7; i++){
            addObjet(leaderboard[i]);
        }

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("Orbitron.ttf")));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Gameplay getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Gameplay();
        }
        return INSTANCE;
    }

    public void showMainMenu() {
        gameOver.setVisible(false);
        for (Objet i : leaderboard){
            i.setVisible(false);
            for (MouseListener l : leaderboard[0].getMouseListeners())
                gameOver.removeMouseListener(l);
        }
        for (MouseListener l : gameOver.getMouseListeners())
            gameOver.removeMouseListener(l);
        for (Objet b : mainMenu) {
            b.setVisible(true);
            for (MouseListener l : b.getMouseListeners())
                b.removeMouseListener(l);
            b.addMouseListener(KeyListenerKernel.getInstance());
        }
        for (Objet b : optionMenu) {
            b.setVisible(false);
            for (MouseListener l : b.getMouseListeners())
                b.removeMouseListener(l);
        }
    }

    /**
     * cette méthode affiche le menu des options et attend les choix de l'utilisateur.
     * L'utilisateur peut choisir différentes options telles que le niveau de difficulté, la couleur du serpent, ou
     * retourner au menu principal.
     */
    public void showOptionMenu() {
        for (Objet i : leaderboard){
            i.setVisible(false);
            for (MouseListener l : leaderboard[0].getMouseListeners())
                gameOver.removeMouseListener(l);
        }
        for (Objet b : mainMenu) {
            b.setVisible(false);
            for (MouseListener l : b.getMouseListeners())
                b.removeMouseListener(l);
        }
        for (Objet b : optionMenu) {
            b.setVisible(true);
            for (MouseListener l : b.getMouseListeners())
                b.removeMouseListener(l);
            b.addMouseListener(KeyListenerKernel.getInstance());
        }
    }

    // Source : https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html section showOptionDialog
    /**
     * est appelée lorsque l'utilisateur choisit l'option pour définir le niveau de difficulté. Cette méthode gère la
     * logique associée à la configuration du niveau de difficulté.
     */
    public void chooseDifficultyLevel() {
        Object[] difficultes = {"facile", "normal", "difficile"};
        int n = JOptionPane.showOptionDialog(moteurGraphique.getMainFrame(),
                "Choisissez la difficulté :",
                "Dialogue utilisateur",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                difficultes,  //the titles of buttons
                difficultes[0]); //default button title

        switch (n) {
            case 0 -> Control.getInstance().frameLength = 300;
            case 1 -> Control.getInstance().frameLength = 200;
            case 2 -> Control.getInstance().frameLength = 100;
            default -> JOptionPane.showMessageDialog(moteurGraphique.getMainFrame(), "Annulé.");
        }
    }

    /**
     * est appelée lorsque l'utilisateur choisit l'option pour définir la couleur du serpent. Cette méthode gère la
     * logique associée à la configuration de la couleur du serpent.
     */
    public void chooseSnakeColor() {
        // TODO
    }

    /**
     * cette méthode permet juste d’afficher les instructions/Règles du jeu.
     */
    public void showInstruction() {
        JOptionPane.showMessageDialog(moteurGraphique.getMainFrame(), "1. Contrôlez le serpent avec les touches directionnelles." + System.lineSeparator() +
                "2. Mangez les fruits pour marquer des points." + System.lineSeparator() +
                "3. Évitez de heurter les bords de l'écran ou le  corps du serpent." + System.lineSeparator() +
                "4. Plus vous mangez de fruits, plus votre serpent grandit." + System.lineSeparator() +
                "5. \"Bonne chance!\"");
    }

    /**
     * affiche les meilleurs scores des joueur-euses, en triant la liste des joueur-euses en fonction de leurs scores et en
     * affichant les cinq meilleurs scores. Si la liste des classements est vide, elle affiche un message indiquant
     * qu'aucun classement n'est disponible.
     */
    public void showLeaderboard(String fichierScores){
        gameOver.setVisible(false);
        for (MouseListener l : gameOver.getMouseListeners())
            gameOver.removeMouseListener(l);
        for (Objet b : mainMenu) {
            b.setVisible(false);
            for (MouseListener l : b.getMouseListeners())
                b.removeMouseListener(l);
            b.addMouseListener(KeyListenerKernel.getInstance());
        }
        for (Objet b : optionMenu) {
            b.setVisible(false);
            for (MouseListener l : b.getMouseListeners())
                b.removeMouseListener(l);
        }

        List<Score> meilleursScores = getBestScores(fichierScores);
        for (int i = 0; i < meilleursScores.size(); i++){
            //System.out.println(meilleursScores.get(i));
            leaderboard[i+2].removeAll();
            JLabel label = new JLabel(meilleursScores.get(i).toString());
            label.setFont(font.deriveFont(Font.BOLD, 36));
            leaderboard[i+2].add(label);

        }
        leaderboard[0].addMouseListener(KeyListenerKernel.getInstance());
        for (Objet i : leaderboard){
            i.setVisible(true);
        }

    }


    /**
     * permet de  recueillir des informations du joueur (nom, niveau de    difficulté, couleur du serpent) et les
     * utiliser pour initialiser une nouvelle partie du jeu. Elle  est appelée lorsque l'utilisateur choisit de démarrer
     * une nouvelle partie dans le menu principal
     */
    public void startGame() {
        // TODO : Recueillir informations joueur.
        for (Objet b : mainMenu) {
            b.setVisible(false);
            for (MouseListener l : b.getMouseListeners())
                b.removeMouseListener(l);
        }
        for (Objet b : optionMenu) {
            b.setVisible(false);
            for (MouseListener l : b.getMouseListeners())
                b.removeMouseListener(l);
        }
        this.apple = new Objet(0, 0, new Rectangle(BLOCKSIZE - 2, BLOCKSIZE - 2), "red_apple.png", BLOCKSIZE, BLOCKSIZE);
        initializeLevel();
    }

    private List<Objet[]> getCollisions() {
        List<Objet[]> collisions = new ArrayList<>();

        for (int i = 0; i < objets.size(); i++) {
            Objet objet1 = objets.get(i);

            for (int j = i + 1; j < objets.size(); j++) {
                Objet objet2 = objets.get(j);

                if (objet1 != objet2 && objet1.percute(objet2)) {
                    collisions.add(new Objet[]{objet1, objet2});
                }
            }
        }
        return collisions;
    }

    public void handleCollision() {
        if (snakeExists()) {
            Objet teteSerpent = getSnakeHead();

            if (isOutOfScreen(teteSerpent)) {
                collisionSerpentMur();
            } else {
                for (Objet[] collision : getCollisions()) {
                    if (collision[0] == teteSerpent || collision[1] == teteSerpent) {
                        if (collision[0] == apple || collision[1] == apple) {
                            collisionSerpentPomme();
                        } else if (collision[0] == teteSerpent && snake.contains(collision[1]) || collision[1] == teteSerpent && snake.contains(collision[0])) {
                            collisionSerpent();
                        }
                    }
                }
            }
        }
    }

    public boolean isOutOfScreen(Objet o) {
        return (o.getXposition() + o.getSizeImageX() > moteurGraphique.getWidth())
                || (o.getXposition() < 0)
                || (o.getYposition() + o.getSizeImageY() > moteurGraphique.getHeight())
                || (o.getYposition() < 0);
    }

    /**
     * Que faire dans l'événement de collision entre le serpent et la pomme.
     */
    public void collisionSerpentPomme(){
        score += 1;
        growSnake = true;
        replaceApple();
    }

    /**
     * Que faire dans l'événement de collision entre le serpent et lui-même.
     */
    public void collisionSerpent(){
        System.out.println("Collision Serpent");
        gameOver();
    }

    /**
     * Que faire dans l'événement de collision entre le serpent et le bord de l'écran.
     */
    public void collisionSerpentMur(){
        System.out.println("Collision Mur");
        gameOver();
    }

    @SuppressWarnings("unused")
    public void printSnakePosition() {
        System.out.println("--------------------------------");
        for (Objet objet : snake) {
            System.out.println(objet.getXposition() + " " + objet.getYposition());
        }
        System.out.println("--------------------------------");
    }

    public void moveSnake() {
        if (snakeExists()) {
            if (getSnakeHead().getSpeed() > 0) {
                int taille = snake.size();
                if (growSnake) {
                    for (Objet o : objets)
                        System.out.println("Sprite :" + o.pathImage + "; X Obj : " + o.getXposition() + "; X affich :" + o.getX() + "; Y Obj : " + o.getYposition() + "; Y affich :" + o.getY());
                    Objet last = snake.getLast();
                    addSnakeBlock(createBlocSerpent(last.getXposition(), last.getYposition()));
                    growSnake = false;
                }
                for (int i = taille - 1; i > 0; i--) {
                    Objet suivant = snake.get(i - 1);
                    snake.get(i).setPosition(suivant.getXposition(), suivant.getYposition());
                }
            }
            getSnakeHead().updatePosition();
        }
    }

    /**
     * Selon la touche appuyée, le serpent change de direction.
     * Si le serpent ne bouge pas, il commence à bouger.
     */
    public void changeDirection(KeyEvent e) {
        if (snakeExists()) {
            Objet teteSerpent = getSnakeHead();
            int h = teteSerpent.getHorizontalSpeed();
            int v = teteSerpent.getVerticalSpeed();

            switch (e.getKeyCode()) {
                case VK_RIGHT -> {
                    if (h < 0)
                        break;
                    teteSerpent.setHorizontalSpeed(BLOCKSIZE);
                    teteSerpent.setVerticalSpeed(0);
                }
                case VK_LEFT -> {
                    if (h > 0 || teteSerpent.getSpeed() == 0)
                        break;
                    teteSerpent.setHorizontalSpeed(-BLOCKSIZE);
                    teteSerpent.setVerticalSpeed(0);
                }
                case VK_UP -> {
                    if (v > 0)
                        break;
                    teteSerpent.setHorizontalSpeed(0);
                    teteSerpent.setVerticalSpeed(-BLOCKSIZE);
                }
                case VK_DOWN -> {
                    if (v < 0)
                        break;
                    teteSerpent.setHorizontalSpeed(0);
                    teteSerpent.setVerticalSpeed(BLOCKSIZE);
                }
            }
        }
    }

    /**
     * Référence : <a href="https://stackoverflow.com/questions/18852059/java-list-containsobject-with-field-value-equal-to-x">lien</a> premier code de première réponse
     */

    /**
     * Réinitialiser le niveau.
     * Est aussi utilisé pour initialiser le niveau au début du jeu.
     */
    public void initializeLevel() {
        score = 0;

        Objet bloc1 = createBlocSerpent(5 * BLOCKSIZE, 5 * BLOCKSIZE);
        Objet bloc2 = createBlocSerpent(4 * BLOCKSIZE, 5 * BLOCKSIZE);
        Objet bloc3 = createBlocSerpent(3 * BLOCKSIZE, 5 * BLOCKSIZE);
        addSnakeBlock(bloc1);
        addSnakeBlock(bloc2);
        addSnakeBlock(bloc3);

        addObjet(apple);
        replaceApple();
    }

    /**
     * Créer un bloc de serpent ; il faut ensuite appeler addObjSerpent pour rajouter ce bloc à la liste d'objets et à
     * la liste de blocs du serpent.
     * @param x
     * @param y
     * @return un bloc sous forme d'un Objet
     */
    public Objet createBlocSerpent(int x, int y) {
        return new Objet(x, y, new Rectangle(BLOCKSIZE - 2, BLOCKSIZE - 2), "bloc.png", BLOCKSIZE, BLOCKSIZE);
    }

    /**
     * A appeler après createBlocSerpent pour mettre à jour la liste d'objets et la liste de blocs du serpent.
     * @param o le bloc de serpent
     */
    public void addSnakeBlock(Objet o) {
        snake.add(o);
        addObjet(o);
    }

    /**
     * À appeler après new Objet(...) pour mettre à jour la liste d'objets.
     * @param o
     */
    public void addObjet(Objet o) {
        objets.add(o);
        moteurGraphique.addObjet(o);
    }

    /**
     * Replace la pomme à une position aléatoire, en prenant en compte la position du serpent, la taille de la fenêtre
     */
    public void replaceApple() {
        int newWidth;
        int newHeight;
        boolean checkPosition;

        do {
            Random random = new Random();
            newWidth = random.nextInt(moteurGraphique.getWidth() / BLOCKSIZE) * BLOCKSIZE;
            newHeight = random.nextInt(moteurGraphique.getHeight() / BLOCKSIZE) * BLOCKSIZE;
            checkPosition = true;

            for (Objet element : snake) {
                if (element.getXposition() == newWidth && element.getYposition() == newHeight) {
                    checkPosition = false;
                    break;
                }
            }
        } while (!checkPosition);
        apple.setPosition(newWidth, newHeight);
    }

    public boolean doesNotContain(final List<Objet> l, final Objet ref) {
        return l.stream().noneMatch(o -> o.equals(ref));
    }

    /**
     * Demande et récupère le nom de la personne qui vient de finir sa partie
     * @return le nom récupéré
     */
    private String getPseudo() {
        return pseudo;
    }

    /**
     * Enregister un nouveau score dans le leaderboard
     */
    private void registerScore(String fichierScores){
        String nom = getPseudo();
        writeScore(nom, score, fichierScores);
    }

    /**
     * Inscrit un nouveau score dans le fichier de scores
     * @param nom pseudo de la personne qui joue
     * @param score score de la partie achevée
     * @param fichierScores chemin d'accès vers le fichier de scores
     */
    private void writeScore(String nom, int score, String fichierScores){
        try {
            BufferedWriter ecrireScores = new BufferedWriter(new FileWriter(fichierScores, true));
            ecrireScores.write(nom + " " + score);
            ecrireScores.newLine();
            ecrireScores.flush();
            ecrireScores.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lit le fichier de scores pour récupérer les 10 meilleurs
     * @return la liste des 10 meilleurs scores
     */
    private List<Score> getBestScores(String fichierScores){
        List<Score> scores = new ArrayList<>();
        List<Score> meilleursScores = new ArrayList<>();
        try {
            BufferedReader lireScores = new BufferedReader(new FileReader(fichierScores));
            String ligne = lireScores.readLine();
            while (ligne != null){
                String[] contenu = ligne.split(" ");
                String nom = contenu[0];
                int score = Integer.parseInt(contenu[1]);
                scores.add(new Score(nom, score));
                ligne = lireScores.readLine();
            }
            lireScores.close();
            scores.sort(null);
            for (int i = 0; i < min(5, scores.size()); i++){
                meilleursScores.add(scores.get(scores.size() - 1 - i));
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return meilleursScores;
    }


    /**
     * Que faire si le joueur perd.
     */
    public void gameOver() {
        gameOver.setVisible(true);
        gameOver.addMouseListener(KeyListenerKernel.getInstance());
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LinkedList<Objet> temp = new LinkedList<>();
        for (Objet o : objets)
            if (doesNotContain(List.of(mainMenu), o) && doesNotContain(List.of(optionMenu), o) && !o.equals(gameOver))
                temp.add(o);
        objets.removeAll(temp);
        MoteurGraphique.getInstance().emptyWith(temp);
        snake.clear();
        // TODO: Réctifier Affichage du Panel game_over.png
        System.out.println("Game Over!");
        // TODO: Afficher le leaderboard
        registerScore(fichierScores);
        showLeaderboard(fichierScores);
        System.out.println("Score final : " + score);
    }


    public LinkedList<Objet> getSnake() {
        return snake;
    }

    public LinkedList<Objet> getObjets() {
        return objets;
    }

    public Objet getApple() {
        return apple;
    }

    public int getScore() {
        return score;
    }

    public int getBLOCKSIZE() {
        return BLOCKSIZE;
    }

    /**
     * Cette méthode est la conséquence d'un problème de conception. En effet, il est impossible de savoir où placer
     * un nouveau bloc du serpent quand le serpent mange une pomme, mais on sait que la prochaine fois que le serpent
     * bouge, le nouveau bloc sera à la position du dernier bloc.
     * @return Est-ce que le serpent doit grandir d'un bloc lors de l'appel de la méthode mvtSnake.
     */
    public boolean snakeIsGrowing() {
        return growSnake;
    }

    public boolean snakeExists() {
        return !snake.isEmpty();
    }

    public Objet getSnakeHead() {
        return snake.get(0);
    }

    public void setGrowSnake(boolean growSnake) {
        this.growSnake = growSnake;
    }

    public String getScoresFilePath(){
        return fichierScores;
    }
}