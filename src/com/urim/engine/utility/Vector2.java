package com.urim.engine.utility;

/**
 * Created by urimkrasniqi on 2017-03-08.
 */
public class Vector2 {

    public static final Vector2 zero = new Vector2(0, 0);

    public float x;
    public float y;

    public Vector2() {
    }
    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2 set(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    public Vector2 sub(Vector2 v){
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector2 sub(float x, float y){
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2 add(Vector2 v){
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2 add(float x, float y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2 scl(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2 scl(float x, float y){
        this.x *= x;
        this.y *= y;
        return this;
    }

    public static float len (float x, float y) {
        return (float)Math.sqrt(x * x + y * y);
    }

    public float len () {
        return (float)Math.sqrt(x * x + y * y);
    }

    public Vector2 nor () {
        float len = len();
        if (len != 0) {
            x /= len;
            y /= len;
        }
        return this;
    }

    public static float dst(float x1, float y1, float x2, float y2) {
        final float x_d = x2 - x1;
        final float y_d = y2 - y1;
        return (float)Math.sqrt(x_d * x_d + y_d * y_d);
    }

    public float dst(Vector2 v) {
        final float x_d = v.x - x;
        final float y_d = v.y - y;
        return (float)Math.sqrt(x_d * x_d + y_d * y_d);
    }

    public float dst(float x, float y) {
        final float x_d = x - this.x;
        final float y_d = y - this.y;
        return (float)Math.sqrt(x_d * x_d + y_d * y_d);
    }

    public Vector2 setZero () {
        this.x = 0;
        this.y = 0;
        return this;
    }

    public Vector2 set(float x, float y){
        this.x = x;
        this.y = y;
        return this;
    }

    public String toString () {
        return "(" + x + "," + y + ")";
    }

}
