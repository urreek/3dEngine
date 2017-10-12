package com.urim.engine.scenes;

import com.urim.engine.Constants;
import com.urim.engine.Game;

/**
 * Created by urimkrasniqi on 2017-03-06.
 */
public class IntroScene extends GameScene {

    private String title = Constants.TITLE;
    private int x = 0;
    private int fontSize = 6;

    public IntroScene(Game game, int width, int height) {
        super(game, width, height);
    }

    public void render() {
        fill(0,0,width,height,0xffffff);
        scaleDraw(title,4,x - (4 * fontSize * title.length()) ,height/4, 0x000000);
    }

    public void tick(boolean[] keys) {
        x += 2;
        if(x - (4 * fontSize * title.length()) >= width/2 - (4 * fontSize * title.length()/2)) {
            game.setGameScene(new MenuScene(game, width, height));
        }


    }
}
