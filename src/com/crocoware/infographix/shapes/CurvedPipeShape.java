package com.crocoware.infographix.shapes;

import com.crocoware.infographix.AbstractBorderedDrawable;
import com.crocoware.infographix.IBorderedDrawable;

import android.graphics.Path;

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
public class CurvedPipeShape extends AbstractBorderedDrawable implements
		IBorderedDrawable {
	// A ---------- B
	// | .......... |
	// | .......... |
	// | .......... |
	// | .......... |
	// C ---------- D

	private float xa, xb, xc, xd;
	private float ya, yb, yc, yd;

	// private float x1, x2;
	// private float y1, y2;
	private boolean isHorizontal;

	private Path edges;
	private Path body;

	public CurvedPipeShape(HSegment from, HSegment to) {
		// A-B
		xa = from.x1;
		xb = from.x2;
		ya = yb = from.y;
		// C-D
		xc = to.x1;
		xd = to.x2;
		yc = yd = to.y;
		this.isHorizontal = false;
		buildEdges();
		buildBody();
	}

	public CurvedPipeShape(VSegment from, VSegment to) {
		// A-C
		xa = xc = from.x;
		ya = from.y1;
		yc = from.y2;
		// B-D
		xb = xd = to.x;
		yb = to.y1;
		yd = to.y2;
		this.isHorizontal = true;
		buildEdges();
		buildBody();
	}

	// public PipeShape(float x1, float x2, float y1, float y2,
	// boolean isHorizontal) {
	// super();
	// this.x1 = x1;
	// this.x2 = x2;
	// this.y1 = y1;
	// this.y2 = y2;
	// this.isHorizontal = isHorizontal;
	// buildEdges();
	// buildBody();
	// }

	private void buildBody() {
		body = new Path();
		build(body, true);
	}

	private void buildEdges() {
		edges = new Path();
		build(edges, false);
	}

	private void build(Path edges, boolean isBody) {
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

	protected Path getEdgePath() {
		return edges;
	}

	protected Path getBodyPath() {
		return body;
	}

}
