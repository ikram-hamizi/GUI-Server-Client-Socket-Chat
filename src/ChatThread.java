import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class ChatThread implements Runnable {

	MainServer mainServer;
	Socket client;

	private Scanner scan = new Scanner (System.in);
	private int id;
	private String msg;

	private BufferedReader br; //read in
	PrintStream ps; // write out

	//private ArrayList <Socket> connected_clients_list;

	public ChatThread(MainServer mainServer, Socket client, int count) throws IOException
	{
		this.mainServer = mainServer;
		this.client = client;
		this.id = count;

		System.out.println("Connected with client: " + client + " | ConnectionID = " + id);

		br = new BufferedReader(new InputStreamReader (client.getInputStream()));
		ps = new PrintStream(client.getOutputStream());
	}

	/*public void sendInfoToConnectedClients() throws IOException
	{
		String String_connected_clients_list = "";
		for (int i = 0; i < connected_clients_list.size(); i++) 
		{
			String_connected_clients_list += "(" + i+1 + "): " + connected_clients_list.get(i) + "\n";
		}
		System.out.println(String_connected_clients_list);
		ps.println(String_connected_clients_list);		
	}*/

	@Override
	public void run() { //CHAT
		try {
			//sendInfoToConnectedClients();
			while (true)
			{
				//READ
				msg = br.readLine();
				System.out.println("Client (" + id + "): " + msg);
				
				//WRITE
				System.out.println("Server: ");
				msg = scan.nextLine();
				ps.println(msg);
			}
			//			br.close();
			//			client.close();
			//			ps.close();
		} catch (IOException e) {
			Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, e);
		}
	}

}
