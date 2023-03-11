package com.liampace.geom;

import java.util.Collection;

import org.joml.Vector2f;
import org.joml.Vector2fc;

/**
 * Axis Aligned Rectangle
 */
public class AAR {
    
    private final Vector2f min, max;

    public AAR(AAR other) {
        this.min = other.min;
        this.max = other.max;
    }

    public AAR() {
        this.min = new Vector2f();
        this.max = new Vector2f();
    }

    public AAR set(AAR other) {
        this.min.set(other.min);
        this.max.set(other.max);
        return this;
    }

    public AAR setFromCenter(Vector2f pos, Vector2f size) {
        size.div(-2.0f, min).add(pos);
        size.div(+2.0f, max).add(pos);
        return this;
    }
    public AAR setFromBounds(Vector2f...positions) {
        min.set(Float.MAX_VALUE);
        max.set(Float.MIN_VALUE);
        for (Vector2f pos : positions) {
            min.min(min, pos);
            max.max(max, pos);
        }
        return this;
    }

    public AAR setFromBounds(Collection<Vector2f> positions) {
        min.set(Float.MAX_VALUE);
        max.set(Float.MIN_VALUE);
        positions.forEach(pos -> {
            min.min(min, pos);
            max.max(max, pos);
        });
        return this;
    }
    
    public Vector2fc getMin() {
        return min;
    }
    public Vector2fc getMax() {
        return max;
    }

}
