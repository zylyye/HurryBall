package com.bugbean.hurryball.events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author 姚瑞
 */

public class UserRegisterEvent extends UserLoginEvent {
    public UserRegisterEvent(JTextField userText, JPasswordField userPwd) {
        super(userText, userPwd);
    }

    public UserRegisterEvent(Component parent, JTextField userText, JPasswordField userPwd) {
        super(parent, userText, userPwd);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO 用户点击注册按钮事件
        String username = mUserText.getText();
        String password = new String(mUserPwd.getPassword());
        /*
        1.检测用户名密码是否合法
        2.检测用户是否存在
        3.添加用户
         */
    }
}
