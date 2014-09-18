
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import acm.util.*;
import acm.program.GraphicsProgram;
import acm.graphics.*;
import acm.io.*;

public class BreakOutPractice extends GraphicsProgram
{
	//reminder: doubles can be integers, but integers can't be doubles
	private static final int APP_WIDTH = 400;
	private static final int APP_HEIGHT = 600;
    
	//brick constants
	private static final double BRICK_ROWS= 10;
	private static final double BRICK_COLUMNS= 10;
	private static final double BRICK_WIDTH= 36;
	private static final double BRICK_HEIGHT = 8;
	private static final double BRICK_SPACE = 4;
    
	//paddle constants
	private static final double PADDLE_WIDTH = 60;
	private static final double PADDLE_HEIGHT = 10;
	private static final double PADDLE_Y_OFFSET = 30;
	private static final double PADDLE_VELOCITY = 50;
    
	//ball constants
	private static final double BALL_RADIUS= 10;
	private static final double BALL_WIDTH = BALL_RADIUS *2;
	private static final double BALL_HEIGHT = BALL_WIDTH;
    
	private GRect brick, paddle, labelBox;
	private GOval ball;
	private GObject collider;
	private GLabel start;
	private RandomGenerator rGen = new RandomGenerator();
	double vx = rGen.nextDouble(1, 3);
	double vy = rGen.nextDouble(1,3);
    
    
	int BRICK_Y_OFFSET = 70;
	int x = 0;
	int step = 1;
	int counter = 0;
	int turns = 3;
	double bricks = 100;//BRICK_ROWS * BRICK_COLUMNS
    
    
	//set color array
	private Color[] brickColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};
    
    
	public void run()
	{
    	addKeyListeners();
    	addMouseListeners();
    	gameSetup();
    	playGame();    
    	 

	}// end public void run()    
   
    
	public void playGame()
	{
    	moveBall();
    	movePaddle();
  	 
	}
    
	public void gameSetup()
	{
     	setSize(APP_WIDTH, APP_HEIGHT);
     	makePaddle();
     	makeBall();
     	makeBricks();
     	gameLabel();
	}
    
    
	/*This method creates all of the bricks. */
	public void makeBricks()
	{
    	//create 10 rows of stairs
    	for(int i=0; i< BRICK_ROWS; i++)
    	{   
           	 
        	//create 10 columns
        	for(int j= 0; j<BRICK_COLUMNS; j++)
        	{
            	brick = new GRect(x, BRICK_Y_OFFSET, BRICK_WIDTH, BRICK_HEIGHT);
            	x += BRICK_WIDTH + BRICK_SPACE;
            	brick.setFilled(true);
            	brick.setColor(brickColors[(i/2)]);
            	add(brick);
            	//Make every two bricks same color.
            	//i must divide by two so it loops it as 0,0, 1,1, etc.
        	}
  	 
        	x=0;
        	BRICK_Y_OFFSET += BRICK_HEIGHT + BRICK_SPACE;
       	 
    	}//end stair for loop
   	 
	}//end public void bricks()
    
	public void makePaddle() //paddle method
	{
    	paddle = new GRect(APP_WIDTH/2-PADDLE_Y_OFFSET, APP_HEIGHT-PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
    	paddle.setFilled(true);
    	add(paddle);
	}//end public void paddle()
    
	public void makeBall() //ball method
	{
    	ball = new GOval(APP_WIDTH/2 -BALL_RADIUS, APP_HEIGHT/2 - BALL_RADIUS, BALL_WIDTH, BALL_HEIGHT);
    	ball.setFilled(true);
    	add(ball);
   	 
	}//end public void ball()
    
	//moving paddle right and left
	public void keyPressed(KeyEvent e)
	{
    	switch(e.getKeyCode())
    	{
            	case KeyEvent.VK_RIGHT:
            	paddle.move(PADDLE_VELOCITY, 0);
            	break;
            	case KeyEvent.VK_LEFT:
            	paddle.move(-PADDLE_VELOCITY, 0);
            	break;   
    	}
   	 
    	movePaddle();
   	 
	}//end keyPressed method
    
 
    
	public void mouseMoved(MouseEvent e)
	{
    	double x=e.getX();
    	paddle.setLocation(x, APP_HEIGHT-PADDLE_Y_OFFSET);
    	movePaddle();
	}
    
    
	public void moveBall()
	{
   	 
    	start = new GLabel("Click to Start Turn", APP_WIDTH/2 - 60, APP_HEIGHT/2 - 40);
    	start.setFont("Helvetica-24");
    	add(start);
    	waitForClick();
    	remove(start);
   	 
    	while(bricks >= 0 )
    	{
            	if(ball.getX() + (BALL_RADIUS*2) >= APP_WIDTH || ball.getX() <=0)
            	{
                	vx=-vx;
            	}
            	else if(ball.getY()+(BALL_RADIUS*2) <=0)
            	{
                	vy=-vy;
            	}
            	else if(ball.getY() >= APP_HEIGHT)
            	{
               	 
                	turns--;
               	 
                	if(turns > 0 && bricks > 0)
                	{
                        	add(start);
                    	ball.setLocation(APP_WIDTH/2 -BALL_RADIUS, APP_HEIGHT/2 - BALL_RADIUS);
                    	waitForClick();
                    	remove(start);
                	}
                	else
                	{
                    	loseGame();
                    	break;
                	}
            	}
          	ball.move(vx, vy);
          	pause(4);
          	setCollision();
    	}//end while loop
	}
    
	public void movePaddle()
	{
    	if(paddle.getX() <= 0)
    	{
        	paddle.setLocation(0, APP_HEIGHT-PADDLE_Y_OFFSET);
    	}
    	else if(paddle.getX() >= APP_WIDTH-PADDLE_WIDTH)
    	{
        	paddle.setLocation(APP_WIDTH-(PADDLE_WIDTH), APP_HEIGHT-PADDLE_Y_OFFSET);
    	}    
	}
    
	public boolean checkCollision(double x, double y)
	{
    
    	collider = getElementAt(x, y);
    
    	if(collider == null) {
        	return false;
    	}
    	else if(collider == paddle)
    	{
        	vy = -Math.abs(vy);
        	return true;
    	}
    	else if(collider == labelBox)
    	{
        	vy=-vy;
        	return true;
    	}
    	else
    	{
        	remove(collider);
        	bricks--;
        	vy = -vy;
       	 
        	if(bricks == 0)
        	{
            	winGame();
        	}
       	 
        	return true;
       	 
    	}   	 
	}//end checkCollision()
    
	public void setCollision()
	{
    	if(checkCollision(ball.getX(), ball.getY()))return;
    	if(checkCollision(ball.getX()+BALL_WIDTH, ball.getY()))return;
    	if(checkCollision(ball.getX(), ball.getY()+BALL_WIDTH))return;
    	if(checkCollision(ball.getX()+BALL_WIDTH, ball.getY()+BALL_WIDTH))return;
	}
    
	public void gameLabel()
	{
    	labelBox = new GRect(0,0 , APP_WIDTH, 20);
    	labelBox.setColor(Color.BLACK);
    	add(labelBox);
   	 
    	GLabel lblturns = new GLabel("Turns ", 10, 15);
    	add(lblturns);
   	 
    	welcomeUser();
	}
   
    
	public void loseGame()
	{
    	removeAll();
    	GLabel lose = new GLabel("Game Over", 20, APP_HEIGHT/2);
    	lose.setFont("Garamond-72");
    	add(lose);
  	 
    	playAgain();
	}
    
	public void winGame()
	{
    	removeAll();
    	GLabel win = new GLabel("You Win!", 20, APP_HEIGHT/2);
    	win.setFont("Garamond-72");
    	win.setColor(Color.BLUE);
    	add(win);
   	 
    	playAgain();
	}
    
	public void playAgain()
	{
        	int playAgain;
        	boolean isYes;
        	playAgain = JOptionPane.showConfirmDialog(null, "Do you want to play again?");
        	isYes = (playAgain == JOptionPane.YES_OPTION);
       	 
        	if(isYes == true)
        	{
       		 removeAll();
       		 turns=3;
       		 gameSetup();
       		 playGame();    
     	}

	}
    
	public void welcomeUser()
	{
    	IODialog welcome = new IODialog();
    	String welcomeUser = welcome.readLine("Hello There, my  name's Nicole, what's yours?");
    	GLabel name = new GLabel("Welcome " + welcomeUser, 170, 15);
    	add(name);
	}
 
       	 
}






