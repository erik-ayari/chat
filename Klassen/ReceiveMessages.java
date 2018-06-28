package methods;


import Client.ChatroomGUI;

import javax.swing.*;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ReceiveMessages extends Thread {

    JTabbedPane tabbedPane;
    ArrayList<JPanel> panels;
    int port;
    String username;
    UserSession userSession;
    String[] chatrooms;
    ServerSocket serverSocket;
    ObjectInputStream ois;

    public ReceiveMessages(JTabbedPane tabbedPane, ArrayList<JPanel> panels, int port, String username, UserSession userSession, String[] chatrooms) {
        this.tabbedPane = tabbedPane;
        this.panels = panels;
        this.port = port;
        this.username = username;
        this.userSession = userSession;
        this.chatrooms = chatrooms;
//        try {
//            //////////// KONSTANTER PORT FÃœR TESTEN
//            //serverSocket = new ServerSocket(34568);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void run() {
        ChatroomGUI cgi = new ChatroomGUI(panels, tabbedPane, userSession, chatrooms);
        while (true) {
            try {

                serverSocket = new ServerSocket(55555);

                Socket socket = serverSocket.accept();
                ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
                String[] in = (String[]) inStream.readObject();

                serverSocket.close();

                if (in[0].equals("msgDistribute")) {
                    String message = in[1];
                    String user = in[2];
                    String chatroom = in[3];

                    System.out.println(message+user+chatroom);

                    int index = cgi.findTabByName(chatroom);
                    JTextArea chatHistory = cgi.getChatHistory(index);
                    if (user != username) {
                        chatHistory.append(user + ": " + message + "\n");
                    } else {
                        chatHistory.append("<b>" + user + ": " + message + "</b>");
                    }
                    if(index != tabbedPane.getSelectedIndex()) {
                    	tabbedPane.setBackgroundAt(index, Color.YELLOW);
                    }
                } else if (in[0] == "chatroomadded") {
                	String chatroom = in[1];
                    cgi.addChatroom(chatroom);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
