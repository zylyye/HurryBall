package com.bugbean.hurryball;

import javax.swing.*;
import java.awt.*;

public class Ball {
    private int index;

    private int ballWidth = 50;
    private int ballHeight = 50;
    private int ballX = 400;
    private int ballY = 448;
    private int currentTrapHeight = 0;
    private int currentTrapX = 0;

    private int heightOffset = 0;

    private int jumpHeight = 120;
    private int jumpCount = 2;
    private double[] jumpRecords;
    private boolean jumpAble = true;
    private int totalJumpCount = 0;
    private double speed = 0.008;

    private int tempH = 0;

    private int[] jumpH;
    private double tempRecords;

    private Color ballColor;

    private MainPanel mMainPanel;

    public Ball(MainPanel m,int i) {
        index = i;
        mMainPanel = m;
        ballColor = Color.cyan;
        jumpRecords = new double[jumpCount];
        jumpH = new int[jumpCount];
    }

    public void jump() {
        if (jumpAble && totalJumpCount < jumpCount) {
            if (totalJumpCount == 0&&sum(jumpH)>0){
                jumpH[totalJumpCount] = tempH;

                totalJumpCount++;
                tempRecords = 0;

            }
            totalJumpCount++;

            if (totalJumpCount >= jumpCount) {
                jumpAble = false;
            }
        }
    }

    private int onJump() {
        int h = 0;
        h = sumHeight();
        if (totalJumpCount > 0) {
            jumpH[totalJumpCount - 1] = (int) (jumpHeight * Math.sin(jumpRecords[totalJumpCount - 1] * Math.PI));
            jumpRecords[totalJumpCount - 1] += speed;
            if (jumpRecords[totalJumpCount - 1] >= 0.5) {

                for (int i = 0; i < jumpCount; i++) {
                    jumpRecords[i] = 0;
                }
                totalJumpCount = 0;
            }
        } else {
            if (h > 0) {
//                tempH = (int) (h * Math.sin((tempRecords+0.5) * Math.PI));
                tempH = (int) (h * Math.sin((tempRecords+0.5) * Math.PI));
                tempRecords += (speed-0.002);
                h = tempH;

                if (tempRecords >= 0.5) {
                    for (int i = 0; i < jumpCount; i++) {
                        jumpH[i] = 0;
                    }
                    tempRecords = 0;
                    h = 0;
                    setBallColor(mMainPanel.getPointColor(ballX, index));
                    jumpAble = true;
                    mMainPanel.shakeIt();
                }
            }
        }
        return getBallY() - h;
    }

    public void setHeightOffset(int heightOffset) {
        this.heightOffset = heightOffset;
    }

    private int sum(int[] arr) {
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        return sum;
    }


    private void onHit() {
        if (Math.abs(ballX + ballWidth - currentTrapX) < 5) {
            if (sumHeight() < currentTrapHeight) {
                ballX = currentTrapX - ballWidth;
            }
        }
    }
    public int getBallX() {
        return ballX;
    }

    public void setBallColor(Color ballColor) {
        this.ballColor = ballColor;
    }

    public int sumHeight() {
        return sum(jumpH);
    }

    public void setBallY(int ballY,int offset) {
        this.ballY = ballY - ballWidth - offset;
    }

    public void setCurrentTrapHeight(int currentTrapHeight) {
        this.currentTrapHeight = currentTrapHeight;
    }

    public void setCurrentTrapX(int currentTrapX) {
        this.currentTrapX = currentTrapX;
    }

    public int getBallY() {
        return ballY/* + heightOffset*/;
    }

    public int getBallWidth() {
        return ballWidth;
    }

    public int getBallHeight() {
        return ballHeight;
    }

    public void paint(Graphics2D g2d) {
        onJump();
        onHit();
        g2d.setColor(ballColor);
        g2d.fillOval(ballX,onJump(),ballWidth,ballHeight);
    }

    public void setJumpAble(boolean jumpAble) {
        this.jumpAble = jumpAble;
    }

    public boolean isJumpAble() {
        return jumpAble;
    }
}
