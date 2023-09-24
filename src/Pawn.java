import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import javax.swing.*;

/***********************************************************************************
*
*  Pawn
*
*  @author       [Sam Virsik]
*  @version      [5/28/2021]
*  Description:       
*      
*      The pawn class has a goodMove method which allows it to make only legal moves 
*      for a pawn. It inherits the piece class and does not have any additional variables. 
*      
*      
*
***********************************************************************************/
public class Pawn extends Piece{

	
	public Pawn(Color c, int t)
	{
		super(c, "Pawn", t);
	}
	public Pawn(Piece p)
	{
		super(p);
	}
	
	//changes to do to every move class: check that the move to square is not already filled with a checker from the same team
	//make sure that you are not jumping over other pieces
	public boolean goodMove(Board b, int rowStart, int colStart, int rowEnd, int colEnd, int team) 
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
		if(super.hasMoved()==false)//if a pawn is moving for the first time, they can move 2 times
		{
			if(team == 1)
			{
				if(rowEnd == rowStart+2)
				{
					if(colEnd == colStart)
					{
						if(b.isFilled(rowEnd, colEnd))
						{
							return false;
						}
						if(b.isFilled(rowEnd-1, colEnd))//what i made
						{
							return false;
						}
						return true;
					}
				}
			}
			else if(team == 2)
			{
				if(rowEnd == rowStart-2)
				{
					if(colStart == colEnd)
					{
						if(b.isFilled(rowEnd, colEnd))
						{
							return false;
						}
						if(b.isFilled(rowEnd+1, colEnd))//what i made
						{
							return false;
						}
						return true;
					}
				}
			}
		}
		if(team == 1)
		{
			if(rowStart+1 == rowEnd)
			{
				if(colStart+1 == colEnd || colStart-1 == colEnd)
				{
					if(b.isFilled(rowEnd, colEnd))
					{
						return true;
					}
				}
				else if(colStart == colEnd)
				{
					if(b.isFilled(rowEnd, colEnd))
					{
						return false;
					}
					return true;
				}
			}
		}
		else if(team == 2)
		{
			if(rowStart-1 == rowEnd)//pawn always moves forward
			{
				if(colStart+1 == colEnd || colStart-1 == colEnd)
				{
					if(b.isFilled(rowEnd, colEnd))
					{
						return true;
					}
				}
				else if(colStart == colEnd)
				{
					if(b.isFilled(rowEnd, colEnd))
					{
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}
	public void draw (Graphics g, int x, int y, int width, int height)
	{
		if(super.getTeam() ==1)
		{
			Image wp = new ImageIcon("chess_pawn_white_right-removebg-preview.png").getImage();
			g.drawImage(wp, x, y, width, height, null);
		}
		else if(super.getTeam() ==2)
		{
			Image wp = new ImageIcon("black_chess_pawn-removebg-preview.png").getImage();
			g.drawImage(wp, x, y, width, height, null);
		}
	}
	
}
