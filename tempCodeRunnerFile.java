
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class connectFour extends Canvas implements MouseListener ,ActionListener{
    JLabel label = new JLabel();
	private String message,selectedColor;
	int rowNum;
	private int scorePlayer1, scorePlayer2;
    private final int StartX=400,startY=100,squareSize=100,r=6,c=7;
    private int [][]board=new int[r+2][c+2];//we create more than we need to avoid out of bound error
    private int playerTurn=1; 
    private boolean gameOver=false;
    private JRadioButton red,blue;
    ButtonGroup color;
    
    
    private JButton restartButton;
   
    public connectFour(JFrame frame) {
    	
        addMouseListener(this);
        //choosing players color
        //who choose first starts first
        red=new JRadioButton("RED");
        blue=new JRadioButton("BLUE");
        red.setBounds(200, 100, 100, 50); 
        red.setBackground(Color.red);
        blue.setBackground(Color.blue);
        red.addActionListener(this);
        blue.addActionListener(this);
        blue.setBounds(200, 160, 100, 50);        
        
        color=new ButtonGroup();
        color.add(blue);
        color.add(red);
        frame.add(blue);   
        frame.add(red);        

        // Create reset button
        restartButton = new JButton("Restart");
        restartButton.setBounds(1200, 650, 200, 60);
        restartButton.setBackground(Color.orange);
        restartButton.addActionListener(this);
        frame.add(restartButton);
        
        
    }
   

    @Override
    public void actionPerformed(ActionEvent e) {
        Object press = e.getSource();
if(press==restartButton) {
    	// Reset the game state
    	scorePlayer1 = 0;
    	scorePlayer2 = 0;
        gameOver = false;
        playerTurn = 1;
        message = "Start";

        // Clear the board
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                board[i+1][j+1] = 0;
            }
        }

        // Repaint the component
        repaint();}
		if(press==blue) {
			selectedColor="blue";
}
		else if(press==red) {
			selectedColor="red";

}
    }
   
    public void paint( Graphics g )
    {
        createGameBoard(g);
        putShape(g);
        changeMessageColors(g);

    }
    
    
    public void createGameBoard( Graphics g ){
        g.setColor(Color.white);
        g.fillRect(StartX,startY,c*squareSize,r*squareSize);
        g.setColor(Color.black);
        
        // Draw horizontal lines(rows)
        for (int i = 0; i <= r; i++) {
            int y = startY + i * squareSize;
            g.drawLine(StartX, y, StartX + c * squareSize, y);
        }

        // Draw vertical lines(columns)
        for (int j = 0; j <= c; j++) {
            int x = StartX + j * squareSize;
            g.drawLine(x, startY, x, startY + r * squareSize);
        }
      
    }
    
    //creating ovals to put on game board when needed
    public void putShape(Graphics g){
    	 g.setFont(new Font("Arial", Font.ITALIC, 32));
    	 
    	   for(int k=0;k<r;k++){
    		   for(int l=0;l<c;l++) {
    			int centerX = StartX + l * squareSize+squareSize / 2;
   	            int centerY = startY + k * squareSize+squareSize / 2;
   	            int ovalSize = squareSize - 10;
    			   g.setColor(Color.LIGHT_GRAY);
	                g.fillOval(centerX - ovalSize / 2, centerY - ovalSize / 2, ovalSize, ovalSize);
    		   }
    	   }
    	 
    	 for (int i = 0; i < r+1 ; i++) {
    	        for (int j = 0; j < c+1 ; j++) {
    	            int centerX = StartX + (j-1) * squareSize+squareSize / 2;
    	            int centerY = startY + (i-1) * squareSize+squareSize / 2;
    	            int ovalSize = squareSize - 10;  
    	            
    	            
if(selectedColor.equals("red")) {
						
			 if (board[i][j] == 1) {
    	                g.setColor(Color.red);
    	                g.fillOval(centerX - ovalSize / 2, centerY - ovalSize / 2, ovalSize, ovalSize);
    	   } else if (board[i][j] == 2) {
    	                g.setColor(Color.blue);
    	                g.fillOval(centerX - ovalSize / 2, centerY - ovalSize / 2, ovalSize, ovalSize);
    	            }
    	           
}//end if
else if(selectedColor.equals("blue")) {
			if (board[i][j] == 1) {
         g.setColor(Color.blue);
         g.fillOval(centerX - ovalSize / 2, centerY - ovalSize / 2, ovalSize, ovalSize);
     }		 else if (board[i][j] == 2) {
         g.setColor(Color.red);
         g.fillOval(centerX - ovalSize / 2, centerY - ovalSize / 2, ovalSize, ovalSize);
     }
    
}
    	        }
    	    }
    }

      public boolean isInGameBoard(int x,int y){
    	// if x between startX and endX and y between startY and endY
      return ( x>StartX && x<StartX+c*squareSize && y>0 && y<startY+r*squareSize);
    }
    //------------------------------------------
    public boolean isEmpty(int row, int col){
      return board[row][col]==0;
    }
    //-------------------------------------------
    public void togglePlayer(){
     
        //switch player	
      if(playerTurn==1) {
    
                          message="Next Turn:Player 2";
                          playerTurn=2;
                         }
      else{
                          message="Next Turn:Player 1";
                          playerTurn=1;
                          }
    }
    //create the message for different conditions
    public void changeMessageColors (Graphics g){
       g.setFont(new Font ("Arial",Font.BOLD, 24));
       if(selectedColor.equals("red")){ 
       if(playerTurn==1)
         g.setColor(Color.red);
        else
          g.setColor(Color.blue);
        
        g.drawString(message,StartX+(r/2-2)*squareSize,40);
       }
       else if(selectedColor.equals("blue")) {
    	   if(playerTurn==1)
    	         g.setColor(Color.blue);
    	        else
    	          g.setColor(Color.red);
    	        
    	        g.drawString(message,StartX+(r/2-2)*squareSize,40);
       }
        
    }
	
	@Override
	public void mouseClicked(MouseEvent evt) {
		
		 if (!gameOver) {
			 
		        if (isInGameBoard(evt.getX(), evt.getY())) {
		        	//first deciding clicked in which column
	                int col=(evt.getX()-StartX)/squareSize +1;//7 possibilities
	                //then find the empty row closest to bottom in that column
		            int emptyRow = getEmptyRowInColumn(col);
		         
		            //while all rows in that column are not full ,put the shape
		            if (emptyRow != -1) {
		                board[emptyRow][col] = playerTurn;
		                
		                // Checking for winning conditions and update scores 
		                int points=0;
		                 // Check for vertical patterns
		                // in vertical we check both for below and above for the cell
		                    if (emptyRow - 3 >= 0 && board[emptyRow - 1][col] == playerTurn && board[emptyRow - 2][col] == playerTurn && board[emptyRow - 3][col] == playerTurn) {
		                        points++;
		                    }
		                    if (emptyRow + 3 < board.length && board[emptyRow + 1][col] == playerTurn && board[emptyRow + 2][col] == playerTurn && board[emptyRow + 3][col] == playerTurn) {
		                        points++;
		                    }

		                    // Check for horizontal patterns
		                    //we check for right and left
		                    if (col - 3 >= 0 && board[emptyRow][col - 1] == playerTurn && board[emptyRow][col - 2] == playerTurn && board[emptyRow][col - 3] == playerTurn) {
		                        points++;
		                    }
		                    if (col + 3 < board[emptyRow].length && board[emptyRow][col + 1] == playerTurn && board[emptyRow][col + 2] == playerTurn && board[emptyRow][col + 3] == playerTurn) {
		                        points++;
		                    }

		                    // Check for diagonal patterns
		                    //there is four diagonal part needs to check
		                   
		                 // Diagonal (top-left to bottom-right)

		                    if (emptyRow - 3 >= 0 && col - 3 >= 0 && board[emptyRow - 1][col - 1] == playerTurn && board[emptyRow - 2][col - 2] == playerTurn && board[emptyRow - 3][col - 3] == playerTurn) {
		                        points++;
		                    }
		                    if (emptyRow + 3 < board.length && col + 3 < board[emptyRow].length && board[emptyRow + 1][col + 1] == playerTurn && board[emptyRow + 2][col + 2] == playerTurn && board[emptyRow + 3][col + 3] == playerTurn) {
		                        points++;
		                    }
		                 // Diagonal (top-right to bottom-left)
		                    if (emptyRow - 3 >= 0 && col + 3 < board[emptyRow].length && board[emptyRow - 1][col + 1] == playerTurn && board[emptyRow - 2][col + 2] == playerTurn && board[emptyRow - 3][col + 3] == playerTurn) {
		                        points++;
		                    }
		                    if (emptyRow + 3 < board.length && col - 3 >= 0 && board[emptyRow + 1][col - 1] == playerTurn && board[emptyRow + 2][col - 2] == playerTurn && board[emptyRow + 3][col - 3] == playerTurn) {
		                        points++;
		                    }

		                    // Check for middle cases
		                    
		                 // Check if there are three same pieces above and one piece below
		                    if (emptyRow - 2 >= 0 && emptyRow + 1 < board.length && board[emptyRow - 1][col] == playerTurn && board[emptyRow - 2][col] == playerTurn && board[emptyRow + 1][col] == playerTurn) {
		                        points++;
		                    }
		                 // Check if there are three same pieces to the left and one piece to the right
		                    if (col - 2 >= 0 && col + 1 < board[emptyRow].length && board[emptyRow][col - 1] == playerTurn && board[emptyRow][col - 2] == playerTurn && board[emptyRow][col + 1] == playerTurn) {
		                        points++;
		                    }
		                 // Check if there are three same pieces diagonally from top-left to bottom-right and one piece diagonally from bottom-right to top-left
		                    if (emptyRow - 2 >= 0 && col + 2 < board[emptyRow].length && board[emptyRow - 1][col + 1] == playerTurn && board[emptyRow - 2][col + 2] == playerTurn && board[emptyRow + 1][col - 1] == playerTurn) {
		                        points++;
		                    }
		                 // Check if there are three same pieces diagonally from top-right to bottom-left and one piece diagonally from bottom-left to top-right
		                    if (emptyRow - 2 >= 0 && col - 2 >= 0 && board[emptyRow - 1][col - 1] == playerTurn && board[emptyRow - 2][col - 2] == playerTurn && board[emptyRow + 1][col + 1] == playerTurn) {
		                        points++;
		                    }
		                    
		                    //give the point 
		                    if(playerTurn==1) {          
		                    	scorePlayer1+=points;}
		                    else {
		                    	scorePlayer2+=points;  }  
		                  
		                    if (points > 0) {
		                        // Player wins if at least one pattern(4) is found
		                        if (playerTurn == 1) {
		                            message = "Player 1 Wins!";
		                        } else {
		                            message = "Player 2 Wins!";
		                        }
		                        gameOver = true;
		                        //if no pattern is found switch the player and continue
		                    } else {
		                        togglePlayer();}
		                    
		              		            }
		        }
		        
		    }
         // Repaint the board to reflect the any graphical changes
		 repaint(); 
	}
	private int getEmptyRowInColumn(int col) {
	    for (int row = r; row > 0; row--) {
	        if (isEmpty(row, col)) {
	            return row;//starts from bottom and return first empty one closest to bottom
	        }
	    }
	    return -1;  // Column is full
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
	public static void main(String[] args) {
	        JFrame game = new JFrame("Connect Four");
	            game.setSize(1024,768);
	            game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            game.add(new connectFour(game));
	            game.setVisible(true);
	      }

}
