package physique;

public class Objet {
    double x = 0;
    double y = 0;
    double speed = 0; //vitesse
    double direction = 0; //Angle en radian


    IForme forme;


    public Objet(double initialDirection, double initialSpeed) {
        this.direction = initialDirection;
        this.speed = initialSpeed;
    }

    //mettre à jour la position de l'objet en fonction de la direction et de la vitesse

    public void updatePosition() {
        x = speed * Math.cos(direction);
        y = speed * Math.sin(direction);

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







}


