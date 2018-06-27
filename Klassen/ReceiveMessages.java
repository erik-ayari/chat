package methods;

import client.ChatroomGUI;
import client.Chat;

import java.awt.Font;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.AttributedString;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class ReceiveMessages implements Runnable{
	
	JTabbedPane tabbedPane;
	ArrayList<JPanel> panels;
	int port;
	String username;
	ServerSocket serverSocket;
	ObjectInputStream ois;
	
	public ReceiveMessages(JTabbedPane tabbedPane, ArrayList<JPanel> panels, int port, String username) {
		this.tabbedPane = tabbedPane;
		this.panels = panels;
		this.port = port;
		this.username = username;
		try {
			serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		ChatroomGUI cgi = new ChatroomGUI(panels);
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
				String[] in = (String[]) inStream.readObject();
				
				serverSocket.close();
				
				if(in[0]=="msgdistribute") {
					String message = in[1];
					String user = in[2];
					String chatroom = in[3];
					int index = cgi.findTabByName(chatroom, tabbedPane);
					JTextArea chatHistory = cgi.getChatHistory(index);
					if(user!=username) {
						chatHistory.append(user + ": " + message + "\n");
					} else {
						chatHistory.append("<b>" + user + ": " + message + "</b>");
					}
				} else if(in[0]=="chatroomadded") {
					String chatroom = in[1];
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
