package resources;

public class bishop extends chessPiece {
	 	
	public bishop(colors colorIn) {
		
		pieceColor = colorIn;
		
		if (colorIn==colors.BLACK)
			displayChars = "bB ";
		else	
			displayChars = "wB ";
	}
		
	public cell take()
	{
		//Just here to make the return for compilation
		return new cell();
	}
	
	public void move(cell [][] board, int rankIn, char fileIn, cell end) throws InvalidMoveException{
		
		if(!this.checkValidMove(board, rankIn, fileIn, end) || !this.colCheck(board, rankIn, fileIn, end))
			throw new InvalidMoveException();
		
		end.setPiece(board[rankIn][fileIn-'a'].removePiece());
				
	}
	
	
	public boolean checkValidMove(cell [][] board,int rankIn, char fileIn, cell end)
	{
	
	
		if(end.getPiece()!=null)
			if(end.getPiece().getPieceColor() == pieceColor)
				return false;
		
		if(((rankIn - end.getRank()) == ((fileIn-end.getFile())) - 1) || 
			((rankIn - end.getRank()) == (-(fileIn - end.getFile())) - 1))
			
			return true;
		
		
		return false;
		
	}
	
	boolean colCheck(cell [] [] board, int rankIn, char fileIn, cell end)
	{
	
		int i = rankIn;
		int j = (int) fileIn - 'a';
		int k = end.getRank() - 1;
		int l = (int) end.getFile() - 'a';
		
		if(i < k && j < l)
			while(i<k)
			{
				i++;
				j++;
				
				if(board[i][j].getPiece() != null)
				{
					
					if(board[i][j].getPiece().getPieceColor() == this.pieceColor)
						
						return false;
					
					else if(board[i][j].getPiece().getPieceColor() != this.pieceColor)
					{
					
						System.out.println("This cannot be done! You must take the piece at " + ((char) j + 'a') + i);
						return false;
				
					}
				
				}
			}
		
		if(i<k && j > l)
			while (i<k)
			{
				i++;
				j--;
				if(board[i][j].getPiece() != null)
				{
					if(board[i][j].getPiece().getPieceColor() == this.pieceColor)
						
						return false;
					
					else if(board[i][j].getPiece().getPieceColor() != this.pieceColor)
					{
						System.out.println("This cannot be done! You must take the piece at " + ((char) j + 'a') + i);
						return false;
					}
			
				}
			}
		
		if(i>k && j < l)
			while (i<k)
			{
				i--;
				j++;
				if(board[i][j].getPiece() != null)
				{
					if(board[i][j].getPiece().getPieceColor() == this.pieceColor)
						
						return false;
					
					else if(board[i][j].getPiece().getPieceColor() != this.pieceColor)
					{
						String occupyCell = "";
						System.out.println("This cannot be done! You must take the piece at " + occupyCell);
						return false;
					}
			
				}
			}
		
		if(i > k && j > l)
			while (i>k)
			{
				i--;
				j--;
				
				if(board[i][j].getPiece() != null)
				{
				
					if(board[i][j].getPiece().getPieceColor() == this.pieceColor)
						
						return false;
					
					else if(board[i][j].getPiece().getPieceColor() != this.pieceColor)
					{
						System.out.println("This cannot be done! You must take the piece at " + ((char) j + 'a') + i);
						return false;
					}
				
				}
			}
		

		return true;
		
	}
}
