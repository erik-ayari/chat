package methods;

import methods.exceptions.InvalidLoginArguments;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UserSession {

    private String username, password, serverIP;
    private int serverPort;

    public UserSession(String username, String password) throws InvalidLoginArguments {

        try {
            if (checkIdentity(username, password)) {
                this.username = username;
                this.password = password;
            } else {
                throw new InvalidLoginArguments("Wrong Username/Password");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setServerIPandPort(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }


    private boolean checkIdentity(String username, String password) throws JSONException, IOException {


        String pfad = "C:/Users/Erik/eclipse-workspace/Client/src/methods/users.json";
        File file = new File(pfad);

        String content = new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8");
        JSONObject json = new JSONObject(content);


        for (Iterator it = json.keys(); it.hasNext(); ) {

            String item = (String) it.next();

            if (username.equals(item) && password.equals(json.getString(item))) {
                return true;
            }
        }
        return false;
    }

    private void loginToServer() {


    }

    public void sendMessageToServer(String message) {

        try {
            Socket socket = new Socket(this.serverIP, this.serverPort);

            //PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ObjectOutput out = new ObjectOutputStream(socket.getOutputStream());


            out.writeObject(new String[]{"msg", message, "Fortnite"});


            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
