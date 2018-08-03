package com.bugbean.hurryball.kernel;

import com.bugbean.hurryball.gameframe.MainFrame;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class ShadowPill extends Pill {

    public ShadowPill() {
        setPillColor(randomColor());
        randomX();
        setWidth(40);
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(getPillColor());
        movedHeight = getMovedY();
        Ellipse2D ellipse2D = new Ellipse2D.Double(getPillX(), movedHeight, getWidth(), getHeight());
        g2d.fill(ellipse2D);
        setPillX(getPillX()-getSpeed());
        if (getPillX() < -getWidth()) {
            randomX();
            setPillColor(randomColor());
        }
    }

    @Override
    public void randomX() {
        int x = MainFrame.width + mRandom.nextInt(1500);
        setPillX(x);
        randomSpeed();
    }
}
