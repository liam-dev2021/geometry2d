package com.liampace.geom.curves;

import org.joml.Vector2f;

import com.liampace.geom.Bezier2f;

public class Quadratic2f implements Bezier2f {

    private final Vector2f start, control, end;

    public Quadratic2f(Quadratic2f other) {
        this.start = other.start;
        this.control = other.control;
        this.end = other.end;
    }
    public Quadratic2f(Vector2f start, Vector2f control, Vector2f end) {
        this.start = start;
        this.control = control;
        this.end = end;
    }
    public Quadratic2f(float x0, float y0, float x1, float y1, float x2, float y2) {
        this.start = new Vector2f(x0, y0);
        this.control = new Vector2f(x1, y1);
        this.end = new Vector2f(x2, y2);
    }

    public Quadratic2f set(Quadratic2f other) {
        return null;
    }
    public Quadratic2f set(Vector2f start, Vector2f control, Vector2f end) {
        return null;       
    }
    public Quadratic2f set(float x0, float y0, float x1, float y1, float x2, float y2) {
        return null;
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

    private int solve(float start, float control, float end, float[] dest) {
        return 0;
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
