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

    public static JPanel sliderPanel = new JPanel();

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
    private int maxTime = 1000;
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

    public Gameplay()
    {
        initListeners();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        reset();
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g)
    {
    	
        g.setColor(Color.DARK_GRAY);
        g.fillRect(1,1, (width*3/4), 717);

        // Labels
        g.setColor(Color.white);
        g.drawString("Frames: " + frames, 5, 15);
        g.drawString("Time: " + (System.currentTimeMillis() - startTime)/1000, 5, 25);
        g.drawString("Score: " + score, 5, 35);
        g.drawString("Combo: " + combo, 5, 45);
        g.drawString("Lives: " + lives, 5, 55);


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
                score += 1;
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
    public void actionPerformed(ActionEvent e) {
        timer.start();
        //frames = (float) (((System.currentTimeMillis() - frameTime)));
        //frames /= 1000;
        //frames = Math.round(1/frames);
        frameCount++;
        if(System.currentTimeMillis() - frameTime >= 1000){
            //System.out.println("Frames: " + frameCount);
            frames = frameCount;
            frameCount = 0;
            frameTime = System.currentTimeMillis();
        }
        summonCircle();
        updateCircle();
        repaint(0, 0, width*3/4, height);
        if(lives <= 0)
            reset();
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {

    }

    @Override
    public void keyPressed(KeyEvent e) 
    {

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



