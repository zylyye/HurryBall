package com.bugbean.hurryball.core;

import java.awt.*;

public class Barrier {
    private int width;
    private int height;
    private int x;
    private int y;
    private Color barColor;
    private int speed = 2;
    private double increment;
    private boolean smallAble = true;

    public Barrier() {
        width = 10;
        height = 80;
        barColor = Color.GRAY;
        x = 1500;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        if (increment > 0 && x < -width) {
            increment = 0;
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int heightOnVisibel() {
        int h = 0;
        if(increment<=0.5) {
            h = (int) (height * Math.sin((increment+0.5) * Math.PI));
            increment += 0.01;
        }
        return height - h;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }

    public void paint(Graphics2D g2d) {
        g2d.setColor(barColor);
        int h = heightOnVisibel();
        Rectangle rect = new Rectangle(x,y-h,width,h);
        g2d.fill(rect);
        x -= speed;
    }

    public void setBarColor(Color barColor) {
        this.barColor = barColor;
    }

    public boolean isSmallAble() {
        return smallAble;
    }

    public void setSmallAble(boolean smallAble) {
        this.smallAble = smallAble;
    }
}
