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
    
    public void waitForMessage(ArrayList<JPanel> panels, JTabbedPane tabbedPane) {
    	while(true) {
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
                	int index = findTabByName(channel, tabbedPane);
                	BorderLayout layout = (BorderLayout) panels.get(index).getLayout();
    				JTextArea chatHistory = (JTextArea) layout.getLayoutComponent(BorderLayout.CENTER);
    				chatHistory.append("\n" + message);
                    //return new String[]{message, channel};

                case "login":
                    break;

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    	}
    }
    
    public int findTabByName(String title, JTabbedPane tab)  
    {
      int tabCount = tab.getTabCount();
      for (int i=0; i < tabCount; i++) 
      {
        String tabTitle = tab.getTitleAt(i);
        if (tabTitle.equals(title)) return i;
      }
      return -1;
    }

}
