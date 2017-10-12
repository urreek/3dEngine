package com.urim.engine.graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


/**
 * Created by urimkrasniqi on 2017-02-07.
 */
public class Art {
    public static Bitmap floors = loadBitmap("/floorG.png");
    public static Bitmap font = loadBitmap("/font.png");

    public static Bitmap loadBitmap(String fileName) {
        try {
            BufferedImage img = ImageIO.read(Art.class.getResource(fileName));

            int w = img.getWidth();
            int h = img.getHeight();

            Bitmap result = new Bitmap(w, h);

            img.getRGB(0, 0, w, h, result.pixels, 0, w);

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getCol(int c) {
        int r = (c >> 16) & 0xff;
        int g = (c >> 8) & 0xff;
        int b = (c) & 0xff;


        return r << 16 | g << 8 | b;
    }
}