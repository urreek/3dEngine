package com.urim.engine.scenes;

import com.urim.engine.Game;

import java.awt.event.KeyEvent;

/**
 * Created by urimkrasniqi on 2017-03-06.
 */
public class OptionsScene extends GameScene {
    public static final String title = "Resolutions";
    public static final String[] menu = {"800/600", "400/300", "200/150"};
    public int selected = 0;
    private int fontSize = 6;

    public OptionsScene(Game game, int width, int height) {
        super(game, width, height);
    }

    @Override
    public void render() {
        fill(0,0,width,height,0xffffff);

        scaleDraw(title, 4,width/2 - (4 * fontSize * title.length()/2) ,height/4, 0);

        for(int i = 0; i < menu.length; i++){
            if(selected == i){
                scaleDraw(menu[i], 4,width/2 - (4 * fontSize * menu[i].length()/2),height/2 + i * 16 * 4, 0xff0000);
            }
            else{
                scaleDraw(menu[i], 4,width/2 - (4 * fontSize * menu[i].length()/2),height/2 + i * 16 * 4, 0);
            }
        }
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
                MenuScene scene = new MenuScene(game, width, height);
                if(menu[selected] == "800/600"){
                    scene.setResolution(800, 600);
                }
                else if(menu[selected] == "400/300"){
                    scene.setResolution(400, 300);
                }
                else if(menu[selected] == "200/150"){
                    scene.setResolution(200, 150);
                }
                game.setGameScene(scene);
                use = true;
            }
        }
        else {
            use = false;
        }
    }
}
