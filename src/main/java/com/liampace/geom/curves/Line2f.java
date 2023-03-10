package com.liampace.geom.curves;

import org.joml.Matrix3x2fc;
import org.joml.Vector2f;

import com.liampace.geom.Bezier2f;

public class Line2f implements Bezier2f {

    public static final int LENGTH = 2;

    /**
     * Performs a Linear Interpolation as defined by {@code (P0)(1-t) + (P1)(t)}
     * 
     * @param x0   X coordinate of start point
     * @param y0   Y coordinate of start point
     * @param x1   X coordinate of end point
     * @param y1   Y coordinate of end point
     * @param t    interpolation factor between [0-1] range
     * @param dest will hold the result
     * @return {@code dest}
     */
    public static Vector2f Interpolate(float x0, float y0, float x1, float y1, float t, Vector2f dest) {
        float nt = (1 - t);
        return dest.set(
                nt * x0 + t * x1,
                nt * y0 + t * y1);
    }

    /**
     * Solves the linear equation as defined by {@code ax + by = c}
     * 
     * @param a     the coefficient of the first term
     * @param b     the coefficient of the second term
     * @param index the starting position of {@code dest} in which the roots will be
     *              written to
     * @param dest  will hold the results
     * @return number of roots
     */
    public static int SolveLinearEquation(float a, float b, int index, float[] dest) {
        if (b == 0) {
            return 0;
        }
        dest[index] = -a / b;
        return 1;
    }

    /**
     * Converts a 1D linear bezier into a standard form linear equation and finds
     * the roots of that equation
     * 
     * @param start the starting point of the 1D bezier
     * @param end   the ending point of the 1D bezier
     * @param index the starting position of {@code dest} in which the roots will be
     *              written to
     * @param dest  will hold the roots
     * @return number of roots
     */
    public static int solve(float start, float end, int index, float[] dest) {
        return Line2f.SolveLinearEquation(start, end - start, index, dest);
    }

    private final Vector2f start, end;

    public Line2f() {
        this(new Vector2f(), new Vector2f());
    }

    public Line2f(Line2f other) {
        this(other.start, other.end);
    }

    public Line2f(Vector2f start, Vector2f end) {
        this.start = start;
        this.end = end;
    }

    public Line2f(float x0, float y0, float x1, float y1) {
        this(new Vector2f(x0, y0), new Vector2f(x1, y1));
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
        switch (index) {
            case 0:
                return this.getStart();
            case 1:
                return this.getEnd();
            default:
                throw new IndexOutOfBoundsException(
                        "Expected index in range [0-%s] but recieved: %s".formatted(LENGTH - 1, index));
        }
    }

    public Vector2f getStart() {
        return start;
    }
    public Vector2f getEnd() {
        return end;
    }

    @Override
    public Vector2f getPosition(float t, Vector2f dest) {
        return Interpolate(start.x, start.y, end.x, end.y, t, dest);
    }

    @Override
    public Vector2f getDerivative(float t, Vector2f dest) {
        return end.sub(start, dest);
    }

    @Override
    public int getInterceptsX(int index, float[] dest) {
        return Line2f.solve(start.y, end.y, index, dest);
    }

    @Override
    public int getInterceptsY(int index, float[] dest) {
        return Line2f.solve(start.x, end.x, index, dest);
    }

    @Override
    protected Line2f clone() {
        return new Line2f(this);
    }

    @Override
    public Line2f transformPosition(Matrix3x2fc matrix) {
        return (Line2f)Bezier2f.super.transformPosition(matrix);
    }

    @Override
    public Line2f transformDirection(Matrix3x2fc matrix) {
        return (Line2f)Bezier2f.super.transformDirection(matrix);
    }
}
