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

public class Objet extends JPanel {
    private double x;
    private double y;
    private double speed;

    public enum Direction {LEFT, UP, DOWN, RIGHT}
    private Direction direction;

    private final IForme forme;

    private BufferedImage bufferedImage;
    private String pathImage;
    private Image image;
    private int sizeImageX;
    private int sizeImageY;
    private double rotation;

    // Constructeur général.
    public Objet(double initialX, double initialY, IForme forme, String pathImage, int sizeImageX, int sizeImageY) {
        this.x = initialX;
        this.y = initialY;
        this.forme = forme;
        this.pathImage = pathImage;
        this.direction = Direction.RIGHT;

        Logger logger = LogManager.getLogger(this.getClass());
        logger.debug("Construct a JPanel");
        //String path = pathImage;
        if (logger.isDebugEnabled()) {
            String message = MessageFormat.format("Loading image at path {0}", pathImage);
            logger.debug(message);
        }
        try {
            bufferedImage = ImageIO.read(Objects.requireNonNull(getClass().getResource(pathImage)));
            //image = ImageIO.read(new File(pathImage));

        } catch (Exception ex) {
            String message = MessageFormat.format("Error: Cannot load image at path: {0}", pathImage);
            logger.error(message, ex);
        }
        //this.setSize(new Dimension(sizeImageX, sizeImageY));
        this.sizeImageX = sizeImageX;
        this.sizeImageY = sizeImageY;
        image = bufferedImage.getScaledInstance(sizeImageX, sizeImageY, Image.SCALE_DEFAULT);
//        this.setPreferredSize(new Dimension(sizeImageX, sizeImageY));
    }

    // Constructeur pour tests collision.
    public Objet(double initialX, double initialY, IForme forme) {
        this.x = initialX;
        this.y = initialY;
        this.forme = forme;
    }


    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setOpaque(false);
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform transform = new AffineTransform();

        transform.translate((double) sizeImageX / 2, (double) sizeImageY / 2);
        transform.rotate(rotation);
        transform.translate((double) -sizeImageX / 2, (double) -sizeImageY / 2);

        //g2d.drawImage(image, 0, 0, this.sizeImageX, this.sizeImageY, null);
        g2d.drawImage(image, transform, this);
    }

    public void moveRight() {
        x += speed;
        setRotation(0);
    }

    public void moveLeft() {
        x -= speed;
        setRotation(Math.PI);
    }

    public void moveUp() {
        y -= speed;
        setRotation(3 * Math.PI / 2);
    }

    public void moveDown() {
        y += speed;
        setRotation(Math.PI / 2);
    }

    public void move() {
        switch (direction) {
            case LEFT -> moveLeft();
            case UP -> moveUp();
            case DOWN -> moveDown();
            case RIGHT -> moveRight();
        }
    }

    public boolean percute(Objet other) {
        return forme.percute(this, other);
    }


    public void eventCollision(Objet other) {
        System.out.println(other.pathImage + " (" + other.getXposition() + "," + other.getYposition() + ")");
    }

    public double getXposition() {
        return x;
    }

    public double getYposition() {
        return y;
    }

    public void setRotation(double radians) {
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

    public void setSpeed(double newSpeed) {
        this.speed = newSpeed;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}

