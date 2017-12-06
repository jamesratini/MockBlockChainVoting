import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class testClient {
	private static Socket sock;

	public static void main(String arg[]) throws Exception {
		try {
		String host = "localhost";
		int port = 8080;
		InetAddress address = InetAddress.getByName(host);

		sock = new Socket(address, port);

		//print out to let the user know we connected to the server
		System.out.println("CONNECTED TO SERVER");

		//send the message to the server
		OutputStream outsteam = sock.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(outsteam);
		BufferedWriter bufferw = new BufferedWriter(osw);

		String message = "This should def work duh -people\n";

		bufferw.write(message);
		bufferw.flush();


		//get the return message from the server
		InputStream input = sock.getInputStream();
		InputStreamReader isr = new InputStreamReader(input);
		BufferedReader buffer = new BufferedReader(isr);
		String response = buffer.readLine();

		System.out.println("Message from server   " + response + "\n\n\n");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			//close the socket
			try {
				sock.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}