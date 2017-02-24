// Paul Warner and Jared Patriarca
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.security.SecureRandom;


/**
 * Rutgers BitTorrentClient, Version 1
 * Takes two arguments: a torrent file, and a file path to write the data to.
 * @author paule
 *
 */
public class RUBTClient {
	
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Error: too few arguments");
			System.exit(1);
		}
		
		ServerSocket connectionSocket = openPort();
		if (connectionSocket == null) {
			System.out.println("Error: Couldn't open socket");
			System.exit(1);
		}
		
		String peerId = generatePeerId();
		
		Torrent t = new Torrent(args[0]);
		try {
			t.fetchTrackerData(peerId, connectionSocket);
		} catch(IOException e) {
			System.out.println("Error connecting to tracker");
			System.exit(1);
		}
		t.beginDownload(peerId, args[1]);
	}
	
	/**
	 * Open a socket that this peer uses to listen for new connections.
	 * As per the BitTorrent protocol this only tries to open connections on ports 6881-6889
	 * @return A server socket 
	 */
	static ServerSocket openPort() {
		int start = 6881;
		ServerSocket sock = null;
		while (sock == null && start <= 6889) {
			try {
				sock = new ServerSocket(start);	
			} catch (IOException ioe) {}
			start++;
		}
		return sock;
	}
	
	/**
	 * Generate a random, unique peer id used to identify this peer 
	 * @return
	 */
	static String generatePeerId() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(100, random).toString(32);
	}
}
 