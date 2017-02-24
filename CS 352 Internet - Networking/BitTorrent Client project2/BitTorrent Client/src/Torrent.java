// Paul Warner and Jared Patriarca
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import GivenTools.Bencoder2;
import GivenTools.BencodingException;
import GivenTools.TorrentInfo;

public class Torrent {
	
	static final ByteBuffer KEY_PEERS = ByteBuffer.wrap(new byte[] {'p', 'e', 'e', 'r', 's'});
	static final ByteBuffer KEY_COMPLETE = ByteBuffer.wrap(new byte[] {'c', 'o', 'm', 'p', 'l', 'e', 't', 'e'});
	static final ByteBuffer KEY_INCOMPLETE = ByteBuffer.wrap(new byte[] {'i', 'n', 'c', 'o', 'm', 'p', 'l', 'e', 't', 'e'});
	static final ByteBuffer KEY_MIN_INTERVAL = ByteBuffer.wrap(new byte[] {'m', 'i', 'n', ' ', 'i', 'n', 't', 'e', 'r', 'v', 'a', 'l'});
	static final ByteBuffer KEY_DOWNLOADED = ByteBuffer.wrap(new byte[] {'d', 'o', 'w', 'n', 'l', 'o', 'a', 'd', 'e', 'd'});
	static final ByteBuffer KEY_INTERVAL = ByteBuffer.wrap(new byte[] {'i', 'n', 't', 'e', 'r', 'v', 'a', 'l'});
	
	TorrentInfo ti;
	
	List<Peer> peers;
	
	List<Piece> needed;
	List<Piece> requested;
	List<Piece> have;

	int nextPiece;
	
	BitField bfield;
	
	public List<Peer> getPeers() {
		return peers;
	}

	Peer seeder;
	
	public Peer getSeeder() {
		return seeder;
	}

	int complete;
	int incomplete;
	int min_interval;
	int downloaded;
	int interval;
	
	HashSet<String> validIPs = new HashSet<String>(Arrays.asList(new String[] {"172.16.97.11", "172.16.97.12", "172.16.97.13"}));
	
	String infoHash;

	public String getInfoHash() {
		return infoHash;
	}
	
	public Torrent(String fp) {
		byte[] infoBuffer;
		try {
			infoBuffer = java.nio.file.Files.readAllBytes(Paths.get(fp));	
		} catch (IOException ioe) {
			System.out.println("Error reading file "+fp);
			return;
		}
		try {
			this.ti = new TorrentInfo(infoBuffer);	
		} catch (BencodingException bee) {
			System.out.println("Error with info in file "+fp+", perhaps the file is corrupted");
			return;
		}
		this.peers = null;
		this.infoHash = Utility.escapeString(ti.info_hash);
		bfield = new BitField(ti.piece_hashes.length);
		needed = new ArrayList<Piece>();
		for (int i = 0; i < ti.piece_hashes.length-1; ++i) {
			Piece p = new Piece(i, ti.piece_length, ti.piece_hashes[i]);
			needed.add(p);
		}
		// Last piece can be either equal to or shorter than piece_length, check the remainder of file_length and piece_length
		if (ti.file_length % ti.piece_length == 0) {
			needed.add(new Piece(ti.piece_hashes.length-1, ti.piece_length, ti.piece_hashes[ti.piece_hashes.length-1]));
		}
		else {
			needed.add(new Piece(ti.piece_hashes.length-1, ti.file_length % ti.piece_length, ti.piece_hashes[ti.piece_hashes.length-1]));
		}
		requested = new ArrayList<Piece>();
		have = new ArrayList<Piece>();
		nextPiece = 0;
	}
	
