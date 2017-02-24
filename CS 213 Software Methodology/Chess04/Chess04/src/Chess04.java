import resources.*;
import java.util.Scanner;

public class Chess04 {

	public static void main(String[] args) {
		
		colors checkColor = colors.WHITE;	
		
		chessBoard playArea = new chessBoard();
		Scanner Keyboard = new Scanner(System.in);
		
		/*System.out.println(playArea.boardCells[1][1].getPiece().getCharacters());
		//TODO use this to see a array view of all valid moves for a given piece
		for (cell[] curLine:playArea.boardCells){
			for (cell curCell:curLine){
				if(playArea.boardCells[1][1].getPiece().checkValidMove(playArea.boardCells[1][1].getRank(), playArea.boardCells[1][1].getFile(), curCell))
					System.out.print("T");
				else
					System.out.print("F");
			}System.out.println();
		}
		
		*/
		
		long counter=0;
		playGame(playArea, Keyboard, checkColor, counter);
	}

	public static void playGame(chessBoard playArea, Scanner Keyboard, colors checkColor, long counter)
	{

		try
		{
			
			String pieceOrigin = "";
			String pieceDest = "";
			String special = "";
			
			while(!pieceOrigin.equalsIgnoreCase("resign"))
			{
				System.out.println(playArea);

				//TODO uncomment this to test for check and checkmate
				
				if (playArea.testSpecialCases()){
					//System.out.print("Game ended");
					return;
				}
							
				//check to see whos turn it is
				if (counter%2 == 0){
					System.out.print("\nWhite's move: ");
					checkColor = colors.WHITE;	
				}
				else{	
					System.out.print("\nBlack's move: ");
					checkColor = colors.BLACK;	
				}
				
				pieceOrigin = Keyboard.next();
				//get the piece to move
				char[] pOrig = pieceOrigin.toCharArray();
				
				//test if the game is to resign
				if (pieceOrigin.equals("resign")){
					char[] chArray = checkColor.toString().toLowerCase().toCharArray();
					chArray[0] = Character.toUpperCase(chArray[0]);
					String resignColor="";
					
					for(int i = 0; i<chArray.length; i++)
						resignColor+=chArray[i];
					
					System.out.print(resignColor + " resigned\nDraw");
					return;
				}				
				
				if (playArea.getPiece((Character.getNumericValue(pOrig[1]) - 1), pOrig[0]).getPieceColor() != checkColor){
					throw new InvalidMoveException();}
				
				pieceDest = Keyboard.next();
				//get the spot to move the current piece
				if(pieceOrigin.equalsIgnoreCase(pieceDest))
					throw new InvalidMoveException();
				
				char[] pDest = pieceDest.toCharArray();
				
				//for pawn promotion
				if ((playArea.getPiece((Character.getNumericValue(pOrig[1]) - 1), pOrig[0]).getCharacters().equals("wp "))&&(pDest[1]=='8'||pDest[1]=='1'))
					special = Keyboard.next();
				
				//moves the piece
				playArea.movePiece(special, Character.getNumericValue(pOrig[1]), pOrig[0], Character.getNumericValue(pDest[1]), pDest[0]);
				
				counter++;
	
				/*//checkmate
				for(int i = 0; i<8; i++)
					for(int j=0; j<8; j++)
						if(playArea.getPiece(i,(char)(j+'a')) !=null)
							if(playArea.getPiece(i, (char) (j+'a')).getCharacters().contains("K"))
								if(playArea.getPiece(i, ((char) (j+'a'))).getPieceColor() != checkColor)
								{

									if(chessPiece.checkMateKing((King)playArea.getPiece(i, (char)(j+'a')), playArea.boardCells, playArea.boardCells[i][j]))
											throw new CheckMateException(playArea.getPiece(i, (char)(j+'a')).getPieceColor());
								}
				
				//check
				for(int i = 0; i<8; i++)
					for(int j=0; j<8; j++)
						if(playArea.getPiece(i,(char)(j+'a')) !=null)
							if(playArea.getPiece(i, (char) (j+'a')).getCharacters().contains("K"))
								if(playArea.getPiece(i, ((char) (j+'a'))).getPieceColor() != checkColor)
								{

									if(chessPiece.checkKing((King)playArea.getPiece(i, (char)(j+'a')), playArea.boardCells, playArea.boardCells[i][j]))	
										throw new CheckException(playArea.getPiece(i, (char)(j+'a')).getPieceColor());
									
								}
				 	*/
				

		
			}
			
		}
		
		catch (InvalidMoveException e)
		{
			
			System.out.println("\nIllegal move, try again\n");
			counter--;
			Keyboard.nextLine();
			playGame(playArea, Keyboard, checkColor, counter-1);
			
		}

		catch (IndexOutOfBoundsException e)
		{
			
			System.out.println("\nIllegal move, try again\n");
			counter--;
			playGame(playArea, Keyboard, checkColor, counter-1);
			
		}

		catch (NullPointerException e)
		{
			
			System.out.print("\n\nIllegal move, try again\n\n");
			counter--;
			playGame(playArea, Keyboard, checkColor, counter-1);
			
		}

		/*catch (CheckException e)
		{
			
			System.out.println(e.message);
			playGame(playArea, Keyboard, checkColor, counter);

		
		}
		
		catch (CheckMateException e)
		{
			
			System.out.println(e.message);
	
		
		}*/


	}

}
