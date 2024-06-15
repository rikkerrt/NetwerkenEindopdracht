import games.Hangman;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;

public class Server {
    private static ArrayList<Connection> connections = new ArrayList<>();
    private static Connection player;
    private static Connection player2;


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);

        while (true) {
            Socket socket = serverSocket.accept();
//
            if (player == null) {
                player = new Connection(socket);
                connections.add(player);
            } else if (player2 == null) {
                player2 = new Connection(socket);
                connections.add(player2);
            }
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
