package com.bugbean.hurryball.gameframe;

import com.bugbean.hurryball.gamepanel.MainPanel;
import com.bugbean.hurryball.kernel.GameTuneController;
import com.bugbean.utils.ZTools;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
//    private int width = 1500;
    public static int width = 1500;
//    private int height = 800;
    public static int height = 1000;
    private MainPanel mMainPanel;
    private int flushSpeed = 10;

    public MainFrame(int playerNum) {
        setSize(width, height);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mMainPanel = new MainPanel(this,playerNum);
        add(mMainPanel);
        new Thread(()->{
            while(true) {

                while (!mMainPanel.isPaused()) {
                    mMainPanel.repaint();
                    ZTools.sync();
                    try {
                        Thread.sleep(flushSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Color bg = new Color(218, 255, 239, 200);
        mMainPanel.setBackground(bg);
        addKeyListener(mMainPanel);

        ZTools.makeItMoveAble(this);
        setLocationRelativeTo(null);
        setVisible(true);
        new GameTuneController(this).start();
    }

    public int getFlushSpeed() {
        return flushSpeed;
    }

    public void setFlushSpeed(int flushSpeed) {
        this.flushSpeed = flushSpeed;
    }



    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public MainPanel getMainPanel() {
        return mMainPanel;
    }





    public static void main(String[] args) {
        new MainFrame(1);
    }
}
