package com.urim.engine;

import java.awt.event.*;

/**
 * Created by urimkrasniqi on 2017-02-13.
 */

public class InputHandler implements KeyListener {
    public boolean[] keys = new boolean[65536];

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code > 0 && code < keys.length) {
            keys[code] = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code > 0 && code < keys.length) {
            keys[code] = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }
}