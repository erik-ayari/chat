package client;

import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;

public class ChatroomGUI {
	
	ArrayList<JPanel> panels;
	
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
}
