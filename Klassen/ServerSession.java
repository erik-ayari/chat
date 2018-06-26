package methods;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerSession {

    private int serverPort;

    public ServerSession(int serverPort) {
        this.serverPort = serverPort;
    }

    public String[] waitForMessage() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.serverPort);

            //Eine Verbindung wird eingegangen
            Socket socket = serverSocket.accept();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String[] input = (String[]) in.readObject();


            String art = input[0];
            String message = input[1];
            String channel = input[2];

            serverSocket.close();

            switch (art) {
                case "msg":
                    return new String[]{message, channel};

                case "login":
                    break;

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new String[]{"ERROR"};
    }


}
