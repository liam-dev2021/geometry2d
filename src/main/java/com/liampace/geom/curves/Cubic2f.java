package com.liampace.geom.curves;

import org.joml.Vector2f;

import com.liampace.geom.Bezier2f;

public class Cubic2f implements Bezier2f {
    
    public static final int LENGTH = 4;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLength'");
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
