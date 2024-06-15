import games.Hangman;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import jdk.nashorn.internal.AssertsEnabled;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connection {
    private String nickname;
    private Matcher match;
    private final BufferedWriter writer;
    private final BufferedReader reader;
    private final Socket socket;
    Pattern pat = Pattern.compile("^[A-Z][a-z]{4,14}$");

    public Connection(Socket socket) {
        this.socket = socket;
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Thread thread = new Thread(this::receiveData);
        thread.start();
    }

    private void receiveData() {
        try {
            while (socket.isConnected()) {
                String message = reader.readLine();

                if (message == null) {
                    break;
                }

                if (nickname == null) {
                    Matcher match = pat.matcher(message);
                    if (!match.matches()) {
                        write("Je nickname voldoet niet aan de eisen\n");
                        System.out.println("voldoet niet");
                    } else {
                        nickname = message;
                        write("Je wordt nu weergegeven als: " + nickname + "\n");
                        Server.WriteToAllExcept(message + " is nu verbonden\n", nickname);
                    }
                } else {
                    Server.WriteToAll(nickname + " SAYS: " + message + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Server.disconnect(this);
        }
    }


    public String getNickname() {
        return this.nickname;
    }

    public Socket getSocket() {
        return socket;
    }

    public void write(String message) {
        try {
            writer.write(message);
            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void launchHangman() {

    }

    @Override
    public String toString() {
        return nickname;
    }
}