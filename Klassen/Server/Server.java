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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame("Welcome!");
					port = Integer.parseInt(JOptionPane.showInputDialog(frame, "Please enter a port."));
					Server window = new Server();
					window.frame.setVisible(true);
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
		JButton removeUser = new JButton();
		removeUser.setFont(new Font("Tahoma", Font.PLAIN, 40));
		removeUser.setText("Remove User");
		removeUser.addActionListener(removeAction(i));
		messageField.setFont(new Font("Tahoma", Font.PLAIN, 40));
		messageField.addActionListener(sendAction(i));
		try {
			String localHost = InetAddress.getLocalHost().toString();
			String lh[] = localHost.split("/");
			chatHistory.append("Server started on " + lh[1]);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chatHistory.setFont(new Font("Tahoma", Font.PLAIN, 40));
		chatHistory.setEditable(false);
		panel.add(chatHistory, BorderLayout.CENTER);
		//panel.add(messageField, BorderLayout.PAGE_END);
		panel.add(removeUser, BorderLayout.PAGE_END);
		panels.add(panel);
		tabbedPane.addTab(name, panels.get(i));
		++i;
	}
	
	public Action sendAction(int index) {
		Action action = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
			
		};
		return action;
	}
	
	public Action removeAction(int index) {
		Action action = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
			
		};
		return action;
	}
}
