package Grepolis.util;

import Grepolis.GrepolisBot;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

import static Grepolis.util.MyLogger.logError;

public class Image {

    public static String getImage(String imageLocation) {
        CodeSource codeSource = GrepolisBot.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            logError(e);
        }
        String jarDir = null;
        if (jarFile != null) {
            jarDir = jarFile.getParentFile().getPath();
        }
        return "file:///" + jarDir + imageLocation;
    }

    public static ImageView imageToGrayscale(ImageView imageView) {
        ColorAdjust desaturate = new ColorAdjust();
        desaturate.setSaturation(-1);
        imageView.setEffect(desaturate);
        return imageView;
    }

//    public static javafx.scene.image.Image imageToGrayscale(javafx.scene.image.Image image) {
//        for (int x = 0; x < image.getWidth(); ++x)
//            for (int y = 0; y < image.getHeight(); ++y)
//            {
//                int rgb = image.getRGB(x, y);
//                int r = (rgb >> 16) & 0xFF;
//                int g = (rgb >> 8) & 0xFF;
//                int b = (rgb & 0xFF);
//
//                int grayLevel = (r + g + b) / 3;
//                int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
//                return image;
//            }
//            return null;
//    }
//
//    public static BufferedImage toBufferedImage(javafx.scene.image.Image img) {
//
//        return SwingFXUtils.fromFXImage(img, null);
//    }
}
