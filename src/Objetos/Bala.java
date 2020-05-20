package Objetos;

import java.io.Serializable;

public class Bala implements Serializable {

    double posX;
    double posY;
    private double velocityY;
    
    public Bala(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
        velocityY = 0.5;
    }
    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
    public void forwardBullet (double time){        
               
        posY = posY -  velocityY * time;
    }
}
