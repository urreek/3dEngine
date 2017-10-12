package com.urim.engine.scenes;

import com.urim.engine.Game;
import com.urim.engine.graphics.Bitmap;

import java.awt.*;

/**
 * Created by urimkrasniqi on 2017-03-04.
 */
public abstract class GameScene extends Bitmap{


    public Game game;
    public static boolean up , down , use, esc = false;

    public GameScene(Game game, int width, int height) {
        super(width, height);
        this.game = game;
    }

    public abstract void tick(boolean[] keys);

    public abstract void render();

}