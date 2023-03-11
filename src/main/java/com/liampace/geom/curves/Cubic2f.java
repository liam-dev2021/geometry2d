package com.liampace.geom.curves;

import org.joml.Vector2f;

import com.liampace.geom.Bezier2f;

public class Cubic2f implements Bezier2f {
    
    public static final int LENGTH = 4;

    /**
     * Performs a Cubic Interpolation as defined by {@code (P0)(1-t)^3 + (P1)3t(1-t)^2 + (P2)(1-t)t^2 + (P3)t^3}
     * @param x0 X coordinate of the starting point
     * @param y0 Y coordinate of the starting point
     * @param x1 X coordinate of the first control point
     * @param y1 Y coordinate of the first control point
     * @param x2 X coordinate of the second control point
     * @param y2 Y coordinate of the second control point
     * @param x3 X coordinate of the ending point
     * @param y3 Y coordinate of the ending point
     * @param t interpolation factor between [0-1] range
     * @param dest will hold the result
     * @return {@code dest}
     */
    public static Vector2f Interpolate(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3, float t, Vector2f dest) {
        float nt = (1 - t);
		float ntnt = nt*nt;
		float ntntnt = ntnt*nt;
		float tt = t*t;
        float ttt = tt*t;
        float ntntt3 = ntnt*t*3;
        float nttt3 = nt*tt*3;
        return dest.set(
            ntntnt * x0 + ntntt3 * x1 + nttt3 * x2 + ttt * x3,
            ntntnt * y0 + ntntt3 * y1 + nttt3 * y2 + ttt * y3
        );
    }

    private final Vector2f start, controlA, controlB, end;

    public Cubic2f(Cubic2f other) {
        this(other.start, other.controlA, other.controlB, other.end);
    }
    public Cubic2f(Vector2f start, Vector2f controlA, Vector2f controlB, Vector2f end) {
        this.start = start;
        this.controlA = controlA;
        this.controlB = controlB;
        this.end = end;
    }
    public Cubic2f(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3) {
        this(new Vector2f(x0 ,y0), new Vector2f(x1, y1), new Vector2f(x2, y2), new Vector2f(x3, y3));
    }

    public Cubic2f set(Cubic2f other) {
        return this.set(other.start.x, other.start.y, other.controlA.x, other.controlA.y, other.controlB.x, other.controlB.y, other.end.x, other.end.y);
    }
    public Cubic2f set(Vector2f start, Vector2f controlA, Vector2f controlB, Vector2f end) {
        return this.set(start.x, start.y, controlA.x, controlA.y, controlB.x, controlB.y, end.x, end.y);
    }
    public Cubic2f set(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3) {
        this.start.set(x0, y0);
        this.controlA.set(x1, y1);
        this.controlB.set(x2, y2);
        this.end.set(x3, y3);
        return this;
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public Vector2f getPoint(int index) {
        switch(index) {
            case 0: return start;
            case 1: return controlA;
            case 2: return controlB;
            case 3: return end;
            default: throw new IndexOutOfBoundsException("Expected index in range [0-%s] but recieved: %s".formatted(LENGTH-1, index));
        }
    }
    
    @Override
    public Vector2f getPosition(float t, Vector2f dest) {
        return Cubic2f.Interpolate(start.x, start.y, controlA.x, controlA.y, controlB.x, controlB.y, end.x, end.y, t, dest);
    }
    
    @Override
    public Vector2f getDerivative(float t, Vector2f dest) {
        return Quadratic2f.Interpolate(
            controlA.x - start.x, controlA.y - start.y, 
            controlB.x -controlA.x, controlB.y -controlA.y, 
            end.x - controlB.x, end.y - controlB.y, 
            t, dest
        );
    }
    
    @Override
    public int getInterceptsX(int index, float[] dest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInterceptsX'");
    }
    
    @Override
    public int getInterceptsY(int index, float[] dest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInterceptsY'");
    }

}
