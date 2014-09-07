package com.crocoware.infographix.utils;

import android.graphics.PointF;

/**
 * A position in 2D space.
 * 
 * Offers several utility methods for manipulating this object.
 * 
 * By convention, methods prefixed by "set..." create a new object, while those
 * only containing a verb (translate...) change the values of this
 * object (and return the object itself for commodity)
 * 
 * @author Benoit
 * 
 */
public class Position extends PointF implements Cloneable {

	public Position(float x1, float y1) {
		super(x1, y1);
	}

	public Object clone() {
		return new Position(x, y);
	}

	public Position translate(Vector forward) {
		offset(forward.getX(), forward.getY());
		return this;
	}

	/**
	 * Offset the coordinates of the vector by a multiple of the given vector
	 * 
	 * @param forward
	 * @return the point itself
	 */
	public Position translate(float f, Vector forward) {
		offset(forward.getX() * f, forward.getY() * f);
		return this;
	}

	public static Position getCenterOf(Position a, Position b) {
		return new Position((a.x + b.x) / 2, (a.y + b.y) / 2);
	}

}
