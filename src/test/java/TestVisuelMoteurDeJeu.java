import kernel.Objet;
import graphique.MoteurGraphique;
import physique.Cercle;
import physique.Rectangle;
import java.util.LinkedList;

/* Control est le contrôle-commande du jeu-vidéo.
 * Il permet de mettre ensemble les différentes parties du jeu (physique, graphique).
 * Il est responsable de faire tourner le jeu.
 */
public class TestVisuelMoteurDeJeu {

    public static void main(String[] args){
        LinkedList<Objet> objs = new LinkedList<>();
        var mg = new MoteurGraphique();
        Objet pacman = new Objet(25, 25, new Cercle(60), "pacman.png", 120, 120);
        objs.add(pacman);
        Objet monstre = new Objet(250, 0, new physique.Rectangle(120, 120), "pink_ghost.png", 120, 120);
        objs.add(monstre);
        for (int i = 710; i < 1000; i+=50){
            objs.add(new Objet(i+100, 500, new Cercle(10), "ball.png", 20, 20));
            objs.add(new Objet(i, 20, new Cercle(10), "ball.png", 20, 20));
            objs.add(new Objet(100, i-150, new Cercle(10), "ball.png", 20, 20));
            objs.add(new Objet(i-700, 300, new Cercle(10), "ball.png", 20, 20));
        }
        Objet monstreRose = new Objet(250, 0, new physique.Rectangle(120, 120), "pink_ghost.png", 120, 120);
        objs.add(monstreRose);
        Objet monstreBleu = new Objet(800, 300, new physique.Rectangle(120, 120), "blue_ghost.png", 120, 120);
        objs.add(monstreBleu);
        Objet monstreRouge = new Objet(400, 550, new Rectangle(120, 120), "red_ghost.png", 120, 120);
        objs.add(monstreRouge);
        mg.init_display(objs);
    }
}

