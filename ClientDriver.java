import java.io.*;

public class ClientDriver
{
	public static void main(String args[])
	{
		try
		{
			Client c = new Client();
			c.sendTransactionRequest();
		}
		catch(IOException x)
		{
			System.out.printf("Failed");
			x.printStackTrace();
		}
		
	}
}