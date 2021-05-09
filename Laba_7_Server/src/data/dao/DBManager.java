package data.dao;

import collectionofflats.MyTreeMap;
import data.netdata.ClientIdentificate;
import typesfiles.Coordinates;
import typesfiles.Flat;
import typesfiles.Furnish;
import typesfiles.House;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Date;

public class DBManager {
    private static DBManager instance;

    private String url;
    private String passwordServer;
    private String usernameServer;
    private Connection connection;

    private static final String pepper = "134F@!!9hTn4-@+*dfs*12";
    private static final String INSERT_NEW_USER_REQUEST = "INSERT INTO clients (login, password) " +
            "VALUES (?, ?)";
    private static final String LOAD_COLLECTION_REQUEST = "SELECT * FROM flats " +
            "INNER JOIN coordinates ON flats.coordinates_id = coordinates.id " +
            "INNER JOIN furnish ON flats.furnish_id = furnish.id " +
            "INNER JOIN houses ON flats.house_id = houses.id ";
    private static final String INSERT_FLAT_TO_REQUEST = "INSERT INTO flats " +
            "(name, creationdate, area, numsofrooms, numsofbaths, timetometro, " +
            "coordinates_id, furnish_id, house_id, client_id)" +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";


    private DBManager(String url, String passwordServer, String usernameServer) {
        this.url = url;
        this.passwordServer = passwordServer;
        this.usernameServer = usernameServer;
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver for working with DB has been found");
        } catch (ClassNotFoundException e) {
            System.out.println("Work error with database...exit from program");
            System.exit(-2);
        }// System.out.println("Username: " + usernameServer + ", password: " + passwordServer);
    }

    // the Singleton manager realization
    public static DBManager getInstance(String url, String passwordServer, String usernameServer) {
        if (instance == null) {
            instance = new DBManager(url, passwordServer, usernameServer);
        }
        return instance;
    }

    public void connectToDatabase()
            throws SQLException {
        connection = DriverManager.getConnection(url, usernameServer, passwordServer);

        /*String sql = "INSERT INTO flats (name, creationdate, area, numsofrooms, numsofbaths, timetometro, coordinates_id, furnish_id, house_id, client_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "ABoba");
        preparedStatement.setTimestamp(2, new Timestamp( new Date().getTime()));
        preparedStatement.setLong(3, 120);
        preparedStatement.setLong(4, 110);
        preparedStatement.setInt(5, 5);
        preparedStatement.setInt(6, 10);
        preparedStatement.setInt(7, 1);
        preparedStatement.setInt(8, 1);
        preparedStatement.setInt(9, 1);
        preparedStatement.setInt(10, 1);
        preparedStatement.execute();*/
    }

    private String hashPassword(String password) {
        String MD5 = password + pepper;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            MD5 = new String(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Problem with hashing! Password isn't hashed");
        }
        return MD5;
    }

    public void insertNewClient(String login, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_USER_REQUEST);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, hashPassword(password));
        preparedStatement.execute();
    }

    private Flat parseToFlat(ResultSet flat) throws SQLException {
        int id = flat.getInt(1);
        String name = flat.getString(2);
        Date dateCreate = flat.getDate(3);
        long area = flat.getLong(4);
        long numsOfRooms = flat.getLong(5);
        int numberOfBaths = flat.getInt(6);
        long time = flat.getLong(7);
        Coordinates coord = new Coordinates(flat.getFloat(13), flat.getDouble(14));
        Furnish furnish = Furnish.valueOf(flat.getString(16));
        House house = new House(flat.getString(18),
                flat.getInt(19),
                flat.getInt(21),
                flat.getInt(20));
        Flat parsing = Flat.newBuilder()
                .setId(id)
                .setName(name)
                .setCreationDate(dateCreate)
                .setArea(area)
                .setNumberOfRooms(numsOfRooms)
                .setNumberOfBathrooms(numberOfBaths)
                .setTimeToMetroOnFoot(time)
                .setCoordinates(coord)
                .setFurnish(furnish)
                .setHouse(house)
                .build();
        System.out.println("\n Добавлена квартира: \n" + parsing);
        return parsing;
    }

    public void loadFullCollection(MyTreeMap mapManager) throws SQLException {
        PreparedStatement sqlOfLoad = connection.prepareStatement(LOAD_COLLECTION_REQUEST);
        ResultSet collectionDB = sqlOfLoad.executeQuery();

        int key = 1;
        while (collectionDB.next()) {
            /*System.out.println("id = " + collectionDB.getString(1) + "\n" +
                    "name = " + collectionDB.getString(2) + "\n" +
                    "date = " + collectionDB.getString(3) + "\n" +
                    "area = " + collectionDB.getString(4) + "\n" +
                    "nums of rooms = " + collectionDB.getString(5) + "\n" +
                    "nums of baths = " + collectionDB.getString(6) + "\n" +
                    "time to metro = " + collectionDB.getString(7) + "\n" +
                    "coordinates id = " + collectionDB.getString(8) + "\n" +
                    "furnish id = " + collectionDB.getString(9) + "\n" +
                    "house id = " + collectionDB.getString(10) + "\n" +
                    "client id = " + collectionDB.getString(11) + "\n" +
                    "id of coord = " + collectionDB.getString(12) + "\n" +
                    "coord X = " + collectionDB.getString(13) + "\n" +
                    "coord Y = " + collectionDB.getString(14) + "\n" +
                    "id of furnish = " + collectionDB.getString(15) + "\n" +
                    "FURNISH type = " + collectionDB.getString(16) + "\n" +
                    "id of house = " + collectionDB.getString(17) + "\n" +
                    "name of house = " + collectionDB.getString(18) + "\n" +
                    "year = " + collectionDB.getString(19) + "\n" +
                    "number of lifts = " + collectionDB.getString(20) + "\n" +
                    "number of flats = " + collectionDB.getString(21) + "\n");
            */
            mapManager.addFlat(key++, parseToFlat(collectionDB));
        }
    }

    public void insertNewFlat(Flat addingFlat, ClientIdentificate client) {

    }
}

