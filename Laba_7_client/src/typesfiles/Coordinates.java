package typesfiles;

import java.io.Serializable;

/**
 * Home position class
 */
public class Coordinates implements Serializable {
    private float x; //Максимальное значение поля: 483
    private double y; //Максимальное значение поля: 311

    public Coordinates(float x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return - a coordinate X of house
     */
    public float getX() { return x; }

    /**
     * @param x - set new coordinate X
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return - a coordinate Y of house
     */
    public double getY() {
        return y;
    }

    /**
     * @param y - set new coordinate Y
     */
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x = " + x +
                ", y = " + y +
                '}';
    }
}
