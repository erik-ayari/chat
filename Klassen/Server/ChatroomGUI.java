package server;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import javax.swing.*;

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
	
	public void addChatrooms(String[] chatrooms, JTabbedPane tabbedPane) {
		
		
		for(int i = 0; i < chatrooms.length; i++) {
			JPanel panel = new JPanel(new BorderLayout());
			JTextArea chatHistory = new JTextArea();
			JTextField messageField = new JTextField();
			JButton removeUser = new JButton();
			removeUser.setFont(new Font("Tahoma", Font.PLAIN, 40));
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
				chatHistory.append("Server started on " + lh[1]);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			chatHistory.setFont(font);
			chatHistory.setEditable(false);
			panel.add(chatHistory, BorderLayout.CENTER);
			panel.add(removeUser, BorderLayout.PAGE_END);
			panels.add(panel);
			tabbedPane.addTab(chatrooms[i], panels.get(i));
		}
	}
}
