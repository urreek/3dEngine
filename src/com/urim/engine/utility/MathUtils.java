package com.urim.engine.utility;

/**
 * Created by urimkrasniqi on 2017-03-10.
 */
public class MathUtils {

    public static float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}
