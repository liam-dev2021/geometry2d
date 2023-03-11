package com.liampace.geom;

import java.util.function.Consumer;

import org.joml.Vector2f;

public interface Bezier2f {
    /**
     * Returns the number of points that define the bezier curve.
     * 
     * @return the number of points that define the bezier curve
     */
    public int getLength();

    /**
     * Returns the point at the specified index of the bezier curve.
     * 
     * @param index the index of the desired point
     * @return the point at the specified index of the bezier curve
     */
    public Vector2f getPoint(int index);

    /**
     * Interpolates between all points of the bezier curve using the given
     * interpolation factor {@code t}. Then stores the result in {@code dest}.
     * If {@code t} is {@code 0.0} the result is the bezier's starting point.
     * If {@code t} is {@code 1.0} the result is the bezier's ending point.
     * 
     * @param t    interpolation factor between [0-1] range
     * @param dest will hold the result
     * @return {@code dest}
     */
    public Vector2f getPosition(float t, Vector2f dest);

    /**
     * Interpolates the derivative of the bezier curve using the given interpolation
     * factor {@code t}. Then stores the result in {@code dest}.
     * 
     * @param t    interpolation factor between [0-1] range
     * @param dest will hold the result
     * @return {@code dest}
     */
    public Vector2f getDerivative(float t, Vector2f dest);

    /**
     * Calculates the derivative vector and normalizes it using the given
     * interpolation factor {@code t}. Then stores the result in {@code dest}.
     * 
     * @param t    interpolation factor between [0-1] range
     * @param dest will hold the result
     * @return {@code dest}
     */
    default Vector2f getTangent(float t, Vector2f dest) {
        return this.getDerivative(t, dest).normalize();
    }

    /**
     * Calculates the tangent vector and rotates it +90 Degrees so that if the
     * tangent was equal to {@code (1, 0)}, the result stored in {@code dest} would
     * be {@code (0, 1)}.
     * 
     * @param t    interpolation factor between [0-1] range
     * @param dest will hold the result
     * @return {@code dest}
     */
    default Vector2f getNormalCCW(float t, Vector2f dest) {
        return this.getTangent(t, dest).perpendicular();
    }

    /**
     * Calculates the tangent vector and rotates it -90 Degrees so that if the
     * tangent was equal to {@code (1, 0)}, the result stored in {@code dest} would
     * be {@code (0, -1)}.
     * 
     * @param t    interpolation factor between [0-1] range
     * @param dest will hold the result
     * @return {@code dest}
     */
    default Vector2f getNormalCW(float t, Vector2f dest) {
        return this.getNormalCCW(t, dest).negate();
    }

    /**
     * Solves the bezier curve where the roots cross the X-Axis, aka when Y = 0.
     * 
     * @param index starting index position for storing the roots
     * @param dest  will hold all real roots of this bezier curve starting at index
     *              {@code index}
     * @return number of roots found
     */
    public int getInterceptsX(int index, float[] dest);

    /**
     * Solves the bezier curve where the roots cross the Y-Axis, aka when X = 0.
     * 
     * @param index starting index position for storing the roots
     * @param dest  will hold all real roots of this bezier curve starting at index
     *              {@code index}
     * @return number of roots found
     */
    public int getInterceptsY(int index, float[] dest);

    /**
     * Performs the given action for each point that defines the bezier curve.
     * 
     * @param action The action to be performed for each point
     */
    default void forEach(Consumer<Vector2f> action) {
        for (int i = 0, length = this.getLength(); i < length; i++) {
            action.accept(this.getPoint(i));
        }
    }

    /**
     * Calculates the interpolation factor in which the given point is projected onto the bezier curve
     * @param position A 2D Cartesian Coordinate
     * @return interpolation factor between the range of [0-1]
     */
    default float project(Vector2f position) {
        float[] temp = new float[this.getLength() - 1];
        // Translate the bezier so that the given position is equal to the origin
        this.forEach(p -> p.sub(position));
        int count = this.getInterceptsY(0, temp);
        float proj = Float.MAX_VALUE;
        // Finds closest root
        for (int i = 0; i < count; i++) {
            proj = Math.min(proj, temp[i]);
        }
        // Translate the bezier so that the origin is equal to the given position
        this.forEach(p -> p.add(position));
        // Clamps root to [0-1] range
        return Math.max(Math.min(proj, 1), 0);
    }
}
