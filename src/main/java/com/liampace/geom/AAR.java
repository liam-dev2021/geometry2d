package com.liampace.geom;

import org.joml.Vector2f;
import org.joml.Vector2fc;

/**
 * Axis Aligned Rectangle
 */
public class AAR {
    
    private final Vector2f min, max;

    public AAR() {
        this.min = new Vector2f();
        this.max = new Vector2f();
    }

    public AAR setFromCenter(Vector2f pos, Vector2f radii) {
        return null;
    }
    public AAR setFromBounds(Vector2f...positions) {
        return null;
    }
    
    public Vector2fc getMin() {
        return min;
    }
    public Vector2fc getMax() {
        return max;
    }

}
