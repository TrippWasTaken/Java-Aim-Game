package com.assignment.main;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

//Main Class creates window
public class Main 
{

    private static int width = 1280;
    private static int height = 720;
    
    public static void main(String[] args)
    {
    	/*
    	 * Custom cursor
    	 * if this happens to not work remove this code
    	 */
    	Toolkit toolKit = Toolkit.getDefaultToolkit();
        Image Cursor = toolKit.getImage("Cursor.gif");
        Cursor cursor = toolKit.createCustomCursor(Cursor, new Point(), "Cursor");
        //end custom cursor
        
        JFrame frame = new JFrame();
        JPanel gamePanel = new JPanel();
        gamePanel.setBounds(0, 0, width*3/4, height);
        Gameplay game = new Gameplay();
        
        frame.setCursor(cursor);	//setting custom cursor
        frame.setBounds(100, 100, 1280, 720);
        frame.setTitle("Assignment");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);

        frame.setVisible(true);
        
    }
}

