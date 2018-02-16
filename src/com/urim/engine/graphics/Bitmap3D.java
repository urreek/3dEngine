package com.urim.engine.graphics;

import com.urim.engine.*;
import com.urim.engine.entities.Block;
import com.urim.engine.utility.Vector3;

/**
 * Created by urimkrasniqi on 2017-02-07.
 */
public class Bitmap3D extends Bitmap {

    private Vector3 cam;
    private double rot , rSin, rCos;
    private double fov;
    private double[] zBuffer;
    private double[] zBufferWall;

    public Bitmap3D(int width, int height) {
        super(width, height);
        zBuffer = new double[width * height];
        zBufferWall = new double[width];
        fov = height;
        cam = Vector3.zero;
        rot = 0;
        rSin = Math.sin(rot);
        rCos = Math.cos(rot);
    }

    public void render(Game game){
        cam = game.player.position;
        rot = game.player.rot;
        rSin = Math.sin(rot);
        rCos = Math.cos(rot);

        for(int x = 0; x < width; x++){
            zBufferWall[x] = 0;
        }

        renderFloor();
        renderLevel(game.level);
        renderFog();
    }
    public void renderFloor(){
        double floorPosition = 8;
        double ceilingPosition = 8;


        for(int y = 0; y < height; y++){
            double yd = (y - height / 2.0) / fov;

            double zd = (floorPosition + cam.y) / yd;

            if(yd < 0)
                zd = (ceilingPosition - cam.y) / -yd;


            for(int x = 0; x < width; x++){
                double xd = (x  - width / 2.0) / fov;
                xd *= zd;

                double xx = xd * rCos + zd * rSin + (0.5 + cam.x) * 16;
                double yy = zd * rCos - xd * rSin + (0.5 + cam.z) * 16;

                int xPix = (int)(xx);
                int yPix = (int)(yy);

                if(xx < 0)
                   continue;
                if(yy < 0)
                   continue;

                zBuffer[x + y * width] = zd;
                pixels[x + y * width] = Art.floors.pixels[(xPix & 7) + (-1-yPix & 7) * 16];
            }
        }
    }

    public void renderLevel(Level level){
        int width = Level.width;
        int height = Level.height;
        for(int x = -width; x <= width; x++){
            for(int z = -height; z <= height; z++){
                Block block = level.createBlock(x, z);
                Block east = level.createBlock(x + 1, z);
                Block south = level.createBlock(x, z +1);

                if(block.solid){
                    if(!east.solid){
                        renderWall(x + 1, x + 1, z, z + 1, 0);
                    }
                    if(!south.solid){
                        renderWall(x + 1, x, z + 1, z + 1, 0);
                    }
                }
                else{
                    if(east.solid){
                        renderWall(x+1, x+1, z+1, z, 0);
                    }
                    if(south.solid){
                        renderWall(x, x+1, z+1, z+1, 0);
                    }
                }
            }
        }
    }


