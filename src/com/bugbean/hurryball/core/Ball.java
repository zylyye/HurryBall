package com.bugbean.hurryball.core;

import com.bugbean.hurryball.gamepanel.MainPanel;

import java.awt.*;

public class Ball {
    private int index;

    private double score;

    private int ballWidth = 50;
    private int ballHeight = 50;
    private int ballX = 600;
    private int ballY = 448;
    private int currentTrapHeight = 0;
    private int currentTrapX = 0;
    private int currentTrapWidth = 0;

    private int heightOffset = 0;

    private int jumpHeight = 120;
    private int jumpCount = 2;
    private double[] jumpRecords;
    private boolean jumpAble = true;
    private int totalJumpCount = 0;
    private double speed = 0.02;

    private int aheadSpeed = 0;

    private int tempH = 0;

    private int[] jumpH;
    private double tempRecords;

    private Color ballColor;


    private MainPanel mMainPanel;

    private boolean isGameOver;

    public Ball(MainPanel m,int i) {
        index = i;
        mMainPanel = m;

        //#8CC4FA
        ballColor = new Color(0x8c,0xc4,0xfa);
        jumpRecords = new double[jumpCount];
        jumpH = new int[jumpCount];
    }

    /**
     * 小球跳跃
     */
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
        // 已跳跃总高度
        h = sumHeight();
        // 跳跃次数大于0，则正在进行跳跃
        if (totalJumpCount > 0) {
            jumpH[totalJumpCount - 1] = (int) (jumpHeight * Math.sin(jumpRecords[totalJumpCount - 1] * Math.PI));
            jumpRecords[totalJumpCount - 1] += speed;
            if (jumpRecords[totalJumpCount - 1] >= 0.5) {
                // 跳跃结束
                for (int i = 0; i < jumpCount; i++) {
                    jumpRecords[i] = 0;
                }
                // 重置已跳跃次数
                totalJumpCount = 0;
            }
        } else {
            // 跳跃次数为0，如果跳跃总高度为0，则小球正在下落
            if (h > 0) {
                tempH = (int) (h * Math.sin((tempRecords+0.5) * Math.PI));
                tempRecords += (speed-0.002);
                h = tempH;
                if (tempRecords >= 0.5) {
                    // 小球落地，清零已跳跃高度
                    for (int i = 0; i < jumpCount; i++) {
                        jumpH[i] = 0;
                    }
                    tempRecords = 0;
                    h = 0;
                    if (mMainPanel != null) {
                        setBallColor(mMainPanel.getPointColor(ballX, index));
                        mMainPanel.shakeIt();
                    }
                    jumpAble = true;
                }
            }
        }
        // 返回小球所在的高度
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
        if (isGameOver) {
            return;
        }

        // 小球跳跃总高度
        int h = sumHeight();
        // 判断小球是否处于陷阱上且跳跃高度是否小于等于0
        if (getBallX() >= currentTrapX-getBallWidth()/2
                && getBallX() + getBallWidth()/2 <= currentTrapX + currentTrapWidth
                && sumHeight()<=0) {
            drop(1500,0.007);
        }
    }

    public void drop(int dropHeight) {
        drop(dropHeight, 0.01);
    }

    public void drop(int dropHeight,double changeDuration) {
        int y = getBallY();
        setGameOver(true);
        new Thread(()->{
            double duration = 0;
            while (duration <= 0.5) {
                int h = (int) (dropHeight * Math.sin((duration+0.5) * Math.PI));
                ballY = y + (dropHeight - h);
                duration += changeDuration;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mMainPanel.gameOver();
        }).start();
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
        return ballY + heightOffset;
    }

    public int getBallWidth() {
        return ballWidth;
    }

    public int getBallHeight() {
        return ballHeight;
    }

    public int getAheadSpeed() {
        return aheadSpeed;
    }

    public void setAheadSpeed(int aheadSpeed) {
        this.aheadSpeed = aheadSpeed;
    }

    public void setBallX(int ballX) {
        this.ballX = ballX;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
    public void setJumpAble(boolean jumpAble) {
        this.jumpAble = jumpAble;
    }

    public boolean isJumpAble() {
        return jumpAble;
    }

    public int getCurrentTrapWidth() {
        return currentTrapWidth;
    }

    public void setCurrentTrapWidth(int currentTrapWidth) {
        this.currentTrapWidth = currentTrapWidth;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Color getBallColor() {
        return ballColor;
    }

    public void addScore(int value) {
        new Thread(()->{
            double tempScore = getScore();
            double duration = 0;
            while (duration <= value) {
                setScore(tempScore + duration);
                duration += 1;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            setScore(Math.round((double)getScore()/10)*10);
        }).start();
    }

    /**
     * 绘制小球
     * @param g2d
     */
    public void paint(Graphics2D g2d) {
        onHit();
        ballX += aheadSpeed;
        g2d.setColor(ballColor);
        g2d.fillOval(ballX,onJump(),ballWidth,ballHeight);
    }


}
