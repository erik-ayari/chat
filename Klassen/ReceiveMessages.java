package methods;

import client.ChatroomGUI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class ReceiveMessages implements Runnable{
	
	JTabbedPane tabbedPane;
	ArrayList<JPanel> panels;
	int port;
	ServerSocket serverSocket;
	ObjectInputStream ois;
	
	public ReceiveMessages(JTabbedPane tabbedPane, ArrayList<JPanel> panels, int port) {
		this.tabbedPane = tabbedPane;
		this.panels = panels;
		this.port = port;
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
				
				String type = in[0];
				String message = in[1];
				String chatroom = in[2];
				
				serverSocket.close();
				
				if(type=="msg") {
					int index = cgi.findTabByName(chatroom, tabbedPane);
					JTextArea chatHistory = cgi.getChatHistory(index);
					chatHistory.append(message + "\n");
				} else if(type=="login") {
					
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
