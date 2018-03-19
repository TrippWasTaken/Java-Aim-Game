package com.assignment.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable
{

	/**
	 * Main Game class
	 */
	private static final long serialVersionUID = 4392619180354507678L;
	
	public static final int WIDTH = 1280, HEIGHT = WIDTH / 16*9;	//forces a 16:9 window
	private Thread thread;
	private boolean running = false;
	public Game()
	{
		new Window(WIDTH, HEIGHT, "Game", this);
		
	}
	
	//Starting the game thread
	public synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
		setRunning(true);
	}
	
	public synchronized void stop()
	{
		try 
		{
			thread.join();	//stopping the thread
			setRunning(false);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	
	//Game loop taken from google explained to the best of my ability
	public void run()
	{
        long lastTime = System.nanoTime();	//most accurate time variable
        double ns = 1000000000.0 / 60.0;	//The optimal time divided by the traget fps which is 60
        double delta = 0;	//Calculates the time passed so it can catch up
        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;
        
        //Will loop until game ends
        while(running)
        {
        	long now = System.nanoTime();	//getting the difference between last update to now
            delta += (now - lastTime) / ns;	//getting time elapsed in seconds
            lastTime = now;
            
            
            while(delta >=1)	//ensures updates are kept at 60 a second
            {
            	update();
            	updates++;
            	delta--;
            }

        	render();
        	frames++;
        	
        	
        	
        	//fps and update counter
        	if(System.currentTimeMillis() - timer > 1000)
            {
        		timer += 1000;
        		System.out.println("FPS: " + frames);
        		System.out.println("Updates: " + updates);
        		frames = 0;
        		updates = 0;
            }
                
        }
        stop();
	}
	
	//Everything in the game that updates
	private void update() 
	{
		
	}
	
	//Everything in the game that renders
	private void render() 
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
			
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.dispose();
		bs.show();
		
		
		
	}
	
	

	public static void main(String[] args) 
	{
		new Game();
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
