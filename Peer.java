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

	public void openServer() 
	{
		// Initialize a server
		// Run server on a thread so application can listen for incoming connections while doing other things

	}

	public void openClient()
	{
		// Initialize a client
		// Run client on a thread so application can send connections while doing other things
	}
}