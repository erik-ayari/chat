package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Login {

	private JFrame frame;
	private JTextField txtServer;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JTextField txtChatroom;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 300, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblLogIn = new JLabel("Log In");
		lblLogIn.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblLogIn.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblLogIn);
		
		txtServer = new JTextField();
		txtServer.setFont(new Font("Tahoma", Font.PLAIN, 40));
		txtServer.setText("Server");
		frame.getContentPane().add(txtServer);
		txtServer.setColumns(10);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 40));
		txtUsername.setText("Username");
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 40));
		txtPassword.setText("Password");
		frame.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		txtChatroom = new JTextField();
		txtChatroom.setFont(new Font("Tahoma", Font.PLAIN, 40));
		txtChatroom.setText("Chatroom");
		frame.getContentPane().add(txtChatroom);
		txtChatroom.setColumns(10);
		
		JButton btnLogIn = new JButton("Enter");
		btnLogIn.setFont(new Font("Tahoma", Font.PLAIN, 40));
		frame.getContentPane().add(btnLogIn);
	}

}
