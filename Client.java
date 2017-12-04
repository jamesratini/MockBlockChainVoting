import java.util.*;
import java.net.*;

public class Client
{

	private static ArrayList<String> peerList; // Contains peerIds
	private static ArrayList<String> serverList; // Contains IPAddresses
	private ArrayList<Socket> serverSocketList; // Contains the 

	public Client()
	{
		// on init, attempt to connect to the "anchor" peer
		// If connection is successful, keep running
		// Else, exit application with message "unable to reach the network"

	}

	public void sendTransactionRequest()
	{
		// iterate through all serverSockets and attempt to connect and send the message
	}

}