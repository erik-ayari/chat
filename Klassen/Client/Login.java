package client;

import javax.swing.*;
import java.awt.*;

public class Login {

	JFrame frame;
	JTextField txtServer, txtUsername, txtPassword, txtChatroom;
	JButton btnLogIn;

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

	public Login() {
		initialize();
	}

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
		
		btnLogIn = new JButton("Enter");
		btnLogIn.setFont(new Font("Tahoma", Font.PLAIN, 40));
		frame.getContentPane().add(btnLogIn);
		
	}

}
