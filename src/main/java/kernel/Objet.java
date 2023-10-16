package kernel;

import physique.IForme;

public class Objet {
    public double x = 0;
    public double y = 0;

    public String getPathImage() {
        return pathImage;
    }

    String pathImage;

    double speed = 0; //vitesse
    double direction = 0; //Angle en radian
    public IForme forme;


    public Objet(double initialX, double initialY, IForme forme) {
        this.x = initialX;
        this.y = initialY;
        this.forme = forme;
        System.out.println("Objet");
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSpeed(){
        return speed;
    }

    public double getHSpeed() {
        return speed * Math.cos(direction);
    }

    // Collisions
    public boolean percute(Objet other) {
        return forme.percute(this, other);
    }
}
