package physique;

public class Rectangle implements IForme {
    private int longu;
    private int haut;

    public Rectangle(int longueur, int hauteur) {
        longu = longueur;
        haut = hauteur;
    }

    @Override
    public boolean percute(Objet other) {
        return false;
    }
}
