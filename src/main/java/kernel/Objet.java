package kernel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import physique.IForme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;
import java.util.Objects;

import static java.lang.Math.abs;

/**
 * La classe "Objet" étend JPanel et représente un objet graphique dans un jeu.
 * Elle gère les aspects physiques, affichage et de mouvement de l'objet.
 */
public class Objet extends JPanel {
    // Partie physique
    private int x;
    private int y;
    private int horizontalSpeed;
    private int verticalSpeed;
    private final IForme forme;
    private BufferedImage bufferedImage;
    public String pathImage;
    private Image image;
    private int sizeImageX;
    private int sizeImageY;

    /**
     * Constructeur de la classe "Objet" pour un objet graphique avec une image.
     *
     * @param initialX    Coordonnée X initiale de l'objet.
     * @param initialY    Coordonnée Y initiale de l'objet.
     * @param forme       Forme physique de l'objet (implémente IForme).
     * @param pathImage   Chemin vers l'image de l'objet.
     * @param sizeImageX  Largeur souhaitée de l'image.
     * @param sizeImageY  Hauteur souhaitée de l'image.
     */
    public Objet(int initialX, int initialY, IForme forme, String pathImage, int sizeImageX, int sizeImageY) {
        this.x = initialX;
        this.y = initialY;
        this.forme = forme;
        this.pathImage = pathImage;
        this.horizontalSpeed = 0;
        this.verticalSpeed = 0;

        Logger logger = LogManager.getLogger(this.getClass());
        logger.debug("Construct a JPanel");
        if (logger.isDebugEnabled()) {
            String message = MessageFormat.format("Loading image at path {0}", pathImage);
            logger.debug(message);
        }
        try {
            bufferedImage = ImageIO.read(Objects.requireNonNull(getClass().getResource(pathImage)));

        } catch (Exception ex) {
            String message = MessageFormat.format("Error: Cannot load image at path: {0}", pathImage);
            logger.error(message, ex);
        }
        this.sizeImageX = sizeImageX;
        this.sizeImageY = sizeImageY;
        image = bufferedImage.getScaledInstance(sizeImageX, sizeImageY, Image.SCALE_DEFAULT);
    }

    /**
     * Constructeur alternatif pour les tests de collision.
     *
     * @param initialX Coordonnée X initiale de l'objet.
     * @param initialY Coordonnée Y initiale de l'objet.
     * @param forme    Forme physique de l'objet (implémente IForme).
     */
    public Objet(int initialX, int initialY, IForme forme) {
        this.x = initialX;
        this.y = initialY;
        this.forme = forme;
        this.horizontalSpeed = 0;
        this.verticalSpeed = 0;
    }

    /**
     * Avancer l'objet le temps d'un pas.
     * @author Sellou, Julien
     */
    public void updatePosition() {
        x += horizontalSpeed;
        y += verticalSpeed;
    }

    /**
     * Définit la position de l'objet.
     *
     * @param x Nouvelle coordonnée X.
     * @param y Nouvelle coordonnée Y.
     */
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Surcharge de la méthode paintComponent pour l'affichage graphique de l'objet.
     *
     * @param g Graphics utilisé pour le rendu.
     * @author Kawthar, Nesrine
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setOpaque(false);
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform transform = new AffineTransform();

        transform.translate((double) sizeImageX /2, (double) sizeImageY /2);
        transform.translate((double) -sizeImageX /2, (double) -sizeImageY /2);

        g2d.drawImage(image, transform, this);
    }

    /**
     * Vérifie si cet objet entre en collision avec un autre objet.
     *
     * @param other Objet avec lequel vérifier la collision.
     * @return true si collision, sinon false.
     * @author Sellou, Julien
     */
    public boolean percute(Objet other) {
        return forme.percute(this, other);
    }

    public int getXposition() { return x; }

    public int getYposition() {
        return y;
    }

    /**
     * Calcule la vitesse de l'objet d'une façon peu coûteuse en processeur O(1).
     * L'alternative plus exacte pour un jeu quelconque serait return hspeed * hspeed + vspeed * vspeed i.e la norme
     * euclidienne.
     * @return vitesse par la distance de Manhattan.
     */
    public int getSpeed() { return abs(horizontalSpeed) + abs(verticalSpeed); }

    public int getSizeImageX() {
        return sizeImageX;
    }

    public int getSizeImageY() {
        return sizeImageY;
    }

    public IForme getForme() {
        return forme;
    }

    public void setHorizontalSpeed(int horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
    }

    public void setVerticalSpeed(int verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public int getHorizontalSpeed() {
        return horizontalSpeed;
    }

    public int getVerticalSpeed() {
        return verticalSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Objet objet)) return false;
        return getX() == objet.getX() && getY() == objet.getY() && this.horizontalSpeed == objet.getHorizontalSpeed() && this.verticalSpeed == objet.getVerticalSpeed() && getSizeImageX() == objet.getSizeImageX() && getSizeImageY() == objet.getSizeImageY()  && bufferedImage.equals(objet.bufferedImage) && pathImage.equals(objet.pathImage) && image.equals(objet.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), horizontalSpeed, verticalSpeed, getForme(), bufferedImage, pathImage, image, getSizeImageX(), getSizeImageY());
    }
}

