package data.dao;

import java.io.Console;
import java.sql.Connection;

public class DBManager {
    private String url;
    private String passwordServer;
    private String usernameServer;
    private Connection connection;

    public DBManager(String url) {
        this.url = url;
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver for working with DB has been found");
        } catch (ClassNotFoundException e) {
            System.out.println("Work error with database...exit from program");
            System.exit(-2);
        }

        Console autorization = System.console();
        autorization.printf("Enter the username: ");
        usernameServer = autorization.readLine();
        autorization.printf("Enter the password: ");
        passwordServer = new String(autorization.readPassword());

        System.out.println("Username: " + usernameServer + ", password: " + passwordServer);
    }
}
