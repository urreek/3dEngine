package com.urim.engine.entities;

import com.urim.engine.Level;
import com.urim.engine.utility.MathUtils;
import com.urim.engine.utility.Vector3;

import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

/**
 * Created by urimkrasniqi on 2017-02-13.
 */
public class Player {

    public Vector3 position;
    public Vector3 prePosition;
    public Vector3 velocity;
    public double rot;
    public final double rotSpeed = 0.025;
    public double speed = 0.05;
    public double gravity = 0.025;


    public  Player(){
        this(Vector3.zero,0);
    }

    public Player(Vector3 position, float rot){
        this.position = position;
        prePosition = new Vector3(position);
        velocity = Vector3.zero;
        this.rot = rot;
    }

    public void update(boolean[] keys){
        prePosition.set(position);
        inputUpdate(keys);

        velocity.y += -gravity;
        velocity.y = MathUtils.clamp(velocity.y,-1, 1);

        Vector3 norVelocity = velocity.normalized();
        
        move(norVelocity);
        clamp();

    }

    private void inputUpdate(boolean[] keys){
        velocity.set(0, velocity.y, 0);
        if(keys[KeyEvent.VK_UP]) velocity.z = 1;
        if(keys[KeyEvent.VK_DOWN]) velocity.z = -1;
        if(keys[KeyEvent.VK_LEFT]) velocity.x = -1;
        if(keys[KeyEvent.VK_RIGHT]) velocity.x = 1;
        if(keys[KeyEvent.VK_X]) rot -= rotSpeed;
        if(keys[KeyEvent.VK_C]) rot += rotSpeed;
        if(keys[KeyEvent.VK_SPACE] && !isInAir()) {
            velocity.y += 2;
        }
    }

    public void move(Vector3 velocity){
        position.y += this.velocity.y;
        position.x += (velocity.x * Math.cos(rot) + velocity.z * Math.sin(rot)) * speed;
        position.z += (velocity.z * Math.cos(rot) - velocity.x * Math.sin(rot)) * speed;
    }

    private void clamp(){
        position.x = MathUtils.clamp(position.x, 0.25f, (Level.width - 0.25f));
        position.y = MathUtils.clamp(position.y,0,7.5f);
        position.z = MathUtils.clamp(position.z, 0.25f, Level.height - 0.25f);
        if(position.y == 7.5){
            velocity.y = 0;
        }
        else if(position.y == 0){
            velocity.y = 0;
        }
    }


    public boolean isInAir(){
        if(position.y > 0)
            return true;

        else
            return false;
    }

    public void checkCollision(Block[] blocks){
        for (Block block : blocks) {
            if(block instanceof SolidBlock) {
                SolidBlock temp = (SolidBlock)block;

                Rectangle2D xz = new Rectangle2D.Double(temp.position.x, temp.position.z, 1, 1);
                Rectangle2D pxz = new Rectangle2D.Double(position.x-0.25, position.z-0.25, 0.5, 0.5);

                if (pxz.intersects(xz)) {
                    if(new Rectangle2D.Double(prePosition.x-0.25, position.z-0.25, 0.5, 0.5).intersects(xz)){
                        position.z = prePosition.z;
                    }
                    if(new Rectangle2D.Double(position.x-0.25, prePosition.z-0.25, 0.5, 0.5).intersects(xz)){
                        position.x = prePosition.x;
                    }
                }
            }
        }
    }
}
