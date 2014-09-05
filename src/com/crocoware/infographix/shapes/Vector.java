package com.crocoware.infographix.shapes;

import android.graphics.PointF;

public class Vector {
	protected float dx, dy;

	public Vector(float x, float y) {
		dx = x;
		dy = y;
	}

	public float getX() {
		return dx;
	}

	public float getY() {
		return dy;
	}

	/**
	 * Normalize the vector (make its length==1)
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
	 * @return the angle of the direction of the given vector coordinates, in degrees
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
}
