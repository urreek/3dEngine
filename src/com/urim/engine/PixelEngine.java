package com.urim.engine;
import com.urim.engine.graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by urimkrasniqi on 2017-02-07.
 */

public class PixelEngine extends Canvas implements  Runnable {

	private static final long serialVersionUID = 1L;
	
	public static final String TITLE = Constants.TITLE;
    public static final int WIDTH = Constants.WIDTH;
    public static final int HEIGHT = Constants.HEIGHT;

    private static final double TARGET_FPS = 60.0;

    private Game game;
    private Thread thread;
    private boolean running;
    private InputHandler input;
    private Screen screen;
    private BufferedImage img;
    private int[] pixels;


    public PixelEngine(JFrame frame){
        game = new Game(frame);
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        screen = new Screen(WIDTH, HEIGHT);
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        input = new InputHandler();
        addKeyListener(input);
    }


    private synchronized void start(){
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();

    }

    public synchronized void run() {
        int frames = 0;

        double unprocessedSeconds = 0;
        long lastTime = System.nanoTime();
        double secondsPerTick = 1 / TARGET_FPS;
        int tickCount = 0;

        requestFocus();

        while (running) {
            long now = System.nanoTime();
            long passedTime = now - lastTime;
            lastTime = now;
            if (passedTime < 0) passedTime = 0;
            if (passedTime > 100000000) passedTime = 100000000;

            unprocessedSeconds += passedTime / 1000000000.0;
            boolean ticked = false;

            while (unprocessedSeconds > secondsPerTick) {
                tick();
                unprocessedSeconds -= secondsPerTick;
                ticked = true;

                tickCount++;
                if (tickCount % 60 == 0) {
                    System.out.println(frames + " fps");
                    lastTime += 1000;
                    frames = 0;
                }
            }

            if (ticked) {
                render();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void tick() {
        game.tick(input.keys);
    }

    private void render(){

        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        screen.render(game);

        for(int i = 0; i < WIDTH * HEIGHT; i++){
            pixels[i] = screen.pixels[i];
        }

        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame(TITLE);
        PixelEngine game = new PixelEngine(frame);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        if(Constants.FULLSCREEN) {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            gd.setFullScreenWindow(frame);
        }
        frame.setVisible(true);
        game.start();
    }
}
