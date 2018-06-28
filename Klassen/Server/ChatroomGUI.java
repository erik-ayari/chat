package Server;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import javax.swing.*;

public class ChatroomGUI {

    ArrayList<JPanel> panels;
    JTabbedPane tabbedPane;
    ArrayList<String> chatrooms;
    Font font = new Font("Tahoma", Font.PLAIN, 40);

    public ChatroomGUI(ArrayList<JPanel> panels, JTabbedPane tabbedPane, ArrayList<String> chatrooms) {
        this.panels = panels;
        this.tabbedPane = tabbedPane;
        this.chatrooms = chatrooms;
    }

    public JTextArea getChatHistory(int index) {
        BorderLayout layout = (BorderLayout) panels.get(index).getLayout();
        JTextArea chatHistory = (JTextArea) layout.getLayoutComponent(BorderLayout.CENTER);
        return chatHistory;
    }

    public JTextField getMessageField(int index) {
        BorderLayout layout = (BorderLayout) panels.get(index).getLayout();
        JTextField messageField = (JTextField) layout.getLayoutComponent(BorderLayout.PAGE_END);
        return messageField;
    }

    public int findTabByName(String title)
    {
        int tabCount = tabbedPane.getTabCount();
        for (int i=0; i < tabCount; i++)
        {
            String tabTitle = tabbedPane.getTitleAt(i);
            if (tabTitle.equals(title)) return i;
        }
        return -1;
    }

    public void addCurrentChatrooms(ArrayList<String> chats) {

    	int i = 0;
        for(String chat: chats) {
            JPanel panel = new JPanel(new BorderLayout());
            JTextArea chatHistory = new JTextArea();
            JTextField messageField = new JTextField();
            JButton removeUser = new JButton();
            removeUser.setFont(font);
            removeUser.setText("Remove User");
            removeUser.addActionListener(new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // Remove User

                }

            });
            try {
                String localHost = InetAddress.getLocalHost().toString();
                String lh[] = localHost.split("/");
                chatHistory.append("Server started on " + lh[1] + "\n");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            chatHistory.setFont(font);
            chatHistory.setEditable(false);
            panel.add(chatHistory, BorderLayout.CENTER);
            panel.add(removeUser, BorderLayout.PAGE_END);
            panels.add(panel);
            tabbedPane.addTab(chat, panels.get(i));
        }
        i++;
    }
    
    public void addChatroom(String chatroom) {
    	tabbedPane.removeAll();
    	chatrooms.add(chatroom);
    	addCurrentChatrooms(chatrooms);
    }
    
    public void printInEveryChatroom(String message) {
    	for(JPanel panel : panels) {
    		BorderLayout layout = (BorderLayout) panel.getLayout();
    		JTextArea chatHistory = (JTextArea) layout.getLayoutComponent(BorderLayout.CENTER);
    		chatHistory.append(message + "\n");
    	}
    }
}
