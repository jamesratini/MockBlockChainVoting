import java.util.*;
import java.io.*;
import java.net.*;

public class ServerDriver
{
	public static void main(String args[]) throws IOException
	{
		ServerSocket listener = new ServerSocket(5000);
		try
		{
			while(true)
			{
				System.out.println("listening");
				Socket sock = listener.accept();
				System.out.println("Accepted ClientConnection from " + sock.getRemoteSocketAddress());

				try
				{
					BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					String clientMessage = in.readLine();
					System.out.println(clientMessage);
				}
				finally
				{
					sock.close();
				}
			}
		}
		finally
		{

			listener.close();
		}
	}
}