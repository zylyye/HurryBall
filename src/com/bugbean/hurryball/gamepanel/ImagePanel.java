package com.bugbean.hurryball.gamepanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private Image bgImage;

    public ImagePanel(String bgPath) {
        try {
            bgImage = ImageIO.read(new File(bgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
    }
}
