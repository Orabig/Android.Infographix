package com.crocoware.infographix.shapes;

/**
 * An object representing an horizontal segment
 * 
 */
public final class VSegment {
	protected float x, y1, y2;

	public VSegment(float x, float y1, float y2) {
		super();
		this.x = x;
		this.y1 = y1;
		this.y2 = y2;
	}

	public float getX() {
		return x;
	}

	public float getY1() {
		return y1;
	}

	public float getY2() {
		return y2;
	}

	public float getHeight() {
		return Math.abs(y2 - y1);
	}

}
