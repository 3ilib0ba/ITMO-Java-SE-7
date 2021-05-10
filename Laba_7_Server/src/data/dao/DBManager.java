package data.dao;

import collectionofflats.MyTreeMap;
import commands.exceptions.InvalidArgExcaption;
import data.dao.exceptions.InvalidPasswordException;
import data.dao.exceptions.NotClientObjectException;
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
    private static final String FIND_USER_REQUEST = "SELECT * FROM clients " +
            "WHERE login = ?";
    private static final String LOAD_COLLECTION_REQUEST = "SELECT * FROM flats " +
            "INNER JOIN coordinates ON flats.coordinates_id = coordinates.id " +
            "INNER JOIN furnish ON flats.furnish_id = furnish.id " +
            "INNER JOIN houses ON flats.house_id = houses.id ";
    private static final String INSERT_COORDINATES_REQUEST = "INSERT INTO coordinates " +
            "(x, y) " +
            "VALUES (?, ?) RETURNING id ";
    private static final String INSERT_HOUSE_REQUEST = "INSERT INTO houses " +
            "(name, year, numberoflifts, numberofflats) " +
            " VALUES (?, ?, ?, ?) RETURNING id";
    private static final String FIND_FURNISH_REQUEST = "SELECT id FROM furnish " +
            "WHERE type = ?";
    private static final String INSERT_FLAT_TO_REQUEST = "INSERT INTO flats " +
            "(name, creationdate, area, numsofrooms, numsofbaths, timetometro, " +
            "coordinates_id, furnish_id, house_id, client_id)" +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_FLAT_BY_ID_REQUEST = "SELECT * FROM flats WHERE id = ?";
    private static final String UPDATE_FLAT_BY_ID_REQUEST = "UPDATE flats SET " +
            "name = ?, creationdate = ?, area = ?, numsofrooms = ?, numsofbaths = ?, " +
            "timetometro = ?, coordinates_id = ?, furnish_id = ?, house_id = ?, client_id = ? " +
            "WHERE id = ?";


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

    public void insertNewClient(ClientIdentificate client) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_USER_REQUEST);
        preparedStatement.setString(1, client.getLogin());
        preparedStatement.setString(2, client.getPassword());
        client.setIdOfClient(findUser(client.getLogin(), client.getPassword()));
        preparedStatement.execute();
    }

    public Integer findUser(String login, String password)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_REQUEST);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();

        Integer id = null;
        if (resultSet.next()) {
            String passwordInDB = resultSet.getString(3);
            if (password.equals(passwordInDB)) {
                id = resultSet.getInt(1);
            } else {
                throw new InvalidPasswordException("Input invalid password");
            }
        }
        return id;
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
            mapManager.addFlat(MyTreeMap.ID_MAX++, parseToFlat(collectionDB));
        }
    }

    public int insertNewCoordinates(Coordinates coordinates) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COORDINATES_REQUEST);
        preparedStatement.setFloat(1, coordinates.getX());
        preparedStatement.setDouble(2, coordinates.getY());
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        resultSet.next();
        int idOfCoord = resultSet.getInt(1);
        preparedStatement.close();
        return idOfCoord;
    }

    public int insertNewHouse(House house) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_HOUSE_REQUEST);
        preparedStatement.setString(1, house.getName());
        preparedStatement.setInt(2, house.getYear());
        preparedStatement.setInt(3, house.getNumberOfLifts());
        preparedStatement.setInt(4, house.getNumberOfFlatsOnFloor());
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        resultSet.next();
        int idOfHouse = resultSet.getInt(1);
        preparedStatement.close();
        return idOfHouse;
    }

    public int findFurnishType(Furnish furnish) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_FURNISH_REQUEST);
        preparedStatement.setString(1, furnish.toString());
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        resultSet.next();
        int idOfFurnish = resultSet.getInt(1);
        preparedStatement.close();
        return idOfFurnish;
    }

    public void insertNewFlat(Flat addingFlat, ClientIdentificate client)
            throws SQLException {
        try {
            connection.setAutoCommit(false);
            int idCoord = insertNewCoordinates(addingFlat.getCoordinates());
            int idHouse = insertNewHouse(addingFlat.getHouse());
            int idFurnish = findFurnishType(addingFlat.getFurnish());

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FLAT_TO_REQUEST);

            preparedStatement.setString(1, addingFlat.getName());
            preparedStatement.setTimestamp(2, new Timestamp(addingFlat.getCreationDate().getTime()));
            preparedStatement.setLong(3, addingFlat.getArea());
            preparedStatement.setLong(4, addingFlat.getNumberOfRooms());
            preparedStatement.setInt(5, addingFlat.getNumberOfBathrooms());
            preparedStatement.setLong(6, addingFlat.getTimeToMetroOnFoot());
            preparedStatement.setInt(7, idCoord);
            preparedStatement.setInt(8, idFurnish);
            preparedStatement.setInt(9, idHouse);
            preparedStatement.setInt(10, client.idOfClient);

            preparedStatement.execute();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void updateFlatById(Flat updating, ClientIdentificate client, Integer idSwap)
            throws SQLException {
        try {
            PreparedStatement checkIdObj = connection.prepareStatement(FIND_FLAT_BY_ID_REQUEST);
            checkIdObj.setInt(1, idSwap);
            ResultSet result = checkIdObj.executeQuery();
            if (result.next()) {
                if (client.idOfClient == result.getInt(11))
                    throw new NotClientObjectException("This flat isn't your");
            } else {
                throw new InvalidArgExcaption("Flat with this ID not found...");
            }
        } catch (SQLException e) {
            throw e;
        }

        try {
            connection.setAutoCommit(false);
            int idCoord = insertNewCoordinates(updating.getCoordinates());
            int idHouse = insertNewHouse(updating.getHouse());
            int idFurnish = findFurnishType(updating.getFurnish());

            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FLAT_BY_ID_REQUEST);

            preparedStatement.setString(1, updating.getName());
            preparedStatement.setTimestamp(2, new Timestamp(updating.getCreationDate().getTime()));
            preparedStatement.setLong(3, updating.getArea());
            preparedStatement.setLong(4, updating.getNumberOfRooms());
            preparedStatement.setInt(5, updating.getNumberOfBathrooms());
            preparedStatement.setLong(6, updating.getTimeToMetroOnFoot());
            preparedStatement.setInt(7, idCoord);
            preparedStatement.setInt(8, idFurnish);
            preparedStatement.setInt(9, idHouse);
            preparedStatement.setInt(10, client.idOfClient);
            preparedStatement.setInt(11, idSwap);

            preparedStatement.execute();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}

