package com.bugbean.hurryball;

import com.bugbean.hurryball.gameframe.LoginFrame;
import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            // game start here
            LoginFrame.getLoginFrame().open();
        });
    }
}
