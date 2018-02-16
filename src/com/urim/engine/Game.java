package com.urim.engine;

import com.urim.engine.entities.Player;
import com.urim.engine.scenes.GameScene;
import com.urim.engine.scenes.IntroScene;

import javax.swing.*;
import java.awt.event.WindowEvent;

/**
 * Created by urimkrasniqi on 2017-02-13.
 */
public class Game {

    public double time;
    public Player player;
    public Level level;
    public GameScene scene;
    public JFrame frame;
    public boolean fullscreen;

    public Game (JFrame frame){
        this.fullscreen = false;
        this.frame = frame;
        this.scene = new IntroScene(this, Constants.WIDTH, Constants.HEIGHT);
    }

    public void newGame(){
        level = new Level(20, 20);
        player = new Player(level.getPlayerSpawn(), 0);
    }

    public void tick(boolean[] keys){
        scene.tick(keys);
        time++;
    }

    public void setGameScene(GameScene scene) {
        this.scene = scene;
    }

    public void closeGame(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
    public void setFullScreen(){

    }

}
