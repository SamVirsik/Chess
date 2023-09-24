import java.awt.*;

/***********************************************************************************
*
*  Piece
*
*  @author       [Sam Virsik]
*  @version      [5/28/2021]
*  Description:       
*      
*      The Piece class represents each piece on the board, although no piece in chess
*      will be just a piece, but rather an extention of the piece class. For example, a king
*      is not just a piece but also a king. 
*      
*      The Piece class is inherited by the Pawn, Rook, Knight, Bishop, Queen, and King classes
*      
*      Piece is a class which contains the following information: 
*      1. Where the piece is located on the board
*      2. Which team the piece is on (1=white, 2=black)
*      3. If the piece has moved (used to allow pawns to move 2 squares on their first move)
*      
*      
*      Each class which inherits Piece has the following information and methods:
*      1. A goodMove() method, which returns whether an attempted move is allowed according to
*      which type of piece it is
*      2. A draw method which draws an imported image of the chess piece. Credit for the images 
*      goes to: Hum 3d Chess Piece Models
*      https://hum3d.com/3d-models/classic-chess-queen-white/
*
***********************************************************************************/
public class Piece {

	private Color color;
	private Color borderColor;
	private int row, col;
	private String type;
	private int team;
	private boolean hasMoved = false;
	
	
	public Piece(Color c, String type, int t)
	{
		color = c;
		this.type = type;
		team = t;
	}
	public Piece(Color c, Color b)
	{
		color = c;
		borderColor = b;
	}
	public Piece(Piece p)
	{
		row = p.getRow();
		col = p.getCol();
		type = p.getType();
		team = p.getTeam();
		hasMoved = p.hasMoved();
	}
	public Piece(Piece p, boolean check)//used in making queen
	{
		if(p!=null)
		{
			row = p.getRow();
			col = p.getCol();
			type = "Queen";
			team = p.getTeam();
			hasMoved = true;
		}

	}
	public void changePosition(int r, int c)
	{
		row = r;
		col = c;
	}
	public int getRow()
	{
		return row;
	}
	public int getCol()
	{
		return col;
	}
	public void draw(Graphics g, int x, int y, int width, int height)
	{
		g.setColor(borderColor);
		g.drawOval(x, y, width, height);
		g.setColor(color);
		g.fillOval(x, y, width, height);
	}
	public String getType()
	{
		return type;
	}
	public boolean goodMove(Board b, int rowStart, int colStart, int rowEnd, int colEnd, int team)//always overrided in child class
	{
		return false;
	}
	public int getTeam()
	{
		return team;
	}
	public Piece changePiece(Piece a, Piece b)
	{
		return b;
	}
	public void change(Piece a)
	{
		type = a.getType();
		team = a.getTeam();
		hasMoved = a.hasMoved();
	}
	public void changeMove()
	{
		hasMoved = true;
	}
	public boolean hasMoved()
	{
		return hasMoved;
	}
	public void changeRow(int r)
	{
		row = r;
	}
	public void changeCol(int c)
	{
		col = c;
	}
	public void changeType(String t)
	{
		type = t;
	}
	public void changeTeam(int t)
	{
		team = t;
	}
	public void changeMove(boolean m)
	{
		hasMoved = m;
	}
}
