import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.Buffer;

public class Connection {
    private String nickname;
    private final BufferedWriter writer;
    private final BufferedReader reader;
    private final Socket socket;
    public Connection(Socket socket){
        this.socket = socket;
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        Thread thread = new Thread(this::receiveData);
        thread.start();
    }
    private void receiveData(){
        try {
            while (socket.isConnected()){
                String message = this.reader.readLine();
                if (message == null){
                    break;
                }
                if (nickname == null){
                    if (message.equals("")){
                        writer.write("Nickname is niet ingevuld");
                    } else {
                        nickname = message;
                        writer.write("Je wordt nu weergegeven als: " + nickname + "\n");
                        Server.WriteToAllExcept(message + " is nu verbonden\n", nickname);

                    }
                }
                else {
                    Server.WriteToAll(nickname + " SAYS: " + message + "\n");
                }
                writer.flush();
            }
        } catch (Exception e) {

        }

        Server.disconnect(this);
    }
    public String getNickname(){
        return this.nickname;
    }
    public void write(String message){
        try {
            writer.write(message);
            writer.flush();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public void launchHangman(){

    }

    @Override
    public String toString() {
        return nickname;
    }
}
