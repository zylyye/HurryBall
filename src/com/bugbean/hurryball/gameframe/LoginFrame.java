package com.bugbean.hurryball.gameframe;

import com.bugbean.hurryball.events.LoginCancelGameEvent;
import com.bugbean.hurryball.events.LoginStartGameEvent;
import com.bugbean.hurryball.gamepanel.ImagePanel;
import com.bugbean.utils.ZTools;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LoginFrame extends JFrame{
    private JButton loginBtn;
    private JButton cancelBtn;
    private int width = 1024;
    private int height = 768;

    public LoginFrame(){
        loginBtn=new JButton("");
        loginBtn.setBounds(630, 468, 300, 70);
        loginBtn.setContentAreaFilled(false);
        loginBtn.addActionListener(new LoginStartGameEvent(this));
        this.add(loginBtn);

        cancelBtn=new JButton("");
        cancelBtn.setBounds(630, 570, 300, 70);
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.addActionListener(new LoginCancelGameEvent());
        this.add(cancelBtn);

        JPanel bgPanel = new ImagePanel("images/login_bg_with_right_logo.png");
        this.add(bgPanel);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
		this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("BBR/2.jpg").getImage());
        this.setVisible(true);
        ZTools.makeItMoveAble(this);
    }
    public static void main(String[] args) {
        new LoginFrame();
    }
}