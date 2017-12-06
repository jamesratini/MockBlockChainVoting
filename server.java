import java.net.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;




public class server {
	public static int port = 8080;
	private static Socket sock;

	public static void main(String arg[]) throws Exception {

		try{
			//create the socket and set it up to be a server socket
			ServerSocket server = new ServerSocket(port);

			//print out to let the user know what port we are listening on
			//and the fact that we are listening
			System.out.println("Waiting for connection on port: " + port);

			//keep the server up and running
			while(true) {
				//wait for a connection
				//once we have a connection we will complete the rest of the code
				sock = server.accept();

				//let them know we connected
				System.out.println("CONNECTED\n\n\n\n");

				//grab the input from the client
				InputStream is = sock.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);

				//create a buffer to read through message
				BufferedReader buffer = new BufferedReader(isr);

				//grab the message from the buffer
				String message = buffer.readLine();

				//print out to let the user know that we recieved a message from the client
				System.out.println("Message from client:    " + message + "\n\n");

				//send a return message
				String returnMessage = "Message recieved";;


				//open an output stream to send the response bac
				OutputStream os = sock.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bufferw = new BufferedWriter(osw);

				bufferw.write(returnMessage);

				bufferw.flush();
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally {
			try {
				sock.close();
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}
}




