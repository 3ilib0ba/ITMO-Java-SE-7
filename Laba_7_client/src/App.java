import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException {
        System.out.println("Client is running");

        Scanner scanner = new Scanner(System.in);

        Client client = new Client("localhost", 2467, scanner);
        client.run();
    }
}
