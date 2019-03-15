package com.bugbean.hurryball.events;

import com.bugbean.hurryball.gameframe.LoginFrame;
import com.bugbean.hurryball.gameframe.SelectFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginStartGameEvent implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO 开始游戏
        LoginFrame.getLoginFrame().close();
        //获得玩家数量选择窗口
        SelectFrame.getSelectFrame().setVisible(true);
    }
}
