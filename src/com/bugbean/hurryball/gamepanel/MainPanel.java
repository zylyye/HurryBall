package com.bugbean.hurryball.gamepanel;

import com.bugbean.hurryball.gameframe.MainFrame;
import com.bugbean.hurryball.gameframe.SelectFrame;
import com.bugbean.hurryball.core.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MainPanel extends JPanel implements KeyListener {
    /**
     * 刷新间隔偏移量
     */
    private long flushOffset = 0;

    private MainFrame mContainer;
    private Random mRandom = new Random();
    private static BufferedImage bgImage;
    private MusicManager mMusicManager;

    private int initX = 0;

    private int gameHeight = 300;

    private int[] lineStartXs;
    private int lineStartX = 00;

    private int[] lineStartYs;
    private int lineStartY = 500;

    private int[] lineWidths;
    private int lineWidth = 100;

    private int[] trap;
    private int trapHeight = 500;

    private int[] lineDurations;
    private int lineDuration = 20;
    private int speed = 2;
    private Color[] mColors;
    private Color lineColor;
    private Ball[] balls;
    private int ballCount = 1;
    private Ball ball;
//    private int lineCounts = 26;
    private int lineCounts = 56;

    private Barrier[] bars;
    private int barNum = 1;

    private Pill[][] pills;
    private int pillCount = 2;
    private int pillScore = 50;

    private ShadowPill[][] shadowPills;
    private int shadowPillCount = 2;
    private int shadowPillScore = 30;

    private SpeedPill[][] speedPills;
    private int speedPillCount = 1;
    private int speedPillScore = 70;




    //游戏是否暂停
    private boolean isPaused = false;

    //滑块降落高度
    private int dropHeight = 200;


    //旋转参数
    private double unite = Math.PI/180;
    private double theta = 0;
    private double lastTheta;
    private double rotateTheta = 90*unite;
    private double minRotateTheta = 15*unite;
    private double rotateDuration = 0;
    private double[] availableRotateTheta = {15, 30, 45};

    //抖动参数
    private double shakeY = 0;
    private double shakeHeight = 15;
    private double shakeDuration = 0;


    private boolean isOnAccelerate = false;
    private boolean isBallOnAhead = false;

    //缩放
    private double scaleX = 2.0;
    private double scaleY = 2.0;
    private boolean isOnScale = false;

    //游戏效果触发控制
    private boolean aceleratePillVisible = true;
    private boolean accelerateAble = true;
    private boolean rotatePillVisible = true;
    private boolean rotateAble = true;
    private boolean shadowPillVisible = true;
    private boolean shadowAble = true;
    private boolean bgVisible = true;

    private Color[] pairColor = {new Color(0x8c,0xc4,0xfa), new Color(182, 0, 255)};

    private int[] trapIndex;
    private int trapNum = 2;




    public MainPanel(MainFrame container,int playerNum) {
        mContainer = container;
        ballCount = playerNum;
        mMusicManager = MusicManager.getMusicManager();
        try {
            bgImage = ImageIO.read(new File("images/game_bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        lineStartXs   = new int[ballCount];
        lineStartYs   = new int[ballCount];
        lineWidths    = new int[ballCount];
        lineDurations = new int[ballCount];
        trap          = new int[ballCount];
        trapIndex     = new int[trapNum];
        randomTrap();
        lineStartY = mContainer.getHeight() - (4-ballCount)*150;
        arrayFill(lineStartXs,lineStartX);
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

        speedPills = new SpeedPill[ballCount][speedPillCount];
        for (int i = 0; i < ballCount; i++) {
            for (int j = 0; j < speedPillCount; j++) {
                speedPills[i][j] = new SpeedPill();
                speedPills[i][j].setPillY(lineStartYs[i]-200);
            }
        }

        ball = balls[0];
        ball.setBallY(lineStartY,5);


        mColors = new Color[lineCounts];
        for (int i = 0; i < lineCounts; i++) {
            mColors[i] = randomColor();
        }
        setScale(1.0,1.0,0.003);
        mMusicManager.play(0);
//        rotate(360*unite,0.004);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        long start = System.currentTimeMillis();
        Graphics2D g2d = (Graphics2D) g;
        if (bgVisible) {
            g2d.drawImage(bgImage,0,0,1920,1080,null);
        }

        g2d.setFont(new Font("SansSerif",Font.BOLD, 16));

        g2d.setColor(pairColor[0]);
        g2d.drawString("旋转效果："+(isRotateAble()?"ON":"OFF"),40,26);
        g2d.drawString("阴影效果："+(isShadowAble()?"ON":"OFF"),40,49);
        g2d.drawString("加速效果："+(isAccelerateAble()?"ON":"OFF"),40,71);
        RoundRectangle2D rotatePillShape = new RoundRectangle2D.Double(10, 10, 20, 20,6,6);
        Ellipse2D shadowPillShape = new Ellipse2D.Double(10, 35, 20, 15);
        g2d.fill(rotatePillShape);
        g2d.fill(shadowPillShape);
        g2d.setPaint(new GradientPaint(0,0,pairColor[0],10,10,pairColor[1],true));
        Rectangle2D speedPillShape = new Rectangle2D.Double(10, 55, 20, 20);

        g2d.fill(speedPillShape);



        //画面缩放
        g2d.scale(scaleX,scaleY);
        g2d.translate(0,shakeY);
        g2d.rotate(theta,getWidth()/2,getHeight()/2);
        for (int j = ballCount-1; j >= 0; j--) {

            //绘制游戏分数
            g2d.setColor(balls[j].getBallColor());
            g2d.fillOval(700,lineStartYs[j]-280,balls[j].getBallWidth()/3*2,balls[j].getBallHeight()/3*2);
//            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
            g2d.setFont(new Font("SansSerif",Font.BOLD, 20));

            g2d.drawString("SCORE:"+(int)(balls[j].getScore()),760,lineStartYs[j]-255);

            if(rotatePillVisible) {
                for (int i = 0; i < pillCount; i++) {
                    pills[j][i].paint(g2d);

                    if (pillHitTest(balls[j], pills[j][i])) {
                        pills[j][i].randomX();
                        if (rotateAble) {
                            rotate(availableRotateTheta[mRandom.nextInt(availableRotateTheta.length)]);
                        }
                        balls[j].addScore(pillScore);
                    }
                }
            }

            if(shadowPillVisible) {
                for (int i = 0; i < shadowPillCount; i++) {
                    shadowPills[j][i].paint(g2d);
                    if (pillHitTest(balls[j], shadowPills[j][i])) {
                        shadowPills[j][i].randomX();
                        if(shadowAble){
                            randomShadow(20, 80);
                        }
                        balls[j].addScore(shadowPillScore);
                    }
                }
            }

            if(aceleratePillVisible) {
                for (int i = 0; i < speedPillCount; i++) {
                    speedPills[j][i].paint(g2d);
                    if (pillHitTest(balls[j], speedPills[j][i])) {
                        speedPills[j][i].randomX();
                        if(accelerateAble) {
                            accelerateFlushSpeed(5000, 4);
                        }
                        balls[j].addScore(speedPillScore);
                    }
                }
            }



            for (int i = 0; i < lineCounts; i++) {
                g2d.setColor(lineColor);
                g2d.setStroke(new BasicStroke(10));
                int startX = lineStartXs[j] + i * (lineDurations[j] + lineWidths[j]);
                int endX = startX + lineWidths[j];
                int startY = lineStartYs[j];
                if (startX < getWidth() - 400) {



                    //滑块上滑
                    for (int k = trapNum-1; k >=0; k--) {
                        if (i == trapIndex[k]) {
                            int h = 0;
                            if (startX < getWidth() - 1000) {
                                h = trapHeight;
                            } else {
                                double d = (double) (startX - (getWidth() - 1000)) / ((1000 - 400) * 2);
                                h = (int) (trapHeight * Math.sin((d + 0.5) * Math.PI));
                            }
//                        balls[j].setCurrentTrapHeight(h);
                            if(true) {
                                balls[j].setCurrentTrapX(startX);
                                balls[j].setCurrentTrapWidth(lineWidth);
                            }
                            startY -= h;
                        }
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

                //垂直杆状障碍物
                if(false) {
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
                randomTrap();
            }


            balls[j].paint(g2d);
            long end = System.currentTimeMillis();
            // 设置刷新时间偏移量
            this.setFlushOffset(end - start);
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
        if (e.getKeyCode() == KeyEvent.VK_0 && ballCount > 1) {
            ballJump(2);
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            rotate(30);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            accelerateFlushSpeed(5000,5);
        }

        if (e.getKeyCode() == KeyEvent.VK_C) {
            Color bg = new Color(255, 255, 255, (int)(Math.random()*255));
            this.setBackground(bg);
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            ballAhead(ball,5000,2);
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            setScale(scaleX+0.2, scaleY + 0.2);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            setScale(scaleX - 0.2, scaleY - 0.2);
        }
        if (e.getKeyCode() == KeyEvent.VK_B) {
            randomAllColor(5000);
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            ball.drop(500,0.007);
        }

        if (e.getKeyCode() == KeyEvent.VK_G) {
            if (bgVisible) {
                bgVisible = false;
            } else {
                bgVisible = true;
            }
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (isPaused) {
                setPaused(false);
                mMusicManager.play(0);
            } else {
                setPaused(true);
                mMusicManager.stop(0);
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
        rotate(rTheta,0.01);
    }
    public void rotate(double rTheta,double changeDuration) {
        new Thread(() ->{
            while (rotateDuration <= 0.5) {
                double tempTheta = rTheta *unite* Math.sin(rotateDuration * Math.PI);
                rotateDuration += changeDuration;
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
            res = true;
        }
        return res;
    }

    public void randomShadow(int min, int max) {
        int alpha = mRandom.nextInt(max - min) + min;
        Color bg = new Color(218, 255, 239, alpha);
        this.setBackground(bg);
    }

    public void accelerateFlushSpeed(int timeout, int value) {
        if (isOnAccelerate) {
            return;
        }
        isOnAccelerate = true;
        int originalFlushSpeed = mContainer.getFlushSpeed();
        int currentFlushSpeed = originalFlushSpeed - value;
        if (currentFlushSpeed <= 0) {
            currentFlushSpeed = originalFlushSpeed;
        }
        mContainer.setFlushSpeed(currentFlushSpeed);
        new Thread(() ->{
            try {
                Thread.sleep(timeout);
                mContainer.setFlushSpeed(originalFlushSpeed);
                isOnAccelerate = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void ballAhead(Ball b, int timeout, int ballAheadSpeed) {
        if (isBallOnAhead) {
            return;
        }
        isBallOnAhead = true;
        speed = -2 * speed;
        if (b != null) {
            ball.setAheadSpeed(ballAheadSpeed);
        }
        new Thread(() -> {
            try {
                Thread.sleep(timeout);
                speed = -speed / 2;
                if (b != null) {
                    b.setAheadSpeed(0);
                }
                isBallOnAhead = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void setScale(double sX, double sY) {
        setScale(sX,sY,0.008);
    }
    public void setScale(double sX, double sY,double changeDuration) {
        if (isOnScale) {
            return;
        }
        isOnScale = true;
        double scaleDurationX = sX - scaleX;
        double scaleDurationY = sY - scaleY;
        new Thread(()->{
            double duration = 0;
            double tempX = scaleX;
            double tempY = scaleY;
            while (duration <= 0.5) {
                scaleX = tempX + scaleDurationX * Math.sin(duration * Math.PI);
                scaleY = tempY + scaleDurationY * Math.sin(duration * Math.PI);
                duration += changeDuration;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isOnScale = false;
        }).start();
    }

    public void randomAllColor(int timeout) {
        new Thread(()->{
            int initTime = 0;
            while (initTime <= timeout) {
                for (int i = 0; i < lineCounts; i++) {
                    mColors[i] = randomColor();
                }
                try {
                    Thread.sleep(100);
                    initTime += 100;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean isAceleratePillVisible() {
        return aceleratePillVisible;
    }

    public void setAceleratePillVisible(boolean aceleratePillVisible) {
        this.aceleratePillVisible = aceleratePillVisible;
    }

    public boolean isAccelerateAble() {
        return accelerateAble;
    }

    public void setAccelerateAble(boolean accelerateAble) {
        this.accelerateAble = accelerateAble;
    }

    public boolean isRotatePillVisible() {
        return rotatePillVisible;
    }

    public void setRotatePillVisible(boolean rotatePillVisible) {
        this.rotatePillVisible = rotatePillVisible;
    }

    public boolean isRotateAble() {
        return rotateAble;
    }

    public void setRotateAble(boolean rotateAble) {
        this.rotateAble = rotateAble;
    }

    public boolean isShadowPillVisible() {
        return shadowPillVisible;
    }

    public void setShadowPillVisible(boolean shadowPillVisible) {
        this.shadowPillVisible = shadowPillVisible;
    }

    public boolean isShadowAble() {
        return shadowAble;
    }

    public void setShadowAble(boolean shadowAble) {
        this.shadowAble = shadowAble;
    }

    public void gameOver() {
        for (int i = 0; i < ballCount; i++) {
            if (!balls[i].isGameOver()) {
                return;
            }
            setPaused(true);
            mContainer.dispose();
            mMusicManager.stop(0);
            SelectFrame.getSelectFrame().setVisible(true);
        }
    }

    public void randomTrap() {
        int duration = 18/trapNum;
        trapIndex[0] = 10 + mRandom.nextInt(5);
        for (int i = 1; i < trapNum; i++) {
            trapIndex[i] = trapIndex[i - 1]+5 + mRandom.nextInt(10
            );
        }
    }

    public long getFlushOffset() {
        return flushOffset;
    }

    public void setFlushOffset(long flushOffset) {
        this.flushOffset = flushOffset;
    }
}
