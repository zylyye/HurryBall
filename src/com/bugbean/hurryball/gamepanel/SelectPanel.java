package com.bugbean.hurryball.gamepanel;

import com.bugbean.hurryball.core.Ball;
import com.bugbean.hurryball.core.ThreadPool;
import com.bugbean.utils.ZTools;
import java.awt.*;

public class SelectPanel extends ImagePanel {
    private Ball[] mBalls;
    public SelectPanel(String bgPath) {
        super(bgPath);
        mBalls = new Ball[6];
        //#90C7FB
        Color ballColor = new Color(94, 188, 0xfb);

        for (int i = 0; i < mBalls.length; i++) {
            mBalls[i] = new Ball(null,i);
            mBalls[i].setBallColor(ballColor);
        }

        mBalls[0].setBallX(160);
        mBalls[1].setBallX(435);
        mBalls[2].setBallX(540);
        mBalls[3].setBallX(720);
        mBalls[4].setBallX(815);
        mBalls[5].setBallX(910);
        mBalls[0].setBallY(400,0);
        mBalls[1].setBallY(400,0);
        mBalls[2].setBallY(400,0);
        mBalls[3].setBallY(400,0);
        mBalls[4].setBallY(400,0);
        mBalls[5].setBallY(400,0);

        // 小球周期性跳动
        ThreadPool.submit(()->{
            while (true) {
                for (int i = 0; i < mBalls.length; i++) {
                    try {
                        Thread.sleep(500);
                        //小球跳跃
                        mBalls[i].jump();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        ThreadPool.submit(()->{
            while(true){
                try {
                    Thread.sleep(10);
                    // 重新绘制
                    repaint();
                    ZTools.sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < mBalls.length; i++) {
            mBalls[i].paint((Graphics2D) g);
        }
    }
}
