package com.assignment.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


//Gameplay and settings
public class Gameplay extends JPanel implements KeyListener, ActionListener 
{

    /**
	 * This is where all things that are rendered and have anything to with the actual gameplay aspect of the game
	 * are done,such as the action listeners, settings, circle spawning
	 */
	private static final long serialVersionUID = 1L;

	//window dimensions 
    private int width = 1280;
    private int height = 720;
    private int border = 3;
    
    //Circle starting and end sizes
    private int startWidth = 4;
    private int minSize = 3;

    // Settings
    private int growthRateMod = 1;
    private int targetSize = 50;
    private int desiredFPS = 60;
    private int difTime = 300;
    private int maxTime = 1500;
    private int lives = 5;
    
    //Growth rate of the circles which canbe multiplied by the growthrateMod to incease it
    private float growthRate = 0.5f;
    
    ///combo and score variables
    private long score = 0;
    private int combo = 0;
    
    //Timer to start game
    private long startTime = System.currentTimeMillis();
    private long lastTime = System.currentTimeMillis();
    
    //Framerate variables
    private long frameTime = System.currentTimeMillis();
    private int frames = 0;
    private int frameCount = 0;
    
    
    private Timer timer;
    private int delay = (int) ((1000/desiredFPS));
    
    //Mouse position
    private Point mousePos = new Point(0, 0);
    
    //ArrayList for all the circles spawned 
    private ArrayList<Circle> circles = new ArrayList<>();
    
    //bool for pasuing the game
    private boolean isPaused = false;

