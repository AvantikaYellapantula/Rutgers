package resources;

public class cell{

	private int rank;
	private char file;
	private String cellDisplay;
	private chessPiece currentPiece;
	
	public cell(){
		file = 'a';
		rank = 0;
	}

	public cell(int rankIn, char fileIn, String cellDispIn){
		rank = rankIn;
		file = fileIn;
		cellDisplay = cellDispIn;
	}

	public char getFile(){
		return file;
	}
	
	public int getRank(){
		return rank;
	}

	public void setFile(char fileIn){
		file = fileIn;
	}

	public void setRank(int rankIn){
		rank = rankIn;
	}

	public void setCell(cell cellIn){
		file = cellIn.getFile();
		rank = cellIn.getRank();
	}
	
	public void setDisplay(String cellDispIn){
		cellDisplay = cellDispIn;
	}
	
	public void setPiece(chessPiece pieceIn){	
		currentPiece = pieceIn;
	}
	
	public chessPiece removePiece(){	
		chessPiece piece=currentPiece;
		currentPiece=null;
		return piece;
	}

	public chessPiece getPiece(){

		return currentPiece;
	}
	
	public boolean equals(cell cellIn){
		if(file == cellIn.getFile() && rank == cellIn.getRank())
			return true;

		return false;
	}
	
	public String getDisplay(){
		return cellDisplay;
	}

	public String toString(){
		String thisCell = "";
		thisCell += rank;
		thisCell += file;

		return (thisCell);
	}

}