package com.game;

import java.awt.Color;
import java.awt.Graphics;

public class BrickMain{
	public static final int BRICK_WIDTH = 60;
	public static final int BRICK_HEIGHT = 20;
	public static final Color BRICK_COLOR = Color.GREEN;
	private int xPos, yPos; 
	private int life;

	public BrickMain(int xPos, int yPos,int life){
		this.xPos = xPos;
		this.yPos = yPos;
		this.life = life;
	}

	public int getxPos(){  return xPos;    }
	public int getY(){  return yPos;    }

	public boolean hitBy(Ball b){
		//first check if it hits from the bottom or top
		if(b.getxPos() <= (xPos + BRICK_WIDTH) && b.getxPos() >= xPos){
			//hit bottom
			if(b.getyPos() <= (yPos + BRICK_HEIGHT) && b.getyPos() >= (yPos + (BRICK_HEIGHT / 2))){
				b.setySpeed(b.getySpeed() * -1);
				return true;
			}
			//hit top
			else if(b.getyPos() >= (yPos - Ball.DIAMETER) && b.getyPos() < (yPos + (Ball.DIAMETER / 3))){
				b.setySpeed(b.getySpeed() * -1);
				return true;
			}
		}
		//determines if it from a side
		else if(b.getyPos() <= (yPos + BRICK_HEIGHT) && b.getyPos() >= yPos){
			//hit right
			if(b.getxPos() <= (xPos + BRICK_WIDTH) && b.getxPos() > (xPos + (BRICK_WIDTH - (Ball.DIAMETER / 2)))){
				b.setxSpeed(b.getxSpeed() * -1);
				return true;
			}
			//hit left
			else if(b.getxPos() >= (xPos - Ball.DIAMETER) && b.getxPos() < (xPos + (Ball.DIAMETER / 2))){
				b.setxSpeed(b.getxSpeed() * -1);
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics g){
		g.setColor(Color.white);
		g.fillRect(xPos, yPos, BRICK_WIDTH, BRICK_HEIGHT);
		g.setColor(BRICK_COLOR);
		g.fillRect((xPos+2), (yPos+2), BRICK_WIDTH-4, BRICK_HEIGHT-4);
	}
	public void decrementType(){
		--life;
	}
	public boolean dead() {
		if(life == 0)
			return true;
		return false;
	}
}