    public void renderWall(double xLeft, double xRight, double zDistanceLeft, double zDistanceRight, double yHeight) {
        double correction = 16;
        double xcLeft = (xLeft - cam.x) * 2;
        double zcLeft = (zDistanceLeft - cam.z) * 2;

        double rotLeftSideX = xcLeft * rCos - zcLeft * rSin;
        double yCornerTL =  (-0.5 - yHeight + cam.y / correction) * 2;
        double yCornerBL = (+0.5 - yHeight + cam.y / correction) * 2;
        double rotLeftSideZ = zcLeft * rCos + xcLeft * rSin;

        double xcRight = (xRight - cam.x) * 2;
        double zcRight = (zDistanceRight - cam.z) * 2;

        double rotRightSideX = xcRight * rCos - zcRight * rSin;
        double yCornerTR =  (-0.5 - yHeight + cam.y / correction) * 2;
        double yCornerBR = (+0.5 - yHeight + cam.y / correction) * 2;
        double rotRightSideZ = zcRight * rCos + xcRight * rSin;
//------------------------------------------------------------------

        //Fix clipping-------------------------------------------------------
        double tex30 = 0;
        double tex40 = 8;
        double clip = 0.2;

        if(rotLeftSideZ < clip && rotRightSideZ < clip)
            return;

        if(rotLeftSideZ < clip){
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex30 = tex30 + (tex40 - tex30) * clip0;
        }

        if(rotRightSideZ < clip){
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex40 = tex30 + (tex40 - tex30) * clip0;
        }
        //------------------------------------------------------------------

        double xPixelLeft = (rotLeftSideX / rotLeftSideZ * fov + width / 2.0);
        double xPixelRight = (rotRightSideX / rotRightSideZ * fov + width / 2.0);

        if(xPixelLeft >= xPixelRight)
            return;

        int xPixelLeftInt = (int)Math.ceil(xPixelLeft);
        int xPixelRightInt = (int)Math.ceil(xPixelRight);

        if(xPixelLeftInt < 0){
            xPixelLeftInt = 0;
        }

        if(xPixelRightInt > width){
            xPixelRightInt = width;
        }

        double yPixelLeftTop = (yCornerTL / rotLeftSideZ * fov + height / 2.0);
        double yPixelLeftBottom = (yCornerBL / rotLeftSideZ * fov + height / 2.0);
        double yPixelRightTop = (yCornerTR / rotRightSideZ * fov + height / 2.0);
        double yPixelRightBottom = (yCornerBR / rotRightSideZ * fov + height / 2.0);



        double tex1 = 1 / rotLeftSideZ;
        double tex2 = 1 / rotRightSideZ;
        double tex3 = tex30 / rotLeftSideZ;
        double tex4 = tex40 / rotRightSideZ - tex3;

        for(int x = xPixelLeftInt; x < xPixelRightInt; x++){

            double pixelRotation = (x-xPixelLeft) / (xPixelRight - xPixelLeft);
            //Dont render walls behind other walls------------------
            double zWall = (tex1 + (tex2 - tex1) * pixelRotation);
            if(zBufferWall[x] > zWall){
                continue;
            }
            zBufferWall[x] = zWall;

            //------------------------------------------------------
            int xTexture = (int)((tex3 + tex4 * pixelRotation) / zWall);

            double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
            double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

            int yPixelTopInt = (int)Math.ceil(yPixelTop);
            int yPixelBottomInt = (int)Math.ceil(yPixelBottom);


            if(yPixelTopInt < 0){
                yPixelTopInt = 0;
            }
            if(yPixelBottomInt > height){
                yPixelBottomInt = height;
            }


            for(int y = yPixelTopInt; y < yPixelBottomInt; y++){
                double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
                int yTexture = (int)(8 * pixelRotationY);
                pixels[x + y * width] = Art.floors.pixels[8 + (xTexture & 7)  + (yTexture & 7) * 16];
                zBuffer[x + y * width] = 1 / zWall * 8;
                }
            }
    }

    public void renderSprite(double x, double y, double z){
        double correction = 16;
        double xc = (x - cam.x) * 2;
        double yc = (y + (cam.y / correction)) * 2;
        double zc = (z - cam.z) * 2;

        double rotX = xc * rCos - zc * rSin;
        double rotY = yc;
        double rotZ = zc * rCos + xc * rSin;

        double xPixel = rotX / rotZ * fov + width / 2.0;
        double yPixel = rotY / rotZ * fov + height / 2.0;

        double xPixelL = xPixel - 64 / rotZ;
        double xPixelR = xPixel + 64 / rotZ;

        double yPixelL = yPixel - 64 / rotZ;
        double yPixelR = yPixel + 64 / rotZ;

        int xpl = (int) xPixelL;
        int xpr = (int) xPixelR;
        int ypl = (int) yPixelL;
        int ypr = (int) yPixelR;

        if(xpl < 0)
            xpl = 0;
        if(xpr > width)
            xpr = width;
        if(ypl < 0)
            ypl = 0;
        if(ypr > height)
            ypr = height;

        rotZ *= 8;

        for(int yp = ypl; yp < ypr; yp++){
            for(int xp = xpl; xp < xpr; xp++){
                if(zBuffer[xp + yp * width] > rotZ){
                    pixels[xp + yp * width] = 0xff0000;
                    zBuffer[xp+ yp * width] = rotZ;
                }
            }
        }
    }

    public void renderFog(){
        for(int i = 0; i < zBuffer.length; i++) {
            double z = zBuffer[i];
            //Lightning variables that can be used
            //double xx = i%width - width/2;
            //double yy = i/width - height/2;
            int color = pixels[i];

            int brightness = (int)(50000 / ((z * z)));

            if (brightness > 255) {
                brightness = 255;
            }
            if (brightness < 0) {
                brightness = 0;
            }
            int r = (color >> 16) & 0xff;
            int g = (color >> 8) & 0xff;
            int b = (color) & 0xff;

            r = r * brightness / 255;
            g = g * brightness / 255;
            b = b * brightness / 255;
            pixels[i] = r << 16 | g << 8 | b;
        }
    }
}
