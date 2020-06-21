package Objetos;

import javafx.scene.image.Image;

public class Numero {

    private Image image;
    private double positionX;
    private double positionY;
    private double velocityY;
    private double width;
    private double height;

    public Numero() {
        positionX = 0;
        positionY = 0;
        velocityY = 0.5;
    }

    public void setImage(Image i) {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

    public void update(double time) {
        positionY = positionY +  velocityY * time;
    }
    
    public Image getImage() {
        return image;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
    @Override
    public String toString() {
        return "PositionY: " + positionY;
    }
}
