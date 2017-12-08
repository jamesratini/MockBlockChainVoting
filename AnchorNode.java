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
					addNode(initialConnectNode, clientPort);
					


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

	private void addNode(Socket initialConnectNode, int clientPort) throws IOException {
		// TODO Auto-generated method stub
		//if there are no nodes in the blockchain
		if(allNodes.isEmpty())
		{
			allNodes.add(new Node(initialConnectNode.getInetAddress().getHostAddress(),clientPort));
		}
		else
		{
			ArrayList<String> bestNeighbors = findBestNeighbors();
			String returnString = "";
			for(String s : bestNeighbors)
			{
				returnString = returnString + s + "/";
			}
			PrintWriter output = new PrintWriter(initialConnectNode.getOutputStream(), true);
			output.println(returnString);
			allNodes.add(new Node(initialConnectNode.getInetAddress().getHostAddress(), clientPort));
		}
		
		
	}

	private ArrayList<String> findBestNeighbors()
	{
		// iterate through all Nodes, return 2 with least numConnections
		ArrayList<String> bestList = new ArrayList<String>();
		//we have to set the nodes to null because they need important parameters 
		Node smallest = null;
		Node secondSmallest = null;
		//step through all the nodes, checking for the smallest number of connections on nodes.
		
		for(Node n : allNodes )
		{
			if(smallest == null)
			{
				smallest = n;
			}
			else if(secondSmallest == null)//we use an else if here so that it doesnt set the first node twice in the first iteration.
			{
				if(n.getNumConnections() < smallest.getNumConnections())
				{
					//switch over to the second place before overwriting smallest..
					secondSmallest = smallest;
					
					//put the node we are currently on in the smallest holder.
					smallest = n;
				}
			}
			else//if the variables are both not null, compare n to both. bumping smallest to second, replacing second smallest, or neither.
			{
				if(n.getNumConnections() < smallest.getNumConnections())
				{
					//switch down to the second place
					secondSmallest = smallest;
					smallest = n;
				}
				else if(n.getNumConnections() < secondSmallest.getNumConnections())
				{
					//overwriting second smallest.
					secondSmallest = n;
				}
			}
		}
		
		if(smallest != null)
		{
			bestList.add(smallest.getIP() + ':' + smallest.getPort());
		}
		if(secondSmallest != null)
		{
			bestList.add(secondSmallest.getIP() + ':' + secondSmallest.getPort());
		}
		return bestList;
		
	}
	
}