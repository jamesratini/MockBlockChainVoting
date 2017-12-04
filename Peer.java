public class Peer
{
	private int peerId;
	private String ipAddress;
	private int port;
	private PeerClient client;
	private PeerServer server; 

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

	}

	public void openClient()
	{
		// Initialize a client
	}
}