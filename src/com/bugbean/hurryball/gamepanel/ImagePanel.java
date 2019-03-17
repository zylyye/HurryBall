package com.bugbean.hurryball.gamepanel;

import com.bugbean.hurryball.core.Manifest;
import javax.swing.*;
import java.awt.*;

/**
 * 背景面板
 */
public class ImagePanel extends JPanel {
    private Image bgImage;
    /**
     * 面板背景图片路径
     * @param bgPath
     */
    public ImagePanel(String bgPath) {
        bgImage = Manifest.getImage(bgPath);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
    }
}