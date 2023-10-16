package physique;

import kernel.Objet;

public class Cercle implements IForme {
    double r;

    public Cercle(double rayon) {
        r = rayon;
    }

    public boolean percute(Objet self, Objet other) {
        IForme oShape = other.forme;
        if (oShape.getClass().equals(Rectangle.class))
            return IForme.percuteCercleRect(self, other, this, (Rectangle) oShape);
        if (oShape.getClass().equals(Ligne.class))
            return IForme.percuteCercleLigne(self, other, this, (Ligne) oShape);
        if (oShape.getClass().equals(Cercle.class)) {
            double sx = self.x + avoirLong();
            double sy = self.y + avoirHaut();
            double ox = other.x + oShape.avoirLong();
            double oy = other.y + oShape.avoirHaut();
            double sr = r;
            double or = ((Cercle) oShape).r;
            return Math.pow(sx - ox, 2) + Math.pow(sy - oy, 2) <= Math.pow(sr + or, 2);
        }
        return false;
    }

    public double avoirLong() {
        return 2 * r;
    }

    public double avoirHaut() {
        return 2 * r;
    }
}
