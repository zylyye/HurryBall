package com.bugbean.hurryball.kernel;

import com.bugbean.hurryball.gameframe.MainFrame;
import com.bugbean.hurryball.gamepanel.MainPanel;

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
            mMainPanel.setShadowAble(false);
            mMainPanel.setRotateAble(false);
            mMainPanel.setAccelerateAble(false);
            sleep(12000);

            //00:12
            mMainPanel.rotate(30);
            sleep(13000);

            //00:24
            mMainPanel.setScale(0.8, 0.8);
            mMainPanel.randomAllColor(25000);
            sleep(25000);

            //00:51
            mMainPanel.ballAhead(null,15000,0);
            sleep(15000);

            //1:01
            mMainFrame.setFlushSpeed(mMainFrame.getFlushSpeed()-4);
            sleep(13000);
            mMainPanel.rotate(-30);
            mMainPanel.setScale(1,1);
            mMainFrame.setFlushSpeed(mMainFrame.getFlushSpeed() + 3);

            mMainPanel.setShadowAble(true);
            mMainPanel.setRotateAble(true);
            mMainPanel.setAccelerateAble(true);
        }).start();

    }

    public void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
