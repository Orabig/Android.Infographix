package com.crocoware.infographix.utils;

import android.graphics.PointF;

/**
 * TODO : Rename to "Position"
 * 
 * @author Benoit
 * 
 */
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

	public static Point getMiddle(Point a, Point b) {
		return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
	}

}
