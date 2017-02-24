package resources;

public class chessBoard
{
	
	public cell [] [] boardCells = new cell [8] [8];
	//private colors pieceColor;
	
	public chessBoard()
	{
		
		makeBoard(boardCells);

	}


	private void makeBoard(cell [][] boardCells)
	{

		char startChar = 'a';
		String cellDisp = "";

		for(int i = 0; i<8; i++)
		{
			
			for(int j=0; j<8; j++)
			{
				
				if((i%2==0 && j%2==0) || (i%2==1 && j%2==1))
					cellDisp = "## ";
				else
					cellDisp = "   ";
				
				char fileChar = (char) (startChar+j);
				
				boardCells[i][j] = new cell(i+1, fileChar , cellDisp);

			}
		}
		
		//adding pieces
		King W_X = new King(colors.WHITE);boardCells[0][3].setPiece(W_X);
		King B_X = new King(colors.BLACK);boardCells[7][4].setPiece(B_X);

		Queen W_Q = new Queen(colors.WHITE);boardCells[0][4].setPiece(W_Q);
		Queen B_Q = new Queen(colors.BLACK);boardCells[7][3].setPiece(B_Q);

		Rook W_R1 = new Rook(colors.WHITE);boardCells[0][0].setPiece(W_R1);
		Rook B_R1 = new Rook(colors.BLACK);boardCells[7][0].setPiece(B_R1);
		Rook W_R2 = new Rook(colors.WHITE);boardCells[0][7].setPiece(W_R2);
		Rook B_R2 = new Rook(colors.BLACK);boardCells[7][7].setPiece(B_R2);

		bishop W_B1 = new bishop(colors.WHITE);boardCells[0][2].setPiece(W_B1);
		bishop B_B1 = new bishop(colors.BLACK);boardCells[7][2].setPiece(B_B1);
		bishop W_B2 = new bishop(colors.WHITE);boardCells[0][5].setPiece(W_B2);
		bishop B_B2 = new bishop(colors.BLACK);boardCells[7][5].setPiece(B_B2);
		
		Knight W_K1 = new Knight(colors.WHITE);boardCells[0][1].setPiece(W_K1);
		Knight B_K1 = new Knight(colors.BLACK);boardCells[7][1].setPiece(B_K1);
		Knight W_K2 = new Knight(colors.WHITE);boardCells[0][6].setPiece(W_K2);
		Knight B_K2 = new Knight(colors.BLACK);boardCells[7][6].setPiece(B_K2);

		Pawn B_P1 = new Pawn(colors.BLACK);boardCells[6][0].setPiece(B_P1);
		Pawn B_P2 = new Pawn(colors.BLACK);boardCells[6][1].setPiece(B_P2);
		Pawn B_P3 = new Pawn(colors.BLACK);boardCells[6][2].setPiece(B_P3);
		Pawn B_P4 = new Pawn(colors.BLACK);boardCells[6][3].setPiece(B_P4);
		Pawn B_P5 = new Pawn(colors.BLACK);boardCells[6][4].setPiece(B_P5);
		Pawn B_P6 = new Pawn(colors.BLACK);boardCells[6][5].setPiece(B_P6);
		Pawn B_P7 = new Pawn(colors.BLACK);boardCells[6][6].setPiece(B_P7);
		Pawn B_P8 = new Pawn(colors.BLACK);boardCells[6][7].setPiece(B_P8);
		
		Pawn W_P1 = new Pawn(colors.WHITE);boardCells[1][0].setPiece(W_P1);
		Pawn W_P2 = new Pawn(colors.WHITE);boardCells[1][1].setPiece(W_P2);
		Pawn W_P3 = new Pawn(colors.WHITE);boardCells[1][2].setPiece(W_P3);
		Pawn W_P4 = new Pawn(colors.WHITE);boardCells[1][3].setPiece(W_P4);
		Pawn W_P5 = new Pawn(colors.WHITE);boardCells[1][4].setPiece(W_P5);
		Pawn W_P6 = new Pawn(colors.WHITE);boardCells[1][5].setPiece(W_P6);
		Pawn W_P7 = new Pawn(colors.WHITE);boardCells[1][6].setPiece(W_P7);
		Pawn W_P8 = new Pawn(colors.WHITE);boardCells[1][7].setPiece(W_P8);

	}
	
