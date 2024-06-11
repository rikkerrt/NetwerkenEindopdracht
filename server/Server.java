import games.Hangman;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;

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
//            if (connections.size() >= 2) {
//                socket.close();
//            }

            Connection connection = new Connection(socket);


            connections.add(connection);
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
    public static void launchHangman(){
        if(connections.size() >= 2) {
            player = connections.get(0);
            player2 = connections.get(1);
        }

        new Hangman();
    }

    public static void disconnect(Connection connection) {
        connections.remove(connection);
    }
}
