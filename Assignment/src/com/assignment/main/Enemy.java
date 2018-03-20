package com.assignment.main;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends GameObject
{
	public Enemy(int x, int y, ID id)
	{
		super(x , y , id);
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillOval(x, y, 100, 100);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, 100, 100);
		
	}
	
	public void update() 
	{
		
	}
}
