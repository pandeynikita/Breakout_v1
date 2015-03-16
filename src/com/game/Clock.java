package com.game;

import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class Clock extends JPanel implements Observer
{
	private Timer time = null;
	private int seconds = 1;
	private int minutes;
	private JLabel title; 
	private boolean clockToggle = true;
	private static final Font clockFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	
	
	public Clock()
	{
		super();
		title = new JLabel("0:00");	
		title.setFont(clockFont);
		this.add(title);		
	}
	
	
	 public void start(){
	        time = new Timer(1000, new ActionListener(){
	        public void actionPerformed( ActionEvent e ) {
	        	//System.out.println(clockToggle);
	                minutes = seconds/60;
	                int secondsToPrint = seconds%60;
	                if (secondsToPrint < 10)
	                {
	                	String temp = minutes + ":0" + secondsToPrint;
	                	title.setText(temp);
	                	
	                }
	                else
	                {
	                	String temp = minutes + ":" + secondsToPrint;
	                	title.setText(temp);
	                }
	                seconds++;
	                
	                
	                	
	                //System.out.println("start");
	            }
	        });
	       time.start();
	        
	    }
	 
	 	 
	 @Override
	 public void update(boolean toggle)
	 {
		 //System.out.println("Asdsadas");
		 if(toggle)
		 {
		 //clockToggle = toggle;
		 //new Clock().start();
			 time.stop();
			 }
		 else
		 {
			 //System.out.println("asd");
			 start();
			 //clockToggle = toggle;
			 //time.stop();
		 }
	 }
	
	 /*public static void main(String args[])
	 {
		 JFrame frame = new JFrame();
		 Clock c = new Clock();
	     frame.add(c);
	     frame.pack();
	     frame.setResizable(false);
	     frame.setVisible(true);
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   	 c.start();
	   	 
	   	 while(true)
	   	 {
	   		if(c.seconds >= 3)
	   			break;
	   	 }
	   	c.update(false);
	
	 } */
	
}