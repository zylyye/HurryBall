package com.bugbean.hurryball.core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Manifest {
    private static Image icon;

    public static Image getIcon() {
        if (icon == null) {
            icon =  getImage("images/logo_icon.png");
        }
        return icon;
    }

    public static BufferedImage getImage(String imgPath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getResourceURL(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static URL getResourceURL(String path) {
        try {
            return new File(path).toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return Manifest.class.getResource("/" + path);
    }
}
