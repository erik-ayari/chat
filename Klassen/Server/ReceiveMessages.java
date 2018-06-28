package methods;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import Server.ChatroomGUI;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class ReceiveMessages extends Thread {
	
	JTabbedPane tabbedPane;
	ArrayList<JPanel> panels;
	ServerSession serverSession;
	int port;
	
	public ReceiveMessages(JTabbedPane tabbedPane, ArrayList<JPanel> panels, ServerSession serverSession, int port) {
		this.tabbedPane = tabbedPane;
		this.panels = panels;
		this.serverSession = serverSession;
		this.port = port;
	}
	
	public void run() {
		while(true) {
		try {

            ServerSocket serverSocket = new ServerSocket(port);

            //Eine Verbindung wird eingegangen
            Socket socket = serverSocket.accept();


            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String[] input = (String[]) in.readObject();

            String type = input[0];


            serverSocket.close();

            if (type=="msg") {
            	String message = input[1];
            	String sender = input[2];
                String chatroom = input[3];

                // an alle austeilen
                int port = socket.getPort();

                ArrayList<String> userIPS = new ArrayList<>();

                String pfad = "src/methods/data/users.json";
                File file = new File(pfad);
                String content = new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8");

                JSONObject json = new JSONObject(content);

                for (Iterator it = json.keys(); it.hasNext(); ) {

                    String ip = (String) it.next();
                    userIPS.add(ip);

                }

                //System.out.println(userIPS);

                for (String ip : userIPS) {
                    DistributeMessages temp = new DistributeMessages(new String[]{"msgDistribute", message, (String) json.get(ip), chatroom}, ip, port);
                    temp.start();

                }


                //ALle Benutzer austeilen {msgDistribute, message, user, chatroom}
                ChatroomGUI cgi = new ChatroomGUI(panels);
                int index = cgi.findTabByName(chatroom, tabbedPane);
                JTextArea chatHistory = cgi.getChatHistory(index);
                chatHistory.append(sender + ": " + message + "\n");
            } else if(type=="login") {
                String username = input[1];
                String password = input[2];
                String ip = socket.getInetAddress().toString().replace("/", "");

                System.out.println(ip);

                boolean success = serverSession.login(username, password);

                Socket answer = new Socket(ip, 34568);
                PrintWriter out = new PrintWriter(answer.getOutputStream(), true);
                out.println(success);
                answer.close();

                ChatroomGUI cgi = new ChatroomGUI(panels);
                cgi.printInEveryChatroom(username + " hat den Chat betreten.");

            } else if(type=="getChatrooms") {

                String[] chatrooms = serverSession.getCurrentChatrooms();
                String ip = socket.getInetAddress().toString().replace("/", "");

                Socket answer = new Socket(ip, 45450);
                ObjectOutput objectOut = new ObjectOutputStream(answer.getOutputStream());
                objectOut.writeObject(chatrooms);
                answer.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
	}
	}
	
}
