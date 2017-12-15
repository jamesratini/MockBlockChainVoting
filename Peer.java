import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.*;


// TO TEST
// Make sure server recieves proper message when its supposed to
// Make sure validationVotes is actually thread safe
// Make sure all neighbors receive the introduction message.
// Make sure receiving an introduction message is properly handled

public class Peer
{
	private int peerId;
	private AtomicInteger validationVotesTrue;
	private AtomicInteger validationVotesFalse;
	private String ipAddress;
	private int serverPort;
	private Client client;
	private PublicRecords publicRecords;
	private static ArrayList<String> neighborServerList; // Contains the IP/Port pair of neighbors

	//private Server server; 

	public Peer(String ip, int port) throws IOException
	{
		// Assign important information about the peer
		// This information is used by other peers to identify this peer
		//peerId = id;
		ipAddress = ip;
		serverPort = port;
		neighborServerList = new ArrayList<String>();
		publicRecords = new PublicRecords();
		validationVotesTrue = new AtomicInteger(0);
		validationVotesFalse = new AtomicInteger(0);

		// Initialize Ledger and Public Record
		try{
			initialConnect();
			new Thread()
			{
				public void run()
				{
					openServer();
				}
			}.start();
			
		}
		catch(IOException e) {

		}
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
			ServerSocket server = new ServerSocket(serverPort);

			//print out to let the user know what port we are listening on
			//and the fact that we are listening
			System.out.println("Waiting for connection on port: " + serverPort);

			//keep the server up and running
			while(true) 
			{
				//wait for a connection
				//once we have a connection we will complete the rest of the code

				// Run as a thread so we can accept multiple connections
				
				processMessage(server.accept());
				
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
			System.out.printf("%s voting for %s is valid \n", senderKey, receiverKey);
			retVal = true;
		}
		else {
			System.out.println("SHOULDNT BE IN HERE FUCKTARD");
		}

		return retVal;
	}
	private void processMessage(Socket connection) throws IOException
	{
		//let them know we connected
		//System.out.printf("Connected to %s", connection.getInetAddress().getHostAddress());
		System.out.println("something");
		//grab the input from the client
		InputStream is = connection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		System.out.println("something also");

		//create a buffer to read through message
		BufferedReader buffer = new BufferedReader(isr);
		System.out.println("something even");

		//grab the message from the buffer
		String message = buffer.readLine();
		// Will take a different branch for all possible outcomes

		System.out.println(message);
		if(message.contains("Transaction Request"))
		{
			// TransactionRequest
			// Evaluate
			// Respond with our evaluation of the request
			System.out.println("fucke me up dawg");

			String[] split = message.split(":");
			PrintWriter output = new PrintWriter(connection.getOutputStream(), true);

			if(evaluateTransaction(split[1], split[2]))
			{
				output.printf("true");
			}
			else
			{
				output.printf("false");
			}


		}
		else if(message.contains("Introduction"))
		{
			// New Neighbor
			// Add new neighbor info to my neighbors

			// Current introduction message contains no useful info. For POC purposes, it doesn't need to
			//System.out.println(message);

			// Add the new neighbor
			String[] splitMsg = message.split(":");
			neighborServerList.add(splitMsg[1]);

			for(int i = 0; i < neighborServerList.size(); i++)
			{
				System.out.println(neighborServerList.get(i));
			}


		}
	
		else if(message.contains("Approved Transaction"))
		{
			// Valid Transaction Request
			// Add Transaction
			// Keep tally, can't add until all nodes verify it is a successful transaction
		}

		connection.close();
	}

	public void sendTransactionRequest(String receiver, String myPublicKey) throws IOException
	{
		// Iterate through neighbors (everyone) and send the transaction request
		// Server aspect will listen to neighbors evaluations, if 
		System.out.println("In STR");
		ThreadGroup allThreads = new ThreadGroup("Transaction Request Threads");
		for(int i = 0; i < neighborServerList.size(); i++)
		{
			String[] splitPair = neighborServerList.get(i).split(":");

			// Start a new thread to handle the selected neighbor. This thread will remain active until it receives a response from its neighbor. It will then increment a thread safe variable
			new Thread(allThreads, "name?")
			{
				public void run()
				{
					try
					{
						System.out.println("Made new thread");
						String[] myPair = splitPair;			
						
						// Send out the transaction request info
						Socket neighbor = new Socket(myPair[0], Integer.parseInt(myPair[1]));
					
						PrintWriter output = new PrintWriter(neighbor.getOutputStream(), true);
						output.printf("Transaction Request:%s:%s", myPublicKey, receiver);
						
						// Wait for out chosen neighbor to respond back
						BufferedReader in = new BufferedReader(new InputStreamReader(neighbor.getInputStream()));
						while(true)
						{
							String response = in.readLine();

							if(response != null)
							{
								if(response == "true")
								{
									System.out.println("Received true");
									// Increment atomic integer for true validation
									validationVotesTrue.incrementAndGet();
								}
								else if(response == "false")
								{
									System.out.println("Received false");
									// Increment atomic integer for false validation
									validationVotesFalse.incrementAndGet();
								}

								break;
							}
						}
						neighbor.close();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					// DO THREADS CLEANLY KILL THEMSELVES????
				}	
			}.start();
			
			// Wait for all threads to finish
			// Tally the votes
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

			output.println(5000);
			
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
			for(int i = 0; i < neighborServerList.size(); i++)
			{
				System.out.println(neighborServerList.get(i));
			}
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
		
		for(String neighbor : neighborServerList)
		{
			String[] splitNeighbor = neighbor.split(":");
			// Send an intro message
			// TODO: This message will contain all of this peers info that other peers need to know, public key and remaining votes
			// TODO: Servers will handle this differently than receiving a transactionRequest, I think

			new Thread()
			{
				public void run()
				{
					try
					{
						String[] myPair = splitNeighbor;
						Socket connectingNeighbor = new Socket(myPair[0], Integer.parseInt(myPair[1]));
						PrintWriter output = new PrintWriter(connectingNeighbor.getOutputStream(), true);
						output.printf("Introduction: %s%d", ipAddress, serverPort);
						connectingNeighbor.close();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}.start();
			
					
			


		}
	}


}