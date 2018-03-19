package com.assignment.main;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.Color;

public class Window extends Canvas 
{

/**
 * This class is used to create the windows for the game
 * 
 */
private static final long serialVersionUID = 3585776272584043286L;

	public Window(int width, int height, String title, Game game)
	{
		JFrame frame = new JFrame(title);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		
		//Window size, minimum and maximum
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);	//Don't allow for resizing of window
		frame.setLocationRelativeTo(null);		//Starts window in the middle of the screen instead of top left
		frame.add(game);	//add game class
		frame.setVisible(true);
		game.start();
		
	}

}
