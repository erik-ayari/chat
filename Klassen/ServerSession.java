package methods;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSession {

    private int serverPort;

    public ServerSession(int serverPort) {
        this.serverPort = serverPort;
    }

    public String run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.serverPort);

            //Eine Verbindung wird eingegangen
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String input = in.readLine();
            String art = input.split("-", 2)[0];

            serverSocket.close();

            switch (art) {
                case "msg":
                    String message = input.split("-", 2)[1];
                    //System.out.println(message);
                    return message;

                case "login":
                    break;

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "abc";
    }


}
