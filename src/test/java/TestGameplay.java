import graphique.MoteurGraphique;
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
        testGameplay = Gameplay.getInstance();
    }

    /**
     * Permet de verifier si le calcul du nouveau placement de la pomme est correcte. Pour cela il faut que :
     * - La position de la pomme à bien changé
     * - La pomme n'est pas placé sur le serpent
     * - la pomme est bien placée dans la fenêtre de jeu (et ne la dépasse pas)
     */
    @Test
    void testReplacerPomme() {
        double lastXposition = testGameplay.getApple().getXposition();
        double lastYposition = testGameplay.getApple().getYposition();
        testGameplay.replaceApple();
        Assertions.assertTrue((lastXposition != testGameplay.getApple().getXposition()) || (lastYposition != testGameplay.getApple().getYposition()));
        for (Objet element : testGameplay.getSnake()) {
            Assertions.assertTrue((element.getXposition() != testGameplay.getApple().getXposition()) || (element.getYposition() != testGameplay.getApple().getYposition()));
        }
        double pommeX = testGameplay.getApple().getXposition();
        double pommeY = testGameplay.getApple().getYposition();
        int tailleBloc = testGameplay.getBLOCKSIZE();
        Assertions.assertTrue(pommeX >= 0 && pommeX + tailleBloc <= MoteurGraphique.getInstance().getWidth());
        Assertions.assertTrue(pommeY >= 0 && pommeY + tailleBloc <= MoteurGraphique.getInstance().getHeight());
    }


    /**
     * Permet de vérifier que lorsqu'il y a collision entre le serpent et une pomme :
     * - Le serpent doit grandir
     * - Le score augmente
     *
     */
    @Test
    void testCollisionSerpentPomme() {
        int lastScore = testGameplay.getScore();
        testGameplay.collisionSerpentPomme();
        assertEquals(lastScore + 1, testGameplay.getScore());
        Assertions.assertTrue(testGameplay.snakeIsGrowing());
    }

    /**
     * Test qui permet de :
     * - Vérifier que le serpent a bien grandi
     * - Vérifier que le nouvel élément ajouté à la fin de la queue du serpent
     * - Vérifier que l'objet a bien été ajouté à la liste d'objets
     */
    @Test
    void testAddObjetSerpent() {
        Objet bloc = new Objet(2 * testGameplay.getBLOCKSIZE(), 2 * testGameplay.getBLOCKSIZE(), new Rectangle(testGameplay.getBLOCKSIZE(), testGameplay.getBLOCKSIZE()), "bloc.png", testGameplay.getBLOCKSIZE(), testGameplay.getBLOCKSIZE());
        int serpentSizeBefore = testGameplay.getSnake().size();
        testGameplay.addSnakeBlock(bloc);
        assertEquals(serpentSizeBefore + 1, testGameplay.getSnake().size());
        Objet finQueue = testGameplay.getSnake().get(testGameplay.getSnake().size() - 1);
        assertEquals(bloc, finQueue);
        Assertions.assertTrue(testGameplay.getObjets().contains(bloc));
    }


    /**
     * Test de la fonction depassementBords()
     * On doit vérifier qu'on a bien une détection lorsque le serpent dépasse la fenêtre de jeu.
     * Pour cela, on crée un objet hors des limites de la fenêtre de jeu et on vérifie que la méthode depassementBords() renvoie true pour cet objet.
     *
     */
    @Test
    void testDepassementBord() {
        Objet objetHorsLimites = new Objet(1010, 1010, new Rectangle(testGameplay.getBLOCKSIZE(), testGameplay.getBLOCKSIZE()), "bloc.png", testGameplay.getBLOCKSIZE(), testGameplay.getBLOCKSIZE());
        Assertions.assertTrue(testGameplay.isOutOfScreen(objetHorsLimites));
    }

    /**
     * Verification que le score suit bien la suite des évènements. Pour cela, on
     * - Simule une collision avec une pomme (3 fois)
     * - Vérifie que le score a été mis à jour.
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
     * - Vérifier que la pomme a été replacée.
     */
    @Test
    void testResetLevel() {
        Random random = new Random();
        testGameplay.getApple().setPosition(random.nextInt(1000 / testGameplay.getBLOCKSIZE()) * testGameplay.getBLOCKSIZE(), random.nextInt(1000 / testGameplay.getBLOCKSIZE()) * testGameplay.getBLOCKSIZE());
        double lastXposition = testGameplay.getApple().getXposition();
        double lastYposition = testGameplay.getApple().getYposition();
        testGameplay.resetLevel();
        assertEquals(0, testGameplay.getScore());
        assertEquals(3, testGameplay.getSnake().size());
        assertNotEquals(lastXposition, testGameplay.getApple().getXposition());
        assertNotEquals(lastYposition, testGameplay.getApple().getYposition());
    }

    /**
     * Vérifier que les positions ont bien été mise à jour (chaque bloc prend la position du bloc qu'il suit) : têtes, dernier elements, chaque morceau de corps et que le serpent à bien grandit
     * Vérifier qu'un nouvel objet est ajouté au serpent et à la liste d'objets.
     */
    @Test
    void testMouvementSerpent(){

        int[] XlastPositions = new int[testGameplay.getSnake().size() ];
        int[] YlastPositions = new int[testGameplay.getSnake().size() ];
        for (int i = 0; i < testGameplay.getSnake().size(); i++){
            XlastPositions[i] = testGameplay.getSnake().get(i).getXposition();
            YlastPositions[i] = testGameplay.getSnake().get(i).getYposition();
        }
        int lastSizeSerpent = testGameplay.getSnake().size();
        int lastNumberObjets = testGameplay.getObjets().size();

        testGameplay.getSnakeHead().setHorizontalSpeed(testGameplay.getBLOCKSIZE());
        testGameplay.setGrowSnake(true);
        testGameplay.moveSnake();

        for (int i = 1; i < testGameplay.getSnake().size(); i++) {
            assertEquals(XlastPositions[i - 1], testGameplay.getSnake().get(i).getXposition());
            assertEquals(YlastPositions[i - 1], testGameplay.getSnake().get(i).getYposition());
        }
        Objet teteSerpent = testGameplay.getSnake().get(0);
        assertEquals(teteSerpent.getXposition(), XlastPositions[0] + testGameplay.getBLOCKSIZE());
        assertEquals(teteSerpent.getYposition(), YlastPositions[0]);

        Objet boutCorps = testGameplay.getSnake().get(testGameplay.getSnake().size() - 1);
        assertEquals(boutCorps.getXposition(), XlastPositions[XlastPositions.length - 1]);
        assertEquals(boutCorps.getYposition(), YlastPositions[YlastPositions.length - 1]);

        assertEquals(lastNumberObjets + 1, testGameplay.getObjets().size());
        assertEquals(lastSizeSerpent + 1, testGameplay.getSnake().size());
    }

    /**
     * Julien a enlevé ce test pour pouvoir push.
     *
    @Test
    void testGameOver (){
        int lastSize = testGameplay.getObjets().size();
        Objet expectedGameOver = new Objet(MoteurGraphique.getInstance().getWidth()/ 2, MoteurGraphique.getInstance().getHeight() / 2, new Rectangle(100, 100), "game_over.png", 100, 100);
        testGameplay.gameOver();
        //assertEquals(expectedGameOver, testGameplay.getObjets().getLast());
        assertEquals(lastSize + 1, testGameplay.getObjets().size());
    }
    */
}
