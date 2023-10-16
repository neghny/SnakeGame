package physique;

import kernel.Objet;

public class Ligne implements IForme {
    double l;
    double h;

    public Ligne(double longueur, double hauteur) {
        l = longueur;
        h = hauteur;
    }

    public boolean percute(Objet self, Objet other) {
        IForme of = other.forme;
        if (of.getClass().equals(Rectangle.class))
            return IForme.percuteLigneRect(self, other, this, (Rectangle) of);
        if (of.getClass().equals(Ligne.class))
            return IForme.percuteLigneLigne(self.x, self.y, self.x + l, self.y + h, other.x, other.y, other.x + of.avoirLong(), other.y + of.avoirHaut());
        if (of.getClass().equals(Cercle.class))
            return IForme.percuteCercleLigne(other, self, (Cercle) of, this);
        return false;
    }

    public double avoirLong() {
        return l;
    }

    public double avoirHaut() {
        return h;
    }
}
