package kernel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import physique.IForme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class Objet extends JPanel {
    public double x = 0;
    public double y = 0;

    public String getPathImage() {
        return pathImage;
    }

    String pathImage;

    double speed = 0; //vitesse
    double direction = 0; //Angle en radian
    public IForme forme;

    BufferedImage image;
    Objet objet;
    int sizeImageX;
    int sizeImageY;

    // Constructeur pour tests collision.
    public Objet(double initialX, double initialY, IForme forme) {
        this.x = initialX;
        this.y = initialY;
        this.forme = forme;
    }
    // Constructeur général.
    public Objet(double initialX, double initialY, IForme forme, String pathImage, int sizeImageX, int sizeImageY) throws IOException {
        this.x = initialX;
        this.y = initialY;
        this.forme = forme;
        this.pathImage = pathImage;

        Logger logger = LogManager.getLogger(this.getClass());
        logger.debug("Construct a MyJavaPanel");
        String path = pathImage;
        if (logger.isDebugEnabled()) {
            String message = MessageFormat.format("Loading image at path {0}", path);
            logger.debug(message);
        }
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
        } catch (Exception ex) {
            String message = MessageFormat.format("Error: Cannot load image at path: {0}", path);
            logger.error(message, ex);
        }
        this.sizeImageX = sizeImageX;
        this.sizeImageY = sizeImageY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, sizeImageX, sizeImageY, null);
    }


    //mettre à jour la position de l'objet en fonction de la direction et de la vitesse

    public void updatePosition() {
        x += getHSpeed();
        y += speed * Math.sin(direction);
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
    public void eventCollision(Objet other) {}



    public double getXposition() {
        return x;
    }


    public double getYposition() {
        return y;
    }

    public int getSizeImageX() {
        return sizeImageX;
    }

    public int getSizeImageY() {
        return sizeImageY;
    }
}
