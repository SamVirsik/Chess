import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/***********************************************************************************
*
*  Queen
*
*  @author       [Sam Virsik]
*  @version      [5/28/2021]
*  Description:       
*      
*      The Queen class has a goodMove method which allows it to make only legal moves 
*      for a Queen. It inherits the piece class and does not have any additional variables. 
*      
*      
*
***********************************************************************************/
public class Queen extends Piece{
	
	public Queen(Color c, int t)
	{
		super(c, "Queen", t);
	}
	public Queen(Piece p)
	{
		super(p);
	}
	public Queen(Piece p, boolean t)
	{
		super(p, false);
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
		if(rowStart == rowEnd)//if it moves strait
		{
			if(colStart != colEnd)
			{
				boolean noJump = true;
				if(colStart>colEnd)
				{
					for(int c = colEnd+1; c<colStart; c++)//this loops through to make sure it isnt jumping over pieces
					{
						if(b.getSquare(rowStart, c).isFilled())
						{
							noJump = false;
						}
					}
				}
				else if(colStart<colEnd)
				{
					for(int c = colStart+1; c<colEnd; c++)
					{
						if(b.getSquare(rowStart, c).isFilled())
						{
							noJump = false;
						}
					}
				}
				if(noJump)
				{
					return true;
				}
				return false;
			}
		}
		else if(colStart == colEnd)
		{
			if(rowStart != rowEnd)
			{
				boolean noJump = true;
				if(rowStart>rowEnd)
				{
					for(int r = rowEnd+1; r<rowStart; r++)//this loops through to make sure it isnt jumping over pieces
					{
						if(b.getSquare(r, colStart).isFilled())
						{
							noJump = false;
						}
					}
				}
				else if(rowStart<rowEnd)
				{
					for(int r = rowStart+1; r<rowEnd; r++)
					{
						if(b.getSquare(r, colStart).isFilled())
						{
							noJump = false;
						}
					}
				}
				if(noJump)
				{
					return true;
				}
				return false;
			}
		}
		if(rowStart != rowEnd && colStart != colEnd)
		{
			if((double)(rowStart-rowEnd)/(double)(colStart-colEnd) == -1.0)//using slope formula to find if it is moving diagonally
			{
//				if(true)
//				{
//					return true;
//				}
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
			Image wp = new ImageIcon("white_chess_queen-removebg-preview.png").getImage();
			g.drawImage(wp, x, y, width, height, null);
		}
		else if(super.getTeam() ==2)
		{
			Image wp = new ImageIcon("black_chess_queen-removebg-preview.png").getImage();
			g.drawImage(wp, x, y, width, height, null);
		}
	}
}