import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.*;


public class Peer
{
	private int peerId;
	private AtomicInteger validationVotesTrue;
	private AtomicInteger validationVotesFalse;
	private String ipAddress;
	private int port;
	private Client client;
	private PublicRecords publicRecords;
	private static ArrayList<String> neighborServerList; // Contains the IP/Port pair of neighbors

	//private Server server; 

	public Peer(int id, String ip, int port)
	{
		// Assign important information about the peer
		// This information is used by other peers to identify this peer
		peerId = id;
		ipAddress = ip;
		this.port = port;
		neighborServerList = new ArrayList<String>();
		publicRecords = new PublicRecords();
		validationVotesTrue = new AtomicInteger(0);
		validationVotesFalse = new AtomicInteger(0);

		// Initialize Ledger and Public Record
	}

	// PEER
	public void addTransaction(String sender, String receiver)
	{
		// Add a new Transaction to the Ledger
		// Check if size == 10
			// true - Create new Block
			// false - return
	}

	// ------ SERVER ------
	public void openServer() 
	{
		// Initialize a server
		// Run server on a thread so application can listen for incoming connections while doing other things

		
		

		

		try{
			//create the socket and set it up to be a server socket
			ServerSocket server = new ServerSocket(port);

			//print out to let the user know what port we are listening on
			//and the fact that we are listening
			System.out.println("Waiting for connection on port: " + port);

			//keep the server up and running
			while(true) {
				//wait for a connection
				//once we have a connection we will complete the rest of the code

				// Run as a thread so we can accept multiple connections
				
				processMessage(server.accept());

				

				//print out to let the user know that we recieved a message from the client
				//System.out.println("Message from client:    " + message + "\n\n");

				//send a return message
				//String returnMessage = "Message recieved";;


				//open an output stream to send the response bac
				//OutputStream os = sock.getOutputStream();
				//OutputStreamWriter osw = new OutputStreamWriter(os);
				//BufferedWriter bufferw = new BufferedWriter(osw);

				//bufferw.write(returnMessage);

				//bufferw.flush();
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	public boolean evaluateTransaction(String senderKey, String receiverKey)
	{
		boolean retVal = false;
		// Check if the publicKey has any remaining votes
		// If evaluation is good - send true back to sending server
		// If evaluation is bad - send false back to sending server
		if(publicRecords.contains(senderKey) && publicRecords.contains(receiverKey) && publicRecords.hasVote(senderKey))
		{
			retVal = true;
		}

		return retVal;
	}
	private void processMessage(Socket connection) throws IOException
	{
		//let them know we connected
		System.out.println("CONNECTED\n\n\n\n");

		//grab the input from the client
		InputStream is = connection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);

		//create a buffer to read through message
		BufferedReader buffer = new BufferedReader(isr);

		//grab the message from the buffer
		String message = buffer.readLine();
		// Will take a different branch for all possible outcomes

		
		if(message.contains("Transaction Request"))
		{
			// TransactionRequest
			// Evaluate
			// Respond with our evaluation of the request

			String[] split = message.split(":");
			PrintWriter output = new PrintWriter(connection.getOutputStream(), true);

			if(evaluateTransaction(split[1], split[2]))
			{
				output.printf("Response Validation: true");
			}
			else
			{
				output.printf("Response Validation: false");
			}
		}
		else if(message.contains("Introduction"))
		{
			// New Neighbor
			// Add new neighbor info to my neighbors
		}
		else if(message.contains("Response Validation"))
		{
			// Response to our transaction request
			// Keep running tally of # true & # false
			// Once true + false == size of all neighbors, send out confirmation transaction is good. don't do anything if false
		}
		else if(message.contains("Approved Transaction"))
		{
			// Valid Transaction Request
			// Add Transaction
			// Keep tally, can't add until all nodes verify it is a successful transaction
		}

		connection.close();
	}

	// ------- CLIENT---------
	/*public void openClient()
	{
		// Initialize a client
		// Run client on a thread so application can send connections while doing other things
		
	}*/

	public void sendTransactionRequest(String receiver, String myPublicKey) throws IOException
	{
		// Iterate through neighbors (everyone) and send the transaction request
		// Server aspect will listen to neighbors evaluations, if 
		// TODO: Run in a thread
		for(int i = 0; i < neighborServerList.size(); i++)
		{
			String[] splitPair = neighborServerList.get(i).split(":");
			Socket neighbor = new Socket(splitPair[0], Integer.parseInt(splitPair[1]));
			try
			{
				PrintWriter output = new PrintWriter(neighbor.getOutputStream(), true);
				output.printf("Transaction Request:%s:%s:%d", myPublicKey, receiver, peerId);
				
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


}