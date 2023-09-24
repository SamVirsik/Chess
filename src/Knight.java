import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/***********************************************************************************
*
*  Knight
*
*  @author       [Sam Virsik]
*  @version      [5/28/2021]
*  Description:       
*      
*      The Knight class has a goodMove method which allows it to make only legal moves 
*      for a Knight. It inherits the piece class and does not have any additional variables. 
*      
*      
*
***********************************************************************************/
public class Knight extends Piece{
	
	public Knight(Color c, int t)
	{
		super(c, "Knight", t);
	}
	public Knight(Piece p)
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
		if(rowStart == rowEnd+1 || rowStart == rowEnd-1)//scenario 1 for horse, moves 1 in row value and 2 in col value
		{
			if(colStart == colEnd+2 || colStart == colEnd -2)
			{
				return true;
			}
		}
		if(rowStart == rowEnd + 2 || rowStart == rowEnd-2)//scenario 2 for horse, moves 2 in row value and 1 in col value
		{
			if(colStart == colEnd + 1 || colStart == colEnd - 1)
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
			Image wp = new ImageIcon("chess_knight_white-removebg-preview.png").getImage();
			g.drawImage(wp, x, y, width, height, null);
		}
		else if(super.getTeam() ==2)
		{
			Image wp = new ImageIcon("black_chess_knight-removebg-preview.png").getImage();
			g.drawImage(wp, x, y, width, height, null);
		}
	}
}