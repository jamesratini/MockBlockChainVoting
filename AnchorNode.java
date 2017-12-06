import java.util.*;
import java.io.*;
import java.net.*;

public class AnchorNode
{
	private ArrayList<Node> allNodes;
	public AnchorNode() throws IOException
	{	
		allNodes = new ArrayList<Node>();

		try
		{
			runServer();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void runServer() throws IOException
	{
		// Always be running a server
		ServerSocket anchorSocket = new ServerSocket(8081);
		while(true)
		{
			try
			{
				

				Socket initialConnectNode = anchorSocket.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(initialConnectNode.getInputStream()));
				String clientMessage = in.readLine();
				System.out.println(clientMessage);
				if(clientMessage == "Initial Connect")
				{
					// Only happens when new node runs initialConnect()
					int clientPort = Integer.parseInt(in.readLine());
					System.out.println(clientMessage);
					
					ArrayList<Node> bestNeighbors = findBestNeighbors();
					PrintWriter output = new PrintWriter(initialConnectNode.getOutputStream(), true);
					output.println("Hello, From Anchor Node");

					allNodes.add(new Node(initialConnectNode.getInetAddress().getHostAddress(), clientPort));


				}
				else
				{
					// Shouldn't happen
				}
				

				// Once accepted, select 2 IP/port Pairs, and serialize objects, then send
				
			}
			finally
			{
				anchorSocket.close();
			}
		}
	}

	private ArrayList<Node> findBestNeighbors()
	{
		// iterate through all Nodes, return 2 with least numConnections
	}
}