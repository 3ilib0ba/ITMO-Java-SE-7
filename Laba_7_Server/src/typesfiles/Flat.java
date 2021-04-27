package typesfiles;

import java.io.Serializable;
import java.util.Date;

/**
 * Flat class
 */
public class Flat implements Comparable<Flat>, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
                    // Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long area; //Максимальное значение поля: 667, Значение поля должно быть больше 0
    private Long numberOfRooms; //Поле может быть null, Значение поля должно быть больше 0
    private int numberOfBathrooms; //Значение поля должно быть больше 0
    private long timeToMetroOnFoot; //Значение поля должно быть больше 0
    private Furnish furnish; //Поле не может быть null
    private House house; //Поле может быть null

    /**
     * Private constructor. All objects must be creating with a builder template
     */
    private Flat() {
    }

    /**
     * @return id of this flat
     */
    public int getId() {
        return id;
    }

    /**
     * @return name of this flat
     */
    public String getName() {
        return name;
    }

    /**
     * @return coordinates of this flat
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return creationDate of this flat
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @return area of this flat
     */
    public long getArea() {
        return area;
    }

    /**
     * @return number od rooms of this flat
     */
    public Long getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * @return number of bathrooms of this flat
     */
    public int getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    /**
     * @return time to metro on foot of this flat
     */
    public long getTimeToMetroOnFoot() {
        return timeToMetroOnFoot;
    }

    /**
     * @return furnish of this flat
     */
    public Furnish getFurnish() {
        return furnish;
    }

    /**
     * @return house of this flat
     */
    public House getHouse() {
        return house;
    }

    /**
     * @param id to set new id this flat
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name to set new name this flat
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param coordinates to set new coordinates this flat
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @param creationDate to set new creation date this flat
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @param area to set new area this flat
     */
    public void setArea(long area) {
        this.area = area;
    }

    /**
     * @param numberOfRooms to set new number od rooms this flat
     */
    public void setNumberOfRooms(Long numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    /**
     * @param numberOfBathrooms to set new number od bathrooms this flat
     */
    public void setNumberOfBathrooms(int numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    /**
     * @param timeToMetroOnFoot to set new time to metro on foot this flat
     */
    public void setTimeToMetroOnFoot(long timeToMetroOnFoot) {
        this.timeToMetroOnFoot = timeToMetroOnFoot;
    }

    /**
     * @param furnish to set new furnish this flat
     */
    public void setFurnish(Furnish furnish) {
        this.furnish = furnish;
    }

    /**
     * @param house to set new house this flat
     */
    public void setHouse(House house) {
        this.house = house;
    }

    // Создаём статический метод, при помощи которого
    // будут вызываться private constructors

    /**
     * @return new Inner class that can build new Flat object
     */
    public static Builder newBuilder() {
        return new Flat().new Builder();
    }

    @Override
    public int compareTo(Flat o) {
        long a = this.getArea() - o.getArea();
        int b = this.id - o.getId();

        if (b < 0) {
            return -1;
        } else if (b > 0) {
            return 1;
        }

        if (a < 0) {
            return -1;
        } else if (a > 0) {
            return 1;
        }

        return 0;
    }

    /**
     * Inner class for comfortable creation new objects
     */
    public class Builder {
        /**
         * Private constructor.
         */
        private Builder() {
        }

        /**
         * @param id
         * @return Builder with object with new Id
         */
        public Builder setId(int id) {
            Flat.this.id = id;

            return this;
        }

        /**
         * @param name
         * @return Builder with object with new name
         */
        public Builder setName(String name) {
            Flat.this.name = name;

            return this;
        }

        /**
         * @param coordinates
         * @return Builder with object with new coordinates
         */
        public Builder setCoordinates(Coordinates coordinates) {
            Flat.this.coordinates = coordinates;

            return this;
        }

        /**
         * @return Builder with object with new creationDate
         */
        public Builder setCreationDate() {
            Flat.this.creationDate = new Date();

            return this;
        }

        /**
         * @param area
         * @return Builder with object with new area
         */
        public Builder setArea(long area) {
            Flat.this.area = area;

            return this;
        }

        /**
         * @param numberOfRooms
         * @return Builder with object with new number of rooms
         */
        public Builder setNumberOfRooms(Long numberOfRooms) {
            Flat.this.numberOfRooms = numberOfRooms;

            return this;
        }

        /**
         * @param numberOfBathrooms
         * @return Builder with object with new number of bathrooms
         */
        public Builder setNumberOfBathrooms(int numberOfBathrooms) {
            Flat.this.numberOfBathrooms = numberOfBathrooms;

            return this;
        }

        /**
         * @param timeToMetroOnFoot
         * @return Builder with object with new time to metro
         */
        public Builder setTimeToMetroOnFoot(long timeToMetroOnFoot) {
            Flat.this.timeToMetroOnFoot = timeToMetroOnFoot;

            return this;
        }

        /**
         * @param furnish
         * @return Builder with object with new furnish
         */
        public Builder setFurnish(Furnish furnish) {
            Flat.this.furnish = furnish;

            return this;
        }

        /**
         * @param house
         * @return Builder with object with new House
         */
        public Builder setHouse(House house) {
            Flat.this.house = house;

            return this;
        }

        /**
         * @return new flat object
         */
        public Flat build() {
            return Flat.this;
        }
    }

    @Override
    public String toString() {
        return "Flat{\n" +
                "\tid=" + id + ", \n" +
                "\tname='" + name + '\'' + ", \n" +
                "\tcoordinates=" + coordinates + ", \n" +
                "\tcreationDate=" + creationDate + ", \n" +
                "\tarea=" + area + ", \n" +
                "\tnumberOfRooms=" + numberOfRooms + ", \n" +
                "\tnumberOfBathrooms=" + numberOfBathrooms + ", \n" +
                "\ttimeToMetroOnFoot=" + timeToMetroOnFoot + ", \n" +
                "\tfurnish=" + furnish + ", \n" +
                "\thouse=" + house + "\n" +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
