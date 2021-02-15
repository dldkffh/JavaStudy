import java.net.*;
import java.io.*;

public class UDPPoke {

	private int bufferSize; // in bytes
	private DatagramSocket socket;
	private DatagramPacket outgoing;

	public UDPPoke(InetAddress host, int port, int bufferSize, int timeout) throws SocketException {

		outgoing = new DatagramPacket(new byte[1], 1, host, port);
		this.bufferSize = bufferSize;
		socket = new DatagramSocket(0);
		socket.connect(host, port); // requires Java 2
		socket.setSoTimeout(timeout);

	}

	public UDPPoke(InetAddress host, int port, int bufferSize) throws SocketException {
		this(host, port, bufferSize, 30000);
	}

	public UDPPoke(InetAddress host, int port) throws SocketException {
		this(host, port, 8192, 30000);
	}

	public byte[] poke() throws IOException {

		byte[] response = null;
		try {
			socket.send(outgoing);
			DatagramPacket incoming = new DatagramPacket(new byte[bufferSize], bufferSize);
			// next line blocks until the response is received
			socket.receive(incoming);
			int numBytes = incoming.getLength();
			response = new byte[numBytes];
			System.arraycopy(incoming.getData(), 0, response, 0, numBytes);
		} catch (IOException ex) {
			// response will be null
		}

		// may return null
		return response;
	}

	public static void main(String[] args) {

		InetAddress addr = null;
		InetAddress[] addrArr = null;
		InetAddress host;
		int port = 13;
		String name = "localhost";
		try {
			addrArr = InetAddress.getAllByName(name);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < addrArr.length; i++) {
			System.out.println("IP ¸ñ·Ï[" + i + "] : " + addrArr[i]);

			try {
				host = InetAddress.getByName(name);
//				port = Integer.parseInt(name);
				if (port < 1 || port > 65535)
					throw new Exception();
			} catch (Exception ex) {
				System.out.println("Usage: java UDPPoke host port");
				return;
			}

			try {
				UDPPoke poker = new UDPPoke(host, port);
				byte[] response = poker.poke();
				if (response == null) {
					System.out.println("No response within allotted time");
					return;
				}
				String result = new String(response, "UTF-8");
				System.out.println(result);
			} catch (Exception ex) {
				System.err.println(ex);
				ex.printStackTrace();
			}

		} // end main
	}
}
