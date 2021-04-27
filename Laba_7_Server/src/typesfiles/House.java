package typesfiles;

import java.io.Serializable;

/**
 * Class of house type of flat
 */
public class House implements Serializable {
    private String name; //Поле не может быть null
    private int year; //Значение поля должно быть больше 0
    private int numberOfFlatsOnFloor; //Значение поля должно быть больше 0
    private Integer numberOfLifts; //Значение поля должно быть больше 0

    public House(){

    }

    /**
     * Constructor for create a new house
     * @param name set new name
     * @param year set new year of building
     * @param numberOfFlatsOnFloor set number of flats on floor
     * @param numberOfLifts set number of lifts
     */
    public House(String name, int year, int numberOfFlatsOnFloor, Integer numberOfLifts) {
        this.name = name;
        this.year = year;
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
        this.numberOfLifts = numberOfLifts;
    }

    /**
     * @return name of this house
     */
    public String getName() {
        return name;
    }

    /**
     * @param name set new name of this house
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }

    public void setNumberOfFlatsOnFloor(int numberOfFlatsOnFloor) {
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }

    public Integer getNumberOfLifts() {
        return numberOfLifts;
    }

    public void setNumberOfLifts(Integer numberOfLifts) {
        this.numberOfLifts = numberOfLifts;
    }

    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", numberOfFlatsOnFloor=" + numberOfFlatsOnFloor +
                ", numberOfLifts=" + numberOfLifts +
                '}';
    }
}
