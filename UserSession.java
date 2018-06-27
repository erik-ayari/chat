package methods;


import methods.exceptions.InvalidLoginArguments;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class UserSession {

    private String username, password, serverIP;
    private int serverPort;

    public UserSession(String username, String password, String serverIP, int serverPort) throws InvalidLoginArguments, IOException {

        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.username = username;
        this.password = password;

        if (!loginToServer(username, password)) {
            throw new InvalidLoginArguments("Wrong Username/Password");
        }

    }


    private boolean loginToServer(String username, String password) throws IOException {


        sendInfoToServer(new String[]{"login", username, password});

        ServerSocket serverSocket = new ServerSocket(34567);
        Socket socket = serverSocket.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        return Boolean.parseBoolean(in.readLine());


    }

    public String[] getCurrentChatRooms() {
        
    }


    public void sendChatMessageToServer(String message) {
        sendInfoToServer(new String[]{"msg", message, "Fortnite"});
    }


    private void sendInfoToServer(String[] message) {
        try {
            Socket socket = new Socket(this.serverIP, this.serverPort);

            ObjectOutput out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(message);
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
