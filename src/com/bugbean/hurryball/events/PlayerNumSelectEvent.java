package com.bugbean.hurryball.events;

import com.bugbean.hurryball.gameframe.MainFrame;
import com.bugbean.hurryball.gameframe.SelectFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerNumSelectEvent implements ActionListener {
    private SelectFrame mSelectFrame;
    private int mPlayerNum;

    public PlayerNumSelectEvent(SelectFrame selectFrame,int playerNum) {
        mSelectFrame = selectFrame;
        mPlayerNum = playerNum;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mSelectFrame.dispose();
        new MainFrame(mPlayerNum);
    }
}
