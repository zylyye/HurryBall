package com.bugbean.hurryball.gameframe;

import com.bugbean.hurryball.core.Manifest;
import com.bugbean.hurryball.events.LoginCancelGameEvent;
import com.bugbean.hurryball.events.LoginStartGameEvent;
import com.bugbean.hurryball.gamepanel.ImagePanel;
import com.bugbean.utils.ZTools;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 登录界面单例类
 */
public class LoginFrame extends BaseFrame{
    private static LoginFrame loginFrame;
    private JButton loginBtn;
    private JButton cancelBtn;
    private int width = 1024;
    private int height = 768;

    /**
     * 获取游戏开始窗口对象单例
     * @return
     */
    public static LoginFrame getLoginFrame() {
        // 对象懒加载
        if (loginFrame == null) {
            loginFrame = new LoginFrame();
        }
        return loginFrame;
    }

    private LoginFrame(){
        loginBtn=new JButton();
        loginBtn.setBounds(630, 468, 300, 70);

        //设置按钮透明
        loginBtn.setContentAreaFilled(false);

        loginBtn.addActionListener(new LoginStartGameEvent());
        this.add(loginBtn);

        cancelBtn=new JButton("");
        cancelBtn.setBounds(630, 570, 300, 70);
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.addActionListener(new LoginCancelGameEvent());
        this.add(cancelBtn);

        // 创建背景面板
        JPanel bgPanel = new ImagePanel("images/login_bg_with_right_logo.png");
        // 添加到容器中作为容器背景
        this.add(bgPanel);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        // 去除
        this.setUndecorated(true);
		this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Manifest.getIcon());
        // 窗口可移动
        ZTools.makeItMoveAble(this);
    }
}