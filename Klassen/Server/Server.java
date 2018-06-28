package Server;

import methods.ReceiveMessages;
import methods.ServerSession;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Server {

    static int port;
    ServerSession serverSession;
    JTabbedPane tabbedPane;
    ArrayList<JPanel> panels;
    int i;
    String[] chatrooms;
    ChatroomGUI cgi;
    private JFrame frame;

    public Server() throws IOException, JSONException {
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame frame = new JFrame("Welcome!");
                    port = Integer.parseInt(JOptionPane.showInputDialog(frame, "Please enter a port."));
                    Server window = new Server();
                    window.frame.setVisible(true);
                    //serverSession = new ServerSession(port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() throws IOException, JSONException {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        frame = new JFrame("Server Administrator");
        frame.setBounds(100, 100, screen.width / 2, screen.height / 2);
        frame.setLocation(screen.width / 2 - frame.getSize().width / 2, screen.height / 2 - frame.getSize().height / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panels = new ArrayList<JPanel>();

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 40));

        serverSession = new ServerSession(port);

        chatrooms = serverSession.getCurrentChatrooms();
        cgi = new ChatroomGUI(panels);
        cgi.addChatrooms(chatrooms, tabbedPane);

        frame.setContentPane(tabbedPane);

        ReceiveMessages rmsg = new ReceiveMessages(tabbedPane, panels, serverSession, port);
        rmsg.start();
    }

}
