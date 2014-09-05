package com.crocoware.infographix.shapes;

import android.graphics.Path;

import com.crocoware.infographix.AbstractBorderedDrawable;
import com.crocoware.infographix.IBorderedDrawable;

/**
 * An horizontal or vertical "pipe" shape (rectangle with top and bottom border)
 * 
 * Edges are drawn on sides, not on start and end of the pipe. End of the pipe
 * are always horizontal (for a vertical pipe) or vertical (for an horizontal
 * pipe)
 * 
 * 
 * @author Benoit
 * 
 */
public class PipeShape extends AbstractBorderedDrawable implements
		IBorderedDrawable {
	// A ---------- B
	// | .......... |
	// | .......... |
	// | .......... |
	// | .......... |
	// C ---------- D

	protected float xa, xb, xc, xd;
	protected float ya, yb, yc, yd;

	// private float x1, x2;
	// private float y1, y2;
	protected boolean isHorizontal;

	public PipeShape(HSegment from, HSegment to) {
		float y1 = from.getY();
		float y2 = to.getY();
		if (y1 > y2) {
			float y3 = y1;
			y1 = y2;
			y2 = y3;
		}
		// A-B
		xa = from.x1;
		xb = from.x2;
		ya = yb = y1;
		// C-D
		xc = to.x1;
		xd = to.x2;
		yc = yd = y2;
		this.isHorizontal = false;
		rebuild();
	}

	public PipeShape(VSegment from, VSegment to) {
		float x1 = from.getX();
		float x2 = to.getX();
		if (x1 > x2) {
			float x3 = x1;
			x1 = x2;
			x2 = x3;
		}
		// A-C
		xa = xc = x1;
		ya = from.y1;
		yc = from.y2;
		// B-D
		xb = xd = x2;
		yb = to.y1;
		yd = to.y2;
		this.isHorizontal = true;
		rebuild();
	}

	protected void build(Path edges, boolean isBody) {
		edges.moveTo(xa, ya);
		if (!isHorizontal && !isBody) {
			edges.lineTo(xc, yc);
			edges.moveTo(xd, yd);
			edges.lineTo(xb, yb);
			return;
		}
		edges.lineTo(xb, yb);
		if (isBody)
			edges.lineTo(xd, yd);
		else
			edges.moveTo(xd, yd);
		edges.lineTo(xc, yc);
		if (isBody)
			edges.close();
	}

	@Override
	public float getLeft() {
		return Math.min(xa, xc);
	}

	@Override
	public float getTop() {
		return Math.min(ya, yb);
	}

	@Override
	public void translate(float dx, float dy) {
		xa += dx;
		xb += dx;
		xc += dx;
		xd += dx;
		ya += dy;
		yb += dy;
		yc += dy;
		yd += dy;

		rebuild();
	}

	@Override
	public void resize(float left, float top, float width, float height) {
		float ratioX = width / getWidth();
		float ratioY = height / getHeight();
		translate(- getLeft(),- getTop());
		xa *= ratioX;
		xb *= ratioX;
		xc *= ratioX;
		xd *= ratioX;
		ya *= ratioY;
		yb *= ratioY;
		yc *= ratioY;
		yd *= ratioY;
		translate(left, top);
	}

	@Override
	public float getRight() {
		return Math.max(xb, xd);
	}

	@Override
	public float getBottom() {
		return Math.max(yc, yd);
	}

}