    //Gameplay class
    public Gameplay()
    {
    	initSettings();
        initListeners();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    
    //Paint class to render and update everything
	public void paint(Graphics g)
    {
		
        g.setColor(new Color(40, 40, 40));//Using RGB colours to set colours as they offer more customisation to the standard colour option 
        g.fillRect(0,0, 1280, 720);
      
        // Labels on the screen 
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Frames: " + frames, 970, 40);
        g.drawString("Time: " + (System.currentTimeMillis() - startTime)/1000, 970, 70);
        g.drawString("Score: " + score, 970, 100);
        g.drawString("Combo: " + combo, 970, 130);
        g.drawString("Lives: " + lives, 970, 160);
        g.drawString("Click the circles", 970, 220); 
        g.drawString("Spacebar to pause ", 970, 250);
        
        //if the lives reach 0 display the game over text appears
        if(lives <= 0)
        {
            g.drawString("Game Over", width*3/4*1/2, height/2);
            g.drawString("Click on the window to go again", width*3/4*1/2-100, height/2+24);
        }
        
        //Play Area Border
        g.setColor(Color.BLACK);
        g.fillRect(width*3/4,0, 1, 720);

        //Rendering the circles
        for(int i = 0; i < circles.size(); i++)
        {
            Circle c = circles.get(i);
            g.setColor(c.getColor());
            g.fillOval(c.getPosition().x, c.getPosition().y, (int) c.getRadii()*2, (int) c.getRadii()*2);
        }
        g.dispose();
    }

	//Checking if the circle is hit when the  mouse is clicked by comparing the circle position to the mouse cursor position
    public void hitCircle()
    {
        for(int i = 0; i < circles.size(); i++)
        {
            Circle c = circles.get(i);
            int length = (int) Math.hypot(mousePos.x - (c.getPosition().x + c.getRadii()), mousePos.y - (c.getPosition().y + c.getRadii()));
            if(length <= c.getRadii()+2)
            {
                score += 1;
                combo++;
                if(combo >= 1)		//Once the combo is over 1 start multiplying the score by the combo to give the combo some use
                {
                	score += 1 * combo;
                }
                c = null;	//if the circle is hit set this to null
                circles.remove(i);	//the circle is then removed
                return;		//return that the circle has been hit
            }
        }
        //Otherwise set combo back to 0 and take away a life
        combo = 0;	
        lives--;
    }

    //Spawning the circles
    public void summonCircle()
    {
        long rand = (long) ((Math.random() * maxTime) +  (difTime));	//generating a random long within the maxTime and difTime variables 
        if(System.currentTimeMillis() - lastTime >= rand)
        {
        	/*
        	 * Creating new circle using 3/4s of the screen because that is the play area and ensuring the circle stays within
        	 * the window bounds, all the info is taken from constructor which allows the circles to have different growthrates, startwidths
        	 * and various other properties without having to change the actual circle class.
        	 */
            Circle e = new Circle((int) ((Math.random()* (width*3/4-targetSize*2-border*2 - targetSize+border)) + targetSize+border),
                    (int) ((Math.random() * (height-targetSize*2-border*2 - targetSize+border)) + targetSize/2+border),
                    startWidth, growthRate * growthRateMod, targetSize);
            circles.add(e);
            lastTime = System.currentTimeMillis();
        }
    }

    //updating the  circle sizes
    public void updateCircle()
    {
        for(int i = 0; i < circles.size(); i++)
        {
            Circle c = circles.get(i);

            //if the circle is not registered as hit before it reaches min size, the circle is removed and combo is set to 0 and a life is taken away
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
    
    //after the game is lost this function resets the whole window back to the start by resetting all values
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
        frameCount++;
        if(System.currentTimeMillis() - frameTime >= 1000)
        {
            frames = frameCount;
            frameCount = 0;
            frameTime = System.currentTimeMillis();
        }
        summonCircle();
        updateCircle();
        repaint();

        //if the lives are equal to 0 the circles clear and the timer stops.
        if(lives <= 0)
        {
        	circles.clear();
            timer.stop();
        }
    }
    
    /*
     * Initial settings on startup allow  the user to set the difficulty to their preference
     * This allows the user to start the game of much harder if they prefer to. This loops if the user tries to enter a number
     * and will exit if the user clicks on cancel or the "X" button.
     */
    public void initSettings()
    {
    	boolean isValidNumber = false;		//boolean to confirm that everything entered is a number 
    	
    	//Text fields used for each individual setting
    	JTextField settingSpeed = new JTextField("1");
    	JTextField settingSize = new JTextField("50");
    	JTextField settingMin = new JTextField("300");
    	JTextField settingMax = new JTextField("1500");
    	Object[] settingInfo = 
    		{
    			"Enter the circle growth rate multiplier (Default = 1)", settingSpeed,
    			"Enter the circle size (Default = 50)", settingSize,
    			"Enter the minimum time (ms) before next circle (Default = 300)", settingMin,
    			"Enter the maximum time (ms) before next circle (Default = 1500)", settingMax,
    		};
    		
    		while(isValidNumber == false)	//While this is false loop the settings again
    		{
    			int settingBox = JOptionPane.showConfirmDialog(null, settingInfo, "Please Enter your settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    	    	
    	    	if (settingBox == JOptionPane.CANCEL_OPTION)	//if the cancel button is clicked exit program
    	    	{
    	    		System.exit(0);
    	    	}
    	    	
    	    	if (settingBox == JOptionPane.CLOSED_OPTION)	//if the "x" button clicked is closed exit the program
    	    	{
    	    		System.exit(0);
    	    	}
    	    	
    	    	if (settingBox == JOptionPane.OK_OPTION)		//if the OK button is clicked program assigns the values to strings and tries to parse them to ints
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
    	    	    	isValidNumber = true;	//if everything entered is a number, exits loop by setting this to True
    	    		}
    	    		catch (NumberFormatException e)		//if it catches that theres a letter gives error message  
    	    	    {
    	    			JOptionPane.showMessageDialog(new JPanel(), "Please enter only numbers", "Error", JOptionPane.ERROR_MESSAGE);
    	    	    }
    	    	    
    	    	    if(isValidNumber == true)	//once the settings valued are all done, assigns them to right value in the gameplay
    	    	    {
    	    	    	growthRateMod = Integer.parseInt(value1);
    	    	    	targetSize = Integer.parseInt(value2);
    	    	    	difTime = Integer.parseInt(value3);
    	    	    	maxTime = Integer.parseInt(value4);
    	    	    }    
    		}
    	}
    }//end of settingsInit

    
    @Override
    public void keyTyped(KeyEvent e) 
    {

    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
    	//isPaused is used to allow toggling pausing using only 1 key
    	if(isPaused == false)
    	{
        	if(e.getKeyCode() == 32)
    		{
    			timer.stop();
    			isPaused = true;	//set this to true to allow the next keypress to unpause
    		}
    	}
    	else if(isPaused == true)
    	{
        	if(e.getKeyCode() == 32)
    		{
    			timer.start();
    			isPaused = false;	//Reset  the boolean
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
    
    //Initiating the mouse and key listeners
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
            	//when the game is over allow the player to restart by clicking anywhere on the screen
            	if(lives <=0)
            	{
            			reset();
            			lives = lives+1;
            			timer.start();
            	}
            	//Only register hitcircles when the timer is running, This stops from being able to pause the game and still hit cirlces
            	if(timer.isRunning())
            	{
            		mousePos.x = e.getX();
            		mousePos.y = e.getY();
            		hitCircle();
            	}
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



