package com.bugbean.hurryball.events;

import com.bugbean.hurryball.gameframe.LoginFrame;
import com.bugbean.hurryball.gameframe.SelectFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 开始按钮点击监听器
 */
public class LoginStartGameEvent implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        // 开关闭开始界面
        LoginFrame.getLoginFrame().close();
        //获得玩家数量选择窗口并打开
        SelectFrame.getSelectFrame().open();
    }
}
