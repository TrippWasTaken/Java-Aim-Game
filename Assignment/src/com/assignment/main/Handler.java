package com.assignment.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler 
{
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void update()
	{
		int i;
		for(i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
			
			tempObject.update();
			
		}
	}
	
	public void render(Graphics g)
	{
		int i;
		for(i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject object)
	{
		this.object.add(object);
	}
	
	public void removeObject(GameObject object)
	{
		this.object.remove(object);
	}
}
