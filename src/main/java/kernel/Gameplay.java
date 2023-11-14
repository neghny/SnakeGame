package kernel;

import graphique.MoteurGraphique;
import physique.Rectangle;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Gameplay {
    private static Gameplay INSTANCE;

    private LinkedList<Objet> serpent;
    private LinkedList<Objet> objets;
    private Objet apple;
    private int score = 0;
    private final int BLOCKSIZE = 50;
    private boolean growSnake = false;

    private Gameplay() {
    }

    public static Gameplay getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Gameplay();
        }
        return INSTANCE;
    }

    public void instancierMenuPrincipal() {
        int buttonWidth = 200;
        int buttonHeight = 100;

        Objet playButton = new Objet(400, 100, new Rectangle(buttonWidth, buttonHeight), "btnJouer.png", buttonWidth, buttonHeight);
        Objet tutorialButton = new Objet(400, 250, new Rectangle(buttonWidth, buttonHeight), "btnDid.png", buttonWidth, buttonHeight);
        Objet optionButton = new Objet(400, 400, new Rectangle(buttonWidth, buttonHeight), "btnOpt.png", buttonWidth, buttonHeight);
        Objet leaderboardButton = new Objet(400, 550, new Rectangle(buttonWidth, buttonHeight), "btnLead.png", buttonWidth, buttonHeight);
        Objet exitButton = new Objet(400, 700, new Rectangle(buttonWidth, buttonHeight), "btnQuit.png", buttonWidth, buttonHeight);

        Objet[] mainMenu = new Objet[]{playButton, tutorialButton, optionButton, leaderboardButton, exitButton};
    }

    /**
     * cette méthode affiche le menu des options et attend les choix de l'utilisateur. Tant que l'utilisateur ne choisit
     * pas de retourner au menu principal, la boucle continue à afficher le menu.
     * L'utilisateur peut choisir différentes options telles que le niveau de difficulté, la couleur du serpent, ou
     * retourner au menu principal.
     */
    public void afficherMenuOptions() {
        int buttonWidth = 200;
        int buttonHeight = 100;

        // Créer spinner difficulté
        Objet difficultySpinner = new Objet(400, 350, new Rectangle(buttonWidth, buttonHeight), "btnDif.png", buttonWidth, buttonHeight);
        // Créer spinner couleur
        Objet colorSpinner = new Objet(400, 550, new Rectangle(buttonWidth, buttonHeight), "btnCoul.png", buttonWidth, buttonHeight);

        Objet[] optionMenu = new Objet[]{difficultySpinner, colorSpinner};
    }

    /**
     * est appelée lorsque l'utilisateur choisit l'option pour définir le niveau de difficulté. Cette méthode gère la
     * logique associée à la configuration du niveau de difficulté.
     */
    public void choisirNiveauDifficulte() {

    }

    /**
     * est appelée lorsque l'utilisateur choisit l'option pour définir la couleur du serpent. Cette méthode gère la
     * logique associée à la configuration de la couleur du serpent.
     */
    public void choisirCouleurSerpent() {

    }

    /**
     * cette méthode permet juste d’afficher les instructions/Règles du jeu.
     */
    public void afficherInstructions() {

    }

    /**
     * affiche les meilleurs scores des joueurs, en triant la liste des joueurs en fonction de leurs scores et en
     * affichant les cinq meilleurs scores. Si la liste des classements est vide, elle affiche un message indiquant
     * qu'aucun classement n'est disponible.
     */
    public void consulterClassements() {

    }

    /**
     * permet de  recueillir des informations du joueur (nom, niveau de    difficulté, couleur du serpent) et les
     * utiliser pour initialiser une nouvelle partie du jeu. Elle  est appelée lorsque l'utilisateur choisit de démarrer
     * une nouvelle partie dans le menu principal
     */
    public void demarrerPartie() {
        // TODO : Recueillir informations joueur.
        this.apple = new Objet(0, 0, new Rectangle(BLOCKSIZE - 2, BLOCKSIZE - 2), "pomme.png", BLOCKSIZE, BLOCKSIZE);
//        this.serpent = new LinkedList<>();
        resetLevel();
    }

    public void gestionCollisions(List<Objet[]> collisions) {
        Objet teteSerpent = getTeteSerpent();

        for (Objet[] collision : collisions) {
            if (collision[0] == teteSerpent || collision[1] == teteSerpent) {
                if (collision[0] == apple || collision[1] == apple) {
                    collisionSerpentPomme();
                } else if (collision[0] == teteSerpent && serpent.contains(collision[1]) || collision[1] == teteSerpent && serpent.contains(collision[0])) {
                    collisionSerpent();
                }
            }
        }
        if (depassementBords(teteSerpent)) {
            collisionSerpentMur();
        }
    }
        /*if (teteSerpent.percute(pomme)){
            collisionSerpentPomme();
        }
        else if (depassementBords(teteSerpent)) { // la tête du serpent dépasse les bords
            collisionSerpentMur();
        }
        else {
            for (int i = 1; i < serpent.size(); i++) {
                //if (teteSerpent.percute(gp.serpent.get(i))){
                if (teteSerpent.getXposition() == serpent.get(i).getXposition() && teteSerpent.getYposition() == serpent.get(i).getYposition()) {
                    collisionSerpent();
                }
            }
        }*/

    public Objet getTeteSerpent() {
        return serpent.get(0);
    }

    public void mvtSnake() {
        Objet teteSerpent = getTeteSerpent();
        if (teteSerpent.getSpeed() > 0) {
            int taille = serpent.size();
            if (growSnake) {
                for (Objet o : objets)
                    System.out.println("Sprite :" + o.pathImage + "; X Obj : " + o.getXposition() + "; X affich :" + o.getX() + "; Y Obj : " + o.getYposition() + "; Y affich :" + o.getY());
                Objet dernier = serpent.getLast();
                addObjSerpent(createBlocSerpent(dernier.getXposition(), dernier.getYposition()));
                growSnake = false;
            }
            for (int i = taille - 1; i > 0; i--) {
                Objet suivant = serpent.get(i - 1);
                serpent.get(i).setPosition(suivant.getXposition(), suivant.getYposition());
            }
        }
        // Bouger la tête du serpent avec hspeed et vspeed.
        teteSerpent.updatePosition();
    }

    public void changerDirection(KeyEvent e) {
        int keyPressed = e.getKeyCode();
        Objet teteSerpent = getTeteSerpent();
        if (keyPressed == KeyEvent.VK_RIGHT) {
            teteSerpent.hspeed = BLOCKSIZE;
            teteSerpent.vspeed = 0;
        } else if (keyPressed == KeyEvent.VK_LEFT) {
            teteSerpent.hspeed = -BLOCKSIZE;
            teteSerpent.vspeed = 0;
        } else if (keyPressed == KeyEvent.VK_UP) {
            teteSerpent.vspeed = -BLOCKSIZE;
            teteSerpent.hspeed = 0;
        } else if (keyPressed == KeyEvent.VK_DOWN) {
            teteSerpent.vspeed = BLOCKSIZE;
            teteSerpent.hspeed = 0;
        }
    }

    public boolean depassementBords(Objet o) {
        return (o.getXposition() + o.getSizeImageX() > MoteurGraphique.getInstance().getWidth()) || (o.getXposition() < 0) || (o.getYposition() + o.getSizeImageY() > MoteurGraphique.getInstance().getHeight()) || (o.getYposition() < 0);
    }

    public void collisionSerpentPomme() {
        score += 1;
        growSnake = true;
        replacerPomme();
    }

    public void collisionSerpent() {
        System.out.println("Collision Serpent");
        gameOver();
    }

    public void collisionSerpentMur() {
        System.out.println("Collision Mur");
        gameOver();
    }

    public void resetLevel() { // si on a en parallele une liste objets, est ce qu'il faut pas supprimer les anciens membres du serpent de cette liste ?
        score = 0;
        // On réinitialise le serpent.
        Objet bloc1 = createBlocSerpent(5 * BLOCKSIZE, 5 * BLOCKSIZE);
        Objet bloc2 = createBlocSerpent(4 * BLOCKSIZE, 5 * BLOCKSIZE);
        Objet bloc3 = createBlocSerpent(3 * BLOCKSIZE, 5 * BLOCKSIZE);
        objets = new LinkedList<>();
        addObj(apple);
        serpent = new LinkedList<>();
        addObjSerpent(bloc1);
        addObjSerpent(bloc2);
        addObjSerpent(bloc3);
        // On réinitialise la pomme.
        replacerPomme();
    }

    public Objet createBlocSerpent(int x, int y) {
        return new Objet(x, y, new Rectangle(BLOCKSIZE - 2, BLOCKSIZE - 2), "bloc.png", BLOCKSIZE, BLOCKSIZE);
    }

    public void addObjSerpent(Objet o) {
        serpent.add(o); // le nouvel élément est ajouté à la queue du serpent.
        addObj(o);
    }

    public void addObj(Objet o) {
        objets.add(o);
    }

    /**
     * Replace la pomme à une position aléatoire, en prenant en compte la position du serpent, la taille de la fenêtre
     */
    public void replacerPomme() {
        int newWidth;
        int newHeight;
        boolean verifPosition;
        do {
            Random random = new Random();
            newWidth = random.nextInt(MoteurGraphique.getInstance().getWidth() / BLOCKSIZE) * BLOCKSIZE;
            newHeight = random.nextInt(MoteurGraphique.getInstance().getHeight() / BLOCKSIZE) * BLOCKSIZE;
            verifPosition = true;

            for (Objet element : serpent) {
                if (element.getXposition() == newWidth && element.getYposition() == newHeight) {
                    verifPosition = false;
                    break;
                }
            }
        } while (!verifPosition);
        apple.setPosition(newWidth, newHeight);
    }

    public void gameOver() {
        System.out.println("Game Over!");
        System.out.println("Score final : " + score);
        //on réinitialise le jeu
        resetLevel();
    }


    public LinkedList<Objet> getSerpent() {
        return serpent;
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

    public boolean isGrowSnake() {
        return growSnake;
    }
}