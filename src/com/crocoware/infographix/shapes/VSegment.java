package com.crocoware.infographix.shapes;

/**
 * An object representing an horizontal segment
 * 
 */
public final class VSegment extends Segment {

	public VSegment(float x, float y1, float y2) {
		super(x, y1, x, y2);
	}

	public float getX() {
		return getX1();
	}

	public float getHeight() {
		return Math.abs(getY2() - getY1());
	}

	/**
	 * @param tx
	 * @param ty
	 * @return a new segment which is the translation of the original
	 */
	public VSegment translateV(float tx, float ty) {
		return new VSegment(getX1() + tx, getY1() + ty, getY2() + ty);
	}

	/**
	 * @param ratio
	 * @return a new segment scaled from the original, with the common bottom
	 *         point
	 */
	public VSegment scaleUp(float ratio) {
		return new VSegment(x1, (getY1() - getY2()) * ratio + getY2(), getY2());
	}

	/**
	 * @param ratio
	 * @return a new segment scaled from the original, with the common top point
	 */
	public VSegment scaleBottom(float ratio) {
		return new VSegment(getX1(), getY1(), (getY2() - getY1()) * ratio + getY1());
	}
}
