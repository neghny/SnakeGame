import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import physique.Cercle;
import physique.Ligne;
import kernel.Objet;
import physique.Rectangle;

public class TestCollision {
    @Test
    void testRectColl() {
        var r1 = new Objet(0, 0, new Rectangle(3, 2));
        var r2 = new Objet(2, 1, new Rectangle(2, 3));
        var r3 = new Objet(0, -3, new Rectangle(1, 2));
        assertThat(r1.percute(r2)).isTrue();
        assertThat(r3.percute(r2)).isFalse();
    }

    @Test
    void testCercleColl() {
        var c1 = new Objet(-5, -1, new Cercle(3));
        var c2 = new Objet(-7, -6, new Cercle(3));
        var c3 = new Objet(3, -6, new Cercle(1));
        assertThat(c1.percute(c2)).isTrue();
        assertThat(c3.percute(c2)).isFalse();
    }

    @Test
    void testLigneColl() {
        var l1 = new Objet(-1, -1, new Ligne(5, -2));
        var l2 = new Objet(0, -2, new Ligne(1, 6));
        var l3 = new Objet(2, -3, new Ligne(1, 2));
        assertThat(l1.percute(l2)).isTrue();
        assertThat(l3.percute(l2)).isFalse();
    }

    @Test
    void testLigneCercle() {
        var l1 = new Objet(-1, -1, new Ligne(5, -2));
        var l2 = new Objet(0, -2, new Ligne(1, 6));
        var c1 = new Objet(-5, -1, new Cercle(3));
        assertThat(l1.percute(c1)).isFalse();
        assertThat(c1.percute(l2)).isTrue();
    }

    @Test
    void testLigneRect() {
        var l1 = new Objet(-1, -1, new Ligne(5, -2));
        var l2 = new Objet(0, -2, new Ligne(1, 6));
        var r1 = new Objet(0, 0, new Rectangle(3, 2));
        assertThat(l1.percute(r1)).isFalse();
        assertThat(l2.percute(r1)).isTrue();
    }

    @Test
    void testCercleRect() {
        var r1 = new Objet(0, 0, new Rectangle(3, 2));
        var r2 = new Objet(2, 1, new Rectangle(2, 3));
        var c1 = new Objet(-5, -1, new Cercle(3));
        assertThat(r1.percute(c1)).isTrue();
        assertThat(r2.percute(c1)).isFalse();
    }
}
