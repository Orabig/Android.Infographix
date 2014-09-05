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
		return x1;
	}

	public float getHeight() {
		return Math.abs(y2 - y1);
	}

	/**
	 * @param tx
	 * @param ty
	 * @return a new segment which is the translation of the original
	 */
	public VSegment translateV(float tx, float ty) {
		return new VSegment(x1 + tx, y1 + ty, y2 + ty);
	}

	/**
	 * @param ratio
	 * @return a new segment scaled from the original, with the common bottom
	 *         point
	 */
	public VSegment scaleUp(float ratio) {
		return new VSegment(x1, (y1 - y2) * ratio + y2, y2);
	}

	/**
	 * @param ratio
	 * @return a new segment scaled from the original, with the common top point
	 */
	public VSegment scaleBottom(float ratio) {
		return new VSegment(x1, y1, (y2 - y1) * ratio + y1);
	}
}