	public void movePiece(String special, int rankIn, char fileIn, int rankOut, char fileOut) throws InvalidMoveException {
		
		try
		{
			rankIn--;
			rankOut--;

			
			if (rankOut<1 && rankOut>8){
				throw new InvalidMoveException();
			}

			
			
			boardCells[rankIn][((int)fileIn-'a')].getPiece().move(boardCells, rankIn, fileIn, boardCells[rankOut][(int)fileOut-'a']);
			
			//white pawn promotion
			//System.out.println(boardCells[rankOut][((int)fileOut-'a')].getPiece().getCharacters()+","+rankOut);
			if (boardCells[rankOut][((int)fileOut-'a')].getPiece().getCharacters().equals("wp ") && rankOut==7)
			{
				//System.out.println("PAWN UP("+special+")");
							
				if (special.equals("Q") || special.equals("")){
					Queen W_Q = new Queen(colors.WHITE);boardCells[rankOut][((int)fileOut-'a')].setPiece(W_Q);}
				else if (special.equals("R")){
					Rook W_R = new Rook(colors.WHITE);boardCells[rankOut][((int)fileOut-'a')].setPiece(W_R);}
				else if (special.equals("B")){
					bishop W_B = new bishop(colors.WHITE);boardCells[rankOut][((int)fileOut-'a')].setPiece(W_B);}
				else if (special.equals("N")){
					Knight W_K = new Knight(colors.WHITE);boardCells[rankOut][((int)fileOut-'a')].setPiece(W_K);}
			}
			else if (boardCells[rankOut][((int)fileOut-'a')].getPiece().getCharacters().equals("bp ") && rankOut==0)
			{			
				if (special.equals("Q") || special.equals("")){
					Queen B_Q = new Queen(colors.BLACK);boardCells[rankOut][((int)fileOut-'a')].setPiece(B_Q);}
				else if (special.equals("R")){
					Rook B_R = new Rook(colors.BLACK);boardCells[rankOut][((int)fileOut-'a')].setPiece(B_R);}
				else if (special.equals("B")){
					bishop B_B = new bishop(colors.BLACK);boardCells[rankOut][((int)fileOut-'a')].setPiece(B_B);}
				else if (special.equals("N")){
					Knight B_K = new Knight(colors.BLACK);boardCells[rankOut][((int)fileOut-'a')].setPiece(B_K);}
			}
		}		
		
		catch (InvalidMoveException e)
		{
			
			throw new InvalidMoveException();
			
		}
	}
	
	public chessPiece getPiece(int rankIn, char fileIn){
		return boardCells[rankIn][((int)fileIn-'a')].getPiece();
	}
	
	public boolean testSpecialCases(){
		cell bK=null,wK=null, bA=null,wA=null;
		
		//find the two kings
		for (cell[] curLine:boardCells){
			for (cell curCell:curLine){
				if (curCell.getPiece()!=null){
					if (curCell.getPiece().getCharacters().equals("bK "))
						bK=curCell;
					else if (curCell.getPiece().getCharacters().equals("wK "))
						wK=curCell;
				}
			}
		}
		//System.out.println(boardCells[1][4].getPiece().checkValidMove(boardCells, boardCells[1][4].getRank(), boardCells[1][4].getFile(), wK));
		if (bK==null){
			System.out.println("White wins");
			return true;
		}
		if (wK==null){
			System.out.println("black wins");
			return true;
		}
		
		//check
		boolean Wcheck=false, Bcheck=false, Wcheckmate=false, Bcheckmate=false;
		
		for (cell[] curLine:boardCells){
			for (cell curCell:curLine){
				if (curCell.getPiece()!=null){
					if (curCell.getPiece().getPieceColor()!=bK.getPiece().getPieceColor() && curCell.getPiece().checkValidMove(boardCells, curCell.getRank(), curCell.getFile(), bK)){
						Bcheck=true;
						bA=curCell;
						System.out.println("check");//Black King in check"+curCell.getPiece().getPieceColor()+" "+curCell.getPiece().getCharacters());
						}
					else if (curCell.getPiece().getPieceColor()!=wK.getPiece().getPieceColor() && curCell.getPiece().checkValidMove(boardCells, curCell.getRank(), curCell.getFile(), wK)){
						Wcheck=true;
						wA=curCell;
						System.out.println("check");//White King in check"+curCell.getPiece().getPieceColor()+" "+curCell.getPiece().getCharacters());
						}
				}
			}
		}
			
		//check mate
		if (Wcheck){
			for (cell[] curLine:boardCells){
				for (cell curCell:curLine){
					if (curCell.getPiece()!=null){
						if (wK.getPiece().checkValidMove(boardCells, wK.getRank(), wK.getFile(), curCell))
							Wcheckmate=true;
					}
				}
			}
			
			for (cell[] curLine:boardCells){
				for (cell curCell:curLine){
					if (curCell.getPiece()!=null){
						if (curCell.getPiece().getPieceColor()==wK.getPiece().getPieceColor() && curCell.getPiece().checkValidMove(boardCells, curCell.getRank(), curCell.getFile(), wA))
							Wcheckmate=false;						
					}
				}
			}
		}
		if (Bcheck){
			for (cell[] curLine:boardCells){
				for (cell curCell:curLine){
					if (curCell.getPiece()!=null){
						if (bK.getPiece().checkValidMove(boardCells, bK.getRank(), bK.getFile(), curCell))
							Bcheckmate=true;
					}
				}
			}
			
			for (cell[] curLine:boardCells){
				for (cell curCell:curLine){
					if (curCell.getPiece()!=null){
						if (curCell.getPiece().getPieceColor()==bK.getPiece().getPieceColor() && curCell.getPiece().checkValidMove(boardCells, curCell.getRank(), curCell.getFile(), bA))
							Bcheckmate=false;						
					}
				}
			}
		}
		
		if (Wcheckmate){
			System.out.println(/*"White King in */"checkmate\nBlack wins");
			return true;
		}
		if (Bcheckmate){
			System.out.println(/*"Black King in */"checkmate\nWhite wins");
			return true;
		}
		
		return false;
	}
	
	public String toString()
	{
		String boardPrint = "\n";
		String bottomRow = " a  b  c  d  e  f  g  h";
		
		for(int i = 7; i >= 0; i--)
		{
			for(int j = 0; j<=7; j++)
			{
				if (boardCells[i][j].getPiece()!=null)
					boardPrint += boardCells[i][j].getPiece().getCharacters();
				else
					boardPrint += (boardCells[i][j].getDisplay());
			}

			boardPrint += " " + (i+1) + "\n";
		}
		boardPrint += bottomRow;
		
		return boardPrint;
		
					
	}

}