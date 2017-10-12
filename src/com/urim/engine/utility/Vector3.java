package com.urim.engine.utility;

/**
 * Created by urimkrasniqi on 2017-03-08.
 */
public class Vector3 {
    public static final Vector3 zero = new Vector3(0, 0, 0);
    public float x;
    public float y;
    public float z;

    public Vector3() {
    }

    public Vector3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3(final Vector3 vector) {
        this.set(vector);
    }

    public Vector3 set(Vector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        return this;
    }

    public Vector3 sub(Vector3 v){
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }

    public Vector3 sub(float x, float y, float z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3 add(Vector3 v){
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }

    public Vector3 add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3 scl(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    public Vector3 scl(float x, float y, float z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public static float len (float x, float y, float z) {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }

    public float len () {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3 normalized () {
        float len = len();
        Vector3 temp = new Vector3(x , y, z);
        if (len != 0) {
            temp.x /= len;
            temp.y /= len;
            temp.z /= len;
        }
        return temp;
    }

    public Vector3 normalize (){
        float len = len();
        if (len != 0) {
            x /= len;
            y /= len;
            z /= len;
        }
        return this;
    }

    public static float dst(float x1, float y1, float z1, float x2, float y2, float z2) {
        final float x_d = x2 - x1;
        final float y_d = y2 - y1;
        final float z_d = z2 - z1;
        return (float)Math.sqrt(x_d * x_d + y_d * y_d + z_d * z_d);
    }

    public float dst(Vector3 v) {
        final float x_d = v.x - x;
        final float y_d = v.y - y;
        final float z_d = v.z - z;
        return (float)Math.sqrt(x_d * x_d + y_d * y_d + z_d * z_d);
    }

    public float dst(float x, float y, float z) {
        final float x_d = x - this.x;
        final float y_d = y - this.y;
        final float z_d = z - this.z;
        return (float)Math.sqrt(x_d * x_d + y_d * y_d + z_d * z_d);
    }

    public Vector3 setZero() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        return this;
    }

    public Vector3 set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public String toString () {
        return "(" + x + "," + y + "," + z + ")";
    }

}
