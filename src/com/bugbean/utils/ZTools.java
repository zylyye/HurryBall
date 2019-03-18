package com.bugbean.utils;

import java.awt.*;
import java.awt.event.*;

/**
 * 游戏工具类
 */
public class ZTools {

    /**
     * 使窗口可移动
     * @param frame 窗口对象
     */
    public static void makeItMoveAble(Frame frame) {
        // 窗口鼠标动作监听器
        MouseMotionListener m = new FrameDragListener(frame);
        //为窗口添加鼠标事件
        frame.addMouseListener(new MouseAdapter() {
            @Override
            // 鼠标按下
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int x = e.getX();
                    int y = e.getY();
                    ((FrameDragListener) m).setPoint(x, y);
                    frame.addMouseMotionListener(m);
                }
            }

            @Override
            // 鼠标释放
            public void mouseReleased(MouseEvent e) {
                frame.removeMouseMotionListener(m);
            }
        });
    }

    /**
     * windows系统强制画面同步
     */
    public static void sync() {
        if (!System.getProperty("os.name").equalsIgnoreCase("linux")) {
            Toolkit.getDefaultToolkit().sync();
        }
    }

    /**
     * 窗体拖动监听器类
     */
    private static class FrameDragListener extends MouseMotionAdapter {
        // 鼠标点击时，点击位置在X轴上离窗体左上角的距离
        private int x;
        // 鼠标点击时，点击位置在Y轴上离窗体左上角的距离
        private int y;
        // 作用的窗体对象
        private Frame mFrame;

        public FrameDragListener(Frame frame) {
            mFrame = frame;
        }

        // 设置点击初始位置
        public void setPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int nX = e.getXOnScreen() - x;
            int nY = e.getYOnScreen() - y;
            mFrame.setLocation(nX, nY);
        }
    }
}
