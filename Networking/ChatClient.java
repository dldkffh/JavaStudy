import java.net.*;
import java.io.*;

public class ChatClient {
	public static void main(String[] args) {
		String hostname = "localhost";
		if (args.length > 0) {
			hostname = args[0];
		}

		PrintWriter networkOut = null;
		BufferedReader networkIn = null;
		try {
			
			Socket theSocket = new Socket(hostname, 7);
			networkIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			networkOut = new PrintWriter(theSocket.getOutputStream());
			System.out.println("Connected to echo server");
			while (true) {
				String theLine = userIn.readLine();
				if (theLine.equals("."))
					break;
				networkOut.println(theLine);
				networkOut.flush();
				System.out.println(networkIn.readLine());
			}
		} // end try
		catch (IOException ex) {
			System.err.println(ex);
		}
		finally {
			try {
				if (networkIn != null)
					networkIn.close();
				if (networkOut != null)
					networkOut.close();
			}
			catch (IOException ex) {
			}
		}
	} // end main
} // end EchoClient
