package com.bugbean.hurryball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MainPanel extends JPanel implements KeyListener {

    private MainFrame mContainer;
    private Random mRandom = new Random();

    private int initX = 0;

    private int gameHeight = 300;

    private int[] lineStartXs;
    private int lineStartX = 0;

    private int[] lineStartYs;
    private int lineStartY = 500;

    private int[] lineWidths;
    private int lineWidth = 100;

    private int[] trap;
    private int trapHeight = 80;

    private int[] lineDurations;
    private int lineDuration = 20;
    private int speed = 2;
    private Color[] mColors;
    private Color lineColor;
    private Ball[] balls;
    private int ballCount = 1;
    private Ball ball;
    private int lineCounts = 26;

    private Barrier[] bars;
    private int barNum = 1;

    private Pill[][] pills;
    private int pillCount = 3;
    private ShadowPill[][] shadowPills;
    private int shadowPillCount = 4;



    //游戏是否暂停
    private boolean isPaused = false;

    //滑块降落高度
    private int dropHeight = 100;


    //旋转参数
    private double unite = Math.PI/180;
    private double theta = 0;
    private double lastTheta;
    private double rotateTheta = 90*unite;
    private double minRotateTheta = 30*unite;
    private double rotateDuration = 0;
    private double[] availableRotateTheta = {30 * unite, 60 * unite, 90 * unite};

    //抖动参数
    private double shakeY = 0;
    private double shakeHeight = 15;
    private double shakeDuration = 0;


    public MainPanel(MainFrame container) {
        mContainer = container;
        lineStartXs   = new int[ballCount];
        lineStartYs   = new int[ballCount];
        lineWidths    = new int[ballCount];
        lineDurations = new int[ballCount];
        trap          = new int[ballCount];
        lineStartY = mContainer.getHeight()-100;
        arrayFill(lineStartYs,lineStartY);
        for (int i = 0; i < ballCount; i++) {
            lineStartYs[i] = lineStartY - gameHeight*i;
        }
        arrayFill(lineWidths,lineWidth);
        arrayFill(lineDurations,lineDuration);


        balls = new Ball[ballCount];
        for (int i = 0; i < ballCount; i++) {
            balls[i] = new Ball(this, i);
            balls[i].setBallY(lineStartYs[i],5);
        }

        bars = new Barrier[barNum];
        for (int i = 0; i < barNum; i++) {
            bars[i] = new Barrier();
            bars[i].setX(lineStartX + 600);
        }

        pills = new Pill[ballCount][pillCount];
        for (int i = 0; i < ballCount; i++) {
            for (int j = 0; j < pillCount; j++) {
                pills[i][j] = new Pill();
                pills[i][j].setPillY(lineStartYs[i]-130);
            }
        }

        shadowPills = new ShadowPill[ballCount][shadowPillCount];
        for (int i = 0; i < ballCount; i++) {
            for (int j = 0; j < shadowPillCount; j++) {
                shadowPills[i][j] = new ShadowPill();
                shadowPills[i][j].setPillY(lineStartYs[i]-120);
            }
        }

        ball = balls[0];
        ball.setBallY(lineStartY,5);


        mColors = new Color[lineCounts];
        for (int i = 0; i < lineCounts; i++) {
            mColors[i] = randomColor();
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(0,shakeY);


        g2d.rotate(theta,getWidth()/2,getHeight()/2+100);
        for (int j = ballCount-1; j >= 0; j--) {

            for (int i = 0; i < pillCount; i++) {
                pills[j][i].paint(g2d);
            }
            for (int i = 0; i < shadowPillCount; i++) {
                shadowPills[j][i].paint(g2d);
            }

            for (int i = 0; i < lineCounts; i++) {
                g2d.setColor(lineColor);
                g2d.setStroke(new BasicStroke(10));
                int startX = lineStartXs[j] + i * (lineDurations[j] + lineWidths[j]);
                int endX = startX + lineWidths[j];
                int startY = lineStartYs[j];
                if (startX < getWidth() - 400) {
                    if(false) {
                        int h = 0;
                        if (startX < getWidth() - 1000) {
                            h = trapHeight;
                        } else {
                            double d = (double) (startX - (getWidth() - 1000)) / ((1000 - 400) * 2);
                            h = (int) (trapHeight * Math.sin((d + 0.5) * Math.PI));
                        }
                        balls[j].setCurrentTrapHeight(h);
                        balls[j].setCurrentTrapX(startX);
                        startY -= h;
                    }
                }
                if (startX + lineWidths[j] < 200) {
                    double d = (double) (startX + lineWidths[j]) / 400;
                    int h = (int) (dropHeight * Math.sin((d + 0.5) * Math.PI));
                    startY += h;

                    //小球随滑块下落
                    if (balls[j].getBallX() + lineWidths[j] <= 200) {
                        balls[j].setBallY(startY,5);
                        if (balls[j].isJumpAble()) {
                            balls[j].setJumpAble(false);
                        }
                    }
                }else if (startX >= getWidth() - 200) {
                    double d = (double) (startX - (getWidth() - 200)) / 400;
                    int h = (int) ((dropHeight) * Math.sin((d) * Math.PI));
                    startY += -h;
                }
                if(i ==9) {
                    bars[0].setBarColor(getPointColor(startX, j));
                    bars[0].setX(startX+45);
                    bars[0].setY(startY);
                    balls[j].setCurrentTrapX(startX+45);
                    balls[j].setCurrentTrapHeight(80);
                    bars[0].paint(g2d);
                }
                g2d.setColor(mColors[i % (lineCounts/2)]);
                g2d.drawLine(startX, startY, endX, startY);
            }
            lineStartXs[j] -= speed;
            if (lineStartXs[j] <= -(lineCounts/2 * (lineWidths[j] + lineDurations[j]))) {
                lineStartXs[j] = 0;
                bars[0].setSmallAble(!bars[0].isSmallAble());
            }


            balls[j].paint(g2d);
            for (int i = 0; i < pillCount; i++) {
                if (pillHitTest(balls[j], pills[j][i])) {
                    pills[j][i].randomX();
                rotate(availableRotateTheta[mRandom.nextInt(availableRotateTheta.length)]);
                }
            }
            for (int i = 0; i < shadowPillCount; i++) {
                if (pillHitTest(balls[j], shadowPills[j][i])) {
                    shadowPills[j][i].randomX();
                    randomShadow(20,80);
                }
            }



        }
    }

    public Color getPointColor(int x,int index) {
        Color color = mColors[0];
        for (int i = 0; i < lineCounts/2 ; i++) {
            if (Math.abs(lineStartXs[index] + i * (lineWidths[index] + lineDurations[index]) - x) <= lineWidth) {
                color = mColors[i];
            }
        }
        return color;
    }

    private Color randomColor() {
        int r = (int) (Math.random()*255);
        int g = (int) (Math.random()*255);
        int b = (int) (Math.random()*255);

         return new Color(r, g, b);
    }

    private void arrayFill(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = value;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            ballJump(0);
        }

        if (e.getKeyCode() == KeyEvent.VK_1 && ballCount > 0) {
            ballJump(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_2 && ballCount > 1) {
            ballJump(2);
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            rotate(rotateTheta);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            accelerateFlushSpeed(5000,5);
        }


        if (e.getKeyCode() == KeyEvent.VK_C) {

            Color bg = new Color(218, 255, 239, (int)(Math.random()*255));
            this.setBackground(bg);
        }

    }


    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (isPaused) {
                setPaused(false);
            } else {
                setPaused(true);
            }
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void ballJump(int index) {
        balls[index].jump();
    }

    public void setTrap(int userIndex) {

    }

    public void shakeIt() {
        new Thread(()->{
            while (shakeDuration <= 1) {
                shakeY = shakeHeight * Math.sin(shakeDuration * Math.PI);
                shakeDuration += 0.01;
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            shakeY = 0;
            shakeDuration = 0;
        }).start();
    }
    public void rotate(double rTheta) {
        new Thread(() ->{
            while (rotateDuration <= 0.5) {
                double tempTheta = rTheta * Math.sin(rotateDuration * Math.PI);
                rotateDuration += 0.01;
                theta = tempTheta + lastTheta;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            theta = Math.round(theta/minRotateTheta)*minRotateTheta;
            lastTheta = theta;
            rotateDuration = 0;
        }).start();
    }

    public boolean pillHitTest(Ball ball, Pill pill) {
        boolean res = false;
        int xDistance = ball.getBallX() - pill.getPillX();
        int yDistance = ball.getBallY() - ball.sumHeight() - pill.getMovedHeight();
        if ((xDistance < ball.getBallWidth() && xDistance > -pill.getWidth())
                && (yDistance < ball.getBallHeight() && yDistance > -pill.getHeight())) {
//            pill.randomX();
//            rotate(availableRotateTheta[mRandom.nextInt(availableRotateTheta.length)]);
            res = true;
        }
        return res;

    }

    public void randomShadow(int min, int max) {
        int alpha = mRandom.nextInt(max - min) + min;
        Color bg = new Color(218, 255, 239, alpha);
        this.setBackground(bg);
    }

    public void accelerateFlushSpeed(int time, int value) {
        int originalFlushSpeed = mContainer.getFlushSpeed();
        int currentFlushSpeed = originalFlushSpeed - value;
        if (currentFlushSpeed <= 0) {
            currentFlushSpeed = originalFlushSpeed;
        }
        mContainer.setFlushSpeed(currentFlushSpeed);
        new Thread(() ->{
            try {
                Thread.sleep(time);
                mContainer.setFlushSpeed(originalFlushSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }



}
