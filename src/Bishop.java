import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/***********************************************************************************
*
*  Bishop
*
*  @author       [Sam Virsik]
*  @version      [5/28/2021]
*  Description:       
*      
*      The Bishop class has a goodMove method which allows it to make only legal moves 
*      for a Bishop. It inherits the piece class and does not have any additional variables. 
*      
*      
*
***********************************************************************************/
public class Bishop extends Piece{
	
	public Bishop(Color c, int t)
	{
		super(c, "Bishop", t);
	}
	public Bishop(Piece p)
	{
		super(p);
	}
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
			if(rowStart != rowEnd && colStart != colEnd)
			{
				if((double)(rowStart-rowEnd)/(double)(colStart-colEnd) == -1.0)//using slope formula to find if it is moving diagonally
				{
//					if(true)
//					{
//						return true;
//					}
					if(rowEnd<rowStart && colEnd > colStart)
					{
						boolean isJump = false;
						for(int i = 1; i<rowStart-rowEnd; i++)//checking that it doesn't jump pieces
						{
							if(b.getSquare(rowStart-i, colStart+i).isFilled())
							{
								isJump = true;
							}
						}
						return !(isJump);
					}
					else if(rowEnd>rowStart && colEnd<colStart)
					{
						boolean isJump = false;
						for(int i = 1; i<rowEnd-rowStart; i++)//checking that it doesn't jump pieces
						{
							if(b.getSquare(rowStart+i, colStart-i).isFilled())
							{
								isJump = true;
							}
						}
						return !(isJump);
					}
				}
				else if((double)(rowStart-rowEnd)/(double)(colStart-colEnd) == 1.0)
				{
					if(rowEnd>rowStart && colEnd>colStart)
					{
						boolean isJump = false;
						for(int i = 1; i<rowEnd-rowStart; i++)//checking that it doesn't jump pieces
						{
							if(b.getSquare(rowStart+i, colStart+i).isFilled())
							{
								isJump = true;
							}
						}
						return !(isJump);
					}
					else if(rowEnd<rowStart && colEnd<colStart)
					{
						boolean isJump = false;
						for(int i = 1; i<rowStart-rowEnd; i++)//checking that it doesn't jump pieces
						{
							if(b.getSquare(rowStart-i, colStart-i).isFilled())
							{
								isJump = true;
							}
						}
						return !(isJump);
					}
				}
			}
		return false;
	}
	public void draw (Graphics g, int x, int y, int width, int height)
	{
		if(super.getTeam() ==1)
		{
			Image wp = new ImageIcon("Classic_Chess_Bishop_White_-removebg-preview.png").getImage();
			g.drawImage(wp, x, y, width, height, null);
		}
		else if(super.getTeam() ==2)
		{
			Image wp = new ImageIcon("black_chess_bishop-removebg-preview.png").getImage();
			g.drawImage(wp, x, y, width, height, null);
		}
	}
}