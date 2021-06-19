import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Common
{
	
	private static final long serialVersionUID = 1L;	
	public Client(String host)
	{
		super("Facebook Messenger 2.0");
		serverIP = host;
		init();
	}
	
	@Override
	protected void start() 
	{

		try{

			showMessage("Attempting connection... \n");
			connection = new Socket(InetAddress.getByName("192.168.1.81"), 6789);
			showMessage("Connection Established! Connected to: " + connection.getInetAddress().getHostName());
			setupStreams();
			whileChatting();
		}catch(EOFException eofException){
			showMessage("\n Client terminated the connection");
		}catch(IOException ioException){
			ioException.printStackTrace();
		}finally{
			closeConnection();
		}
	
	}
}