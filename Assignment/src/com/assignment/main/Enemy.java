package com.assignment.main;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends GameObject
{

	public Enemy(int x, int y, ID id)
	{
		super(x , y , id);
	}
	private int Size = 1;
	private boolean maxSize = false;
	private boolean minSize = false;
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillOval(x, y, Size, Size);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, Size, Size);
		
	}
	
	public void update() 
	{
		if(maxSize == false)
		{
			Size++;
		}
		if(Size == 150)
		{
			maxSize = true;
			minSize = true;
		}
		if(minSize == true)
		{
			Size--;
		}
		if(Size == 0)
		{
			maxSize = false;
			minSize = false;
		}
		/*
		else
		{
			Size--;
			Size = Math.max(Size,1);
		}*/
	}
}
