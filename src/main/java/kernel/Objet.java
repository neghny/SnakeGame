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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class Objet extends JPanel {
    public double x;
    public double y;

    public String getPathImage() {
        return pathImage;
    }

    String pathImage;

    double speed = 0; //vitesse
    double direction = 0; //Angle en radian
    public IForme forme;

    BufferedImage bufferedImage;
    Image image;
    int sizeImageX;
    int sizeImageY;
    double rotation = 0; //Angle in degrees

    // Constructeur général.

    public Objet(double initialX, double initialY, IForme forme, String pathImage, int sizeImageX, int sizeImageY) {
        this.x = initialX;
        this.y = initialY;
        this.forme = forme;
        this.pathImage = pathImage;

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


    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }
    //mettre à jour la position de l'objet en fonction de la direction et de la vitesse

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setOpaque(false);
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform transform = new AffineTransform();

        transform.translate(sizeImageX/2, sizeImageY/2);
        transform.rotate(Math.toRadians(rotation));
        transform.translate(-sizeImageX/2, -sizeImageY/2);

        //g2d.drawImage(image, 0, 0, this.sizeImageX, this.sizeImageY, null);
        g2d.drawImage(image, transform, this);
    }

    public void updatePosition() {
        x += getHSpeed();
        y += getVSpeed();
    }

    public void moveRight() {
        x += getSpeed();
    }

    public void moveLeft() {
        x -= getSpeed();
    }

    public void moveUp() {
        y -= getSpeed();
    }

    public void moveDown() {
        y += getSpeed();
    }

    //methode pour modifier la direction de l'objet*

    public void setDirection(double newDirection) {
        this.direction = newDirection;

    }

    public void setSpeed(double newSpeed){
        this.speed = newSpeed;
    }

    //Pour obtenir la vitesse actuelle

    public double getDirection(){
        return direction;
    }

    public double getSpeed(){
        return speed;
    }

    public double getHSpeed() {
        return speed * Math.cos(direction);
    }

    public double getVSpeed(){
        return speed * Math.sin(direction);
    }

    // Collisions
    public boolean percute(Objet other) {
        return forme.percute(this, other);
    }
    public LinkedList<Objet> detectCollisions(ArrayList<Objet> objs) {
        LinkedList<Objet> res = new LinkedList<>();
        for (Objet o : objs)
            if (percute(o))
                res.add(o);
        return res;
    }

    // Evénements

    public double getXposition() {
        return x;
    }


    public double getYposition() {
        return y;
    }
    public double getRotation() {return rotation;}

    public void setRotation(double degrees){
        rotation = degrees;
    }

    public int getSizeImageX() {
        return sizeImageX;
    }

    public int getSizeImageY() {
        return sizeImageY;
    }

    public void eventCollision(Objet other) { System.out.println(other.y); }

}

