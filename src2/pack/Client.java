package pack;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @author Ikram Hamizi | Jocelyne Abi Haidar
 */
public class Client {
	public static void main(String[] args) {

		try {
			//1. Create Socket with the same port number as the Server
			Socket socket = new Socket("127.0.0.1", 8001);

			//2. Receive and send messages is done through BufferedReader and PrintStream
			System.out.println("*** Chatting ***\n\n");

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream ps = new PrintStream(socket.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			
			//3. Set User-name
			String name;
			Scanner scan = new Scanner(System.in);
			System.out.print("Enter your name: ");
			name = scan.nextLine();
			name=name.trim();
			if(name == null || name.length() == 0 || name.matches("[\b\t\n\r ]+"))
			{
				name = "anonymous";
			}

			//4. Messages are stored in buffer "msg"
			String msg;
			
			while (true)
			{
				System.out.print(name + ": ");
				msg = stdin.readLine();
		
				//5. Send the message to "Server"
				ps.println(msg);
				
				//6. Read from server (and print).
				while ((msg = br.readLine()) != null)
				{
					System.out.println("Server: " + msg);
				}
			}
//			socket.close();
//			br.close();
//			stdin.close();
//			scan.close();
//			ps.close();
		} catch (IOException e) {
			Logger.getLogger("I/O Exception in Client Socket: "+ e);
		}
	}
}
