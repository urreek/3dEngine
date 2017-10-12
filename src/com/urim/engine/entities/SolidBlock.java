package com.urim.engine.entities;

import com.urim.engine.entities.Block;
import com.urim.engine.utility.Vector3;

/**
 * Created by urimkrasniqi on 2017-02-15.
 */
public class SolidBlock extends Block {

    public Vector3 position;

    public SolidBlock(Vector3 position){
        this.position = position;
        solid = true;
    }
}
