package methods.exceptions;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConnectionNotEstablished extends Exception {
	
	public ConnectionNotEstablished() {
		super("Could not establish connection");
		JFrame error = new JFrame("Could not establish connection");
        JOptionPane.showMessageDialog(error,
        	    "Failed to connect. Please check the IP.");
	}

}
