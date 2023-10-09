package physique;

public interface IForme {
    boolean percute(Objet self, Objet other);
    double avoirLong();
    double avoirHaut();
}
