package com.bugbean.hurryball.events;

import com.bugbean.hurryball.gameframe.LoginFrame;
import com.bugbean.hurryball.gameframe.MainFrame;
import com.bugbean.hurryball.gameframe.SelectFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginStartGameEvent implements ActionListener {
    private LoginFrame mLoginFrame;

    public LoginStartGameEvent(LoginFrame loginFrame) {
        mLoginFrame = loginFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO 开始游戏
        mLoginFrame.dispose();
        SelectFrame.getSelectFrame().setVisible(true);
    }
}
