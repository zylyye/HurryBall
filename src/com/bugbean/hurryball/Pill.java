package com.bugbean.hurryball;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

public class Pill {
    private int pillX;
    private int pillY;
    private int speed = 3;
    private int width = 30;
    private int height = 30;
    private Color pillColor;
    protected Random mRandom = new Random();

    protected int movedHeight;


    private double moveDuration = 0;
    private int moveDistance = 100;

    public Pill() {
        pillColor = randomColor();
        randomX();
    }

    public void randomX() {
        pillX = MainFrame.width + mRandom.nextInt(800);
        randomSpeed();
    }

    public Color randomColor() {
        int r = mRandom.nextInt(255);
        int g = mRandom.nextInt(255);
        int b = mRandom.nextInt(255);
        return new Color(r, g, b);
    }

    public void paint(Graphics2D g2d) {
        movedHeight = getMovedY();
//        Rectangle rect = new Rectangle(getPillX(), movedHeight, width, height);
        RoundRectangle2D rect = new RoundRectangle2D.Double(getPillX(), movedHeight, width, height,6,6);
        g2d.setColor(pillColor);
        g2d.fill(rect);
        pillX -= speed;
        if (pillX <= -width) {
            randomX();
            setPillColor(randomColor());
            moveDuration = 0;
        }
    }

    protected int getMovedY() {
        int h = 0;
        h = (int)(moveDistance * Math.sin(moveDuration * Math.PI));
        moveDuration += 0.01;
        return getPillY() + h;
    }

    public void randomSpeed() {
        setSpeed(mRandom.nextInt(5) + 1);
    }

    public int getMovedHeight() {
        return movedHeight;
    }

    public int getPillX() {
        return pillX;
    }

    public void setPillX(int pillX) {
        this.pillX = pillX;
    }

    public int getPillY() {
        return pillY;
    }

    public void setPillY(int pillY) {
        this.pillY = pillY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public Color getPillColor() {
        return pillColor;
    }

    public void setPillColor(Color pillColor) {
        this.pillColor = pillColor;
    }
}
