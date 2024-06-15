import games.Hangman;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;

public class Server {
    private static ArrayList<Connection> connections = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println(InetAddress.getLocalHost());

        while (true) {

            Socket socket = serverSocket.accept();
            connections.add(new Connection(socket));

        }
    }

    public static void WriteToAllExcept(String message, String nickname) {
        for (Connection connection : connections) {
            if (nickname.equals(connection.getNickname()))
                continue;
            connection.write(message);
        }
    }

    public static void WriteToAll(String message) {
        for (Connection connection : connections) {
            connection.write(message);
        }
    }

    public static void disconnect(Connection connection) {
        connections.remove(connection);
    }
}
