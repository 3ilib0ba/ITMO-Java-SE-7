import collectionofflats.StartWorkWithCollection;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        StartWorkWithCollection.initialization();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Server is running");

        Server server = new Server(2468, scanner);
        server.run();
    }
}
