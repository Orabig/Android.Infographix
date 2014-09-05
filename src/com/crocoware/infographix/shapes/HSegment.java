package com.crocoware.infographix.shapes;

/**
 * An object representing an horizontal segment
 * 
 */
public final class HSegment extends Segment {

	public HSegment(float x1, float x2, float y) {
		super(x1,y,x2,y);
	}

	public float getWidth() {
		return Math.abs(x2 - x1);
	}

	public float getY() {
		return y1;
	}

}
