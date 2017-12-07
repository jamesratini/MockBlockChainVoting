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

	// DO NOT RUN AS A THREAD!!!
	// If the client tries to send a transaction prior to all nodes on the network knowing of our existence, then the transaction will fail
	private void initialConnect() throws IOException
	{
		// Reach out to the anchor node. anchor node will respond with 1 or 2 IP/port pairs.
		// Add those pairs to a list to open as sockets later
		Socket anchorSocket = new Socket(Globals.anchorIP, Globals.anchorPort);
		try
		{
			
			PrintWriter output = new PrintWriter(anchorSocket.getOutputStream(), true);
			output.println("Initial Connect");
			output.println(8082);

			// Read welcome message from Anchor
			BufferedReader in = new BufferedReader(new InputStreamReader(anchorSocket.getInputStream()));
			String clientMessage = in.readLine();
			System.out.println(clientMessage); // Should read "Hello, From Anchor Node"

			// Read in the nodes AnchorNode decided was best for this client to call it's neighbors
			// DESIGN DECISION: what format are these nodes coming in??
			// If as a Seriailized object, then deserialize and pull the objects/memebers
			// If as a formatted string "127.0.0.1:5000" then just split at ":"

			// Assume formatted string

			// readLine will block until data is present in the buffer
			String[] neighborNode = in.readLine().split(":");
			neighborServerSocketList.add(new Socket(neighborNode[0], Integer.parseInt(neighborNode[1])));

			neighborNode = in.readLine().split(":");
			neighborServerSocketList.add(new Socket(neighborNode[0], Integer.parseInt(neighborNode[1])));

			// Close connection with AnchorNode
			anchorSocket.close();

			// Introduce yourself to your new neighbors!
		for(Socket neighbhor : neighborServerSocketList)
		{
			// Send an intro message
			// TODO: This message will contain all of this peers info that other peers need to know, public key and remaining votes
			// TODO: Servers will handle this differently than receiving a transactionRequest, I think
		}

			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			anchorSocket.close();
		}
		
		

	}

}