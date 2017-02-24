// Paul Warner and Jared Patriarca
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Piece {
	int index;
	int length;
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	public int getIndex() {
		return index;
	}
	public int getLength() {
		return length;
	}
	public ByteBuffer getInfoHash() {
		return infoHash;
	}
	public ByteBuffer infoHash;
	byte[] data;
	
	public Piece(int index, int length, ByteBuffer infoHash) {
		this.index = index;
		this.length = length;
		this.infoHash = infoHash;
	}
	
	public boolean matchHash(byte[] data) {
		MessageDigest sha1;
		try {
			sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			ByteBuffer data_hash = ByteBuffer.wrap(sha1.digest(data));
			return data_hash.equals(this.infoHash);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true; // unreachable
	}
	
	public boolean matchHash() {
		return matchHash(this.data);
	}
}
