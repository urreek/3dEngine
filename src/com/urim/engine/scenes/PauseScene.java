package com.urim.engine.scenes;

import com.urim.engine.Game;

import java.awt.event.KeyEvent;

/**
 * Created by urimkrasniqi on 2017-03-07.
 */
public class PauseScene extends GameScene {
    public static final String[] menu = {"About", "Exit"};
    public int selected = 0;
    private int fontSize = 6;

    public PauseScene(Game game, int width, int height) {
        super(game, width, height);
    }

    @Override
    public void tick(boolean[] keys) {
        if(keys[KeyEvent.VK_UP]){
            if(!up && selected > 0) {
                selected--;
                up = true;
            }
        }
        else {
            up = false;
        }

        if(keys[KeyEvent.VK_DOWN]){
            if(!down && selected < 1) {
                selected++;
                down = true;
            }
        }
        else {
            down = false;
        }
        if(keys[KeyEvent.VK_ENTER]){
            if(!use) {
                if(menu[selected] == "About"){

                }
                else if(menu[selected] == "Exit"){
                    game.closeGame();
                }

                use = true;
            }
        }
        else {
            use = false;
        }
    }

    @Override
    public void render() {
        for(int i = 0; i < menu.length; i++){
            if(selected == i){
                scaleDraw(menu[i], 4,width/2 - (4 * fontSize * menu[i].length()/2),height/2 + i * 16 * 4, 0xff0000);
            }
            else{
                scaleDraw(menu[i], 4,width/2 - (4 * fontSize * menu[i].length()/2),height/2 + i * 16 * 4, 0xffffff);
            }
        }
    }
}
