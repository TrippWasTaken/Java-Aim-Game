package com.assignment.main;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main 
{

    private static int width = 1280;
    private static int height = 720;
    
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        JPanel gamePanel = new JPanel();
        gamePanel.setBounds(0, 0, width*3/4, height);
        Gameplay game = new Gameplay();
        frame.setBounds(100, 100, 1280, 720);
        frame.setTitle("Assignment");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);

        frame.setVisible(true);
    }

}

