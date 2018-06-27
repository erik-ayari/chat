package server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import javax.swing.*;

public class Server {
	
	private JFrame frame;
	JTabbedPane tabbedPane;
	
	ArrayList<JPanel> panels;
	int i;
	
	static int port;
	ServerSession serverSession;
	String[] chatrooms;
	ChatroomGUI cgi;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame("Welcome!");
					port = Integer.parseInt(JOptionPane.showInputDialog(frame, "Please enter a port."));
					Server window = new Server();
					window.frame.setVisible(true);
					serverSession = new ServerSession(port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Server() {
		initialize();
	}
	
	private void initialize() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame = new JFrame("Server Administrator");
		frame.setBounds(100, 100, screen.width/2, screen.height/2);
		frame.setLocation(screen.width/2-frame.getSize().width/2, screen.height/2-frame.getSize().height/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panels = new ArrayList<JPanel>();
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 40));
		
		chatrooms[] = serverSession.getChatrooms();
		cgi = new ChatroomGUI(panels);
		cgi.addChatrooms(chatrooms[], tabbedPane);
		
		frame.setContentPane(tabbedPane);
		
		while(true) {
			String[] input = serverSession.waitForMessages();
			if(input[0] == "msgdistribute") {
				
			}
		}
	}

}
