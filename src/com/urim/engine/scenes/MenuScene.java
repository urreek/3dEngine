package com.urim.engine.scenes;

import com.urim.engine.Constants;
import com.urim.engine.Game;

import java.awt.event.KeyEvent;

/**
 * Created by urimkrasniqi on 2017-03-04.
 */
public class MenuScene extends GameScene {

    public static final String title = Constants.TITLE;
    public static final String[] menu = {"Play", "Options", "Exit"};
    public int rWidth = 800;
    public int rHeight = 600;
    public int selected = 0;
    private int fontSize = 6;

    public MenuScene(Game game, int width, int height) {
        super(game, width, height);
    }

    public void setResolution(int width , int height){
        rWidth = width;
        rHeight = height;
    }

    public void render() {
        fill(0,0,width,height,0xffffff);
        scaleDraw(title,4,width/2 - (4 * fontSize * title.length()/2) ,height/4, 0);

        for(int i = 0; i < menu.length; i++){
            if(selected == i){
                scaleDraw(menu[i], 4,width/2 - (4 * fontSize * menu[i].length()/2),height/2 + i * 16 * 4, 0xff0000);
            }
            else{
                scaleDraw(menu[i], 4,width/2 - (4 * fontSize * menu[i].length()/2),height/2 + i * 16 * 4, 0);
            }
        }
    }

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
            if(!down && selected < 2) {
                selected++;
                down = true;
            }
        }
        else {
            down = false;
        }
        if(keys[KeyEvent.VK_ENTER]){
            if(!use) {
                if(menu[selected] == "Play"){
                    game.setGameScene(new PlayScene(game, width, height, rWidth, rHeight));
                    game.newGame();
                }
                else if(menu[selected] == "Options"){
                    game.setGameScene(new OptionsScene(game, width, height));
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
}
