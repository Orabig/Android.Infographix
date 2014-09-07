package com.crocoware.infographix.utils;

import android.graphics.PointF;


public class Point extends PointF implements Cloneable {

	public Point(float x1, float y1) {
		super(x1, y1);
	}

	public Object clone() {
		return new Point(x, y);
	}

	public Point offset(Vector forward) {
		offset(forward.getX(), forward.getY());
		return this;
	}

	/**
	 * Offset the coordinates of the vector by a multiple of the given vector
	 * 
	 * @param forward
	 * @return the point itself
	 */
	public Point offset(float f, Vector forward) {
		offset(forward.getX() * f, forward.getY() * f);
		return this;
	}

}
