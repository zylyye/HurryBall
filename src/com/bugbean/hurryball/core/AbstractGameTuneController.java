package com.bugbean.hurryball.core;

import com.bugbean.hurryball.gameframe.MainFrame;
import com.bugbean.hurryball.gamepanel.MainPanel;

/**
 * @author zhuyilong
 * @since 2019/3/26
 */
public abstract class AbstractGameTuneController {
    protected MainPanel mainPanel;
    protected MainFrame mainFrame;

    public AbstractGameTuneController(MainFrame mainFrame, MainPanel mainPanel) {
        this.mainFrame = mainFrame;
        this.mainPanel = mainPanel;
    }

    public AbstractGameTuneController(MainFrame mainFrame) {
        this(mainFrame, mainFrame.getMainPanel());
    }

    public abstract void process();

    public void start() {
        ThreadPool.submit(()->{
            this.process();
        });
    }


    /**
     * 线程休眠指定毫秒数
     * @param timeout
     */
    protected void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
