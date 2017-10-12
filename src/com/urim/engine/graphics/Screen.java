package com.urim.engine.graphics;

import com.urim.engine.Game;

import java.awt.*;
import java.util.Random;

/**
 * Created by urimkrasniqi on 2017-02-07.
 */
public class Screen extends Bitmap {

    public Screen(int width, int height) {
        super(width, height);
    }

    public void render(Game game){
        game.scene.render();
        draw(game.scene, 0, 0);
    }
}
