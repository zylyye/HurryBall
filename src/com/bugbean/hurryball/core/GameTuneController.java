package com.bugbean.hurryball.core;

import com.bugbean.hurryball.gameframe.MainFrame;
import com.bugbean.hurryball.gamepanel.MainPanel;


/**
 * 游戏画面节奏控制
 */
public class GameTuneController {
    private MainFrame mMainFrame;
    private MainPanel mMainPanel;


    public GameTuneController(MainFrame mainFrame) {
        mMainFrame = mainFrame;
        mMainPanel = mMainFrame.getMainPanel();
    }

    public GameTuneController(MainPanel mainPanel) {
        mMainPanel = mainPanel;
    }


    public void start() {
        new Thread(()->{
//            mMainPanel.setShadowAble(false);
//            mMainPanel.setRotateAble(false);
//            mMainPanel.setAccelerateAble(false);
            sleep(12000);

            //00:12

            //旋转画面
            mMainPanel.rotate(30);
            sleep(13500);

            //00:25

            //缩放画面
            mMainPanel.setScale(0.8, 0.8);

            //地板变色
            mMainPanel.randomAllColor(24000);
            sleep(23500);

            //00:49

            //地板前移
            mMainPanel.ballAhead(null,15000,0);
            sleep(14500);

            //1:04

            //加快移动速度
            mMainFrame.setFlushSpeed(mMainFrame.getFlushSpeed()-4);
            sleep(9800);


            //1:13
            mMainPanel.rotate(-30);
            mMainPanel.setScale(1,1);
            mMainFrame.setFlushSpeed(mMainFrame.getFlushSpeed() + 3);

            mMainPanel.setShadowAble(true);
            mMainPanel.setRotateAble(true);
            sleep(25500);

            //1:38
            mMainPanel.setAccelerateAble(true);
            mMainPanel.randomAllColor(71000);



        }).start();
    }


    /**
     * 线程休眠指定毫秒数
     * @param timeout
     */
    public void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
