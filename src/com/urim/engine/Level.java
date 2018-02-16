package com.urim.engine;

import com.urim.engine.entities.Block;
import com.urim.engine.entities.SolidBlock;
import com.urim.engine.utility.Vector3;

import java.util.Random;

/**
 * Created by urimkrasniqi on 2017-02-15.
 */
public class Level {
    public static int width, height;
    public Block[] blocks;

    public Level(int width, int height){
        Level.width = width;
        Level.height= height;

        generateLevel();
    }

    public void generateLevel(){
        blocks = new Block[width * height];

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Block block;
                Random random = new Random();
                if(random.nextInt(4) == 3){
                    block = new SolidBlock(new Vector3(x, 0, y));
                }
                else{
                    block = new Block();
                }
                blocks[x + y * width] = block;
            }
        }
    }

    public Vector3 getPlayerSpawn(){
        Random random = new Random();
        float x = random.nextInt(width);
        float y = random.nextInt(height);

        while(blocks[(int)x + (int)y * width] instanceof SolidBlock){
            x = random.nextInt(width);
            y = random.nextInt(height);
        }

        return new Vector3(x+0.5f,0, y+0.5f);
    }

    public Block createBlock(int x, int y){
        if(x < 0 || y < 0 || x >= width || y >= height){
            return new SolidBlock(new Vector3(x, 0, y));
        }
        return  blocks[x + y * width];
    }
}
