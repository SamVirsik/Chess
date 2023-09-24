import java.awt.*;

/***********************************************************************************
*
*  Square
*
*  @author       [Sam Virsik]
*  @version      [5/28/2021]
*  Description:       
*      
*      The Square class represents each location on the chessBoard, if there is 
*      or isn't a piece inside. 
*      
*      Square is a class which has the following information:
*      1. A boolean isFilled which represents if the Square contains a Piece
*      2. Its own location
*      
*      Square has a draw method which first draws the actual square, then draws the piece
*      if the square is filled
*      
*      Square also has a contains click method which is used in the Chess mousePressed method 
*      which returns whether the coordinates sent into the method correspond to the 
*      corrdinates of the Square. If so, the method returns true. 
*      
*      
*
***********************************************************************************/
public class Square {
private boolean isFilled; 
private Piece piece; 
private int row, col;
private Color color;
	public Square(int row, int col, Color color)
	{
		this.row = row; 
		this.col = col;
		this.color = color;
		isFilled = true;
	}
	public Square(Square b)
	{
		isFilled = b.isFilled();
		row = b.getRow();
		col = b.getCol();
		if(isFilled)
		{
			if(b.getPiece().getType().equals("King"))
			{
				piece = new King(b.getPiece());
			}
			else if(b.getPiece().getType().equals("Bishop"))
			{
				piece = new Bishop(b.getPiece());
			}
			else if(b.getPiece().getType().equals("Rook"))
			{
				piece = new Rook(b.getPiece());
			}
			else if(b.getPiece().getType().equals("Queen"))
			{
				piece = new Queen(b.getPiece());
			}
			else if(b.getPiece().getType().equals("Knight"))
			{
				piece = new Knight(b.getPiece());
			}
			else if(b.getPiece().getType().equals("Pawn"))
			{
				piece = new Pawn(b.getPiece());
			}
		}
	}
	public Square()
	{
		isFilled = false; 
	}
	public void removePiece()
	{
		piece = null;
		isFilled = false; 
	}
	public void addPiece(Piece p)
	{
		piece = null;
		piece = p;
		isFilled = true;
	}
	public boolean isFilled()
	{
		return !(piece == null);
	}
	public Piece getPiece()
	{
		return piece;
	}
	public Color getColor()
	{
		return color;
	}
	public int getRow()
	{
		return row;
	}
	public int getCol()
	{
		return col;
	}
	public void draw(Graphics g, int width, int height, int border, int titleBar, int rightBoarder)
	{
		int x = col*width + border;
		int y = row*height + border + titleBar;
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		g.setColor(color);
		g.fillRect(x, y, width, height);
		if(isFilled())
		{
			piece.draw(g, x, y, width, height);
		}
	}
	public void drawTwo(Graphics g, int width, int height, int border, int titleBar, int rightBoarder, Color color)
	{
		int x = col*width + border;
		int y = row*height + border + titleBar;
//		g.fillRect(0, 0, 600, 600); //test
		g.setColor(color);
//		g.fillRect(450, 450, width, height);
//		g.fillRect(50*x, 50*y, 600, 600); //test
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		
		if(isFilled())
		{
			piece.draw(g, x, y, width, height);
		}
	}
	public void drawThree(Graphics g, int width, int height, int border, int titleBar, int rightBorder, Color color)
	{
		int x = col*width + border;
		int y = row*height + border + titleBar;
		if(isFilled())
		{
			piece.draw(g, x, y, width, height);
		}
	}
	public boolean containsClick(int x, int y, int width, int height, int border, int titleBar, int r, int c)
	{
		if(x>c*width+border && x<(c+1)*width+border)
		{
			if(y>r*height+border+titleBar && y<(r+1)*width+border)
			{
				return true;
			}
		}
		return false;
	}
	public Square change(Square a, Square b)
	{
		isFilled = a.isFilled();
		row = a.getRow();
		col = a.getCol();
		if(isFilled)
		{
			if(a.getPiece().getType().equals("King"))
			{
				piece = new King(color.BLACK, a.getPiece().getTeam());
			}
			else if(a.getPiece().getType().equals("Bishop"))
			{
				piece = new Bishop(color.BLACK, a.getPiece().getTeam());
			}
			else if(a.getPiece().getType().equals("Rook"))
			{
				piece = new Rook(color.BLACK, a.getPiece().getTeam());
			}
			else if(a.getPiece().getType().equals("Queen"))
			{
				piece = new Queen(color.BLACK, a.getPiece().getTeam());
			}
			else if(a.getPiece().getType().equals("Knight"))
			{
				piece = new Knight(color.BLACK, a.getPiece().getTeam());
			}
			else if(a.getPiece().getType().equals("Pawn"))
			{
				piece = new Pawn(color.BLACK, a.getPiece().getTeam());
			}
			
			piece.change(a.getPiece());
		}
		return b;
	}
	public void changeFill(boolean f)
	{
		isFilled = f;
	}
	public void changeLocation(int r, int c)
	{
		row = r; 
		col = c;
	}
	public void makeQueen(Piece p)
	{
		piece = new Queen(p, false);
	}
}
