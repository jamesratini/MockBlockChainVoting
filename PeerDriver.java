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

			if(System.in.read() == 'x')
			{
				me.sendTransactionRequest("James", "Chase");
			}
		}
		catch(Exception ex)
		{

		}
		
		// run something
	}
}