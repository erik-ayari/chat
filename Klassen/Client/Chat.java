package client;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Chat {
	
	//LOGIN-Variablen
	private static String server;
	private static String username;
	private static String password;
	private static String chatroom;

	private JFrame frame;
	JTabbedPane tabbedPane;
	ArrayList<JPanel> panels;
	int i;

	/**
	 * Launch the application.
	 */
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
							username = loginWindow.txtUsername.getText();
							password = loginWindow.txtPassword.getText();
							chatroom = loginWindow.txtChatroom.getText();
							if(chatroom == "Chatroom") { chatroom = "#main"; }
							performLogin(server, username, password, chatroom);
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

	/**
	 * Create the application.
	 */
	public Chat() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame = new JFrame();
		frame.setBounds(100, 100, screen.width/2, screen.height/2);
		frame.setLocation(screen.width/2-frame.getSize().width/2, screen.height/2-frame.getSize().height/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panels = new ArrayList<JPanel>();
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 40));
		
		addChatroom("#main");
		addChatroom("#gtav");
		addChatroom("#detroitbecomehuman");
		addChatroom("#fortnite");
		
		frame.setContentPane(tabbedPane);
	}
	
	public void addChatroom(String name) {
		JPanel panel = new JPanel(new BorderLayout());
		JTextArea chatHistory = new JTextArea();
		JTextField messageField = new JTextField();
		messageField.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panel.add(chatHistory, BorderLayout.CENTER);
		panel.add(messageField, BorderLayout.PAGE_END);
		panels.add(panel);
		tabbedPane.addTab(name, panels.get(i));
		++i;
	}

	/*Login-Methode, mit Anfrage ob der Chatroom schon existiert oder ob er neu erstellt werden soll
	 * Außerdem sollte eine ArrayList mit allen Chatrooms ankommen.
	 */
	public static void performLogin(String server, String username, String password, String chatroom) {
		
	}
}
