package resources;

public abstract class chessPiece
{
	boolean castleCheck = false;
	String displayChars;
	colors pieceColor;
	
	public abstract void move(cell [][] board, int rankIn, char fileIn, cell end) throws InvalidMoveException;
	//abstract boolean take(cell [][] board, int rankIn, char fileIn, cell end);
	public abstract boolean checkValidMove (cell[][] board, int rankIn, char fileIn, cell end);
	
	public static boolean checkKing(King checkPiece, cell [] [] board, cell thisCell)
	{

		return checkPiece.checkVerify(board, thisCell);
						
	}
	
	public static boolean checkMateKing(King checkPiece, cell [] [] board, cell thisCell)
	{

		return checkPiece.checkMate(board, thisCell);
		
				
	}
	public String getCharacters(){
		return displayChars;
	}
	
	public colors getPieceColor() {
		
		return pieceColor;
		
	}
	
	public void takePiece(chessPiece takePiece)
	{
		
		takePiece = null;
		
	}
	
	public boolean take(cell [][] board, int rankIn, char fileIn, cell end)
	{
		//Just here to make the return for compilation
		return true;
	}
	
	public boolean equals(chessPiece comparePiece)
	{
		
		if(this.getCharacters().compareTo(comparePiece.getCharacters()) == 0 && this.getPieceColor() == comparePiece.getPieceColor())
			return true;
		
		return false;
		
	}	
	

}