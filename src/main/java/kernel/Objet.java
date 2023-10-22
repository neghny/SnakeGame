package kernel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import physique.IForme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class Objet extends JPanel {
    public int x;
    public int y;

    public String getPathImage() {
        return pathImage;
    }

    String pathImage;

    int hspeed = 0;
    int vspeed = 0;
    public IForme forme;

    BufferedImage bufferedImage;
    Image image;
    int sizeImageX;
    int sizeImageY;
    double rotation = 0; //Angle in degrees

    // Constructeur pour tests collision.
    public Objet(int initialX, int initialY, IForme forme) {
        this.x = initialX;
        this.y = initialY;
        this.forme = forme;
    }
    // Constructeur général.
    public Objet(int initialX, int initialY, IForme forme, String pathImage, int sizeImageX, int sizeImageY) {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setOpaque(false);
        g.drawImage(image, 0, 0, this.sizeImageX, this.sizeImageY, null);
    }


    public void setPosition(int x, int y) {
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
        x += hspeed;
        y += vspeed;
    }

    //methode pour modifier la direction de l'objet*

    public void setDirection(double dir) {
        setDirSpd(getSpeed(), dir);
    }

    public void setSpeed(double spd){
        setDirSpd(spd, getDirection());
    }

    public void setDirSpd(double dir, double spd) {
        hspeed = (int) floor(cos(dir) * spd);
        vspeed = (int) floor(sin(dir) * spd);
    }

    //Pour obtenir la vitesse actuelle

    public double getDirection() {
        if (hspeed > 0 && vspeed > 0)
            return atan((double) vspeed / hspeed);
        if (hspeed < 0 && vspeed > 0)
            return atan((double) vspeed / -hspeed);
        if (hspeed < 0 && vspeed < 0)
            return atan((double) vspeed / hspeed);
        if (hspeed > 0 && vspeed < 0)
            return atan((double) -vspeed / hspeed);
        return 0.;
    }

    public double getSpeed() {
        return sqrt(hspeed * hspeed + vspeed * vspeed);
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
    public void eventCollision(Objet other) {  }



    public int getXposition() {
        return x;
    }

    public int getYposition() {
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
}
