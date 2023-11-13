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


    @BeforeEach
    void setUp() {
        testGameplay = new Gameplay(1000, 1000);
    }

    /**
     * Permet de vérfier si le calcul du nouveau placement de la pomme est correcte. Pour cela il faut que :
     * - La position de la pomme à bien changé
     * - La pomme n'est pas placé sur le serpent
     * - la pomme est bien placé dans la fenêtre de jeu (et ne la dépasse pas)
     */
    @Test
    void testReplacerPomme() {
        double lastXposition = testGameplay.getPomme().getXposition();
        double lastYposition = testGameplay.getPomme().getYposition();

        testGameplay.replacerPomme();

        assertNotEquals(lastXposition, testGameplay.getPomme().getXposition());
        assertNotEquals(lastYposition, testGameplay.getPomme().getYposition());

        for (Objet element : testGameplay.getSerpent()) {
            assertNotEquals(element.getXposition(), testGameplay.getPomme().getXposition());
            assertNotEquals(element.getYposition(), testGameplay.getPomme().getYposition());
        }

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
        assertEquals(lastSerpentSize + 1, testGameplay.getSerpent().size());
        // TODO : Erreur lorsqu'on lance tous les test en même temps
    }

    /**
     * Test qui permet de :
     * - Vérifier que le serpent a bien grandi
     * - Vérifier que le nouvel élément ajouté à la fin de la queue du serpent
     * - Vérifier que l'objet a bien été ajouté à la liste d'objets
     */
    @Test
    void testAddObjetSerpent() {
        Objet bloc = new Objet(2*testGameplay.getTailleBloc(), 2*testGameplay.getTailleBloc(), new Rectangle(testGameplay.getTailleBloc(), testGameplay.getTailleBloc()), "bloc.png", testGameplay.getTailleBloc(), testGameplay.getTailleBloc());
        int serpentSizeBefore = testGameplay.getSerpent().size();
        testGameplay.addObjSerpent(bloc);
        assertEquals(serpentSizeBefore + 1, testGameplay.getSerpent().size());
        Objet finQueue = testGameplay.getSerpent().get(testGameplay.getSerpent().size()-1);
        assertEquals(bloc, finQueue);
        Assertions.assertTrue(testGameplay.getObjets().contains(bloc));
    }


    /**
     * Test de la fonction depassementBords()
     * On doit vérifier qu'on a bien une détection lorsque le serpent dépasse la fenêtre de jeu.
     * Pour cela, on crée un objet hors des limites de la fenêtre de jeu et on vérifie que la méthode depassementBords() renvoie true pour cet objet
     *
     */
    @Test
    void testDepassementBord(){
        Objet objetHorsLimites = new Objet(1010, 1010, new Rectangle(testGameplay.getTailleBloc(), testGameplay.getTailleBloc()), "bloc.png", testGameplay.getTailleBloc(), testGameplay.getTailleBloc());
        Assertions.assertTrue(testGameplay.depassementBords(objetHorsLimites));
    }

    /**
     * Verification que le score suit bien la suite des évènements. Pour cela,
     * - Simuler une collision avec une pomme (3 fois)
     * - Vérifie que le score a été mis à jour
     */
    @Test
    void testScore() {
        testGameplay.collisionSerpentPomme();
        testGameplay.collisionSerpentPomme();
        testGameplay.collisionSerpentPomme();
        assertEquals(3, testGameplay.getScore());
    }

    /**
     * Vérification de la réinitialisation du jeu. Pour cela,
     * - Appel de la méthode resetLevel()
     * - Vérifier que le score a été réinitialisé
     * - Vérifier que le serpent a été réinitialisé avec trois blocs
     * - Vérifier que la pomme a été replacée
     */
    @Test
    void testResetLevel() {

        Random random = new Random();
        testGameplay.getPomme().setPosition(random.nextInt(1000 / testGameplay.getTailleBloc()) * testGameplay.getTailleBloc(), random.nextInt(1000 / testGameplay.getTailleBloc()) * testGameplay.getTailleBloc());
        double lastXposition = testGameplay.getPomme().getXposition();
        double lastYposition = testGameplay.getPomme().getYposition();
        testGameplay.resetLevel();
        assertEquals(0, testGameplay.getScore());
        assertEquals(3, testGameplay.getSerpent().size());
        assertNotEquals(lastXposition, testGameplay.getPomme().getXposition());
        assertNotEquals(lastYposition, testGameplay.getPomme().getYposition());
    }
}
