package com.game;
import java.awt.Color;
import java.awt.Graphics2D;

public class Ball implements Sprite {

	private int xPos, yPos;
	private int xSpeed, ySpeed;
	public static final int DIAMETER = 16;
	public static final Color BALL_COLOR = Color.black;

	public Ball(int xPos, int yPos, int xSpeed, int ySpeed){
		this.xPos   = xPos;
		this.yPos   = yPos;
		this.xSpeed     = xSpeed;
		this.ySpeed     = ySpeed;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public void move(){
		xPos += xSpeed;
		yPos += ySpeed;
	}

	public void draw(Graphics2D g){
		g.setColor(BALL_COLOR);
		g.fillOval(xPos, yPos, DIAMETER, DIAMETER);
		g.setColor(Color.gray);
		g.drawOval(xPos, yPos, DIAMETER, DIAMETER);
	}
}
