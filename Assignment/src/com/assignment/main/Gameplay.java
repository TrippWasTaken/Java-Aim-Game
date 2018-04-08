package com.assignment.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


//Gameplay and settings
public class Gameplay extends JPanel implements KeyListener, ActionListener 
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    private int width = 1280;
    private int height = 720;
    private int border = 3;

    private int startWidth = 4;
    private int minSize = 3;

    // Settings
    private int growthRateMod = 1;
    private int targetSize = 50;
    private int desiredFPS = 60;
    private int difTime = 300;
    private int maxTime = 1500;
    private int lives = 5;
    

    private float growthRate = 0.5f;

    private long score = 0;
    private int combo = 0;

    private long startTime = System.currentTimeMillis();
    private long lastTime = System.currentTimeMillis();

    private long frameTime = System.currentTimeMillis();
    private int frames = 0;
    private int frameCount = 0;

    private Timer timer;
    private int delay = (int) ((1000/desiredFPS));

    private Point mousePos = new Point(0, 0);
    
    private ArrayList<Circle> circles = new ArrayList<>();
    
    private boolean isPaused = false;

    public Gameplay()
    {
    	initSettings();
        initListeners();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        //reset();
        timer = new Timer(delay, this);
        timer.start();
    }

	public void paint(Graphics g)
    {
		
        g.setColor(new Color(40, 40, 40));
        g.fillRect(0,0, 1280, 720);
      
        // Labels
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Frames: " + frames, 970, 40);
        g.drawString("Time: " + (System.currentTimeMillis() - startTime)/1000, 970, 70);
        g.drawString("Score: " + score, 970, 100);
        g.drawString("Combo: " + combo, 970, 130);
        g.drawString("Lives: " + lives, 970, 160);
        g.drawString("Click the circles", 970, 220); 
        g.drawString("Spacebar to pause ", 970, 250); 
        if(lives <= 0)
        {
            g.drawString("Game Over", width*3/4*1/2, height/2);
            g.drawString("Click on the window to go again", width*3/4*1/2-100, height/2+24);
        }
        //Play Area Border
        g.setColor(Color.BLACK);
        g.fillRect(width*3/4,0, 1, 720);

        // Circles
        for(int i = 0; i < circles.size(); i++)
        {
            Circle c = circles.get(i);
            g.setColor(c.getColor());
            g.fillOval(c.getPosition().x, c.getPosition().y, (int) c.getRadii()*2, (int) c.getRadii()*2);
        }
        g.dispose();
    }

    public void hitCircle()
    {
        for(int i = 0; i < circles.size(); i++)
        {
            Circle c = circles.get(i);
            int length = (int) Math.hypot(mousePos.x - (c.getPosition().x + c.getRadii()), mousePos.y - (c.getPosition().y + c.getRadii()));
            if(length <= c.getRadii()+2)
            {
                //System.out.println("Circle hit");
                score += 1 * combo;
                combo++;
                c = null;
                circles.remove(i);
                return;
            }
        }
        combo = 0;
        lives--;
    }

    public void summonCircle()
    {
        long rand = (long) ((Math.random() * maxTime) +  (difTime));
        if(System.currentTimeMillis() - lastTime >= rand)
        {
            Circle e = new Circle((int) ((Math.random()* (width*3/4-targetSize*2-border*2 - targetSize+border)) + targetSize+border),
                    (int) ((Math.random() * (height-targetSize*2-border*2 - targetSize+border)) + targetSize/2+border),
                    startWidth, growthRate * growthRateMod, targetSize);
            //System.out.println(e.getPosition());
            //System.out.println((height-targetSize*2-border*2 - targetSize+border) + targetSize+border);
            circles.add(e);
            lastTime = System.currentTimeMillis();
        }
    }

    public void updateCircle()
    {
        for(int i = 0; i < circles.size(); i++)
        {
            Circle c = circles.get(i);

            if(c.getRadii() < minSize)
            {
                c = null;
                circles.remove(i);
                combo = 0;
                lives--;
                return;
            }
            c.update();

        }
    }

    public void reset() 
    {
        circles.clear();
        lives = 5;
        score = 0;
        combo = 0;
        startTime = System.currentTimeMillis();
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        timer.start();
        //frames = (float) (((System.currentTimeMillis() - frameTime)));
        //frames /= 1000;
        //frames = Math.round(1/frames);
        frameCount++;
        if(System.currentTimeMillis() - frameTime >= 1000)
        {
            //System.out.println("Frames: " + frameCount);
            frames = frameCount;
            frameCount = 0;
            frameTime = System.currentTimeMillis();
        }
        summonCircle();
        updateCircle();
        repaint();
        if(lives <= 0)
        {
        	circles.clear();
            timer.stop();
        }

    }
    
    public void initSettings()
    {
    	boolean isValidNumber = false;
    	
    	JTextField settingSpeed = new JTextField("1");
    	JTextField settingSize = new JTextField("50");
    	JTextField settingMin = new JTextField("300");
    	JTextField settingMax = new JTextField("1500");
    	Object[] settingInfo = {
    			"Enter the circle growth rate multiplier (Default = 1)", settingSpeed,
    			"Enter the circle size (Default = 50)", settingSize,
    			"Enter the minimum time (ms) before next circle (Default = 300)", settingMin,
    			"Enter the maximum time (ms) before next circle (Default = 1500)", settingMax,
    	};
    	
    	int settingBox = JOptionPane.showConfirmDialog(null, settingInfo, "Please Enter your settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    	
    	if (settingBox == JOptionPane.CANCEL_OPTION)
    	{
    		System.exit(0);
    	}
    	
    	if (settingBox == JOptionPane.CLOSED_OPTION)
    	{
    		System.exit(0);
    	}
    	
    	if (settingBox == JOptionPane.OK_OPTION)
    	{	
    	    String value1 = settingSpeed.getText();
    	    String value2 = settingSize.getText();
    	    String value3 = settingMin.getText();
    	    String value4 = settingMax.getText();
    	    
    	    try
    		{
    	    	Integer.parseInt(value1);
    	    	Integer.parseInt(value2);
    	    	Integer.parseInt(value3);
    	    	Integer.parseInt(value4);
    	    	isValidNumber = true;
    		}
    		catch (NumberFormatException e) 
    	    {
    			JOptionPane.showMessageDialog(new JPanel(), "Please enter only numbers", "Error", JOptionPane.ERROR_MESSAGE);
    	    }
    	    
    	    if(isValidNumber == true)
    	    {
    	    	growthRateMod = Integer.parseInt(value1);
    	    	targetSize = Integer.parseInt(value2);
    	    	difTime = Integer.parseInt(value3);
    	    	maxTime = Integer.parseInt(value4);
    	    }
    	    
    	}
    
    	
    	
    	
    	//Input for circle speed
    	//input = (String) JOptionPane.showInputDialog(null,"Enter your desired Circle growth rate multiplier (Default = 1)",input, JOptionPane.PLAIN_MESSAGE,null,null,"1");
    	
    	
    	/*
    	while(isValidNumber == false)
    	{
    		try
    		{
    			System.out.println(Integer.parseInt(input));
    	    	isValidNumber = true;
    		}
    		catch (NumberFormatException e) 
    	    {
    			JOptionPane.showMessageDialog(new JPanel(), "Please enter a number", "Error", JOptionPane.ERROR_MESSAGE);
    			input = (String) JOptionPane.showInputDialog(null,"Enter the rate at which the circles will grow (Default = 1)",input, JOptionPane.PLAIN_MESSAGE,null,null,"1");
    	    }
    	}
    	growthRateMod = Integer.parseInt(input);
    	isValidNumber = false;
    	*/
    }


    @Override
    public void keyTyped(KeyEvent e) 
    {

    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
    	if(isPaused == false)
    	{
        	if(e.getKeyCode() == 32)
    		{
    			timer.stop();
    			isPaused = true;
    		}
    	}
    	else if(isPaused == true)
    	{
        	if(e.getKeyCode() == 32)
    		{
    			timer.start();
    			isPaused = false;
    		}
    	}
    		
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {

    }

    public void setDesiredFPS(int delay)
    {
        timer.setDelay(delay);
    }

    public void setTargetSize(int targetSize)
    {
        this.targetSize = targetSize;
    }

    public void setGrowthRate(int growthRateMod) 
    {
        this.growthRateMod = growthRateMod;
    }

    public void setDifTime(int difTime) 
    {
        this.difTime = difTime;
    }

    public void setMaxTime(int maxTime)
    { 
    	this.maxTime = maxTime; 
    }

    
    public void initListeners()
    {

        addMouseMotionListener(new MouseMotionListener() 
        {
            @Override
            public void mouseDragged(MouseEvent e) 
            {

            }

            @Override
            public void mouseMoved(MouseEvent e) 
            {
                mousePos.x = e.getX();
                mousePos.y = e.getY();
            }
        });

        addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {

            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
            	if(lives <=0)
            	{
            			reset();
            			lives = lives+1;
            			timer.start();
            	}
                mousePos.x = e.getX();
                mousePos.y = e.getY();
                hitCircle();
            }

            @Override
            public void mouseReleased(MouseEvent e) 
            {

            }

            @Override
            public void mouseEntered(MouseEvent e) 
            {

            }

            @Override
            public void mouseExited(MouseEvent e) 
            {

            }
        });
    }

}



