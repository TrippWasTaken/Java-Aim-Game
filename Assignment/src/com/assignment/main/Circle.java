package com.assignment.main;

import java.awt.Color;
import java.awt.Point;

//Circle class
public class Circle 
{
    private Point position;
    private float radii;
    private boolean expanding = true;
    private Color color = new Color(240, 240, 240);
    private float growthRate = 0.5f;
    private int targetSize = 100;
    
    
    //Constructor
    public Circle(int x, int y, float radii, float growthRate, int targetSize)
    {
        position = new Point(x, y);
        this.radii = radii;
        this.growthRate = growthRate;
        this.targetSize = targetSize;
    }
    
    //updating circle object
    public void update()
    {
        if(this.expanding)
        {
            this.radii += growthRate;

            
            //ensuring the circle is drawn from the centre outwards
            //the coordinate system needs the circle properties to be integers
            //the modulos check to keep the circles from moving
            if(growthRate%1 == 0)	
            {
                position.x -= growthRate;
                position.y -= growthRate;
            }
            else
            {
                if(radii%1 == 0) 
                {
                    position.x -= growthRate*2;
                    position.y -= growthRate*2;
                }
            }
        }
        else
        {
            this.radii -= growthRate;
            
            if(growthRate%1 == 0)
            {
                position.x += growthRate;
                position.y += growthRate;
            }
            else
            {
                if(radii%1 == 0) 
                {
                    position.x += growthRate*2;
                    position.y += growthRate*2;
                }
            }
        }

        //Stops expanding once target size is reached and proceeds to shrink
        if(this.radii > targetSize) 
        {
            this.expanding = false;
        }
    }

    //getters and setters
    public Color getColor()
    {
        return color;
    }


    public float getRadii() 
    {
        return radii;
    }

    public Point getPosition() 
    {
        return position;
    }
}
