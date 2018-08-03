package com.bugbean.hurryball.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginCancelGameEvent implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO 结束游戏
        System.exit(0);
    }
}
