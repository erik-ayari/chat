package methods;


import methods.exceptions.InvalidLoginArguments;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

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


        String pfad = "C:/Users/Niels/eclipse-workspace/Schule Chat/src/methods/users.json";
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
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("msg-" + message);

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
