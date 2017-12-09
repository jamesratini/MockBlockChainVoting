import java.util.*;
import java.io.*;
import java.net.*;

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
				System.out.println("CONNECTED TO CLIENT\n");
				
				BufferedReader in = new BufferedReader(new InputStreamReader(initialConnectNode.getInputStream()));
				String clientMessage = in.readLine();
<<<<<<< HEAD
				System.out.println(clientMessage + "\n");
=======
				System.out.println(clientMessage);
				if(clientMessage == "Initial Connect")
				{
					// Only happens when new node runs initialConnect()
					int clientPort = Integer.parseInt(in.readLine());
					System.out.println(clientMessage);
					addNode(initialConnectNode, clientPort);
					
>>>>>>> 07be273315b07863193c1939853bc5a080d9929d


				//send the peer's client the information about its neighbors
				findBestNeighbors();
				String neighborString = getNeighborInfo();
				PrintWriter output = new PrintWriter(initialConnectNode.getOutputStream(), true);
				output.println(neighborString);

				//now add this client to the array list
				int clientPort = Integer.parseInt(in.readLine());
				String clientIP = initialConnectNode.getInetAddress().getHostAddress();
				
				Node newNeighbor = new Node(clientIP, clientPort);
			}
		}
		finally
		{
			anchorSocket.close();
		}
	}




	private String getNeighborInfo() {
		String returnString = "";

		//if the peer is the first one to connect on the network
		if(allNodes.size() == 0) {
			returnString = "no peers on network, please wait for connections";
		}
		//else then there is atleast one other out there
		else {
			ArrayList<Node> neighbors = findBestNeighbors();

			//go through the neighbors and combine thier info into the return string
			for(Node n : neighbors) {
				returnString += n.getIP();
				returnString += ":";
				returnString += Integer.toString(n.getPort());
				returnString += "/";
			}
		}
		return returnString;
	}

<<<<<<< HEAD





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
=======
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
>>>>>>> 07be273315b07863193c1939853bc5a080d9929d
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
<<<<<<< HEAD


		//now add the smallest nodes to the list to return
		newNeighbors.add(smallest);

		//check to see if second smallest is still null
		//if it is then that means that there is only one node in the array list
		if(secondSmallest == null) {
			newNeighbors.add(secondSmallest);
		}

		//return the new neighbors
		return newNeighbors;
=======
		
		if(smallest != null)
		{
			bestList.add(smallest.getIP() + ':' + smallest.getPort());
		}
		if(secondSmallest != null)
		{
			bestList.add(secondSmallest.getIP() + ':' + secondSmallest.getPort());
		}
		return bestList;
		
>>>>>>> 07be273315b07863193c1939853bc5a080d9929d
	}
}