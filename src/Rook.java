import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/***********************************************************************************
*
*  Rook
*
*  @author       [Sam Virsik]
*  @version      [5/28/2021]
*  Description:       
*      
*      The Rook class has a goodMove method which allows it to make only legal moves 
*      for a Rook. It inherits the piece class and does not have any additional variables. 
*      
*      
*
***********************************************************************************/
public class Rook extends Piece{
	
	public Rook(Color c, int t)
	{
		super(c, "Rook", t);
	}
	public Rook(Piece p)
	{
		super(p);
	}
	public boolean goodMove(Board b, int rowStart, int colStart, int rowEnd, int colEnd, int team) //only coded for 1 team
	{
		//ISSUE: They can not see the piece in front of them, likely error with loop checking jump over
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
		if(rowStart == rowEnd)
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
		return false;
	}
	public void draw (Graphics g, int x, int y, int width, int height)
	{
		if(super.getTeam() ==1)
		{
			Image wp = new ImageIcon("white_rook_chess-removebg-preview.png").getImage();
			g.drawImage(wp, x, y, width, height, null);
		}
		else if(super.getTeam() ==2)
		{
			Image wp = new ImageIcon("black_chess_rook-removebg-preview.png").getImage();
			g.drawImage(wp, x, y, width, height, null);
		}
	}
}