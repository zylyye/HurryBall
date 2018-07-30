package com.bugbean.hurryball.events;

import com.bugbean.database.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author 姚瑞
 */

public class UserLoginEvent implements ActionListener {
    protected JTextField mUserText;
    protected JPasswordField mUserPwd;
    protected Component mParent;
    protected UserManager mUserManager;

    public UserLoginEvent(JTextField userText, JPasswordField userPwd) {
        this(null, userText, userPwd);
    }

    public UserLoginEvent(Component parent, JTextField userText, JPasswordField userPwd) {
        mParent = parent;
        mUserText = userText;
        mUserPwd = userPwd;
        mUserManager = UserManager.getUserManager();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO 用户点击登录按钮事件处理
        String username = mUserText.getText();
        String password = new String(mUserPwd.getPassword());
        /*
        1.检测用户名密码是否合法
        2.检测用户是否存在
        3.检测用户名与密码是否匹配
         */
    }
}
