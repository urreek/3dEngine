package com.urim.engine.scenes;

import com.urim.engine.Game;
import com.urim.engine.graphics.Bitmap3D;

import java.awt.event.KeyEvent;

/**
 * Created by urimkrasniqi on 2017-03-04.
 */
public class PlayScene extends GameScene {

    public Bitmap3D viewPort;
    public boolean pause;
    public GameScene pauseScene;

    public PlayScene(Game game, int width, int height, int rWidth, int rHeight) {
        super(game, width, height);
        viewPort = new Bitmap3D(rWidth, rHeight);
        pauseScene = new PauseScene(game, width, height);
        pause = false;
    }

    public void tick(boolean[] keys){
        if(keys[KeyEvent.VK_ESCAPE]){
            if(!esc) {
                esc = true;
                if(pause)
                    pause = false;
                else
                    pause = true;
            }
        }
        else{
            esc = false;
        }


        if(pause) {
            pauseScene.tick(keys);
        }
        else {
            game.player.update(keys);
            game.player.checkCollision(game.level.blocks);
        }
    }

    public void render(){
        viewPort.render(game);
        draw(viewPort, 0, 0,width,height);

        if(pause){
            for(int i = 0; i < width * height; i++){
                pixels[i] = (pixels[i] & 0xfcfcfc) >> 2;
            }
            pauseScene.render();
            draw(pauseScene,0,0, 0x000000, true);
        }
    }
}
