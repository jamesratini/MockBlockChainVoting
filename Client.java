import java.util.*;
import java.io.*;
import java.net.Socket;

public class Client
{

	private static ArrayList<String> neighborPeerList; // Contains peerIds
	private static ArrayList<String> serverList; // Contains IPAddress 
	private static ArrayList<String> neighborServerList; // Contains the IP/Port pair of neighbors

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
		neighborServerList = new ArrayList<String>();

		initialConnect();


	}

	public void sendTransactionRequest(String receiver, String myPublicKey) throws IOException
	{
		// iterate through all serverSockets and attempt to connect and send the message
		// TODO: Run in a thread
		for(int i = 0; i < neighborServerList.size(); i++)
		{
			String[] splitPair = neighborServerList.get(i).split(":");
			Socket neighbor = new Socket(splitPair[0], Integer.parseInt(splitPair[1]));
			try
			{
				PrintWriter output = new PrintWriter(neighbor.getOutputStream(), true);
				output.println("Transaction Incoming");
				output.printf("%s:%s", myPublicKey, receiver);
			}
			finally
			{
				neighbor.close();
			}
			
		} 	
	}

	// DO NOT RUN AS A THREAD!!!
	// If the client tries to send a transaction prior to all nodes on the network knowing of our existence, then the transaction will fail
	// If this function runs as a thread, thats possible if the anchorNode takes a long time to respond
	private void initialConnect() throws IOException
	{
		// Reach out to the anchor node. anchor node will respond with 1 or 2 IP/port pairs.
		// Add those pairs to a list to open as sockets later
		Socket anchorSocket = new Socket(Globals.anchorIP, Globals.anchorPort);
		try
		{
			
			PrintWriter output = new PrintWriter(anchorSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(anchorSocket.getInputStream()));
			output.println("Initial Connect");

			output.println(8082);
			
			String neighborNodes;
			while(true)
			{
				// Read in the nodes AnchorNode decided was best for this client to call it's neighbors
				// DESIGN DECISION: what format are these nodes coming in??
				// If as a Seriailized object, then deserialize and pull the objects members
				// If as a formatted string "127.0.0.1:5000" then just split at ":"

				// Assume formatted string

				// readLine SHOULD block, but... isn't?
				
				neighborNodes = in.readLine();

				if(neighborNodes != null)
				{
					if(neighborNodes.contains("/") == false)
					{
						break;
					}
					else
					{
						String[] neighbors = neighborNodes.split("/");
						//System.out.println(neighbors[1]);
						for(int i = 0; i < neighbors.length; i++)
						{
							neighborServerList.add(neighbors[i]);
						}
						break;
					}

					
				}

			}

			// Introduce yourself to your new neighbors!
			//System.out.println(neighborServerList.get(0));
			if(neighborServerList.size() != 0)
			{
				neighborIntroduction();
			}
		}
		catch(Exception ex)
		{
			anchorSocket.close();
			ex.printStackTrace();
		}

	}

	private void neighborIntroduction() throws IOException
	{
		String[] splitNeighbor;
		for(String neighbor : neighborServerList)
		{
			splitNeighbor = neighbor.split(":");
			// Send an intro message
			// TODO: This message will contain all of this peers info that other peers need to know, public key and remaining votes
			// TODO: Servers will handle this differently than receiving a transactionRequest, I think

			Socket connectingNeighbor = new Socket(splitNeighbor[0], Integer.parseInt(splitNeighbor[1]));
		}
	}
	public void sendValidation(String ip, int port, boolean valid) throws IOException
	{
		// Sends back to the peer if their transaction is valid

		Socket neighborNode = new Socket(ip, port);
		try
		{
			PrintWriter output = new PrintWriter(neighborNode.getOutputStream(), true);
			output.printf("Validation: %s", valid ? "true" : "false");

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			neighborNode.close();
		}

	}

}