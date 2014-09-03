package com.crocoware.infographix.shapes;

/**
 * An object representing an horizontal segment
 * 
 */
public final class HSegment {
	protected float x1, x2, y;

	public HSegment(float x1, float x2, float y) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y = y;
	}

	public float getX1() {
		return x1;
	}

	public float getX2() {
		return x2;
	}

	public float getWidth() {
		return Math.abs(x2 - x1);
	}

	public float getY() {
		return y;
	}

}
