
/** ***************************************Server*****************************************
 * Filename: Server.java
 * Name: Ikram Hamizi | Jocelyne Abi Haidar
 * Class: Networks (Fall2018)
 *
 * ***************************************************************************************
 * Description: 
 * ***************************************************************************************
 *
 * in: String (STDIN) - English Adjective from user
 * out: Synonym of the adjective
 *
 * /**************************************************************************************** */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

	int port, client_counter;
	private ExecutorService processes; //Executor: helps setting the number of processes to be run.
	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	private ArrayList <Socket> connected_clients;
	
	//Getter: connected_clients
	public ArrayList<Socket> getConnectedClients() 
	{
		return connected_clients;
	}
	
	public Server(int port)
	{
		this.port = port;
		processes = Executors.newFixedThreadPool(100); //Allows 100 processes (100 chatters).
		connected_clients = new ArrayList <Socket>(10); //Dynamic
	}
	
	public void serverBegin() throws IOException 
	{
		serverSocket = new ServerSocket(port);
		System.out.println("*** Server started ***");
		while(true)
		{
			clientSocket = serverSocket.accept();
			client_counter++;
			connected_clients.add(clientSocket);
			
			ServerThread st = new ServerThread(this, clientSocket, client_counter);
			processes.execute(st);
		}
	}
    public static void main(String[] args) throws IOException {
    	Server server_object = new Server(8001);
    	server_object.serverBegin();
    }
}
