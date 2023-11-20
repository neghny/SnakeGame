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

    // for testCollision
    public Objet(int initialX, int initialY, IForme forme) {
        this.x = initialX;
        this.y = initialY;
        this.forme = forme;
        this.horizontalSpeed = 0;
        this.verticalSpeed = 0;
    }

    /**
     * Avancer l'objet le temps d'un pas.
     */
    public void updatePosition() {
        x += horizontalSpeed;
        y += verticalSpeed;
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
        transform.translate((double) -sizeImageX /2, (double) -sizeImageY /2);

        g2d.drawImage(image, transform, this);
    }

    /**
     * Décide si this est en collision avec l'objet other.
     */
    public boolean percute(Objet other) {
        return forme.percute(this, other);
    }

    public int getXposition() { return x; }

    public int getYposition() {
        return y;
    }

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

