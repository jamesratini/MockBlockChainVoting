public class Peer
{
	private int peerId;
	private String ipAddress;
	private int port;
	private Client client;
	//private Server server; 

	public Peer(int id, String ip, int port)
	{
		// Assign important information about the peer
		// This information is used by other peers to identify this peer
		peerId = id;
		ipAddress = ip;
		this.port = port;

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

	}

	public boolean evaluateTransaction(String publicKey)
	{
		// Check if the publicKey has any remaining votes
		// If evaluation is good - send true back to sending server
		// If evaluation is bad - send false back to sending server
	}
	private void processMessage(String message)
	{
		// Will take a different branch for all possible outcomes

		// TransactionRequest
			// Evaluate

		// New Neighbor
			// Add new neighbor info to my neighbors

		// Response to our transaction request
			// Keep running tally of # true & # false
			// Once true + false == size of all neighbors, send out confirmation transaction is good. don't do anything if false

		// Valid Transaction Request
			// Add Transaction
	}

	// ------- CLIENT---------
	public void openClient()
	{
		// Initialize a client
		// Run client on a thread so application can send connections while doing other things
		client = new Client();
	}

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
				output.println("Transaction Incoming from: ");
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


}