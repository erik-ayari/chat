package Client;

import methods.ReceiveMessages;
import methods.UserSession;
import methods.exceptions.InvalidLoginArguments;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Chat {

    static UserSession userSession;
    static String server, ip, username, password;
    static int port;
    static JTabbedPane tabbedPane;
    static ArrayList<JPanel> panels;
    static String[] newMessage;
    static String[] chatrooms;
    Font font;
    int i;
    ChatroomGUI cgi;
    private JFrame frame;

    public Chat() throws IOException, ClassNotFoundException {
        initialize();
    }

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
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            username = loginWindow.txtUsername.getText();
                            password = loginWindow.txtPassword.getText();
                            try {
                                userSession = new UserSession(username, password, ip, port);
                            } catch (InvalidLoginArguments e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            loginWindow.frame.setVisible(false);

                            //CHAT
                            Chat chatWindow = null;
                            try {
                                chatWindow = new Chat();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            chatWindow.frame.setVisible(true);




                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() throws IOException, ClassNotFoundException {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        frame = new JFrame();
        frame.setBounds(100, 100, screen.width / 2, screen.height / 2);
        frame.setLocation(screen.width / 2 - frame.getSize().width / 2, screen.height / 2 - frame.getSize().height / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        font = new Font("Tahoma", Font.PLAIN, 40);

        panels = new ArrayList<JPanel>();

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(font);
        Color normal = tabbedPane.getForeground();
        tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(normal!=tabbedPane.getForegroundAt(tabbedPane.getSelectedIndex())) {
					tabbedPane.setForegroundAt(tabbedPane.getSelectedIndex(), normal);
				}
			}
        	
        });

        chatrooms = userSession.getCurrentChatRooms();


        cgi = new ChatroomGUI(panels, tabbedPane, userSession);
        cgi.addCurrentChatrooms(chatrooms);

        frame.setContentPane(tabbedPane);


        ReceiveMessages rmsg = new ReceiveMessages(tabbedPane, panels, port, username, userSession);
        rmsg.start();
        System.out.println("Bis hier");
    }
}
