import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Image;

//to do:
//make a piece taken display window
//allow for touchscreen (flop the pieces orientation)
//make the game end - checkmate or stalemate

/***********************************************************************************
*
*  Chess
*
*  @author       [Sam Virsik]
*  @version      [5/28/2021]
*  Description:       
*      
*      Chess is a class which has the following private information:
*      1. Information about the size of the window
*      2. An instance of the Board class named 'board'
*      3. Information about whose turn it is, and who the winning team is (at the end of the game)
*      4. A boolean currentMove which signifies if the game is waiting for a piece to move, or where to move it
*      5. Information about if the game is over as well as information about if it is time to draw the intro
*      or the board
*      
*      Chess begins the game of chess by initializing the board by calling board.setup(), which sets up the 
*      board with the predefined information for any game of chess. The window is then painted, and the system 
*      waits for using input by mouse. 
*      
*      If the user clicks on the window, they are brought to the mousePressed() Method, which, using a boolean 
*      called currentMove, know whether they have picked a piece to move, if they are picking where to move it, or if the program 
*      is waiting for them to pick a piece. 
*      
*      In the mousePressed Method, once the user has picked a piece to move and where to move it, the program checks that 
*      the move they made is valid according to what type of piece they are moving, if it puts them in check (using inCheck()), 
*      and then moves the piece and repaints the board. 
*      
*      I also have methods which draw the introduction as well as one to draw the right border
*      
*      If I had more time to work on this program, I would have implemented the ability to castle, a move in chess where
*      the king and the rook 'swap' spots, and the king goes into the corner and the rook moves 1 piece toward the king. 
*      
*      One logic error in my code is that when a team is in check, they are not allowed to move, and so the other team
*      never has the chance to take their king. My code checks the end of the game by if a king is taken, so it can never
*      display the ending screen. To fix this, I would have created a potentialMoves method in each piece class
*      which returns all the potential moves made by all of the pieces of a team, and if none of them are goodMoves and 
*      the player is in chess, then the game ends. 
*
***********************************************************************************/

//bug: pawns jump over horses. . . fixed!!!
public class Chess extends JFrame implements MouseListener{

	private static final int WINDOW_WIDTH = 1000;//correct = 1000
	private static final int WINDOW_HEIGHT = 723;//correct = 723
	private static final int RIGHT_BORDER = 300;
	private static final int TITLE_BAR = 23;
	private static final int BORDER = 10;
	private static final int ROWS = 8;
	private static final int COLS = 8;
	
	private static Board board;
//	private static Color color1 = new Color (2, 150, 0);//
	private static Color color1 = Color.gray;//gray!!??!!?!?!?!!?!?!?!?!?!?!???!?!?!!?!?!???!?!?!?1
	private static Color color1a = new Color (204, 204, 204);//light gray?!?!?!!!?!?!???!?!?!?!!?!?!???!?!?!?
	private static Color color2 = Color.white;//?!?!?!!?!?!???!?!?!?!!!?!?!??!??!?!??!1
	private static Color color3 = Color.black;
	private static Color color4 = new Color(102, 51, 0);//this color is brown
	private static int HEIGHT = (WINDOW_HEIGHT-2*BORDER-TITLE_BAR)/ROWS;
	private static int WIDTH = ((WINDOW_WIDTH-2*BORDER)-RIGHT_BORDER)/COLS;
	
	private static boolean currentMove = false;//if true then they have picked the square to move, if false they have moved it or tried to move it, and we are waiting for them to pick again
	private static int startRow, startCol;
	private static int endRow, endCol;
	
	private static int ROWNUM, COLNUM;
	
	private static int turnTeamNumber = 1;//to check that that only the person whose "turn" it is can move
	
	private static boolean stillPlaying = true;
	private static int winningTeam;
	
	private boolean inInstructions = true;
	
	private static Color lightRed = new Color(255, 102, 102);
	private static Color green = new Color(0, 204, 0);
	private static Color yellow = new Color(255, 204, 0);
	private static Color orange = new Color(255, 102, 0);
	private static boolean hasMovedOnce = false; //used in makeDisplayBoard to know whether to display that the move is ok
	
