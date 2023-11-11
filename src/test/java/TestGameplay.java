import kernel.Gameplay;
import kernel.Objet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physique.Rectangle;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestGameplay {

    private Gameplay testGameplay;

    // Afin de s'assurer que chaque test sont indépendants
    @BeforeEach
    void setUp() {
        testGameplay = new Gameplay(1000, 1000);
    }

    /**
     * Permet de vérfier si le calcul du nouveau placement de la pomme est correcte. Pour cela il faut que :
     * - La position de la pomme à bien changé
     * - La pomme n'est pas placé sur le serpent
     */
    @Test
    void testReplacerPomme() {
        double lastXposition = testGameplay.getPomme().getXposition();
        double lastYposition = testGameplay.getPomme().getYposition();

        testGameplay.replacerPomme();
        // Vérifie que la position a bien changé
        assertNotEquals(lastXposition, testGameplay.getPomme().getXposition());
        assertNotEquals(lastYposition, testGameplay.getPomme().getYposition());

        // Vérifie que la pomme n'est pas placé sur le serpent
        for (Objet element : testGameplay.getSerpent()) {
            assertNotEquals(element.getXposition(), testGameplay.getPomme().getXposition());
            assertNotEquals(element.getYposition(), testGameplay.getPomme().getYposition());
        }

        // Vérifier que la pomme est bien placé dans la fenêtre de jeu (et ne la dépasse pas)
        double pommeX = testGameplay.getPomme().getXposition();
        double pommeY = testGameplay.getPomme().getYposition();
        int tailleBloc = testGameplay.getTailleBloc();
        int width = testGameplay.getWidth();
        int height = testGameplay.getHeight();
        Assertions.assertTrue(pommeX >= 0 && pommeX + tailleBloc <= width);
        Assertions.assertTrue(pommeY >= 0 && pommeY + tailleBloc <= height);

    }

    /**
     * Permet de vérifier que lorsqu'il y a collision entre le serpent et une pomme
     * - Le serpent grandit d'un bloc (le serpent possède un objet de plus suivant les positions du dernier bloc)
     * - La pomme est replacée
     */
    /*
    @Test
    void testCollisionSerpentPomme() {
        int lastSerpentSize = testGameplay.getSerpent().size();
        double lastXposition = testGameplay.getPomme().getXposition();
        System.out.println("Ancien X Pomme : " + lastXposition);
        double lastYposition = testGameplay.getPomme().getYposition();
        System.out.println("Ancien Y Pomme : " + lastYposition);

        testGameplay.collisionSerpentPomme();

        System.out.println("Nouveau X Pomme : " + testGameplay.getPomme().getXposition());
        System.out.println("Nouveau Y Pomme : " + testGameplay.getPomme().getYposition());

        // Vérifie que le serpent grandit d'un bloc
        assertEquals(lastSerpentSize + 1, testGameplay.getSerpent().size());
        // TODO : Erreur lorsqu'on lance tous les test en même temps
        // Vérifie que la pomme a été replacée (répétitif avec la méthode précédente, je l'enlève ? )
        assertNotEquals(lastXposition, testGameplay.getPomme().getXposition());
        assertNotEquals(lastYposition, testGameplay.getPomme().getYposition());
        for (Objet element : testGameplay.getSerpent()) {
            assertNotEquals(element.getXposition(), testGameplay.getPomme().getXposition());
            assertNotEquals(element.getYposition(), testGameplay.getPomme().getYposition());
        }
    }

     */

    @Test
    void testAddObjetSerpent() {
        Objet bloc = new Objet(2*testGameplay.getTailleBloc(), 2*testGameplay.getTailleBloc(), new Rectangle(testGameplay.getTailleBloc(), testGameplay.getTailleBloc()), "bloc.png", testGameplay.getTailleBloc(), testGameplay.getTailleBloc());

        int serpentSizeBefore = testGameplay.getSerpent().size();


        testGameplay.addObjSerpent(bloc);

        // Vérifie que le serpent a bien grandi
        assertEquals(serpentSizeBefore + 1, testGameplay.getSerpent().size());
        // Vérifie que le nouvel élément ajouté à la place de la pomme est la nouvelle tête du serpent
        Objet nouvelleTete = testGameplay.getSerpent().get(0);
        assertEquals(bloc, nouvelleTete);
        // Vérifie que l'objet a bien été ajouté à la liste d'objets
        Assertions.assertTrue(testGameplay.getObjets().contains(bloc));
    }

    /**
     * test de la fonction depassementBords
     * On doit vérifier qu'on a bien une détection lorsque le serpent dépasse la fenêtre de jeu
     */
    @Test
    void testDepassementBord(){
        // Créez un objet hors des limites de la fenêtre de jeu
        Objet objetHorsLimites = new Objet(1010, 1010, new Rectangle(testGameplay.getTailleBloc(), testGameplay.getTailleBloc()), "bloc.png", testGameplay.getTailleBloc(), testGameplay.getTailleBloc());
        // Vérifie que la méthode depassementBords() renvoie true pour cet objet
        Assertions.assertTrue(testGameplay.depassementBords(objetHorsLimites));
    }
    /**
     * Verification que le score suit bien la suite des évènements
     */
    @Test
    void testScore() {
        // Simuler une collision avec une pomme (3 fois)
        testGameplay.collisionSerpentPomme();
        testGameplay.collisionSerpentPomme();
        testGameplay.collisionSerpentPomme();
        // Vérifie que le score a été mis à jour
        assertEquals(3, testGameplay.getScore());
    }

    /**
     * Vérification de la réinitialisation du jeu :
     * - création du nouveau serpent
     */
    @Test
    void testResetLevel() {

        Random random = new Random();
        testGameplay.getPomme().setPosition(random.nextInt(1000 / testGameplay.getTailleBloc()) * testGameplay.getTailleBloc(), random.nextInt(1000 / testGameplay.getTailleBloc()) * testGameplay.getTailleBloc());
        double lastXposition = testGameplay.getPomme().getXposition();
        double lastYposition = testGameplay.getPomme().getYposition();

        // Appeler la méthode resetLevel
        testGameplay.resetLevel();

        // Vérifie que le score a été réinitialisé
        assertEquals(0, testGameplay.getScore());
        // Vérifie que le serpent a été réinitialisé avec trois blocs
        assertEquals(3, testGameplay.getSerpent().size());
        // Vérifie que la pomme a été replacée
        assertNotEquals(lastXposition, testGameplay.getPomme().getXposition());
        assertNotEquals(lastYposition, testGameplay.getPomme().getYposition());
    }
}
