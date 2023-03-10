package com.liampace.geom.curves;

import org.joml.Matrix3x2fc;
import org.joml.Vector2f;

import com.liampace.geom.Bezier2f;

public class Quadratic2f implements Bezier2f {

    public static final int LENGTH = 3;

    /**
     * Performs a Quadratic Interpolation as defined by
     * {@code (P0)(1-t)^2 + (P1)(2(1-t)t) + (P2)(t^2)}
     * 
     * @param x0   X coordinate of start point
     * @param y0   Y coordinate of start point
     * @param x1   X coordinate of control point
     * @param y1   Y coordinate of control point
     * @param x2   X coordinate of end point
     * @param y2   Y coordinate of end point
     * @param t    interpolation factor between [0-1] range
     * @param dest will hold the result
     * @return {@code dest}
     */
    public static Vector2f Interpolate(float x0, float y0, float x1, float y1, float x2, float y2, float t,
            Vector2f dest) {
        float nt = (1 - t);
        float ntnt = nt * nt;
        float ntt2 = nt * t * 2;
        float tt = t * t;
        return dest.set(
                ntnt * x0 + ntt2 * x1 + tt * x2,
                ntnt * y0 + ntt2 * y1 + tt * y2);
    }

    /**
     * Solves the quadratic equation as defined by {@code ax^2 = bx + c = 0}
     * 
     * @param a     the coefficient of the first term
     * @param b     the coefficient of the second term
     * @param c     the coefficient of the third term
     * @param index the starting position of {@code dest} in which the roots will be
     *              written to
     * @param dest  will hold the results
     * @return number of roots
     */
    public static int SolveQuadraticEquation(float a, float b, float c, int index, float[] dest) {
        float B = b / a;
        float C = c / a;
        float sqrt = B * B - 4 * C;
        if (sqrt > 0) {
            sqrt = (float) Math.sqrt(sqrt);
            dest[index] = 0.5f * (-B + sqrt);
            dest[index + 1] = 0.5f * (-B - sqrt);
            return 2;
        } else if (sqrt == 0) {
            dest[index] = 0.5f * -B;
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Converts a 1D quadratic bezier into a standard form quadratic equation and
     * finds the roots of that equation
     * 
     * @param start   the starting point of the 1D bezier
     * @param control the control point of the 1D bezier
     * @param end     the ending point of the 1D bezier
     * @param index   the starting position of {@code dest} in which the roots will
     *                be written to
     * @param dest    will hold the roots
     * @return number of roots
     */
    public static int SolveQuadraticBezier(float start, float control, float end, int index, float[] dest) {
        return Quadratic2f.SolveQuadraticEquation(start, (control - start) * 2, start - control * 2 + end, index, dest);
    }

    private final Vector2f start, control, end;

    public Quadratic2f() {
        this(new Vector2f(), new Vector2f(), new Vector2f());
    }

    public Quadratic2f(Quadratic2f other) {
        this(other.start, other.control, other.end);
    }

    public Quadratic2f(Vector2f start, Vector2f control, Vector2f end) {
        this.start = start;
        this.control = control;
        this.end = end;
    }

    public Quadratic2f(float x0, float y0, float x1, float y1, float x2, float y2) {
        this(new Vector2f(x0, y0), new Vector2f(x1, y1), new Vector2f(x2, y2));
    }

    public Quadratic2f set(Quadratic2f other) {
        return this.set(other.start.x, other.start.y, other.control.x, other.control.y, end.x, end.y);
    }

    public Quadratic2f set(Vector2f start, Vector2f control, Vector2f end) {
        return this.set(start.x, start.y, control.x, control.y, end.x, end.y);
    }

    public Quadratic2f set(float x0, float y0, float x1, float y1, float x2, float y2) {
        this.start.set(x0, y0);
        this.control.set(x1, y1);
        this.end.set(x2, y2);
        return this;
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public Vector2f getPoint(int index) {
        switch (index) {
            case 0:
                return this.getStart();
            case 1:
                return this.getControl();
            case 2:
                return this.getEnd();
            default:
                throw new IndexOutOfBoundsException(
                        "Expected index in range [0-%s] but recieved: %s".formatted(LENGTH - 1, index));
        }
    }

    public Vector2f getStart() {
        return start;
    }
    public Vector2f getControl() {
        return control;
    }
    public Vector2f getEnd() {
        return end;
    }

    @Override
    public Vector2f getPosition(float t, Vector2f dest) {
        return Quadratic2f.Interpolate(start.x, start.y, control.x, control.y, end.x, end.y, t, dest);
    }

    @Override
    public Vector2f getDerivative(float t, Vector2f dest) {
        return Line2f.Interpolate(control.x - start.x, control.y - start.y, end.x - control.x, end.y - control.y, t,
                dest);
    }

    @Override
    public int getInterceptsX(int index, float[] dest) {
        return Quadratic2f.SolveQuadraticBezier(start.y, control.y, end.y, index, dest);
    }

    @Override
    public int getInterceptsY(int index, float[] dest) {
        return Quadratic2f.SolveQuadraticBezier(start.x, control.x, end.x, index, dest);
    }

    @Override
    protected Quadratic2f clone() {
        return new Quadratic2f(this);
    }

    @Override
    public Quadratic2f transformPosition(Matrix3x2fc matrix) {
        return (Quadratic2f)Bezier2f.super.transformPosition(matrix);
    }

    @Override
    public Quadratic2f transformDirection(Matrix3x2fc matrix) {
        return (Quadratic2f)Bezier2f.super.transformDirection(matrix);
    }
}
