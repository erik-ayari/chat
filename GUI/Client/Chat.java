package client;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Dimension;

import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Toolkit;

public class Chat {

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
					Chat window = new Chat();
					window.frame.setVisible(true);
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
		JPanel panel = new JPanel();
		panels.add(panel);
		
		tabbedPane.addTab(name, panels.get(i));
		++i;
	}

}
