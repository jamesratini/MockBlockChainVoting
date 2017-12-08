import java.util.*;
import java.io.*;
import java.net.Socket;

public class Client
{

	private static ArrayList<String> neighborPeerList; // Contains peerIds
	//private static ArrayList<String> serverList; // Contains IPAddresses
	private static ArrayList<Socket> neighborServerSocketList; // Contains the 


	public Client() throws IOException
	{
		// on init, attempt to connect to the "anchor" peer
		// If connection is successful, keep running
		// Else, exit application with message "unable to reach the network"

		// DESIGN DECISION - when a node connects to the network, does it indefinitely connect to its neighbor peers? Or only when communication happens.
			// Always connect - current system works - for full decentrilized, this may be the best
			// temp connect - sockets cant be initialized until communication is meant to happen
		// NOTE - on init the client must first connect to an "anchor" node, one that is always on, the anchor will response with the IP and port of 1 or 2 other peers. These will be our neighbors.
		neighborPeerList = new ArrayList<String>(Arrays.asList("John", "James"));
		neighborServerSocketList = new ArrayList<Socket>();

		initialConnect();


	}

	public void sendTransactionRequest() throws IOException
	{
		// iterate through all serverSockets and attempt to connect and send the message
		// TODO: Run in a thread
		for(int i = 0; i < neighborServerSocketList.size(); i++)
		{
			try
			{
				PrintWriter output = new PrintWriter(neighborServerSocketList.get(i).getOutputStream(), true);
				output.printf("Hello, %s", neighborPeerList.get(i));
			}
			finally
			{
				neighborServerSocketList.get(i).close();
			}
			
		} 
		
		
	}

	private void initialConnect() throws IOException
	{
		// Reach out to the anchor node. anchor node will respond with 1 or 2 IP/port pairs.
		Socket anchorSocket = new Socket(Globals.anchorIP, Globals.anchorPort);
		PrintWriter output = new PrintWriter(anchorSocket.getOutputStream(), true);
		output.println("Initial Connect");
		output.println(8082);

		// Read welcome message from Anchor

		// Read neighbor nodes
		neighborServerSocketList.add(new Socket(Globals.anchorIP, Globals.anchorPort));
		neighborServerSocketList.add(new Socket(Globals.anchorIP, Globals.anchorPort));

	}

}