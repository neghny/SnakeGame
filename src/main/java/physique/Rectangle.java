package physique;

public class Rectangle implements IForme {
    private int longu;
    private int haut;

    public Rectangle(int longueur, int hauteur) {
        longu = longueur;
        haut = hauteur;
    }

    public boolean percute(Objet self, Objet other) {
        double sx1 = self.x;
        double sy1 = self.y;
        double ox1 = other.x;
        double oy1 = other.y;
        IForme oShape = other.forme;
        double ox2 = ox1 + oShape.avoirLong();
        double oy2 = oy1 + oShape.avoirHaut();
        double sx2 = sx1 + avoirLong();
        double sy2 = sy1 + avoirHaut();
        if (oShape.getClass().equals(Rectangle.class)) {
            // https://stackoverflow.com/questions/31022269/collision-detection-between-two-rectangles-in-java
            return sx1 < ox2 && sx2 > ox1 && sy1 < oy1 && sy2 > oy2;
        }
        return false;
    }

    public double avoirLong() {
        return longu;
    }

    public double avoirHaut() {
        return haut;
    }
}
