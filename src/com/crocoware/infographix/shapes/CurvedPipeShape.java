package com.crocoware.infographix.shapes;

import android.graphics.Path;

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
public class CurvedPipeShape extends PipeShape implements IBorderedDrawable {
	// A ---------- B
	// | .......... |
	// | .......... |
	// | .......... |
	// | .......... |
	// C ---------- D

	public CurvedPipeShape(HSegment from, HSegment to) {
		super(from, to);
	}

	public CurvedPipeShape(VSegment from, VSegment to) {
		super(from, to);
	}

	protected void build(Path edges, boolean isBody) {
		edges.moveTo(xa, ya);
		if (!isHorizontal) {
			float yac = (ya + yc) / 2;
			edges.cubicTo(xa, yac, xc, yac, xc, yc);
			if (isBody)
				edges.lineTo(xd, yd);
			else
				edges.moveTo(xd, yd);
			float ydb = (yd + yb) / 2;
			edges.cubicTo(xd, ydb, xb, ydb, xb, yb);
			if (isBody)
				edges.close();
		} else {

			float xab = (xa + xb) / 2;
			edges.cubicTo(xab, ya, xab, yb, xb, yb);

			if (isBody)
				edges.lineTo(xd, yd);
			else
				edges.moveTo(xd, yd);

			float xdc = (xc + xd) / 2;
			edges.cubicTo(xdc, yd, xdc, yc, xc, yc);

			if (isBody)
				edges.close();
		}
	}
}
