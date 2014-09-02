package com.crocoware.infographix.shapes;

import com.crocoware.infographix.AbstractBorderedDrawable;
import com.crocoware.infographix.IBorderedDrawable;

import android.graphics.Path;

/**
 * An horizontal or vertical "pipe" shape (rectangle with top and bottom border)
 * 
 * @author Benoit
 * 
 */
public class PipeShape extends AbstractBorderedDrawable implements
		IBorderedDrawable {
	private float x1, x2;
	private float y1, y2;
	private boolean isHorizontal;

	private Path edges;
	private Path body;

	public PipeShape(float x1, float x2, float y1, float y2,
			boolean isHorizontal) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.isHorizontal = isHorizontal;
		buildEdges();
		buildBody();
	}

	private void buildBody() {
		body = new Path();
		build(body, true);
	}

	private void buildEdges() {
		edges = new Path();
		build(edges, false);
	}

	private void build(Path edges, boolean isBody) {
		edges.moveTo(x1, y1);
		if (!isHorizontal && !isBody) {
			edges.lineTo(x1, y2);
			edges.moveTo(x2, y2);
			edges.lineTo(x2, y1);
		} else {
			edges.lineTo(x2, y1);
			if (isBody)
				edges.lineTo(x2, y2);
			else
				edges.moveTo(x2, y2);
			edges.lineTo(x1, y2);
		}
		if (isBody)
			edges.close();
	}

	protected Path getEdgePath() {
		return edges;
	}

	protected Path getBodyPath() {
		return body;
	}

}
