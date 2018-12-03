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
			
			//PART1: Connection with MainServer
			//1. Create Socket with the same port number as the MainServer
			Socket socket_with_main_server = new Socket("127.0.0.1", 8001);
			
			//   Create serverSocket to accept connections
			Server client_server_object = new Server(8001); 
			client_server_object.serverBegin(); //Accept connections (threads)
			
			//2. Receive and send messages is done through BufferedReader and PrintStream
			System.out.println("*** Chatting ***\n\n");

			BufferedReader br = new BufferedReader(new InputStreamReader(socket_with_main_server.getInputStream()));
			PrintStream ps = new PrintStream(socket_with_main_server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			
			//3. Set User-name
			String name;
			Scanner scan = new Scanner(System.in);
			System.out.print("Enter your name: ");
			name = scan.nextLine();
			name=name.trim();
			
			if(name == null || name.length() == 0 || name.matches("[\b\t\n\r ]+"))
			{
				name = "Me";
			}
			
			//5. Print the list of connected users.
			String connected_clients_list = "";
			
			while((connected_clients_list = br.readLine())!= null)
			{
				System.out.println(connected_clients_list);
			}
						
			//PART II: choosing a peer to chat with
			String IP_client2 = ""; //Choose by button or user input
			//example: ip 1
			IP_client2 = "";
			Socket socket_with_client2 = new Socket(IP_client2, 8001);
			
			BufferedReader client_br = new BufferedReader(new InputStreamReader(socket_with_client2.getInputStream()));
			PrintStream client_ps = new PrintStream(socket_with_client2.getOutputStream());
			BufferedReader client_stdin = new BufferedReader(new InputStreamReader(System.in));
			
			//6. Messages are stored in buffer "msg"
			String msg;
	    	
			//Part III: messaging
			while (true)
			{
				System.out.print(name + ": ");
				msg = client_stdin.readLine();
		
				//5. Send the message to "Server"
				client_ps.println(msg);
				
				//6. Read from server (and print).
				msg = client_br.readLine();
				System.out.println("Server: " + msg);
			}
//			socket.close();
//			br.close();
//			stdin.close();
//			scan.close();
//			ps.close();
		} catch (IOException e) {
			Logger.getLogger("I/O Exception in Client Socket: " + e);
		}
	}
}
