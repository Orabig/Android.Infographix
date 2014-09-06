package com.crocoware.infographix.shapes;

import android.graphics.PointF;

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

	public Segment(Segment start) {
		this(start.x1, start.y1, start.x2, start.y2);
	}

	public Segment(PointF a, PointF b) {
		this(a.x, a.y, b.x, b.y);
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
	 * @return the normal of the segment (left-hand oriented). The returned
	 *         vector is normalized (length==1)
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

	public float getLength() {
		return PointF.length(x2 - x1, y2 - y1);
	}

	public Segment[] split(int count) {
		if (count <= 0)
			throw new IllegalArgumentException("count<=0");
		Segment[] parts = new Segment[count];
		float x = x1;
		float y = y1;
		float dx = (x2 - x1) / count;
		float dy = (y2 - y1) / count;
		for (int i = 0; i < count; i++) {
			float x2 = x1 + (i + 1) * dx;
			float y2 = y1 + (i + 1) * dy;
			parts[i] = new Segment(x, y, x2, y2);
			x = x2;
			y = y2;
		}
		return parts;
	}

	public Vector getVector() {
		return new Vector(x2 - x1, y2 - y1);
	}

	public Segment rotate(PointF center, float angle) {
		float cos = (float) Math.cos(angle * Math.PI / 180);
		float sin = (float) Math.sin(angle * Math.PI / 180);
		PointF A = rotatePoint(x1, y1, center, cos, sin);
		PointF B = rotatePoint(x2, y2, center, cos, sin);
		return new Segment(A, B);
	}

	private PointF rotatePoint(float x, float y, PointF center, float cos,
			float sin) {
		float dx = x - center.x;
		float dy = y - center.y;
		return new PointF(center.x + cos * dx - sin * dy, center.y + sin * dx
				+ cos * dy);
	}
}
