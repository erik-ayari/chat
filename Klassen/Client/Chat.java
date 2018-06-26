package client;

import methods.UserSession;
import methods.exceptions.InvalidLoginArguments;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Chat {
	
	static //LOGIN-Variablen
	UserSession userSession;
	private static String server, username, password, chatroom;
	private JFrame frame;
	JTabbedPane tabbedPane;
	static ArrayList<JPanel> panels;
	int i;

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
							try {
								userSession = new UserSession(username,password);
							} catch (InvalidLoginArguments e) {
								// TODO Auto-generated catch block
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
		messageField.addActionListener(sendAction(i));
		panel.add(chatHistory, BorderLayout.CENTER);
		panel.add(messageField, BorderLayout.PAGE_END);
		panels.add(panel);
		tabbedPane.addTab(name, panels.get(i));
		++i;
	}
	
	public Action sendAction(int index) {
		Action action = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				BorderLayout layout = (BorderLayout) panels.get(index).getLayout();
				JTextField messageField = (JTextField) layout.getLayoutComponent(BorderLayout.PAGE_END);
				messageField.setText("");
			}
			
		};
		return action;
	}
}
