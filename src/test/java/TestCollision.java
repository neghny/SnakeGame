import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import physique.Objet;
import physique.Rectangle;

public class TestCollision {
    @Test
    void testRectColl() {
        var r1 = new Objet(0, 0, new Rectangle(3, 2));
        var r2 = new Objet(2, 1, new Rectangle(2, 3));
        var r3 = new Objet()
    }
}
