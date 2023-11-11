import kernel.Gameplay;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameplay {

    Gameplay testGameplay = new Gameplay(1000, 1000);

    /**
     * Permet de vérfier si le calcul du nouveau placement de la pomme est correcte. Pour cela il faut que :
     * - La position de la pomme à bien changé
     * - La pomme n'est pas placé sur le serpent
     */
    @Test
    void testReplacerPomme(){
        int lastXposition = testGameplay.pomme.getXposition()
    }

    /**
     * Permet de vérifier que lorsqu'il il a collision entre le serpent et une pomme
     * - Le serpent grandit d'un bloc (le serpent possède un objet de plus suivant les positions du dernier bloc)
     * - La pomme est replacée
     */
    @Test
    void testCollisionSerpentPomme() {

    }

    /**
     * Permet de vérfier si un objet a bien été ajouté au serpent:
     * - le nouvel élément ajouté à la place de la pomme qui est la nouvelle tête du serpent
     * - l'objet est bien ajouté à la liste d'objets
     * méthodes testée : addObjetSerpent(), addObj()
     */
    @Test
    void testAddObjetSerpent(){

    }

    /**
     * test de la fonction depassementBords
     * On doit vérifier qu'on a bien une détection lorsque le serpent dépasse la fenêtre de jeu
     */
    @Test
    void testDepassementBord(){


    }

    /**
     * Verfication que le score suit bien la suite des évènement
     */
    @Test
    void testScore(){

    }

    /**
     * Vérification de la réinitialisation du jeu :
     * - création du nouveau serpent
     */
    @Test
    void testResetLevel(){

    }
}
