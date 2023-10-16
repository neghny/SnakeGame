package physique;

import kernel.Objet;

public class Rectangle implements IForme {
    private final double longu;
    private final double haut;

    public Rectangle(double longueur, double hauteur) {
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
            return sx1 < ox2 && sx2 > ox1 && sy1 < oy2 && sy2 > oy1;
        }
        if (oShape.getClass().equals(Cercle.class))
            return IForme.percuteCercleRect(self, other, (Cercle) oShape, this);
        if (oShape.getClass().equals(Ligne.class))
            return IForme.percuteLigneRect(self, other, (Ligne) oShape, this);
        return false;
    }

    public double avoirLong() {
        return longu;
    }

    public double avoirHaut() {
        return haut;
    }
}
