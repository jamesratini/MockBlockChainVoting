import java.util.*;
import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AnchorNode
{
	private static ArrayList<Node> allNodes;

	//main function
	public static void main(String arg[]) throws Exception {

		//run the server
		AnchorNode aN = new AnchorNode();
	}


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
		try
		{
			while(true)
			{
				
				System.out.println("listening on port 8081");
				Socket initialConnectNode = anchorSocket.accept();
				System.out.println("CONNECTED TO CLIENT\n\n\n\n\n\n\n\n\n\n");
				
				BufferedReader in = new BufferedReader(new InputStreamReader(initialConnectNode.getInputStream()));
				String clientMessage = in.readLine();
				System.out.println(clientMessage + "\n");

				//now add this client to the array list
				int clientPort = Integer.parseInt(in.readLine());
				String clientIP = initialConnectNode.getInetAddress().getHostAddress();


				String neighborString = getNeighborInfo(clientPort, clientIP);
				PrintWriter output = new PrintWriter(initialConnectNode.getOutputStream(), true);
				output.println(neighborString);

			}
		}
		finally
		{
			anchorSocket.close();
		}
	}




	private String getNeighborInfo(int port, String ip) {
		String returnString = "";

		//if the peer is the first one to connect on the network
		if(allNodes.size() == 0) {
			returnString = "no peers on network, please wait for connections";

			//add the node to the array list
			Node newNeighbor = new Node(ip, port);
			allNodes.add(newNeighbor);
		}
		//else then there is atleast one other out there
		else {
			boolean isOnNetwork = false;

			for(Node n : allNodes) {
				//check whether or not the current node is in the array list
				if(n.getIP() != ip && n.getPort() != port) {
					returnString += n.getIP();
				returnString += ":";
				returnString += Integer.toString(n.getPort());
				returnString += "/";
				}
				//if it is found in the network array list
				else {
					isOnNetwork = true;
				}
			}

			//if is on network is still false, then add the neighbor
			if(isOnNetwork == false) {
				//add the node to the array list
				Node newNeighbor = new Node(ip, port);
				allNodes.add(newNeighbor);
			}


			//ArrayList<Node> neighbors = findBestNeighbors();
		}
		return returnString;
	}







	private ArrayList<Node> findBestNeighbors()
	{
		//this arraylist will hold the two smallest nodes
		ArrayList<Node> newNeighbors = new ArrayList<Node>();

		//these hold the 1st & 2nd nodes with the least connections
		Node smallest = null;
		Node secondSmallest = null;

		//go through the array of aleady defined nodes and check their number of connections
		for(Node n : allNodes) {
			//if smallest is null then just set it to be the first node in the list
			if(smallest == null) {
				smallest = n;
			}
			//on the next iteration of the for loop set secondSmallest
			else if(secondSmallest ==  null) {

				//if the current node has less connections, then switch it
				//with the current smallest
				if(n.getNumConnections() <= smallest.getNumConnections()) {
					//assign the smallest to the second smallest
					secondSmallest = smallest;

					smallest = n;
				}
			}
			//if both variables have a value in it
			else {
				//check the current node against the smallest node
				if(n.getNumConnections() <= smallest.getNumConnections()) {
					//swap around the nodes
					secondSmallest = smallest;
					smallest = n;
				}
				//check the current node against the second smallest node
				else if(n.getNumConnections() < secondSmallest.getNumConnections()) {
					//change the second smallest
					secondSmallest = n;
				}
			}
		}


		//now add the smallest nodes to the list to return
		newNeighbors.add(smallest);

		//check to see if second smallest is still null
		//if it is then that means that there is only one node in the array list
		if(secondSmallest == null) {
		newNeighbors.add(secondSmallest);
		}

		//return the new neighbors
		return newNeighbors;
	}
}