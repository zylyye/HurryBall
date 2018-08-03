package com.bugbean.hurryball;

import com.bugbean.hurryball.gameframe.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            //TODO game start here
            new LoginFrame();
        });
    }
}
