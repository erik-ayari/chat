package client;

import methods.UserSession;
import methods.ReceiveMessages;
import methods.exceptions.InvalidLoginArguments;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class Chat {
	
	static //LOGIN-Variablen
	UserSession userSession;
	static String server, ip, username, password, chatroom;
	static int port;
	private JFrame frame;
	static JTabbedPane tabbedPane;
	Font font;
	static ArrayList<JPanel> panels;
	int i;
	static String[] newMessage;
	static String[] chatrooms;
	ChatroomGUI cgi;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//LOGIN
					Login loginWindow = new Login();
					loginWindow.frame.setVisible(true);
					loginWindow.btnLogIn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							server = loginWindow.txtServer.getText();
							try {
								String[] s = server.split(":");
								ip = s[0];
								port = Integer.parseInt(s[1]);
							} catch(Exception e) {
								e.printStackTrace();
							}
							username = loginWindow.txtUsername.getText();
							password = loginWindow.txtPassword.getText();
							chatroom = loginWindow.txtChatroom.getText();
							if(chatroom == "Chatroom") { chatroom = "#main"; }
							try {
								userSession = new UserSession(username,password,ip,port);
							} catch (InvalidLoginArguments e) {
								e.printStackTrace();
							}
							loginWindow.frame.setVisible(false);
							//CHAT
							Chat chatWindow = new Chat();
							chatWindow.frame.setVisible(true);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Chat() {
		initialize();
	}

	private void initialize() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame = new JFrame();
		frame.setBounds(100, 100, screen.width/2, screen.height/2);
		frame.setLocation(screen.width/2-frame.getSize().width/2, screen.height/2-frame.getSize().height/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		font = new Font("Tahoma", Font.PLAIN, 40);
		
		panels = new ArrayList<JPanel>();
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(font);
		
		chatrooms[] = userSession.getCurrentChatRooms();
		
		cgi = new ChatroomGUI(panels);
		cgi.addChatrooms = new ChatroomGUI(chatrooms[], tabbedPane, userSession);
		
		frame.setContentPane(tabbedPane);
		
		ReceiveMessages rmsg = new ReceiveMessages(tabbedPane, panels, port, username);
		rmsg.run();
	}
}
