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

public class Objet extends JPanel {
    // Partie physique
    private int x;
    private int y;
    /**
     * Vitesse sur l'axe x. A utiliser pour déplacer le serpent.
     */
    public int hspeed = 0;
    /**
     * Vitesse sur l'axe y. A utiliser pour déplacer le serpent.
     */
    public int vspeed = 0;

    // Partie graphique
    private final IForme forme;

    private BufferedImage bufferedImage;
    public String pathImage;
    private Image image;
    private int sizeImageX;
    private int sizeImageY;
    /**
     * Variable purement visuelle.
     */
    private double rotation;

    // Constructeur général.
    public Objet(int initialX, int initialY, IForme forme, String pathImage, int sizeImageX, int sizeImageY) {
        this.x = initialX;
        this.y = initialY;
        this.forme = forme;
        this.pathImage = pathImage;

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

    // Constructeur pour tests collision.
    public Objet(int initialX, int initialY, IForme forme) {
        this.x = initialX;
        this.y = initialY;
        this.forme = forme;
    }

    /**
     * Avancer l'objet le temps d'un pas.
     */
    public void updatePosition() {
        x += hspeed;
        y += vspeed;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setOpaque(false);
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform transform = new AffineTransform();

        transform.translate((double) sizeImageX /2, (double) sizeImageY /2);
        transform.rotate(rotation);
        transform.translate((double) -sizeImageX /2, (double) -sizeImageY /2);

        g2d.drawImage(image, transform, this);
    }

    /**
     * Décide si this est en collision avec l'objet other.
     * @param other
     * @return
     */
    public boolean percute(Objet other) {
        return forme.percute(this, other);
    }

    /**
     * Méthode dépréciée qui décide quoi faire en cas de collision.
     * Désormais, la gestion de l'événement collision se fait dans la classe Gameplay.
     * @param other
     */
    public void eventCollision(Objet other) {
        System.out.println(other.pathImage + " (" + other.getXposition() + "," + other.getYposition() + ")");
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
    public int getSpeed() { return abs(hspeed) + abs(vspeed); }

    public void setRotation(double radians){
        rotation = radians;
    }

    public int getSizeImageX() {
        return sizeImageX;
    }

    public int getSizeImageY() {
        return sizeImageY;
    }

    public IForme getForme() {
        return forme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Objet objet)) return false;
        return getX() == objet.getX() && getY() == objet.getY() && hspeed == objet.hspeed && vspeed == objet.vspeed && getSizeImageX() == objet.getSizeImageX() && getSizeImageY() == objet.getSizeImageY() && Double.compare(objet.rotation, rotation) == 0 && bufferedImage.equals(objet.bufferedImage) && pathImage.equals(objet.pathImage) && image.equals(objet.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), hspeed, vspeed, getForme(), bufferedImage, pathImage, image, getSizeImageX(), getSizeImageY(), rotation);
    }
}

