package com.bugbean.hurryball.core;

import com.bugbean.hurryball.gameframe.MainFrame;
import com.bugbean.hurryball.gamepanel.MainPanel;


/**
 * 游戏画面节奏控制
 */
public class GameTuneController extends AbstractGameTuneController {

    public GameTuneController(MainFrame mainFrame) {
        super(mainFrame);
    }

    @Override
    public void process() {
        ThreadPool.submit(()->{
//            mMainPanel.setShadowAble(false);
//            mMainPanel.setRotateAble(false);
//            mMainPanel.setAccelerateAble(false);
            sleep(12000);

            //00:12

            //旋转画面
            mainPanel.rotate(30);
            sleep(13500);

            //00:25

            //缩放画面
            mainPanel.setScale(0.8, 0.8);

            //地板变色
            mainPanel.randomAllColor(24000);
            sleep(23500);

            //00:49

            //地板前移
            mainPanel.ballAhead(null,15000,0);
            sleep(14500);

            //1:04

            //加快移动速度
            mainFrame.setFlushSpeed(mainFrame.getFlushSpeed()-4);
            sleep(9800);


            //1:13
            mainPanel.rotate(-30);
            mainPanel.setScale(1,1);
            mainFrame.setFlushSpeed(mainFrame.getFlushSpeed() + 3);

            mainPanel.setShadowAble(true);
            mainPanel.setRotateAble(true);
            sleep(25500);

            //1:38
            mainPanel.setAccelerateAble(true);
            mainPanel.randomAllColor(71000);

        });
    }
}
