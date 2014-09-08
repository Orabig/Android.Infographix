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
	 *         degrees, between 0 and 359.999
	 */
	public static float getAngleOf(float dx, float dy) {
		float angle = (float) (180 * Math.atan2(dy, dx) / Math.PI);
		if (angle < 0)
			return angle + 360;
		return angle;
	}

	/**
	 * @return the angle of the direction of the vector, in degrees
	 */
	public float angle() {
		return getAngleOf(dx, dy);
	}

	/**
	 * Offset the coordinates of the vector by the given vector
	 * 
	 * @param forward
	 * @return the vector itself
	 */
	public Vector translate(Vector forward) {
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
	public Vector translate(float mult, Vector forward) {
		dx += forward.dx * mult;
		dy += forward.dy * mult;
		return this;
	}

	/**
	 * Rotates the coordinates of the vector by a given angle (degree, clockwise)
	 * 
	 * @param angle
	 * @return the vector itself
	 */
	public Vector rotate(float angle) {
		float cos = (float) Math.cos(angle * Math.PI / 180);
		float sin = (float) Math.sin(angle * Math.PI / 180);
		float nx = cos * dx - sin * dy;
		dy = sin * dx + cos * dy;
		dx = nx;
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

	/**
	 * Creates a new vector given a length and a direction (angle)
	 * 
	 * @param length
	 * @param angle
	 * @return
	 */
	public static Vector createFromAngle(float length, float angle) {
		double rad = angle * Math.PI / 180;
		return new Vector(length * (float) Math.cos(rad), length
				* (float) Math.sin(rad));
	}
}
