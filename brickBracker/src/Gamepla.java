import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;

public class Gamepla extends JPanel implements ActionListener, KeyListener //Gameplay underline bcz all interface r not overridden
{
    private boolean play= false; //Initially game will not start automatically
    private int score= 0; // Initially score should be zero
    
    private int totalBricks= 21; //Total number of bricks
    
    private Timer timer;  // Timer for ball, how speed the ball will bounce
    private int delay= 8;  // delay time
    
    private int playerX= 310; //position of player in x direction
    
    private int ballposX= 120;
    private int ballposY= 350;
    private int ballXdir= -1;
    private int ballYdir= -2;
    
    private MapGenerator map;
    
    public Gamepla()  // constructor as when program starts it will be called first to execute all codes in it
    {
    	map= new MapGenerator(3, 7); // mapGenerator obj is created, row and col has passed
    	addKeyListener(this);
    	setFocusable(true);
    	setFocusTraversalKeysEnabled(false);
    	timer= new Timer(delay, this);
    	timer.start();
    	
    }

    public void paint(Graphics g) //Design of ball and bricks
    {
    	//background
    	g.setColor(Color.black);
    	g.fillRect(1,1,692,592 );
    	
    	//Drawing maps
    	map.draw((Graphics2D)g); //draw fn of mapGenerator is called and g is casted into 2D
    	
    	//borders
    	g.setColor(Color.yellow);
    	g.fillRect(0,0,3,592);
    	g.fillRect(0,0,692,3);
    	g.fillRect(690,0,3,592); //not making border for bottom 
    	
    	//the paddle
    	g.setColor(Color.green);
    	g.fillRect(playerX, 550, 100, 8 );
    	
    	
    	//score
    	g.setColor(Color.red);
    	g.setFont(new Font("serif", Font.BOLD, 40));
    	g.drawString(""+score, 590, 30);
    	
    	// the ball
    	g.setColor(Color.red);
    	g.fillOval(ballposX,ballposY, 20, 20); //ballposX and ballposY is used instead of any constant bcz we want to move our ball
    	
    	if(ballposY> 570)// Gameover
    	{
    		play= false;
    		ballXdir=0;
    		ballYdir=0;
    		g.setColor(Color.red);
    		g.setFont(new Font("serif", Font.BOLD, 45));
    		g.drawString("GAMEOVER  SCORES:"+score, 50, 300);
    		g.drawString("PRESS ENTER TO RESTART", 50, 260);
    		
    	}
    	
    	
    	g.dispose();
    }
    
    
    
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		
		if(e.getKeyCode()== KeyEvent.VK_RIGHT) //right key button pressed
		{
			if(playerX>=600)   // if the paddle reaches to the boundary
				playerX=600;	// keep paddle at the boundary if player pressed right buttom after reaching at boundary also
			else
				moveRight();
		}
		
		if(e.getKeyCode()== KeyEvent.VK_LEFT) // left key buttom is pressed
		{
			if(playerX<= 10) // if paddle reaches to the left most of the boundary
				playerX=10; // keep paddle at the boundary if pressed left buttom after reaching at left most of the boundary
			else
				moveLeft();
		}
		
		if(e.getKeyCode()== KeyEvent.VK_ENTER) // enter buttom is pressed to Restart the Game
		{
			if(!play)
			{
				play= true;
				ballXdir=-1;
				ballYdir=-2;
				ballposX= 120;
				ballposY= 350;
				totalBricks= 21;
				playerX=310;
				score=0;
				map =new MapGenerator(3,7);
				
				repaint();
			}
		}
		
		
	}
	
	public void moveRight()
	{
		play = true;
		playerX +=20;// 20 pixel move right if right buttom(moveRight fn is called) is pressed
	}
	
	
	public void moveLeft()
	{
		play = true;
		playerX -=20; // 20 pixel move left if left buttom(moveLeft fn is called) is pressed
	}

	
	
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	public void actionPerformed(ActionEvent arg0) // it does not need to call, it calls automatically
	{
	 timer.start();
	 if(play)
	 {
		 if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)))   // if ball position matches with paddle position
			 ballYdir= -ballYdir;// change the direction of ball with 180 degree
		 
		 
		A: for(int i=0; i<map.map.length; i++) // map is reference variable 2nd map is the array name of mapGenerator
		 {
			 for(int j=0; j<map.map[0].length; j++)
			 {
				 if(map.map[i][j] > 0)
				 {
				 int brickX= j * map.brickWidth + 80;
				 int brickY=i * map.brickHeight + 50;
				 int brickWidth= map.brickWidth;
				 int brickHeight= map.brickHeight;
				 
				Rectangle rect= new Rectangle(brickX, brickY, brickWidth, brickHeight); // dimension of wall of brick
				Rectangle ballrect=  new Rectangle(ballposX, ballposY, 20, 20); // dimension of ball, both r in variable form bcz (dynamic position of ball & any [psition of wall where ball strike)
				Rectangle brickrect= rect;
				if(ballrect.intersects(brickrect))  // striking of ball and wall
				{
					map.setBrickValue(0, i, j);
					totalBricks--;
					score +=5;
					
					if(ballposX +19 <= brickrect.x || ballposX +1 >= brickrect.x +brickrect.width) //ball strike the brick wall then ball rebounce back with 180 degree
						ballXdir =-ballXdir;
					else 
						ballYdir= -ballYdir;
					break A;
					
				}
				
			 }
				 }
		 }
			 
			 
		 ballposX +=ballXdir; //move ball in X direction always 
		 ballposY += ballYdir; // move ball in y direction always
		 
		 if(ballposX <0)      // ball collides with left side of d wall <-- direction
			 ballXdir = -ballXdir; // move ball in opposite direction
		 
		 if(ballposY <0)     // ball collides with upper wall 
			 ballYdir = -ballYdir;  // move ball in opposite direction
		 
		 if(ballposX> 670)      //  ball collides with the right side of the wall -->
			 ballXdir= -ballXdir; // move ball in opposite direction of x
		 
	 }
	 repaint();    // it will repaint whole graphics ball, background, paddle... we did it bcz when moveRight or moveLeft fn is called then whole graphics is needed to be same 
		
	}

}
