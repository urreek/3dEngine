package com.urim.engine.graphics;

/**
 * Created by urimkrasniqi on 2017-02-07.
 */
public class Bitmap {

    public final int width;
    public final int height;
    public final int [] pixels;

    private static final String chars = "" + //
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?\"'/\\<>()[]{}" + //
            "abcdefghijklmnopqrstuvwxyz_               " + //
            "0123456789+-=*:;ÖÅÄå                      " + //
            "";

    public Bitmap(int width, int height){
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    public void draw(Bitmap bitmap, int xOffset, int yOffset){
        for (int y = 0; y < bitmap.height; y++){
            int yPix = y + yOffset;
            if(yPix < 0 || yPix >= height)
                continue;

            for(int x = 0; x < bitmap.width; x++){
                int xPix = x + xOffset;
                if(xPix < 0 || xPix >= width)
                    continue;

                int src = bitmap.pixels[x + y * bitmap.width];
                pixels[xPix + yPix * width] = src;
            }
        }
    }

    public void draw(Bitmap bitmap, int xOffset, int yOffset, int width, int height){
        for (int y = 0; y < height; y++){
            int yPix = y + yOffset;
            if(yPix < 0 || yPix >= height)
                continue;

            for(int x = 0; x < width; x++){
                int xPix = x + xOffset;
                if(xPix < 0 || xPix >= width)
                    continue;
                int scale = width/bitmap.width;
                int src = bitmap.pixels[x/scale + y/scale * bitmap.width];
                pixels[xPix + yPix * width] = src;
            }
        }
    }


    public void draw(Bitmap bitmap, int xOffset, int yOffset, int col, boolean all){
        for (int y = 0; y < bitmap.height; y++){
            int yPix = y + yOffset;
            if(yPix < 0 || yPix >= height)
                continue;

            for(int x = 0; x < bitmap.width; x++){
                int xPix = x + xOffset;
                if(xPix < 0 || xPix >= width)
                    continue;

                int src = bitmap.pixels[x + y * bitmap.width];
                if(all){
                    if(src != col) {
                        pixels[xPix + yPix * width] = src;
                    }
                }
                else{
                    if(src == col) {
                        pixels[xPix + yPix * width] = src;
                    }
                }

            }
        }
    }


    public void scaleDraw(Bitmap bitmap, int scale, int xOffset, int yOffset){
        for (int y = 0; y < bitmap.height * scale; y++){
            int yPix = y + yOffset;
            if(yPix < 0 || yPix >= height)
                continue;

            for(int x = 0; x < bitmap.width * scale; x++){
                int xPix = x + xOffset;
                if(xPix < 0 || xPix >= width)
                    continue;

                int src = bitmap.pixels[x/scale + y/scale * bitmap.width];

                    pixels[xPix + yPix * width] = src;
            }
        }
    }

    public void scaleDraw(Bitmap bitmap, int scale, int xOffset, int yOffset, int col){
        for (int y = 0; y < bitmap.height * scale; y++){
            int yPix = y + yOffset;
            if(yPix < 0 || yPix >= height)
                continue;

            for(int x = 0; x < bitmap.width * scale; x++){
                int xPix = x + xOffset;
                if(xPix < 0 || xPix >= width)
                    continue;

                int src = bitmap.pixels[x/scale + y/scale * bitmap.width];
                if (Art.getCol(src) == 0xffffff) {
                    pixels[xPix + yPix * width] = src;
                }
            }
        }
    }



    public void draw(Bitmap bitmap, int xOffs, int yOffs, int xo, int yo, int w, int h, int col) {
        for (int y = 0; y < h; y++) {
            int yPix = y + yOffs;

            if (yPix < 0 || yPix >= height) continue;

            for (int x = 0; x < w; x++) {
                int xPix = x + xOffs;
                if (xPix < 0 || xPix >= width) continue;

                int src = bitmap.pixels[(x + xo) + (y + yo) * bitmap.width];

                if (Art.getCol(src) == 0xffffff) {
                    pixels[xPix + yPix * width] = col;
                }
            }
        }
    }

    public void scaleDraw(Bitmap bitmap, int scale, int xOffs, int yOffs, int xo, int yo, int w, int h, int col) {
        for (int y = 0; y < h * scale; y++) {
            int yPix = y + yOffs;

            if (yPix < 0 || yPix >= height) continue;

            for (int x = 0; x < w * scale; x++) {
                int xPix = x + xOffs;
                if (xPix < 0 || xPix >= width) continue;

                int src = bitmap.pixels[(x/scale + xo) + (y/scale + yo) * bitmap.width];

                if (Art.getCol(src) == 0xffffff) {
                    pixels[xPix + yPix * width] = col;
                }
            }
        }
    }


    public void draw(String string, int x, int y, int col) {
        for (int i = 0; i < string.length(); i++) {
            int ch = chars.indexOf(string.charAt(i));
            if (ch < 0) continue;

            int xx = ch % 42;
            int yy = ch / 42;

            draw(Art.font, x + i * 6, y, xx * 6, yy * 8, 5, 8, col);
        }
    }

    public void scaleDraw(String string,int scale, int x, int y, int col) {
        for (int i = 0; i < string.length(); i++) {
            int ch = chars.indexOf(string.charAt(i));
            if (ch < 0) continue;

            int xx = ch % 42;
            int yy = ch / 42;

            scaleDraw(Art.font, scale, x + i * 6 * scale, y, xx * 6, yy * 8, 5, 8, col);
        }
    }


    public void fill(int x0, int y0, int x1, int y1, int color) {
        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                pixels[x + y * width] = color;
            }
        }
    }


}
