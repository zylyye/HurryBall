package com.bugbean.hurryball.gameframe;

import com.bugbean.hurryball.core.Manifest;
import com.bugbean.hurryball.events.PlayerNumSelectEvent;
import com.bugbean.hurryball.gamepanel.SelectPanel;
import com.bugbean.utils.ZTools;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 玩家数量选择窗口单例类
 */
public class SelectFrame extends BaseFrame{
    private static SelectFrame sSelectFrame;
    private int width = 1024;
    private int height = 768;
    private JButton playerOne;
    private JButton playerTwo;
    private JButton playerThree;

    private SelectFrame() {
        // 1、2、3个玩家选择按钮
        playerOne = new JButton();
        playerTwo = new JButton();
        playerThree = new JButton();
        // 设置背景透明
        playerOne.setContentAreaFilled(false);
        playerTwo.setContentAreaFilled(false);
        playerThree.setContentAreaFilled(false);
        // 设置按钮渲染位置
        playerOne.setBounds(41, 164, 283, 304);
        playerTwo.setBounds(372, 166, 283, 305);
        playerThree.setBounds(696, 167, 283, 305);
        // 按钮添加点击事件监听器
        playerOne.addActionListener(new PlayerNumSelectEvent(this, 1));
        playerTwo.addActionListener(new PlayerNumSelectEvent(this, 2));
        playerThree.addActionListener(new PlayerNumSelectEvent(this, 3));
        // 添加鼠标移入移出事件监听，用于鼠标移动到按钮上时，改变鼠标图标
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        };

        playerOne.addMouseListener(mouseListener);
        playerTwo.addMouseListener(mouseListener);
        playerThree.addMouseListener(mouseListener);

        // 将按钮添加到窗体面板
        add(playerOne);
        add(playerTwo);
        add(playerThree);
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加背景面板
        JPanel bgPanel = new SelectPanel("images/select_bg.png");
        add(bgPanel);
        setSize(width, height);
        addEscBackListener();
        ZTools.makeItMoveAble(this);
        // 设置窗体居中
        setLocationRelativeTo(null);
        setIconImage(Manifest.getIcon());
    }

    /**
     * 获取玩家选择窗口单例
     *
     * @return 选择窗口单例对象
     */
    public static SelectFrame getSelectFrame() {
        // 对象懒加载
        if (sSelectFrame == null) {
            sSelectFrame = new SelectFrame();
        }
        return sSelectFrame;
    }

    // 添加Esc返回登录窗口
    private void addEscBackListener() {
        JRootPane rp = this.getRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
        InputMap inputMap = rp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(stroke, "ESCAPE");
        rp.getActionMap().put("ESCAPE", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                SelectFrame.getSelectFrame().close();
                LoginFrame.getLoginFrame().open();
            }
        });
    }
}
