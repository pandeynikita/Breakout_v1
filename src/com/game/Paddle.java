package com.game;
import java.awt.Color;
import java.awt.Graphics2D;


public class Paddle implements Sprite {
	public static final int Y_POS = Canvas.HEIGHT - 30;
	public static final int P_WIDTH = 150;
	public static final int P_HEIGHT = 10;
	public static final Color PADDLE_COLOR = Color.black;
	private int xPos;
	private int lives;
	public static final int PADDLE_SPEED = 5;
	
	public Paddle(int xPos)
	{
        this.xPos = xPos;
        lives = 1;
    }
	
	public void setXPos(int xPos)
	{ 
        this.xPos = xPos;
        if(xPos < 0) //make sure its not negative value
        {
        	this.xPos = 0;
        }
        if(xPos > (Canvas.WIDTH - P_WIDTH)) //ensure there is enough space for paddle to be drawn without exceeding canvas boundary
        {
        	this.xPos = (Canvas.WIDTH - P_WIDTH);
        }
    }
	
	public int getXPos()
	{
		return xPos;
	}
	
	public void draw(Graphics2D g){
        g.setColor(PADDLE_COLOR);
        g.fillRect(xPos, Y_POS, P_WIDTH, P_HEIGHT);
        g.setColor(Color.gray);
        g.drawRect(xPos, Y_POS, P_WIDTH, P_HEIGHT);
    }
	
	  public int getLives(){ return lives; }
	    public void setLives(int lives){ this.lives = lives; }
	public boolean hitPaddle(Ball b)
	{
        if(b.getxPos() <= xPos + (P_WIDTH + 15))
        {
            if(b.getxPos() >= xPos - 10)
            {
                if((b.getyPos() + (Ball.DIAMETER - 1)) >= (Y_POS))
                {
                    if((b.getyPos() + (Ball.DIAMETER - 1)) <= (Y_POS + (P_HEIGHT - 5)))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
		
}
