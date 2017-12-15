import java.net.*;

public class PeerDriver
{
	public static void main(String[] args)
	{
		// Create Peer
		InetAddress ip;
		try
		{
			ip = InetAddress.getLocalHost();

			Peer me = new Peer(ip.getHostAddress(), 5000);
		}
		catch(Exception ex)
		{

		}
		
		// run something
	}
}