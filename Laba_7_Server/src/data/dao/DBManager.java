package data.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static DBManager instance;

    private String url;
    private String passwordServer;
    private String usernameServer;
    private Connection connection;

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
            throws SQLException{
        try {
            connection = DriverManager.getConnection(url, usernameServer, passwordServer);
        } catch (SQLException e) {
            throw e;
        }
    }
}
