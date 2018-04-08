package com.assignment.main;

import java.awt.Color;
import java.awt.Point;

//Circle
public class Circle 
{

    private Point position;
    private float radii;
    private boolean expanding = true;
    private Color color = new Color(255, 255, 255);
    private float growthRate = 0.5f;
    private int targetSize = 100;

    public Circle()
    {
        position = new Point(0, 0);
        radii = 0;
    }
    
    public Circle(int x, int y)
    {
        position = new Point(x, y);
        radii = 0;
    }

    public Circle(float radii)
    {
        position = new Point(0, 0);
        this.radii = radii;
    }

    public Circle(int x, int y, float radii)
    {
        position = new Point(x, y);
        this.radii = radii;
    }

    public Circle(int x, int y, int radii)
    {
        position = new Point(x, y);
        this.radii = radii;
    }

    public Circle(int x, int y, float radii, float growthRate)
    {
        position = new Point(x, y);
        this.radii = radii;
        this.growthRate = growthRate;
    }

    public Circle(int x, int y, float radii, float growthRate, int targetSize)
    {
        position = new Point(x, y);
        this.radii = radii;
        this.growthRate = growthRate;
        this.targetSize = targetSize;
    }

    public void setGrowthRate(float growthRate)
    {
        this.growthRate = growthRate;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public void update()
    {
        if(this.expanding)
        {
            this.radii += growthRate;

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

        if(this.radii > targetSize) 
        {
            this.expanding = false;
        }
    }

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
