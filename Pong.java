import java.awt.Color;

import acm.program.GraphicsProgram;
import acm.graphics.*;

public class Pong extends GraphicsProgram
{
	private static final int APP_WIDTH = 400;
	private static final int APP_HEIGHT = 600;
	private static final double BRICK_ROWS= 10;
	private static final double BRICK_COLUMNS= 10;
	private static final double BRICK_WIDTH= 36;
	private static final double BRICK_HEIGHT = 8;
	private static final double BRICK_SPACE = 4;
	private static final double PADDLE_WIDTH = 60;
	private static final double PADDLE_HEIGHT = 10;
	private static final double PADDLE_Y_OFFSET = 30;
	private static final double BALL_RADIUS= 10;
	private static final double BALL_WIDTH = BALL_RADIUS *2;
	private static final double BALL_HEIGHT = 20;
    
	int BRICK_Y_OFFSET = 70;
    
	int x = 0;
	
    
	public void run()
	{

    	setSize(APP_WIDTH, APP_HEIGHT);
   	 
    	makePaddle();
    	makeBall();
  		makeBricks();
    	
   	 
	}// end public void run()
    
	public void makeBricks()
	{
		//set color array
		Color[] brickColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};
   	 
    	//create 10 rows of stairs
    	for(int i=0; i<=BRICK_COLUMNS; i++)
    	{   
           	 
        	//create steps going across x axis
        	for(int j= 0; j<BRICK_ROWS; j++)
        	{
            	GRect rect = new GRect(x, BRICK_Y_OFFSET, BRICK_WIDTH, BRICK_HEIGHT);
            	x += BRICK_WIDTH + BRICK_SPACE;
				rect.setColor(brickColors[i/2]);
            	rect.setFilled(true);
            	add(rect);
        	}
       	 
        	x=0;
        	BRICK_Y_OFFSET += BRICK_HEIGHT + BRICK_SPACE;
    	}//end stair for loop
   	 
	}//end public void bricks()
    
	public void makePaddle()
	{
    	GRect paddle = new GRect(APP_WIDTH/2-PADDLE_Y_OFFSET, APP_HEIGHT-PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
    	paddle.setColor(Color.BLACK);
    	paddle.setFilled(true);
    	add(paddle);
	}//end public void paddle()
    
	public void makeBall()
	{
    	GOval ball = new GOval(APP_WIDTH/2 -BALL_RADIUS, APP_HEIGHT/2 - BALL_RADIUS, BALL_WIDTH, BALL_HEIGHT);
    	ball.setColor(Color.BLACK);
    	ball.setFilled(true);
    	add(ball);
   	 
	}//end public void ball()
    
}








