package com.liampace.geom;

import java.util.Collection;

import org.joml.Vector2f;
import org.joml.Vector2fc;

import com.liampace.geom.curves.Line2f;

/**
 * Axis Aligned Rectangle
 */
public class AAR implements Shape {
    
    private final Vector2f min, max;
    private final Line2f[] edges = new Line2f[4];

    public AAR(AAR other) {
        this.min = other.min;
        this.max = other.max;
    }

    public AAR() {
        this.min = new Vector2f();
        this.max = new Vector2f();
        edges[0] = new Line2f();
        edges[1] = new Line2f(edges[0].getEnd(), new Vector2f());
        edges[2] = new Line2f(edges[1].getEnd(), new Vector2f());
        edges[3] = new Line2f(edges[2].getEnd(), edges[0].getStart());
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

    @Override
    protected AAR clone() {
        return new AAR(this);
    }

    @Override
    public boolean contains(Vector2f position) {
        return (min.x <= position.x && position.x <= max.x)
            && (min.y <= position.y && position.y <= max.y); 
    }

    @Override
    public Bezier2f[] getEdges() {
        edges[0].getStart().set(min);
        edges[0].getStart().add(max.x, 0, edges[0].getEnd());
        edges[1].getStart().add(0, max.y, edges[1].getEnd());
        edges[2].getStart().sub(max.x, 0, edges[2].getEnd());
        return edges;
    }

    @Override
    public AAR getBounds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBounds'");
    }

}
