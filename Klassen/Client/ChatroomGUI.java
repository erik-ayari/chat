package Client;

import methods.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class ChatroomGUI {

    ArrayList<JPanel> panels;
    JTabbedPane tabbedPane;
    UserSession userSession;
    int plus;
    Font font = new Font("Tahoma", Font.PLAIN, 40);

    public ChatroomGUI(ArrayList<JPanel> panels, JTabbedPane tabbedPane, UserSession userSession) {
        this.panels = panels;
        this.tabbedPane = tabbedPane;
        this.userSession = userSession;
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

    public String findTitlebyIndex(int index) {

        return tabbedPane.getTitleAt(index);

    }

    public int findTabByName(String title) {
        int tabCount = tabbedPane.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            String tabTitle = tabbedPane.getTitleAt(i);
            if (tabTitle.equals(title)) return i;
        }
        return -1;
    }

    public void addCurrentChatrooms(String[] chatrooms) {


        for (int i = 0; i < chatrooms.length; i++) {
            JPanel panel = new JPanel(new BorderLayout());
            JTextArea chatHistory = new JTextArea();
            JTextField messageField = new JTextField();
            messageField.setFont(font);

            final int b = i;

            messageField.addActionListener(new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    userSession.sendChatMessageToServer(messageField.getText(), findTitlebyIndex(b));
                    messageField.setText("");
                }

            });
            chatHistory.setFont(font);
            chatHistory.setEditable(false);
            panel.add(chatHistory, BorderLayout.CENTER);
            panel.add(messageField, BorderLayout.PAGE_END);
            panels.add(panel);
            tabbedPane.addTab(chatrooms[i], panels.get(i));
            plus = i;
        }
        addPlus(plus);
    }
    
    public void addPlus(int plus) {
    	JPanel addPanel = new JPanel(new BorderLayout());
        JTextField chatroomName = new JTextField();
        chatroomName.setFont(font);
        chatroomName.setText("Chatroom Name");
        JButton add = new JButton();
        add.setFont(font);
        add.setText("OK");
        add.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                userSession.addNewChatroom(chatroomName.getText());
            }

        });
        addPanel.add(chatroomName, BorderLayout.PAGE_START);
        addPanel.add(add, BorderLayout.PAGE_END);
        tabbedPane.addTab("+", addPanel);
    }
    
    public void addChatroom() {
    	tabbedPane.removeAll();
    	String[] chatrooms = null;
		try {
			chatrooms = userSession.getCurrentChatRooms();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
    	addCurrentChatrooms(chatrooms);
    }
}
