import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/***********************************************************************************
*
*  Board
*
*  @author       [Sam Virsik]
*  @version      [5/28/2021]
*  Description:       
*      
*      The board class represents the entire chessBoard (there is only one board in every game)
*      
*      Board is a class which has the following information:
*      1. A 2d array of Squares which makes up the chessBoard
*      2. Information about how big it is
*      
*      The Board class has a method, setup() which initializes the Square objects in 
*      the 2d array of Squares according to how the game of chess starts. This information 
*      has to be hardcoded in for chess. 
*      
*      The Board class has another method, move, which takes in where the user wants to 
*      move a piece, and changes the 2d array of Squares according to what they choose. 
*      In this method the first section is made to change a pawn into a queen at the 
*      end of the Board. 
*
***********************************************************************************/
public class Board {
private Square [][] chessBoard; 
private int numRows, numCols; 
private Color firstColor, secondColor;

	public Board(int numRows, int numCols, Color a, Color b)
{
	chessBoard = new Square [numRows][numCols];
	this.numRows = numRows;
	this.numCols = numCols;
	firstColor = a;
	secondColor = b; 
	
	for(int row = 0; row<numRows; row++)
	{
		for (int col = 0; col < numCols; col++)
		{
			if (row % 2 == 0 && col % 2 == 0 || row % 2 != 0 && col % 2 != 0)
			{
				chessBoard[row][col] = new Square(row, col, firstColor);
			}
			else
			{
				chessBoard[row][col] = new Square(row, col, secondColor);
			}

		}
	}
}
	public Board(Board a)
	{
		chessBoard = new Square[a.getRow()][a.getCol()];
		for(int r = 0; r<chessBoard.length; r++)
		{
			for(int c = 0; c<chessBoard[0].length; c++)
			{
				chessBoard[r][c] = new Square(a.getSquare(r, c));
			}
		}
	}
	public void draw(Graphics g, int width, int height, int border, int titleBar, int rightBorder)
	{
		for (int row = 0; row < numRows; row++)
		{
			for (int col = 0; col < numCols; col++)
			{
				chessBoard [row][col].draw(g, width, height, border, titleBar, rightBorder);
			}
		}
	}
	public void setUp(Color c1, Color c2)//sets up the board with the preset values - has to be hardcoded for the specific game of chess
	{
		for(int row = 0; row<chessBoard.length; row++)
		{
			for(int col = 0; col<chessBoard[0].length; col++)
			{
				if(row == 1)//pawns
				{
					chessBoard[row][col].addPiece(new Pawn(c1, 1));
				}
				else if (row == 6)
				{
					chessBoard[row][col].addPiece(new Pawn(c2, 2));
				}
				else if(row == 0 && col == 4)//king team1
				{
					chessBoard[row][col].addPiece(new King(c1, 1));
				}
				else if(row == chessBoard.length-1 && col == 4)//king team2
				{
					chessBoard[row][col].addPiece(new King(c2, 2));
				}
				else if(row == 0 && col == 0 || row == 0 && col == chessBoard[0].length - 1)//rook 
				{
					chessBoard[row][col].addPiece(new Rook(c1, 1));
				}
				else if(row == chessBoard.length -1 && col == 0 || row == chessBoard.length -1 && col == chessBoard[0].length - 1)//rook
				{
					chessBoard[row][col].addPiece(new Rook(c2, 2));
				}
				else if(row == 0 && col == 1 || row == 0 && col == chessBoard[0].length - 2)//knight
				{
					chessBoard[row][col].addPiece(new Knight(c1, 1));
				}
				else if (row == chessBoard.length-1 && col == 1 || row == chessBoard.length - 1 && col == chessBoard[0].length - 2)//knight
				{
					chessBoard[row][col].addPiece(new Knight(c2, 2));
				}
				else if(row == 0 && col == 2 || row == 0 && col == 5)
				{
					chessBoard[row][col].addPiece(new Bishop(c1, 1));
				}
				else if(row == 7 && col == 2 || row == 7 && col == 5)
				{
					chessBoard[row][col].addPiece(new Bishop(c2, 2));
				}
				else if(row == 0 && col == 3)
				{
					chessBoard[row][col].addPiece(new Queen(c1, 1));
				}
				else if(row == 7 && col == 3)
				{
					chessBoard[row][col].addPiece(new Queen(c2, 2));
				}
			}
		}

	}
	public boolean isFilled(int row, int col)
	{
		return (chessBoard[row][col].isFilled());
	}
	public Square getSquare(int r, int c)
	{
		return chessBoard[r][c];
	}
	public void move(int startR, int startC, int endR, int endC)//eventually fix for castling
	{
		boolean hasChanged = false;
		if(chessBoard[startR][startC].getPiece()!=null)//used to change a pawn to a queen at the end of the board
		{
			if(chessBoard[startR][startC].getPiece().getType().equals("Pawn"))
			{
				if((chessBoard[startR][startC].getPiece().getTeam() == 1 && endR == 7) || (chessBoard[startR][startC].getPiece().getTeam() == 2 && endR == 0))
				{
					chessBoard[endR][endC].removePiece();
					chessBoard[endR][endC].makeQueen(chessBoard[startR][startC].getPiece());
					chessBoard[startR][startC].removePiece();
					hasChanged = true;
				}
			}
		}
		if(hasChanged == false)
		{
			chessBoard[endR][endC].addPiece(chessBoard[startR][startC].getPiece());
			chessBoard[startR][startC].removePiece();
		}

		

	}
	public Square[][] getBoard()
	{
		return chessBoard;
	}
	public Piece getPiece(int r, int c)
	{
		return chessBoard[r][c].getPiece();
	}
	public int getRow()
	{
		return chessBoard.length;
	}
	public int getCol()
	{
		return chessBoard[0].length;
	}
	public void printLocations()//testing method, used to print all the values of the board to check for errors in code (not used in final copy)
	{
		for(int r = 0; r<chessBoard.length; r++)
		{
			for(int c = 0; c<chessBoard[0].length; c++)
			{
				if(chessBoard[r][c].isFilled())
				{
					System.out.println("board at " + r + ", " + c + " is filled with a " + chessBoard[r][c].getPiece().getType());
				}
				else
				{
					System.out.println("board at " + r + ", " + c + " is not filled");
				}
			}
		}
	}
	public void showLocations(Graphics g, int row, int col, int width, int height, int border, int titleBar, int rightBorder, Board b, int ROWS, int COLS)
	{
		ArrayList<Integer> rows = new ArrayList<Integer>();
		ArrayList<Integer> cols = new ArrayList<Integer>();
		for (int c = 0; c< chessBoard.length; c++)
		{
			for (int r = 0; r< chessBoard[0].length; r++)
			{
				if(chessBoard[r][c]!=null)
				{
					if(!(r==row && c == col))
					{
						if(chessBoard[row][col].getPiece().goodMove(b, row, col, r, c, chessBoard[row][col].getPiece().getTeam()) == true)
						{
							if(chessBoard[row][col].getPiece().getTeam()==1)
							{
								if(inCheck(1, 2, row, r, col, c, b, ROWS, COLS) == false)
								{
									rows.add(r);
									cols.add(c);
								}
							}
							else if(chessBoard[row][col].getPiece().getTeam()==2)
							{
								if(inCheck(2, 1, row, r, col, c, b, ROWS, COLS) == false)
								{
									rows.add(r);
									cols.add(c);
								}
							}

						}
					}
				}
			}
		}
//		System.out.println(rows);
//		System.out.println(cols);
		if(rows.size()>0)
		{
					for(int i = 0; i<rows.size(); i++)
					{
//						System.out.println("Drawn at: " + rows.get(i) + ", " + cols.get(i));
						chessBoard [rows.get(i)][cols.get(i)].drawTwo(g, width, height, border, titleBar, rightBorder, Color.GREEN);
					}
		}
		else
		{
			g.setColor(Color.RED);
			g.fillRect(col*width +border, row*height + border+titleBar, width, height);
			chessBoard[row][col].drawThree(g, width, height, border, titleBar, rightBorder, Color.RED);
		}


	}
	public boolean inCheck(int checkKingTeam, int otherTeam, int rs, int re, int cs, int ce, Board bo, int ROWS, int COLS)
	{
		Board b = new Board(bo);
		//change to create a copy of the board
		int kingRow = -1, kingCol = -1;
		boolean inCheck = false;
	
		b.move(rs, cs, re, ce);


		
		//this loops through each square in the board to find the king of the team being checked
		for(int r = 0; r<ROWS; r++)
		{
			for(int c = 0; c<COLS; c++)
			{
				if(b.getSquare(r, c).isFilled())
				{
					if(b.getPiece(r, c).getTeam() == checkKingTeam && b.getPiece(r, c).getType().equals("King"))
					{
						kingRow = r;
						kingCol = c;
					}
				}
			}
		}

		if(kingCol != -1 && kingRow != -1)
		{
			for(int r = 0; r<ROWS; r++)
			{
				for(int c = 0; c<COLS; c++)
				{
					if(b.getSquare(r, c).isFilled())
					{
						if(b.getPiece(r, c).getTeam() == otherTeam)
						{
							if(b.getPiece(r, c).goodMove(b, r, c, kingRow, kingCol, otherTeam))
							{
								inCheck = true;
							}
						}
					}
				}
			}
		}

		return inCheck;
	}
}