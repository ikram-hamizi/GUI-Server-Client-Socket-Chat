import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * @author Ikram Hamizi | Jocelyne Abi Haidar
 */
public class Client extends MainServer {

	int port;
	private ExecutorService processes; //Executor: helps setting the number of processes to be run.
	private ServerSocket peerServerSocket;
	private Socket peerOtherClientSocket;
	private Socket peerMeClientSocket;

	public Client(int port)
	{
		super(port);
		this.port = port;
		
		//Allows 3 processes: client (peer), server (listen to connections), connection to MainServer
		processes = Executors.newFixedThreadPool(3); 
	}

	public void peerServerBegin() throws IOException 
	{
		peerServerSocket = new ServerSocket(port);
		System.out.println("*** Server of Client started ***");
		
		peerOtherClientSocket = peerServerSocket.accept();
		client_counter++;

		ChatThread st = new ChatThread(this, peerOtherClientSocket, 1);
		processes.execute(st); //PROCESS (2) SERVER
	}

	public void peerClientBegin() throws IOException 
	{
		//CHOOSE FROM IPS
		String chosen_IP = "127.0.0.1";
		peerMeClientSocket = new Socket(chosen_IP, 8001); //PEER(2)'s IP
		
		//Start Chat
		ChatThread st = new ChatThread(this, peerMeClientSocket, 1);
		processes.execute(st);
	}
	
	public void peerConnectToMainServerBegin() throws IOException
	{
		//PART1: Connection with MainServer
		//1. Create Socket with the same port number as the MainServer
		Socket socket_with_main_server = new Socket("127.0.0.1", 8001); //MAINSERVER's IP

		//2. Set User-name
		String name;
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter your name: ");
		name = scan.nextLine();
		name=name.trim();

		if(name == null || name.length() == 0 || name.matches("[\b\t\n\r ]+"))
		{
			name = "Me";
		}
		
		//LIST IPs
	}

	public static void main(String[] args)
	{
		Client peer_server_object = new Client(8001);
		try {
			System.out.println("connect to main server");
			peer_server_object.peerConnectToMainServerBegin();
			System.out.println("listen to people");
			peer_server_object.peerServerBegin();
			if (1==2)
			{
				peer_server_object.peerClientBegin();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}	
}
