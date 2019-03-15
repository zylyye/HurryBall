package com.bugbean.hurryball.gameframe;

import javax.swing.*;

/**
 * @author zhuyilong
 * @since 2019/3/15
 */

/**
 * 游戏窗口基类
 */
public class BaseFrame extends JFrame {
    /**
     * 显示窗口
     */
    public void open() {
        this.setVisible(true);
    }

    /**
     * 隐藏窗口
     */
    public void close() {
        this.dispose();
    }
}
