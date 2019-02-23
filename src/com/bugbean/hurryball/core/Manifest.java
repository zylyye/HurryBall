package com.bugbean.hurryball.core;

import javax.swing.*;
import java.awt.*;

public class Manifest {
    private static Image icon;

    public static Image getIcon() {
        if (icon == null) {
            icon = new ImageIcon("images/logo_icon.png").getImage();
        }
        return icon;
    }
}
