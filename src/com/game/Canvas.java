package com.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

public class Canvas extends JPanel implements Observable,ActionListener, MouseMotionListener, MouseListener{
	/**
	 * 
	 */
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	public static final int HEIGHT = 600;
	public static final int WIDTH = 720;
	private int horizontalCount;
	private BufferedImage image;
	private Graphics2D bufferedGraphics;
	private Timer time;
	private static final Font endFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	private static final Font scoreFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);
	public Clock clock = new Clock(); 
	private Paddle paddle;
	private Ball ball;
	ArrayList<ArrayList<BrickMain> > BrickMains;

	/**
	 * Prepares the screen, centers the paddle and the ball. The ball
	 * will be located in the center of the paddle, and the paddle will
	 * be located on the center of the screen
	 * Sunde
	 * The BrickMains are displayed in columns across the screen with the 
	 * screen being split based on the width of an individual BrickMain. 
	 * Each BrickMain is stored in a temporary ArrayList, which is added
	 * to the classes ArrayList which contains all of the BrickMains.
	 */
	public Canvas(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		bufferedGraphics = image.createGraphics();
		time = new Timer(15, this);
		paddle = new Paddle((WIDTH/2)-(Paddle.P_WIDTH/2));
		ball = new Ball(((paddle.getXPos() + (Paddle.P_WIDTH / 2)) - (Ball.DIAMETER / 2)), 
				(Paddle.Y_POS - (Ball.DIAMETER + 10)), -5, -5);

		BrickMains = new ArrayList<ArrayList<BrickMain> >();
		horizontalCount = WIDTH / BrickMain.BRICK_WIDTH;
		for(int i = 0; i < 2; ++i){
			ArrayList<BrickMain> temp = new ArrayList<BrickMain>();

			for(int j = 0; j < horizontalCount; ++j){
				BrickMain tempBrickMain = new BrickMain((j * BrickMain.BRICK_WIDTH), ((i+2) * BrickMain.BRICK_HEIGHT), 1);
				temp.add(tempBrickMain);
			}
			BrickMains.add(temp);
			addMouseMotionListener(this);
			addMouseListener(this);

			requestFocus();
		}
	}

	@Override public void actionPerformed(ActionEvent e){
		checkCollisions();
		ball.move();
		for(int i = 0; i < BrickMains.size(); ++i){
			ArrayList<BrickMain> al = BrickMains.get(i);
			for(int j = 0; j < al.size(); ++j){
				BrickMain b = al.get(j);
				if(b.dead()){
					al.remove(b);
				}
			}
		}
		repaint();
	}

	/**
	 * Checks for any collisions, if the ball hits the upper wall, or the side
	 * walls it changes direction. If the ball goes below the paddle, the position
	 * of the ball gets reset and the player loses a life
	 */
	private void checkCollisions() {
		if(paddle.hitPaddle(ball)){
			ball.setySpeed(ball.getySpeed() * -1);
			return;
		}
		//first check if ball hit any walls
		if(ball.getxPos() >= (WIDTH - Ball.DIAMETER) || ball.getxPos() <= 0){
			ball.setxSpeed(ball.getxSpeed() * -1);
		}
		if(ball.getyPos() > (Paddle.Y_POS + Paddle.P_HEIGHT + 10)){
			resetBall();
		}
		if(ball.getyPos() <= 0){
			ball.setySpeed(ball.getySpeed() * -1);
		}

		//next handle collisions between BrickMains
		int BrickMainRowsActive = 0;
		for(ArrayList<BrickMain> alb : BrickMains){
			if(alb.size() == horizontalCount){
				++BrickMainRowsActive;
			}
		}

		for(int i = (BrickMainRowsActive==0) ? 0 : (BrickMainRowsActive - 1); i < BrickMains.size(); ++i){
			for(BrickMain b : BrickMains.get(i)){
				if(b.hitBy(ball)){
					b.decrementType();
				}
			}
		}
	}

	/**
	 * Sets the balls position to approximately the center of the screen, and
	 * deducts a point from the user. If necessary, ends the game
	 */
	private void resetBall() {
		if(gameOver()){
			time.stop();
			return;
		}
		ball.setxPos(WIDTH/2);
		ball.setySpeed((HEIGHT/2) + 80);
		paddle.setLives(paddle.getLives() - 1);
		// paddle.setScore(paddle.getScore() - 1000);
	}

	private boolean gameOver() {
		if(paddle.getLives() <= 1){
			//System.out.println("hello");
			//notifyObservers(true);
			return true;
		}
		
		return false;
	}

	/**
	 *  Draws the screen for the game, first sets the screen up (clears it)
	 *  and then it begins by setting the entire screen to be white. Finally
	 *  it draws all of the BrickMains, the players paddle, and the ball on the 
	 *  screen
	 */
	@Override public void paintComponent(Graphics g){
		super.paintComponent(g);
		bufferedGraphics.clearRect(0, 0, WIDTH, HEIGHT);
		bufferedGraphics.setColor(Color.WHITE);
		bufferedGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		paddle.draw(bufferedGraphics);
		ball.draw(bufferedGraphics);
		for(ArrayList<BrickMain> row : BrickMains){
			for(BrickMain b : row){
				b.draw(bufferedGraphics);
			}
		}

		if(gameOver() &&
				ball.getyPos() >= paddle.Y_POS + 10){
			time.stop();
			notifyObservers(true);
			bufferedGraphics.setColor(Color.black);
			bufferedGraphics.setFont(endFont);
			bufferedGraphics.drawString("Game Over!", (WIDTH/2) - 85, (HEIGHT/2));
			

		}
		if(empty()){
			bufferedGraphics.setColor(Color.black);
			bufferedGraphics.setFont(endFont);
			bufferedGraphics.drawString("You won!", (WIDTH/2) - 85, (HEIGHT/2));
			time.stop();
			notifyObservers(true);
		}
		g.drawImage(image, 0, 0, this);
		Toolkit.getDefaultToolkit().sync();
	}



	private boolean empty() {
		for(ArrayList<BrickMain> al : BrickMains){
			if(al.size() != 0){
				return false;
			}
		}
		
		return true;
	}

	@Override public void mouseMoved(MouseEvent e){
		paddle.setXPos(e.getX());
	}

	@Override public void mouseDragged(MouseEvent e){}

	@Override public void mouseClicked(MouseEvent e){
		if(time.isRunning()){
			return;
		}
		time.start();
		notifyObservers(false);
		//clock.start();
	}

	public static void main(String[] args){
		JFrame frame = new JFrame();
		Canvas c = new Canvas();
		c.registerObserver(c.clock);
		frame.add(c.clock, BorderLayout.WEST);
		frame.add(c, BorderLayout.NORTH);

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void registerObserver(Observer observer) 
	{
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) 
	{
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(boolean x)
	{
		for (Observer ob : observers) {
			System.out.println("Notifying Observers");
			ob.update(x);
		}

	}
}
