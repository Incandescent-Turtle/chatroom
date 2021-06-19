import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public abstract class Common extends JFrame
{
	private static final long serialVersionUID = 8275809146434479515L;
	protected String userName = "";
	protected JTextField inputField;
	protected JTextArea chatArea;
	protected ObjectOutputStream output;
	protected ObjectInputStream input;
	protected Socket connection;
	protected String serverIP = "";
	
	public Common(String string) 
	{
		super(string);
	}
	
	protected void thingy() {};
	protected void init()
	{
		addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent event) 
	        {
	        	System.out.println("window close");
	            dispose();
	            closeConnection();
	            System.exit(1);
	        }
		});
		inputField = new JTextField();
		inputField.setEditable(false);
		inputField.addActionListener(
				new ActionListener() {
				public void actionPerformed(ActionEvent event)
				{
					sendMessage(event.getActionCommand());
					inputField.setText("");
				}
			}
		);
		add(inputField, BorderLayout.NORTH);
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		add(new JScrollPane(chatArea));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(100,100));
		setVisible(true);
		thingy();

		start();
	}
	protected abstract void start();
		
	protected void ableToType(final boolean canType)
	{
		SwingUtilities.invokeLater(
			new Runnable() {
				public void run()
				{
					inputField.setEditable(canType);
				}
			}
		);
	}
	
	protected void showMessage(final String message)
	{
		SwingUtilities.invokeLater(
			new Runnable() {
				public void run()
				{
					chatArea.append("\n" + message);
				}
			}
		);
	}
	
	protected void sendMessage(String message)
	{
		try{
			output.writeObject(userName + " - " + message);
			output.flush();
			showMessage(userName + " - " + message);
		}catch(IOException ioException){
			chatArea.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
		}
	}
	
	protected void closeConnection()
	{
		showMessage(" Closing Connections... ");
		ableToType(false);
		try{
			output.close();
			
		} catch(IOException | NullPointerException ex){
			ex.printStackTrace();
		} finally {
			try{
				input.close(); 
				
			}catch(IOException | NullPointerException ex){
				ex.printStackTrace();
			} finally {
				try{
					connection.close();
					
				}catch(IOException | NullPointerException ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
	protected void whileChatting() throws IOException
	{
		String message = " You are now connected! ";
		sendMessage(message);
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
				showMessage(message);
			} catch(ClassNotFoundException classNotFoundException) {
				showMessage("Unknown data received!");
			}
		} while(!message.equals(userName + " - " + "END"));	
	}
	
	protected void setupStreams() throws IOException
	{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage(" Streams are now setup ");
	}
}



