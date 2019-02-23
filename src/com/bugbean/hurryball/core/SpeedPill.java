package com.bugbean.hurryball.core;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SpeedPill extends Pill {

    private Color startColor;
    private Color endColor;

    public SpeedPill() {
        setPillColor(randomColor());
        randomX();
        randomGradientColot();
    }
    public void randomGradientColot() {
        startColor = randomColor();
        endColor = randomColor();
    }

    @Override
    public void setPillY(int pillY) {
        super.setPillY(pillY);
        movedHeight = getPillY();
    }

    public void paint(Graphics2D g2d) {
        g2d.setPaint(new GradientPaint(0,0,startColor,10,10,endColor,true));
        Rectangle2D rect = new Rectangle2D.Double(getPillX(), getPillY(), getWidth(), getHeight());
        g2d.fill(rect);
        setPillX(getPillX()-getSpeed());
        if (getPillX() < -getWidth()) {
            randomX();
            randomGradientColot();
        }
    }
}
