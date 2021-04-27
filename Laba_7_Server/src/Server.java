import collectionofflats.MyTreeMap;
import collectionofflats.StartWorkWithCollection;
import commands.Execute;
import commands.exceptions.ExitException;
import data.dao.DBManager;
import data.netdata.Report;
import data.netdata.Request;
import data.workwithrequest.ExecuteRequest;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Scanner;

public class Server {
    private int PORT;
    private InetAddress address;
    private DatagramSocket socket;

    private Scanner scanner;
    private MyTreeMap myMap;
    private static final String DB_URL = "jdbc:postgresql://localhost:9770/studs";
    private DBManager databaseManager;

    public Server(int port, Scanner scanner) {
        PORT = port;
        this.scanner = scanner;

        initDAO();
    }

    private void initDAO() {
        databaseManager = new DBManager(DB_URL);
    }

    public void run() {
        myMap = StartWorkWithCollection.initialization();
        try {
            socket = new DatagramSocket(2467);

            Runnable userInput = () -> {
                try {
                    while (true) {
                        String[] userCommand = (scanner.nextLine()).split(" ", 2);
                        System.out.println(Arrays.toString(userCommand));
                        if (userCommand[0].equals("save") || userCommand[0].equals("exit")) {
                            if (userCommand[0].equals("save") || userCommand.length == 2) {
                                Execute.execute(true, myMap, new Scanner("save\n" + userCommand[1] + "\nexit"), null);
                            }
                            if (userCommand[0].equals("exit")) {
                                Execute.execute(true, myMap, new Scanner("exit"), null);
                            }
                        } else {
                            System.out.println("Server has command save and command exit as well!");
                        }

                    }
                } catch (Exception e) {
                }
            };
            Thread thread = new Thread(userInput);
            thread.start();

            while (true) {
                clientRequest();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public void clientRequest()
            throws ExitException {
        Request request = null;
        Report report = null;

        try {
            byte[] accept = new byte[16384];
            DatagramPacket getPacket = new DatagramPacket(accept, accept.length);

            //Getting a new request from client and doing it
            socket.receive(getPacket);

            //Save path to client
            address = getPacket.getAddress();
            PORT = getPacket.getPort();

            //invoke the command
            request = deserialize(getPacket);
            report = ExecuteRequest.doingRequest(request, myMap);


        } catch (ExitException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //Sending a report to client
            byte[] sendBuffer = new byte[0];
            try {
                sendBuffer = serialize(report);
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, PORT);
                socket.send(sendPacket);
                System.out.println("Sending to " + sendPacket.getAddress() + ", message: " +
                        (report == null ? "ERROR" : report.getReportBody()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private <T> T deserialize(DatagramPacket getPacket) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        T request = (T) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return request;
    }

    private <T> byte[] serialize(T toSerialize) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(toSerialize);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }
}
