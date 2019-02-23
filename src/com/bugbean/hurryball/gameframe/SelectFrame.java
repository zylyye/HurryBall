package com.bugbean.hurryball.gameframe;

import com.bugbean.hurryball.core.Manifest;
import com.bugbean.hurryball.events.PlayerNumSelectEvent;
import com.bugbean.hurryball.gamepanel.ImagePanel;
import com.bugbean.hurryball.gamepanel.SelectPanel;
import com.bugbean.utils.ZTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SelectFrame extends JFrame {

    private static SelectFrame sSelectFrame;
    private int width = 1024;
    private int height = 768;
    private JButton playerOne;
    private JButton playerTwo;
    private JButton playerThree;

    public static SelectFrame getSelectFrame() {
        if (sSelectFrame == null) {
            sSelectFrame = new SelectFrame();
        }
        return sSelectFrame;
    }

    private SelectFrame() {
        playerOne = new JButton();
        playerTwo = new JButton();
        playerThree = new JButton();


        playerOne.setContentAreaFilled(false);
        playerTwo.setContentAreaFilled(false);
        playerThree.setContentAreaFilled(false);


        playerOne.setBounds(41,164,283,304);
        playerTwo.setBounds(372,166,283,305);
        playerThree.setBounds(696,167,283,305);

        playerOne.addActionListener(new PlayerNumSelectEvent(this,1));
        playerTwo.addActionListener(new PlayerNumSelectEvent(this,2));
        playerThree.addActionListener(new PlayerNumSelectEvent(this,3));

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        };

        playerOne.addMouseListener(mouseListener);
        playerTwo.addMouseListener(mouseListener);
        playerThree.addMouseListener(mouseListener);

        add(playerOne);
        add(playerTwo);
        add(playerThree);
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel bgPanel = new SelectPanel("images/select_bg.png");
        add(bgPanel);
        setSize(width,height);
        ZTools.makeItMoveAble(this);
        setLocationRelativeTo(null);
        setIconImage(Manifest.getIcon());
        setVisible(true);
    }

    public static void main(String[] args) {
        new SelectFrame();
    }
}
