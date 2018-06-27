package client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;

import methods.UserSession;

public class ChatroomGUI {
	
	ArrayList<JPanel> panels;
	Font font = new Font("Tahoma", Font.PLAIN, 40);
	
	public ChatroomGUI(ArrayList<JPanel> panels) {
		this.panels = panels;
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
	
	public int findTabByName(String title, JTabbedPane tabbedPane)  
    {
      int tabCount = tabbedPane.getTabCount();
      for (int i=0; i < tabCount; i++) 
      {
        String tabTitle = tabbedPane.getTitleAt(i);
        if (tabTitle.equals(title)) return i;
      }
      return -1;
    }
	
	public void addChatrooms(String[] chatrooms, JTabbedPane tabbedPane, UserSession userSession) {
		
		
		for(int i = 0; i < chatrooms.length; i++) {
			JPanel panel = new JPanel(new BorderLayout());
			JTextArea chatHistory = new JTextArea();
			JTextField messageField = new JTextField();
			messageField.setFont(font);
			messageField.addActionListener(new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent e) {
					userSession.sendMessageToServer(messageField.getText());
					messageField.setText("");
				}
				
			});
			chatHistory.setFont(font);
			chatHistory.setEditable(false);
			panel.add(chatHistory, BorderLayout.CENTER);
			panel.add(messageField, BorderLayout.PAGE_END);
			panels.add(panel);
			tabbedPane.addTab(chatrooms[i], panels.get(i));
		}
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
				
			}
			
		});
		addPanel.add(chatroomName, BorderLayout.PAGE_START);
		addPanel.add(add, BorderLayout.PAGE_END);
		tabbedPane.addTab("+", addPanel);
	}
}
