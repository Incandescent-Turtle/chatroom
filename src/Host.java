import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class Host extends Common {
	

	private static final long serialVersionUID = 1L;
	private ServerSocket server;
	
	public Host()
	{
		super("Facebook Messenger 2.0 - Host");
		init();
		
	}
	
	@Override
	protected void thingy() {
		userName = "SERVER";
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		serverIP = inetAddress.getHostAddress();
		chatArea.append("IP Address:- " + serverIP + "\n");
	}
	
	@Override
	public void start()
	{
		try {
			server = new ServerSocket(6789, 100);
			while(true) 
			{
				try {
					//accepts the client request to join
					showMessage(" Waiting for someone to connect... \n");
					connection = server.accept();
					showMessage(" Now connected to " + connection.getInetAddress().getHostName());
					
					setupStreams();
					whileChatting();
				} catch(EOFException eofException) {
					showMessage(userName + " ended the connection! ");
				} finally {
					closeConnection();
				}
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	@Override
	protected void closeConnection() 
	{
		super.closeConnection();
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}