import java.util.*;
import java.io.*;
import java.net.Socket;

public class Client
{

	//private static ArrayList<String> peerList; // Contains peerIds
	//private static ArrayList<String> serverList; // Contains IPAddresses
	//private ArrayList<Socket> serverSocketList; // Contains the 


	public Client() throws IOException
	{
		// on init, attempt to connect to the "anchor" peer
		// If connection is successful, keep running
		// Else, exit application with message "unable to reach the network"
		Socket sock = new Socket(Globals.anchorIP, Globals.anchorPort);
		BufferedReader input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		String serverResponse = input.readLine();
		System.out.printf("%s", serverResponse);


	}

	public void sendTransactionRequest()
	{
		// iterate through all serverSockets and attempt to connect and send the message
	}

}