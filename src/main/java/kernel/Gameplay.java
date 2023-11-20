package kernel;

import graphique.MoteurGraphique;
import physique.Rectangle;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.awt.event.KeyEvent.*;

public class Gameplay {
    private static Gameplay INSTANCE;
    private final MoteurGraphique moteurGraphique;

    private final LinkedList<Objet> snake;
    private final LinkedList<Objet> objets;
    private Objet apple;
    private int score;
    private final int BLOCKSIZE = 50;
    /**
     * Décide si le serpent doit grandir au prochain appel de mvtSnake.
     */
    private boolean growSnake;

    private Gameplay() {
        moteurGraphique = MoteurGraphique.getInstance();
        growSnake = false;
        score = 0;
        snake = new LinkedList<>();
        objets = new LinkedList<>();
    }

    public static Gameplay getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Gameplay();
        }
        return INSTANCE;
    }

    @SuppressWarnings("unused")
    public void instantiateMainMenu() {
        int buttonWidth = 200;
        int buttonHeight = 100;

        Objet playButton = new Objet(400, 100, new Rectangle(buttonWidth, buttonHeight), "btnJouer.png", buttonWidth, buttonHeight);
        Objet tutorialButton = new Objet(400, 250, new Rectangle(buttonWidth, buttonHeight), "btnDid.png", buttonWidth, buttonHeight);
        Objet optionButton = new Objet(400, 400, new Rectangle(buttonWidth, buttonHeight), "btnOpt.png", buttonWidth, buttonHeight);
        Objet leaderboardButton = new Objet(400, 550, new Rectangle(buttonWidth, buttonHeight), "btnLead.png", buttonWidth, buttonHeight);
        Objet exitButton = new Objet(400, 700, new Rectangle(buttonWidth, buttonHeight), "btnQuit.png", buttonWidth, buttonHeight);

        Objet[] mainMenu = new Objet[]{playButton, tutorialButton, optionButton, leaderboardButton, exitButton};

        // TODO
    }

    /**
     * cette méthode affiche le menu des options et attend les choix de l'utilisateur. Tant que l'utilisateur ne choisit
     * pas de retourner au menu principal, la boucle continue à afficher le menu.
     * L'utilisateur peut choisir différentes options telles que le niveau de difficulté, la couleur du serpent, ou
     * retourner au menu principal.
     */
    @SuppressWarnings("unused")
    public void showOptionMenu() {
        int buttonWidth = 200;
        int buttonHeight = 100;

        // Créer spinner difficulté
        Objet difficultySpinner = new Objet(400, 350, new Rectangle(buttonWidth, buttonHeight), "btnDif.png", buttonWidth, buttonHeight);
        // Créer spinner couleur
        Objet colorSpinner = new Objet(400, 550, new Rectangle(buttonWidth, buttonHeight), "btnCoul.png", buttonWidth, buttonHeight);

        Objet[] optionMenu = new Objet[]{difficultySpinner, colorSpinner};

        // TODO
    }

    /**
     * est appelée lorsque l'utilisateur choisit l'option pour définir le niveau de difficulté. Cette méthode gère la
     * logique associée à la configuration du niveau de difficulté.
     */
    @SuppressWarnings("unused")
    public void chooseDifficultyLevel() {
        // TODO
    }

    /**
     * est appelée lorsque l'utilisateur choisit l'option pour définir la couleur du serpent. Cette méthode gère la
     * logique associée à la configuration de la couleur du serpent.
     */
    @SuppressWarnings("unused")
    public void chooseSnakeColor() {
        // TODO
    }

    /**
     * cette méthode permet juste d’afficher les instructions/Règles du jeu.
     */
    @SuppressWarnings("unused")
    public void showInstruction() {
        // TODO
    }

    /**
     * affiche les meilleurs scores des joueurs, en triant la liste des joueurs en fonction de leurs scores et en
     * affichant les cinq meilleurs scores. Si la liste des classements est vide, elle affiche un message indiquant
     * qu'aucun classement n'est disponible.
     */
    @SuppressWarnings("unused")
    public void showLeaderboard() {
        // TODO
    }

    /**
     * permet de  recueillir des informations du joueur (nom, niveau de    difficulté, couleur du serpent) et les
     * utiliser pour initialiser une nouvelle partie du jeu. Elle  est appelée lorsque l'utilisateur choisit de démarrer
     * une nouvelle partie dans le menu principal
     */
    public void startGame() {
        // TODO : Recueillir informations joueur.
        this.apple = new Objet(0, 0, new Rectangle(BLOCKSIZE - 2, BLOCKSIZE - 2), "red_apple.png", BLOCKSIZE, BLOCKSIZE);
        resetLevel();
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

    public boolean isOutOfScreen(Objet o) {
        return (o.getXposition() + o.getSizeImageX() > moteurGraphique.getWidth())
                || (o.getXposition() < 0)
                || (o.getYposition() + o.getSizeImageY() > moteurGraphique.getHeight())
                || (o.getYposition() < 0);
    }

    public void collisionSerpentPomme(){
        score += 1;
        growSnake = true;
        replaceApple();
    }

    public void collisionSerpent() {
        System.out.println("Collision Serpent");
        gameOver();
    }

    public void collisionSerpentMur() {
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
        if (getSnakeHead().getSpeed() > 0) {
            if (growSnake) {
                for (Objet o : objets)
                    System.out.println("Sprite :" + o.pathImage + "; X Obj : " + o.getXposition() + "; X affich :" + o.getX() + "; Y Obj : " + o.getYposition() + "; Y affich :" + o.getY());
                Objet last = snake.getLast();
                addSnakeBlock(createBlocSerpent(last.getXposition(), last.getYposition()));
                growSnake = false;
            }
            for (int i = snake.size() - 1; i > 0; i--) {
                Objet suivant = snake.get(i - 1);
                snake.get(i).setPosition(suivant.getXposition(), suivant.getYposition());
            }
        }
        getSnakeHead().updatePosition();
    }

    /**
     * Selon la touche appuyée, le serpent change de direction.
     * Si le serpent ne bouge pas, il commence à bouger.
     * @param e
     */
    public void changeDirection(KeyEvent e) {
        Objet teteSerpent = getSnakeHead();

        switch(e.getKeyCode()) {
            case VK_RIGHT: {
                teteSerpent.setHorizontalSpeed(BLOCKSIZE);
                teteSerpent.setVerticalSpeed(0);
                break;
            }
            case VK_LEFT: {
                teteSerpent.setHorizontalSpeed(-BLOCKSIZE);
                teteSerpent.setVerticalSpeed(0);
                break;
            }
            case VK_UP: {
                teteSerpent.setHorizontalSpeed(0);
                teteSerpent.setVerticalSpeed(-BLOCKSIZE);
                break;
            }
            case VK_DOWN: {
                teteSerpent.setHorizontalSpeed(0);
                teteSerpent.setVerticalSpeed(BLOCKSIZE);
                break;
            }
        }
    }

    /**
     * Réinitialise le niveau.
     */
    public void resetLevel() {
        score = 0;
        objets.clear();
        snake.clear();

        Objet bloc1 = createBlocSerpent(5 * BLOCKSIZE, 5 * BLOCKSIZE);
        Objet bloc2 = createBlocSerpent(4 * BLOCKSIZE, 5 * BLOCKSIZE);
        Objet bloc3 = createBlocSerpent(3 * BLOCKSIZE, 5 * BLOCKSIZE);
        addSnakeBlock(bloc1);
        addSnakeBlock(bloc2);
        addSnakeBlock(bloc3);

        addObjet(apple);
        replaceApple();
    }

    public Objet createBlocSerpent(int x, int y) {
        return new Objet(x, y, new Rectangle(BLOCKSIZE - 2, BLOCKSIZE - 2), "bloc.png", BLOCKSIZE, BLOCKSIZE);
    }

    public void addSnakeBlock(Objet o) {
        snake.add(o);
        addObjet(o);
    }

    public void addObjet(Objet o) {
        objets.add(o);
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

    /**
     * Que faire si le joueur perd.
     */
    public void gameOver() {
        // TODO: Afficher le Panel game_over.png
        Objet gameOver = new Objet(MoteurGraphique.getInstance().getWidth() / 2, moteurGraphique.getHeight() / 2, new Rectangle(100, 100), "game_over.png", 100, 100);
        addObjet(gameOver);
        System.out.println("Game Over!");
        // TODO: Afficher le leaderboard
        System.out.println("Score final : " + score);

        resetLevel();
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

    public boolean snakeIsGrowing() {
        return growSnake;
    }

    public Objet getSnakeHead() {
        return snake.get(0);
    }

    public void setGrowSnake(boolean growSnake) {
        this.growSnake = growSnake;
    }
}