	/**
	 * Connect to the tracker indicated in the torrent file and get current metadata on this torrent
	 * @param peerId The peer_id used to identify this peer
	 * @param listeningPort THe port on which this port is currently listening
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void fetchTrackerData(String peerId, ServerSocket listeningPort) throws IOException {
		HttpURLConnection trackerConnection;
		URL url = encodeURL(ti, listeningPort, peerId);
		trackerConnection = (HttpURLConnection)url.openConnection();
		trackerConnection.setRequestMethod("GET");
		InputStream is = trackerConnection.getInputStream();
		this.peers = new ArrayList<Peer>();
		ArrayList<Peer> rupeers = new ArrayList<Peer>();
		byte[] trackerData = new byte[4096]; // Arbitrary size. H
		while (is.read(trackerData) > 0)
			;
		HashMap<ByteBuffer, Object> trackerDictionary;
		try {
			trackerDictionary = (HashMap<ByteBuffer, Object>)Bencoder2.decode(trackerData);
			List<Object> peerInfo = (List<Object>)trackerDictionary.get(KEY_PEERS);
			Peer newPeer;
			for(Object o: peerInfo) {
				newPeer = new Peer((HashMap<ByteBuffer, Object>)o, this);
				this.peers.add(newPeer);
				if (newPeer.getPeer_id().startsWith("-RU1103") && validIPs.contains(newPeer.getIp()))
					rupeers.add(newPeer);
				peers.add(newPeer);
			}
		} catch (BencodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		setSeeder(rupeers);
		this.complete = (Integer)trackerDictionary.get(KEY_COMPLETE);
		this.incomplete = (Integer)trackerDictionary.get(KEY_INCOMPLETE);
		this.min_interval = (Integer)trackerDictionary.get(KEY_MIN_INTERVAL);
		this.interval = (Integer)trackerDictionary.get(KEY_INTERVAL);
		this.downloaded = (Integer)trackerDictionary.get(KEY_DOWNLOADED);
	}
	
	void setSeeder(ArrayList<Peer> rupeers) {
		int[][] rtts = new int[rupeers.size()][2];
		for(int i = 0; i < rtts.length; ++i) {
			rtts[i][0] = i;
			rtts[i][1] = rupeers.get(i).measureRTT();
		}
		Arrays.sort(rtts, (a1, a2) -> a1[1] - a2[1]);
		int i = rtts[0][0];
		this.seeder = rupeers.get(i);
		System.out.println("Connecting to seeder "+seeder.getIp()+" with Average RTT of "+rtts[0][1]);
	}
	
	URL encodeURL(TorrentInfo ti, ServerSocket connectionSocket, String peerId) {
		StringBuffer buf = new StringBuffer();
		buf.append(ti.announce_url.toString());
		buf.append("?info_hash=");
		buf.append(this.infoHash);
		buf.append("&peer_id=");
		try {
			buf.append(URLEncoder.encode(peerId, "UTF-8"));	
		} catch(UnsupportedEncodingException uee) {
			System.out.println(uee.getMessage());
			uee.printStackTrace();
			System.exit(1);
		}
		buf.append("&port=");
		buf.append(connectionSocket.getLocalPort());
		buf.append("&downloaded=");
		buf.append(0);
		buf.append("&left=");
		buf.append(ti.file_length);
		try {
			return new URL(buf.toString());
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public void beginDownload(String peerId, String fp) {
		if (this.seeder.handshake(peerId, ti.info_hash)) {
			System.out.println("handshake successful!");
		} else {
			System.out.println("Handshake unsuccessful!");
		}
		long beginTime = System.nanoTime();
		this.seeder.beginCommunication();
		long endTime = System.nanoTime();
		System.out.println("Total time to download the file:"+(endTime-beginTime)+"ns");
		writeFile(fp);
	}
	
	/**
	 * Find a piece that we need and the peer has.
	 * @param p
	 * @return
	 */
	public Piece findPiece(Peer p) {
		List<Piece> piecesPeerHas = p.getPeerBitField().findNeeded(needed);
		if (needed.size() == 0)
			return null;
		Piece ret = piecesPeerHas.get(new Random().nextInt(piecesPeerHas.size()));
		needed.remove(ret);
		requested.add(ret);
		ret.setDownloadStarted(System.nanoTime());
		return ret;
	}
	
	public BitField getBitField() {
		return bfield;
	}
	
	public Piece getPieceByIndex(int index) {
		for (Piece p : requested) {
			if (p.getIndex() == index)
				return p;
		}
		for (Piece p : needed) {
			if (p.getIndex() == index)
				return p;
		}
		for (Piece p : have) {
			if (p.getIndex() == index)
				return p;
		}
		return null;
	}
	
	public void recievedPiece(int index) {
		this.seeder.signalHave(index);
		Piece recieved = getPieceByIndex(index);
		recieved.setDownloadFinished(System.nanoTime());
		System.out.println("Time to download piece "+index+": "+recieved.getDownloadTime()+"ns");
		if (requested.contains(recieved))
			requested.remove(recieved);
		have.add(recieved);
	}
	
	private void writeFile(String fp) {
		Piece current;
		Collections.sort(have, (Piece p1, Piece p2) -> p1.getIndex() - p2.getIndex());
		DataOutputStream out;
		try {
			out = new DataOutputStream(new FileOutputStream(new File(fp)));
			for(int i = 0; i < have.size(); ++i) {
				current = have.get(i);
				if (current.getData() == null) {
					System.out.println("WARNING!!! MISSING DATA AT INDEX "+current.getIndex());
				} else if (!current.matchHash()) {
					System.out.println("WARNING!!! INCORRECT HASH AT INDEX "+current.getIndex());
				} else {
					out.write(current.getData());	
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
