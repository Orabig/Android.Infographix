package com.crocoware.infographix.utils;

import android.graphics.PointF;

public class Vector implements Cloneable {
	public float dx;
	public float dy;

	public Vector(float x, float y) {
		dx = x;
		dy = y;
	}

	public Object clone() {
		return new Vector(dx, dy);
	}

	public float getX() {
		return dx;
	}

	public float getY() {
		return dy;
	}

	/**
	 * Normalize the vector (make its length==1)
	 * 
	 * @return this vector
	 */
	public Vector normalize() {
		// Normalization
		double length = PointF.length(dx, dy);
		dx = (float) (dx / length);
		dy = (float) (dy / length);
		return this;
	}

	/**
	 * @return the angle of the direction of the given vector coordinates, in
	 *         degrees
	 */
	public static float getAngleOf(float dx, float dy) {
		return (float) (180 * Math.atan2(dy, dx) / Math.PI);
	}

	/**
	 * @return the angle of the direction of the vector, in degrees
	 */
	public float getAngle() {
		return getAngleOf(dx, dy);
	}

	/**
	 * Multiply the coordinates of the vector by
	 * 
	 * @param mult
	 * @return the vector itself
	 */
	public Vector multiply(float mult) {
		dx *= mult;
		dy *= mult;
		return this;
	}

	/**
	 * Offset the coordinates of the vector by the given vector
	 * 
	 * @param forward
	 * @return the vector itself
	 */
	public Vector offset(Vector forward) {
		dx += forward.dx;
		dy += forward.dy;
		return this;
	}

	/**
	 * Offset the coordinates of the vector by a multiple of the given vector
	 * 
	 * @param forward
	 * @return the vector itself
	 */
	public Vector offset(float mult, Vector forward) {
		dx += forward.dx * mult;
		dy += forward.dy * mult;
		return this;
	}

	/**
	 * Scales the vector by a multiple
	 * 
	 * @param f
	 * @return the vector itself
	 */
	public Vector scale(float f) {
		dx *= f;
		dy *= f;
		return this;
	}

	/**
	 * Returns a NEW vector which is a multiple of this one
	 * 
	 * @param f
	 * @return the new vector
	 */
	public Vector getScaled(float f) {
		return new Vector(dx * f, dy * f);
	}
}
