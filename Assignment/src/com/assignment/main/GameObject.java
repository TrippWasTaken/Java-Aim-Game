package com.assignment.main;

import java.awt.Graphics;

public abstract class GameObject 
{
	protected float x, y;
	
	public GameObject(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
	
	
}