	private static boolean shouldDrawCheck = false;//used to know if the makeDisplayBoard method should also write that you are in check
	private static boolean shouldDrawLegal = false;
	private static boolean shouldDrawTeam = false;
	
	
	
	public static void main(String[] args) 
	{
		board = new Board(ROWS, COLS, color1, color2);
		board.setUp(color3, color4);
		
		Chess c = new Chess();
		c.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setTitle("Sam - Chess");
		c.setVisible(true);
		c.addMouseListener(c);
		
	}
	public void paint(Graphics g)
	{
		if(inInstructions == true)//only the first time, then it always diverts to the else if
		{
			writeInstructions(g);
		}
		else if (inInstructions == false && currentMove == true)
		{
			board.showLocations(g, ROWNUM, COLNUM, WIDTH, HEIGHT, BORDER, TITLE_BAR, RIGHT_BORDER, board, ROWS, COLS);
		}
		else if(inInstructions == false && currentMove == false)
		{
			if(stillPlaying == false)
			{
				board.draw(g, WIDTH, HEIGHT, BORDER, TITLE_BAR, RIGHT_BORDER);
				g.setColor(Color.WHITE);
//				g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
				g.fillRect(WINDOW_WIDTH-RIGHT_BORDER-BORDER+1, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
				g.setColor(Color.black);
//				g.setColor(Color.BLACK);
//				g.setFont(new Font("Serif", Font.PLAIN, 30));	

				if(winningTeam==2)
				{
					g.setFont(new Font("Serif", Font.PLAIN, 35));
					g.drawString("Game Over!", WINDOW_WIDTH-RIGHT_BORDER+(RIGHT_BORDER/4), (WINDOW_HEIGHT/6)*3);
					g.setFont(new Font("Serif", Font.PLAIN, 20));
					g.drawString("Black Wins!", WINDOW_WIDTH-RIGHT_BORDER+(RIGHT_BORDER/4), (WINDOW_HEIGHT/2) + WINDOW_HEIGHT/25);
//					g.drawString("Congratulations Black Team!!!", WINDOW_WIDTH/4, WINDOW_HEIGHT/2);
//					g.drawString("You win!", WINDOW_WIDTH/4, WINDOW_HEIGHT/2+WINDOW_HEIGHT/8);
//					
//					Image wp = new ImageIcon("black_chess_king-removebg-preview.png").getImage();
//					g.drawImage(wp, WINDOW_WIDTH/2-WINDOW_WIDTH/8, WINDOW_HEIGHT/5, WINDOW_WIDTH/4, (WINDOW_HEIGHT-23)/4, null);
				}
				else if(winningTeam==1)
				{
					g.setFont(new Font("Serif", Font.PLAIN, 35));
					g.drawString("Game Over!", WINDOW_WIDTH-RIGHT_BORDER+(RIGHT_BORDER/4), (WINDOW_HEIGHT/6)*3);
					g.setFont(new Font("Serif", Font.PLAIN, 20));
					g.drawString("White Wins!", WINDOW_WIDTH-RIGHT_BORDER+(RIGHT_BORDER/4), (WINDOW_HEIGHT/2) + WINDOW_HEIGHT/25);
//					g.drawString("Congratulations White Team!!!", WINDOW_WIDTH/4, WINDOW_HEIGHT/2);
//					g.drawString("You win!", WINDOW_WIDTH/4, WINDOW_HEIGHT/2+WINDOW_HEIGHT/8);
//					
//					Image wp = new ImageIcon("chess_white_king-removebg-preview.png").getImage();
//					g.drawImage(wp, WINDOW_WIDTH/2-WINDOW_WIDTH/8, WINDOW_HEIGHT/5, WINDOW_WIDTH/4, (WINDOW_HEIGHT-23)/4, null);
				}

			}
			else
			{
				board.draw(g, WIDTH, HEIGHT, BORDER, TITLE_BAR, RIGHT_BORDER);

					makeDisplayBoard(g);
					shouldDrawCheck = false;
			}
		}


	}
	public boolean inCheck(int checkKingTeam, int otherTeam, int rs, int re, int cs, int ce, Board bo)
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
    public void mousePressed(MouseEvent e)
    {
    	if(inInstructions == true)
    	{
    		if(e.getX()>WINDOW_WIDTH/2-200 && e.getX()<WINDOW_WIDTH/2+200)
    		{
    			if(e.getY()>WINDOW_HEIGHT-250 && e.getY()<WINDOW_HEIGHT-200)
    			{
    	    		repaint();
    	    		inInstructions = false;
    			}
    		}

    	}
    	else
    	{
        	if(stillPlaying)
        	{
            	int x = e.getX();
            	int y = e.getY();
            	
            	for(int r = 0; r<ROWS; r++)
            	{
            		for(int c = 0; c<COLS; c++)
            		{
            	    	if(board.getSquare(r, c).containsClick(x, y, WIDTH, HEIGHT, BORDER, TITLE_BAR, r, c))
            	    	{
            	    		if(currentMove == false)//if they have not picked the first piece to move yet
            	    		{
                	    		if(board.isFilled(r, c))
                	    		{
                	    			if(board.getPiece(r, c).getTeam() == turnTeamNumber)
                	    			{
                    	    			startRow = r;
                    	    			startCol = c;
                    	    			ROWNUM = r;
                    	    			COLNUM = c;
                    	    			repaint();
                    	    			currentMove = true;
                    	    			
                	    			}
                	    			else
                	    			{
        	    						shouldDrawTeam = true;
        	    						repaint();
                	    			}
                	    		}
            	    		}
            	    		else if(currentMove == true)//if they have picked the first piece to move
            	    		{
            	    				endRow = r;
            	    				endCol = c;
            	    				if(board.getPiece(startRow, startCol).getTeam() == 1)//used in the inCheck method
            	    				{
                	    				if(inCheck(1, 2, startRow, endRow, startCol, endCol, board) == false)//doesn't put you in check - change for right thing
                	    				{
                	    					if(board.getPiece(startRow, startCol).goodMove(board, startRow, startCol, endRow, endCol, board.getPiece(startRow, startCol).getTeam()))
                	    					{
                	    						board.move(startRow, startCol, endRow, endCol);
                	    						hasMovedOnce = true;
                	    						board.getPiece(endRow, endCol).changeMove();
                	    						changeTurn();
                    	    					repaint();
                    	    					//check for checkmate here
                            	    			boolean checkmate = checkForCheckmate(1, 2);
                            	    			if(checkmate)
                            	    			{
//                            	    				System.out.println("white wins! yayayayayay");
                            	    				stillPlaying =false;
                            	    				winningTeam=1;
                            	    			}
                	    					}
                	    					else 
                	    						//if (startRow!=endRow && startCol != endCol)//to fix just make else
                	    					{
                	    						shouldDrawLegal = true;
                	    						repaint();
                	    					}
                	    				}
                	    				else
                	    				{
                	    					shouldDrawCheck = true;
                	    					repaint();
                	    				}
            	    				}
            	    				else if(board.getPiece(startRow, startCol).getTeam() == 2)
            	    				{
                	    				if(inCheck(2, 1, startRow, endRow, startCol, endCol, board) == false)//doesn't put you in check - change for right thing
                	    				{
                	    					if(board.getPiece(startRow, startCol).goodMove(board, startRow, startCol, endRow, endCol, board.getPiece(startRow, startCol).getTeam()))
                	    					{
                	    						board.move(startRow, startCol, endRow, endCol);
                	    						hasMovedOnce = true;
                	    						board.getPiece(endRow, endCol).changeMove();
                	    						changeTurn();
                    	    					repaint();
                    	    					//check for checkmate here
                            	    			boolean checkmate = checkForCheckmate(2, 1);
                            	    			if(checkmate)
                            	    			{
//                            	    				System.out.println("black wins! yayayayayay");
                            	    				stillPlaying=false;
                            	    				winningTeam=2;
                            	    			}
                	    					}
                	    					else 
                	    						//if (startRow!=endRow && startCol != endCol)//to fix just make else
                	    					{
                	    						shouldDrawLegal = true;
                	    						repaint();
                	    					}
                	    				}
                	    				else
                	    				{
                	    					shouldDrawCheck = true;
                	    					repaint();
                	    				}
                
                	    				
                	  
            	    				}
                	    			currentMove = false;
            	    		}
            	    	}
            			
            		}
            	}
        	}
    	}

//    	isGameOver();
    }
    
    public void mouseReleased(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    
    public void changeTurn()
    {
    	if(turnTeamNumber == 1)
    	{
    		turnTeamNumber = 2;
    	}
    	else if(turnTeamNumber == 2)
    	{
    		turnTeamNumber = 1;
    	}
    }
    public void isGameOver()
    {
    	boolean oneCanMove = false;
    	boolean twoCanMove = false;
    	
    	boolean oneInCheck = false;
    	boolean twoInCheck = false;
    	
    	boolean team1 = false;
    	boolean team2 = false;
    	

    	
//    	for(int r = 0; r<ROWS; r++)
//    	{
//    		for(int c = 0; c<COLS; c++)
//    		{
//    			if(board.getPiece(r, c)!= null)
//    			{
//    		    	for(int row = 0; row<ROWS; row++)
//    		    	{
//    		    		for(int col = 0; col<COLS; col++)
//    		    		{
//    		    	    	Board b = new Board(board);
//    		    	    	
//    		    			if(board.getPiece(r, c).goodMove(board, r, c, row, col, board.getPiece(r, c).getTeam()))
//    		    					{
//    		    				if(inCheck(board.getPiece(r, c).getTeam(), getOtherTeam(board.getPiece(r, c).getTeam()), r, row, c, col, b)==false)
//    		    				{
//        		    				if(board.getPiece(r, c).getTeam() == 1)
//        		    				{
//        		    					oneCanMove = true;
//        		    				}
//        		    				else if(board.getPiece(r, c).getTeam() == 2)
//        		    				{
//        		    					twoCanMove = true;
//        		    				}
//    		    				}
//    		    					}
//    		    		}
//    		    	}
//    		    	
//    		   	}
//    		}
//    	}
    	
    	for(int r=0; r<ROWS; r++)
    	{
    		for(int c=0; c<COLS; c++)
    		{
    			if(board.getPiece(r, c)!= null)
    			{
    				if(board.getPiece(r, c).getType().equals("King"))
    				{
    					if(board.getPiece(r, c).getTeam() == 1)
    					{
    						team1 = true;
    					}
    					else if(board.getPiece(r, c).getTeam() == 2)
    					{
    						team2 = true;
    					}
    				}
    			}
    		}
    	}
    	
//    	if(oneCanMove == false && turnTeamNumber == 1)
//    	{
//    		stillPlaying=false;
//    		winningTeam = 2;
//    	}
//    	else if(twoCanMove && turnTeamNumber == 2)
//    	{
//    		stillPlaying=false;
//    		winningTeam = 1;
//    	}
    	if(team1==false)
    	{
    		stillPlaying=false;
    		winningTeam = 2;
    	}
    	if(team2==false) 
    	{
    		stillPlaying=false;
    		winningTeam = 1;
    	}
    }
    	public void makeDisplayBoard(Graphics g)//Draws whose turn it is as well as the Title
    	{
    		g.setColor(Color.WHITE);
    		g.fillRect(WINDOW_WIDTH-RIGHT_BORDER-BORDER+1, 0, WINDOW_WIDTH, WINDOW_HEIGHT);//makes the display clean again, the reason I say +1 is becauses if I make a line over an already drawn line, eclipse removes the first line
    		g.setColor(Color.BLACK);
    		g.drawLine(WINDOW_WIDTH-RIGHT_BORDER-BORDER+1, WINDOW_HEIGHT/3, WINDOW_WIDTH, WINDOW_HEIGHT/3);
    		g.drawLine(WINDOW_WIDTH-RIGHT_BORDER-BORDER+1, (WINDOW_HEIGHT/3)*2, WINDOW_WIDTH, (WINDOW_HEIGHT/3)*2);
			g.setFont(new Font("Serif", Font.PLAIN, 30));	
			
			if(turnTeamNumber == 1)
			{
	    		g.drawString("White's turn", WINDOW_WIDTH-RIGHT_BORDER+(RIGHT_BORDER/5), WINDOW_HEIGHT/5);
			}
			else if(turnTeamNumber == 2)
			{
	    		g.drawString("Black's turn", WINDOW_WIDTH-RIGHT_BORDER+(RIGHT_BORDER/5), WINDOW_HEIGHT/5);
			}
			//writing title
			g.setFont(new Font("Serif", Font.PLAIN, 50));
			g.drawString("Chess", WINDOW_WIDTH-RIGHT_BORDER+(RIGHT_BORDER/4), (WINDOW_HEIGHT/6)*3);
			g.setFont(new Font("Serif", Font.PLAIN, 20));
			g.drawString("By Sam Virsik", WINDOW_WIDTH-RIGHT_BORDER+(RIGHT_BORDER/4), (WINDOW_HEIGHT/2) + WINDOW_HEIGHT/25);
			
			g.setFont(new Font("Serif", Font.PLAIN, 25));
			if(shouldDrawCheck)
			{
					g.setColor(lightRed);
					g.fillRect(WINDOW_WIDTH-RIGHT_BORDER-BORDER+1, (WINDOW_HEIGHT/3)*2, WINDOW_WIDTH, WINDOW_HEIGHT);
					g.setColor(Color.BLACK);
		    		g.drawString("This puts you in check!", WINDOW_WIDTH-RIGHT_BORDER+(RIGHT_BORDER/8), (WINDOW_HEIGHT/6)*5);

			}
			else if(shouldDrawCheck == false && hasMovedOnce == true)
			{
				g.setColor(green);
				g.fillRect(WINDOW_WIDTH-RIGHT_BORDER-BORDER+1, (WINDOW_HEIGHT/3)*2, WINDOW_WIDTH, WINDOW_HEIGHT);
				g.setColor(Color.BLACK);
	    		g.drawString("This move is valid", WINDOW_WIDTH-RIGHT_BORDER+(RIGHT_BORDER/8), (WINDOW_HEIGHT/6)*5);
			}
			if(shouldDrawTeam)
			{
				g.setColor(yellow);
				g.fillRect(WINDOW_WIDTH-RIGHT_BORDER-BORDER+1, (WINDOW_HEIGHT/3)*2, WINDOW_WIDTH, WINDOW_HEIGHT);
				g.setColor(Color.BLACK);
	    		g.drawString("Wrong team!", WINDOW_WIDTH-RIGHT_BORDER+(RIGHT_BORDER/8 + 40), (WINDOW_HEIGHT/6)*5);
				shouldDrawTeam = false;
			}
			if(shouldDrawLegal)
			{
				g.setColor(orange);
				g.fillRect(WINDOW_WIDTH-RIGHT_BORDER-BORDER+1, (WINDOW_HEIGHT/3)*2, WINDOW_WIDTH, WINDOW_HEIGHT);
				g.setColor(Color.BLACK);
	    		g.drawString("Invalid move!", WINDOW_WIDTH-RIGHT_BORDER+(RIGHT_BORDER/8 + 40), (WINDOW_HEIGHT/6)*5);
				shouldDrawLegal = false;
			}

    	}
    	public void writeInstructions(Graphics g)
    	{
    		g.setColor(Color.WHITE);
			g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    		g.setColor(Color.BLACK);
			g.setFont(new Font("Serif", Font.PLAIN, 30));
    		g.drawString("This is the game of chess. In this 2 player game, you play against an opponent", 30, 60);
    		g.drawString("trying to put them in checkmate. The usual rules of chess apply, and white team ", 10, 100);
    		g.drawString("always starts. ", 10, 140);
    		g.drawString("To move around the board, left click a piece to move, then left click where you ", 30, 200);
    		g.drawString("would like to move it. If the piece does not move, it is because the move does ", 10, 240);
    		g.drawString("not follow the rules of chess. If this happens, please choose a new move to make. ", 10, 280);
    		g.drawString("When you are ready to begin, please click the start button.", WINDOW_WIDTH/2-350, 400);
			g.setFont(new Font("Serif", Font.PLAIN, 50));
    		g.drawString("Good luck!", WINDOW_WIDTH/2-115, 600);
    		
    		//button
			g.setFont(new Font("Serif", Font.PLAIN, 40));
			g.setColor(Color.GREEN);
			g.fillRect(WINDOW_WIDTH/2-200, WINDOW_HEIGHT-250, 400, 50);
			g.setColor(Color.BLACK);
    		g.drawRect(WINDOW_WIDTH/2-200, WINDOW_HEIGHT-250, 400, 50);
    		g.drawString("Start", WINDOW_WIDTH/2-45, WINDOW_HEIGHT-250+40);
    	}
    	public int getOtherTeam(int i)
    	{
    		if(i == 1)
    		{
    			return 2;
    		}
    		return 1;
    	}
    	public boolean checkForCheckmate(int teamAttacking, int teamInDanger)
    	{
    		int kingX = -1;
    		int kingY = -1;
    		boolean inCheck = false;
    		//need to check if the other team is attacking king first
    		//steps:
    		//find defending king
    		//check other team to see if anything can move to the king
    		//check to see if the team can move at all
    		for(int x=0; x<ROWS; x++)
    		{
    			for(int y=0; y<COLS; y++)
    			{
    				if(board.getPiece(x, y)!=null)
    				{
    					if(board.getPiece(x, y).getTeam()==teamInDanger && board.getPiece(x, y).getType().equals("King"))
    					{
    						kingX = x;
    						kingY = y;
    					}
    				}
    			}
    		}
    		for(int t=0; t<ROWS; t++)
    		{
    			for(int r=0; r<COLS; r++)
    			{
    				if(board.getPiece(t, r)!= null)//maybe unneccessary
    				{
        				if(board.getPiece(t, r).getTeam()==teamAttacking)
        				{
        					if(board.getPiece(t, r).goodMove(board, t, r, kingX, kingY, board.getPiece(t, r).getTeam()))
        					{
        						inCheck = true;
        					}
        				}
    				}
    			}
    		}
    		if(inCheck == false)
    		{
    			return false;//could be stalemate - need another method
    		}
    		else
    		{
    			boolean canEscape = hasAnyMove(teamAttacking, teamInDanger, kingX, kingY);
    			if(canEscape)
    			{
    				return false;
    			}
    			else
    			{
    				return true;
    			}
    		}
//    		if(hasAGoodMove == false)
//    		{
//    			return true;
//    		}
    	}
    	public boolean hasAnyMove(int teamAttacking, int teamInDanger, int defenseKingX, int defenseKingY)
    	{
    		boolean canEscape = false;
    		for(int x=0; x<ROWS; x++)
    		{
    			for(int y=0; y<COLS; y++)//loops through the board
    			{
    				if(board.getPiece(x, y)!= null)//maybe unneccessary
    				{
        				if(board.getPiece(x, y).getTeam()==teamInDanger)//finds a piece 
        				{
        					for(int z=0; z<ROWS; z++)
        					{
        						for (int c = 0; c<COLS; c++)
        						{
        							if (board.getPiece(x, y).goodMove(board, x, y, z, c, board.getPiece(x, y).getTeam()))
        							{
        								Board bo = new Board(board);
        								bo.move(x, y, z, c);
        								boolean workedSoFar = true;
        								for(int l=0; l<ROWS; l++)
        								{
        									for(int g=0; g<COLS; g++)
        									{
        										if(bo.getPiece(l, g)!= null)
        										{
            										if(bo.getPiece(l, g).getTeam()==teamAttacking)
            										{
            											if(bo.getPiece(l, g).goodMove(bo, l, g, defenseKingX, defenseKingY, bo.getPiece(l, g).getTeam()))
            											{
            												workedSoFar = false;
            											}
            										}
        										}
        									}
        								}
        								if(workedSoFar)
        								{
        									canEscape=true;
        								}

        							}
        						}
        					}
        				}
    				}
    			}
    		}
    		return canEscape;
    	}
}