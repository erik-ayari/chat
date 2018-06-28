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
            throw new InvalidLoginArguments();
        }

    }


    private boolean loginToServer(String username, String password) throws IOException {


        sendInfoToServer(new String[]{"login", username, password});

        ServerSocket serverSocket = new ServerSocket(34568);
        Socket socket = serverSocket.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        return Boolean.parseBoolean(in.readLine());


    }

    public String[] getCurrentChatRooms() throws IOException, ClassNotFoundException {
        sendInfoToServer(new String[]{"getChatrooms"});

        ServerSocket serverSocket = new ServerSocket(45450);
        Socket socket = serverSocket.accept();

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        String[] input = (String[]) in.readObject();

        serverSocket.close();


        return input;
    }


    public void sendChatMessageToServer(String message, String channel) {
        sendInfoToServer(new String[]{"msg", message, this.username, channel});
    }

    public void addNewChatroom(String name) {
        sendInfoToServer(new String[]{"addChatroom", name});
    }


    public void sendInfoToServer(String[] message) {
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
