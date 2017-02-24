// Paul Warner and Jared Patriarca
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class Peer {
	
	static final ByteBuffer HANDSHAKE_HEADER = 
			ByteBuffer.wrap(new byte[] {19, 'B', 'i', 't', 'T', 'o', 'r', 'r', 'e', 'n', 't', ' ',
										'p', 'r', 'o', 't', 'o', 'c', 'o', 'l',
										0, 0, 0, 0, 0, 0, 0, 0});
	static final ByteBuffer KEY_PORT = ByteBuffer.wrap(new byte[] {'p', 'o', 'r', 't'});
	static final ByteBuffer KEY_IP = ByteBuffer.wrap(new byte[] {'i', 'p'});
	static final ByteBuffer KEY_PEER_ID = ByteBuffer.wrap(new byte[] {'p', 'e', 'e', 'r', ' ', 'i', 'd'});
	
	static final byte CHOKE = 0;
	static final byte UNCHOKE = 1;
	static final byte INTERESTED = 2;
	static final byte NOT_INTERESTED = 3;
	static final byte HAVE = 4;
	static final byte BITFIELD = 5;
	static final byte REQUEST = 6;
	static final byte PIECE = 7;
	static final byte CANCEL = 8;
	
	int port;
	String ip;
	String peer_id;
	Torrent to;
	
	boolean peerChoked;
	boolean peerInterested;
	
	boolean choked;
	boolean interested;
	
	BitField peerBitField;
	
	public BitField getPeerBitField() {
		return peerBitField;
	}

	Socket conn;
	DataInputStream in;
	DataOutputStream out;
	
	boolean handshakeCompleted;
	
	public int getPort() {
		return port;
	}

	public String getIp() {
		return ip;
	}

	public String getPeer_id() {
		return peer_id;
	}

	public Peer(HashMap<ByteBuffer, Object> dict, Torrent to) {
		this.port = (Integer)dict.get(KEY_PORT);
		this.ip = new String(((ByteBuffer)dict.get(KEY_IP)).array());
		this.peer_id = new String(((ByteBuffer)dict.get(KEY_PEER_ID)).array());
		this.to = to;
		this.peerChoked = true;
		this.choked = true;
		this.peerInterested = true;
		this.interested = false;
		this.handshakeCompleted = false;
	}
	
	public boolean handshake(String peerId, ByteBuffer infoHash) {
		try {
			this.conn = new Socket(ip, port);
			this.out = new DataOutputStream(conn.getOutputStream());
			this.in = new DataInputStream(conn.getInputStream());
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		StringBuffer buf = new StringBuffer();
		buf.append(new String(HANDSHAKE_HEADER.array()));
		buf.append(new String(infoHash.array())); // info hash
		buf.append(peerId); // and our peer id
		try {
			this.out.write(HANDSHAKE_HEADER.array());
			this.out.write(infoHash.array());
			this.out.write(peerId.getBytes());
			this.out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] arr = new byte[68];
		try {
			this.in.readFully(arr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ByteBuffer recievedInfoHash = ByteBuffer.wrap(new byte[20]);
		System.arraycopy(arr, HANDSHAKE_HEADER.array().length, recievedInfoHash.array(), 0, 20);

		if (recievedInfoHash.equals(infoHash)) {
			this.handshakeCompleted = true;
			return true;
		}
		else {
			try {
				this.conn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
	}
	
	public void sendBitField(BitField b) {
		try {
			this.out.write(b.getBits());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void requestPiece(Piece p) {
		System.out.println("requesting piece "+p.getIndex());
		try {
			this.out.writeInt(13);
			this.out.writeByte(REQUEST);
			this.out.writeInt(p.getIndex());
			this.out.writeInt(0);
			this.out.writeInt(p.getLength());
			this.out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void beginCommunication() {
		int messageLength;
		byte messageId;
		boolean first = true;
		int pieceIndex;
		while (true) {
			try {
				messageLength = in.readInt();
				if (messageLength == 0) {// keepalive
					continue;
				}
				messageId = in.readByte();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			switch(messageId) {
			case CHOKE:
				System.out.println("Choking...");
				peerChoked = true;
				break;
			case UNCHOKE:
				System.out.println("Unchoking...");
				peerChoked = false;
				break;
			case INTERESTED:
				System.out.println("Interested...");
				peerInterested = true;
				break;
			case NOT_INTERESTED:
				System.out.println("Uninterested...");
				peerInterested = false;
				break;
			case HAVE:
				System.out.println("peer has new piece...");
				break;
			case BITFIELD:
				System.out.println("Recieving bitfield...");
				readBitfield();
				break;
			case REQUEST:
				System.out.println("peer requesting piece...");
				break;
			case PIECE:				
				if ((pieceIndex = recievePiece(messageLength)) >= 0) {
					System.out.println("Signaling have piece "+pieceIndex);
					this.to.recievedPiece(pieceIndex); // Tell everyone we now have this piece
				}
				break;
			case CANCEL:
				System.out.println("Peer canceling piece...");
				break;
			default:
				System.out.println("invalid message "+messageId);
				return;
			}
			if (first) {
				signalInterested(true);
				first = false;
			} else {
				Piece p = to.findPiece(this);
				if (p == null)
					return; // We've finished
				else
					requestPiece(p);
			}
		}
	}
	
	public void signalHave(int index) {
		try {
			this.out.writeInt(5);
			this.out.writeByte(HAVE);
			this.out.writeInt(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private int recievePiece(int messageLength) {
		int index = -1;
		int begin;
		int blockLength = messageLength - 9;
		byte[] buffer = new byte[blockLength];
		try {
			index = this.in.readInt();
			System.out.println("recieving pice "+index);
			begin = this.in.readInt();
			int off = 0;
			while (off < buffer.length) {
				off += this.in.read(buffer, off, buffer.length-off);
			}
			Piece p = this.to.getPieceByIndex(index);
			if (begin != 0) {
				System.out.println("We need to do more work to deal with getting pieces with offsets not equal to zero...");
			}
			if (p.matchHash(buffer)) {
				System.out.println("got a matching hash value!");
				p.setData(buffer);
				return index;
			} else {
				System.out.println("Hash mismatch at index "+index);
				return -1;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public void signalChoked(boolean state) {
		if (!handshakeCompleted)
			return;
		try {
			this.out.writeInt(1);
			this.out.writeByte(state == true ? CHOKE : UNCHOKE);
			this.out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		this.interested = state;
	}
	
	private void signalInterested(boolean state) {
		if (!handshakeCompleted)
			return;
		try {
			this.out.writeInt(1);
			this.out.writeByte(state == true ? INTERESTED : NOT_INTERESTED);
			this.out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		this.interested = state;
	}
	
	void readBitfield() {
		byte[] bits = new byte[this.to.getBitField().getBits().length];
		try {
			this.in.read(bits, 0, bits.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		peerBitField = new BitField(bits);
	}
}
