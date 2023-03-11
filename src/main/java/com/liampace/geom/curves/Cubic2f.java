package com.liampace.geom.curves;

import org.joml.Matrix3x2fc;
import org.joml.Vector2f;

import com.liampace.geom.Bezier2f;

public class Cubic2f implements Bezier2f {

    private static final float CUBE_ROOT_EXPONENT = 1.0f / 3.0f;
    private static final float SQRT_3 = (float) Math.sqrt(3.0f);
    public static final int LENGTH = 4;

    /**
     * Performs a Cubic Interpolation as defined by
     * {@code (P0)(1-t)^3 + (P1)3t(1-t)^2 + (P2)(1-t)t^2 + (P3)t^3}
     * 
     * @param x0   X coordinate of the starting point
     * @param y0   Y coordinate of the starting point
     * @param x1   X coordinate of the first control point
     * @param y1   Y coordinate of the first control point
     * @param x2   X coordinate of the second control point
     * @param y2   Y coordinate of the second control point
     * @param x3   X coordinate of the ending point
     * @param y3   Y coordinate of the ending point
     * @param t    interpolation factor between [0-1] range
     * @param dest will hold the result
     * @return {@code dest}
     */
    public static Vector2f Interpolate(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3,
            float t, Vector2f dest) {
        float nt = (1 - t);
        float ntnt = nt * nt;
        float ntntnt = ntnt * nt;
        float tt = t * t;
        float ttt = tt * t;
        float ntntt3 = ntnt * t * 3;
        float nttt3 = nt * tt * 3;
        return dest.set(
                ntntnt * x0 + ntntt3 * x1 + nttt3 * x2 + ttt * x3,
                ntntnt * y0 + ntntt3 * y1 + nttt3 * y2 + ttt * y3);
    }

    /**
     * Solves the cubic equation as defined by {@code ax^3 = bx^2 + cx + d = 0}
     * 
     * @param a     the coefficient of the first term
     * @param b     the coefficient of the second term
     * @param c     the coefficient of the third term
     * @param d     the coefficient of the fourth term
     * @param index the starting position of {@code dest} in which the roots will be
     *              written to
     * @param dest  will hold the results
     * @return number of roots
     */
    public static int SolveCubicEquation(float a, float b, float c, float d, int index, float[] dest) {
        float A = a / d;
        float B = b / d;
        float C = c / d;
        float v0 = (3.0f * B - A * A) / 3.0f;
        float v1 = (2.0f * A * A * A - 9.0f * B * A + 27.0f * C) / 27.0f;
        float offset = C / 3.0f;
        float discrim = v1 * v1 * 0.25f + v0 * v0 / 27.0f;
        float halfV1 = v1 * 0.5f;
        float temp = 0, root = 0;
        int count = 0;
        if (discrim > 0) {
            discrim = (float) Math.sqrt(discrim);
            temp = -halfV1 + discrim;
            if (temp >= 0) {
                root = (float) Math.pow(temp, CUBE_ROOT_EXPONENT);
            } else {
                root = -(float) Math.pow(-temp, CUBE_ROOT_EXPONENT);
            }
            temp = -halfV1 - discrim;
            if (temp >= 0) {
                root += Math.pow(temp, CUBE_ROOT_EXPONENT);
            } else {
                root -= Math.pow(temp, CUBE_ROOT_EXPONENT);
            }
            dest[index] = root;
            count = 1;
        } else if (discrim < 0) {
            float dist = (float) Math.sqrt(-v0 / 3.0f);
            float angle = (float) Math.atan2(Math.sqrt(-discrim), -halfV1) / 3.0f;
            float cos = (float) Math.cos(angle);
            float sin = SQRT_3 * (float) Math.sin(angle);
            dest[index] = 2.0f * dist * cos;
            dest[index + 1] = -dist * (cos + sin);
            dest[index + 2] = -dist * (cos - sin);
        } else {
            if (halfV1 >= 0) {
                temp = (float) Math.pow(halfV1, CUBE_ROOT_EXPONENT);
            } else {
                temp = (float) Math.pow(-halfV1, CUBE_ROOT_EXPONENT);
            }
            dest[index] = 2.0f * temp;
            dest[index + 1] = -temp;
        }
        for (int i = 0; i < count; i++) {
            dest[index + i] -= offset;
        }
        return count;
    }

    /**
     * Converts a 1D cubic bezier into a standard form cubic equation and finds the
     * roots of that equation
     * 
     * @param start    the starting point of the 1D bezier
     * @param controlA the first control point of the 1D bezier
     * @param controlB the second control point of the 1D bezier
     * @param end      the ending point of the 1D bezier
     * @param index    the starting position of {@code dest} in which the roots will
     *                 be written to
     * @param dest     will hold the results
     * @return number of roots
     */
    public static int SolveCubicBezier(float start, float controlA, float controlB, float end, int index,
            float[] dest) {
        return Cubic2f.SolveCubicEquation(
                start,
                (controlA - start) * 3,
                (start - 2 * controlA + controlB) * 3,
                -start + (controlA - controlB) * 3 + end,
                index, dest);
    }

    private final Vector2f start, controlA, controlB, end;

    public Cubic2f() {
        this(new Vector2f(), new Vector2f(), new Vector2f(), new Vector2f());
    }

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
        this(new Vector2f(x0, y0), new Vector2f(x1, y1), new Vector2f(x2, y2), new Vector2f(x3, y3));
    }

    public Cubic2f set(Cubic2f other) {
        return this.set(other.start.x, other.start.y, other.controlA.x, other.controlA.y, other.controlB.x,
                other.controlB.y, other.end.x, other.end.y);
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
        switch (index) {
            case 0:
                return this.getStart();
            case 1:
                return this.getControlA();
            case 2:
                return this.getControlB();
            case 3:
                return this.getEnd();
            default:
                throw new IndexOutOfBoundsException(
                        "Expected index in range [0-%s] but recieved: %s".formatted(LENGTH - 1, index));
        }
    }

    public Vector2f getStart() {
        return start;
    }
    public Vector2f getControlA() {
        return controlA;
    }
    public Vector2f getControlB() {
        return controlB;
    }
    public Vector2f getEnd() {
        return end;
    }

    @Override
    public Vector2f getPosition(float t, Vector2f dest) {
        return Cubic2f.Interpolate(start.x, start.y, controlA.x, controlA.y, controlB.x, controlB.y, end.x, end.y, t,
                dest);
    }

    @Override
    public Vector2f getDerivative(float t, Vector2f dest) {
        return Quadratic2f.Interpolate(
                controlA.x - start.x, controlA.y - start.y,
                controlB.x - controlA.x, controlB.y - controlA.y,
                end.x - controlB.x, end.y - controlB.y,
                t, dest);
    }

    @Override
    public int getInterceptsX(int index, float[] dest) {
        return Cubic2f.SolveCubicBezier(start.y, controlA.y, controlB.y, end.y, index, dest);
    }

    @Override
    public int getInterceptsY(int index, float[] dest) {
        return Cubic2f.SolveCubicBezier(start.x, controlA.x, controlB.x, end.x, index, dest);
    }

    @Override
    protected Cubic2f clone() {
        return new Cubic2f(this);
    }

    @Override
    public Cubic2f transformPosition(Matrix3x2fc matrix) {
        return (Cubic2f)Bezier2f.super.transformPosition(matrix);
    }

    @Override
    public Cubic2f transformDirection(Matrix3x2fc matrix) {
        return (Cubic2f)Bezier2f.super.transformDirection(matrix);
    }
}
