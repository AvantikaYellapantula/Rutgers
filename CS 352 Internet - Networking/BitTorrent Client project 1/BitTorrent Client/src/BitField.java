// Paul Warner and Jared Patriarca
import java.util.ArrayList;
import java.util.List;

public class BitField {
	byte[] bits;
	
	public BitField(int len) {
		bits = new byte[len/8 + (len % 8 > 0 ? 1 : 0)];
		for(int i = 0; i < bits.length; ++i) {
			bits[i] = 0x0;
		}
	}
	
	public BitField(byte[] bits) {
		this.bits = bits;
		System.out.println(Integer.toBinaryString(bits[63] & 0xFF));
	}
	
	public void setBit(int off, int value) {
		if (value == 0) {
			bits[off/8] &= (1 << (7 - (off % 8)));
		} else if (value == 1) {
			bits[off/8] |= (1 << (7 - (off % 8)));
		}
	}
	
	public boolean getBit(int off) {
		return (bits[off/8] & (1 << (7 - (off % 8)))) != 0;
	}
	
	public byte[] getBits() {
		return bits;
	}
	
	public List<Piece> findNeeded(List<Piece> pieces) {
		List<Piece> neededPieces = new ArrayList<>();
		for(Piece p : pieces) {
			if (getBit(p.getIndex()))
				neededPieces.add(p);
		}
		return neededPieces;
	}
}
