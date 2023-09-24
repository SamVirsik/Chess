import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/***********************************************************************************
*
*  King
*
*  @author       [Sam Virsik]
*  @version      [5/28/2021]
*  Description:       
*      
*      The King class has a goodMove method which allows it to make only legal moves 
*      for a King. It inherits the piece class and does not have any additional variables. 
*      
*      
*
***********************************************************************************/
import javax.swing.ImageIcon;
public class King extends Piece{
	
	public King(Color c, int t)
	{
		super(c, "King", t);
	}
	public King(Piece p)
	{
		super(p);
	}
	public boolean goodMove(Board b, int rowStart, int colStart, int rowEnd, int colEnd, int team) //only coded for 1 team
	{
		if(rowStart == rowEnd && colStart == colEnd)
		{
			return false;
		}
		else if(b.getSquare(rowEnd, colEnd).isFilled())
		{
			if(b.getPiece(rowEnd, colEnd).getTeam() == b.getPiece(rowStart, colStart).getTeam())
			{
				return false;
			}
		}
		if(rowEnd == rowStart+1)
		{
			if(colEnd == colStart+1 || colEnd == colStart || colEnd == colStart-1)
			{
				return true;
			}
		}
		else if(rowEnd == rowStart-1)
		{
			if(colEnd == colStart+1 || colEnd == colStart || colEnd == colStart-1)
			{
				return true;
			}
		}
		else if(rowEnd == rowStart)
		{
			if(colEnd == colStart+1 || colEnd == colStart-1)
			{
				return true;
			}
		}
		return false;
	}
	public void draw (Graphics g, int x, int y, int width, int height)
	{
		if(super.getTeam() ==1)
		{
			Image wp = new ImageIcon("chess_white_king-removebg-preview.png").getImage();
			g.drawImage(wp, x, y, width, height, null);
		}
		else if(super.getTeam() ==2)
		{
			Image wp = new ImageIcon("black_chess_king-removebg-preview.png").getImage();
			g.drawImage(wp, x, y, width, height, null);
		}
	}
}
