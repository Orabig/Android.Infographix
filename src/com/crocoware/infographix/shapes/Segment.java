package com.crocoware.infographix.shapes;

/**
 * Instances of this class represent oriented segments.
 * 
 * @author Benoit
 *
 */
public class Segment {
	protected float x1, y1, x2, y2;

	public Segment(float x, float y, float x2, float y2) {
		x1 = x;
		y1 = y;
		this.x2 = x2;
		this.y2 = y2;
	}

	public float getX1() {
		return x1;
	}

	public float getX2() {
		return x2;
	}

	public float getY1() {
		return y1;
	}

	public float getY2() {
		return y2;
	}

	/**
	 * @return the normal of the segment (left-hand oriented). The returned vector is normalized (length==1)
	 */
	public Vector getNormal() {
		float dy = -(x2 - x1);
		float dx = y2 - y1;
		return new Vector(dx, dy).normalize();
	}

	public Segment reverse() {
		return new Segment(x2, y2, x1, y1);
	}

	public void translate(float dx, float dy) {
		x1 += dx;
		x2 += dx;
		y1 += dy;
		y2 += dy;
	}

	/**
	 * @return the angle of the direction of the segment, in degrees
	 */
	public float getAngle() {
		return Vector.getAngleOf(x2 - x1, y2 - y1);
	}
}
