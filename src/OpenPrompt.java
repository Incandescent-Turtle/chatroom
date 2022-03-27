import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class OpenPrompt extends JFrame
{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws UnknownHostException 
	{
		// new PopUp();
		String[] options = { "Host", "Client" };
		int choice = JOptionPane.showOptionDialog(null, "Which would you like to start?", "Messenger Menu",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (choice == 0) {
			new Host();
			return;
		} else if (choice == 1) {
			String ip = JOptionPane.showInputDialog("Enter the host IP:", "192.168.1.XX");
			if (ip != null)
				new Client(ip);
		}
	}
}