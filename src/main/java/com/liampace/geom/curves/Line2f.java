package com.liampace.geom.curves;

import org.joml.Vector2f;

import com.liampace.geom.Bezier2f;

public class Line2f implements Bezier2f {

    public static final int LENGTH = 2;

    private final Vector2f start, end;

    public Line2f(Line2f other) {
        this.start = other.start;
        this.end = other.end;
    }

    public Line2f(Vector2f start, Vector2f end) {
        this.start = start;
        this.end = end;
    }

    public Line2f(float x0, float y0, float x1, float y1) {
        this.start = new Vector2f(x0, y0);
        this.end = new Vector2f(x1, y1);
    }

    public Line2f set(Line2f line) {
        this.set(line.start.x, line.start.y, line.end.x, line.end.y);
        return this;
    }
    public Line2f set(Vector2f start, Vector2f end) {
        this.set(start.x, start.y, end.x, end.y);
        return this;
    }
    public Line2f set(float x0, float y0, float x1, float y1) {
        this.start.set(x0, y0);
        this.end.set(x1, y1);
        return this;
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public Vector2f getPoint(int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPoint'");
    }

    @Override
    public Vector2f getPosition(float t, Vector2f dest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPosition'");
    }

    @Override
    public Vector2f getDerivative(float t, Vector2f dest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDerivative'");
    }

    @Override
    public int getInterceptsX(float[] dest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInterceptsX'");
    }

    @Override
    public int getInterceptsY(float[] dest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInterceptsY'");
    }
    
}
