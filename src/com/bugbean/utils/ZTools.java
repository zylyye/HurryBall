package com.bugbean.utils;

import java.awt.*;
import java.awt.event.*;

public class ZTools {
    public static void makeItMoveAble(Frame frame) {

        MouseMotionListener m = new FrameMove(frame);
        frame.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int x = e.getX();
                    int y = e.getY();
                    ((FrameMove) m).setPoint(x, y);
                    frame.addMouseMotionListener(m);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                frame.removeMouseMotionListener(m);
            }
        });
    }

    public static void sync() {
        if (System.getProperty("os.name").equalsIgnoreCase("linux")) {
            Toolkit.getDefaultToolkit().sync();
        }
    }

    private static class FrameMove extends MouseMotionAdapter {
        private int x;
        private int y;
        private Frame mFrame;

        public FrameMove(Frame frame) {
            mFrame = frame;
        }

        public void setPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int nX = e.getXOnScreen() - x;
            int nY = e.getYOnScreen() - y;
            mFrame.setLocation(nX, nY);
        }
    }

}
