package com.crocoware.infographix.shapes;

import android.graphics.Path;
import android.graphics.RectF;

import com.crocoware.infographix.AbstractBorderedDrawable;
import com.crocoware.infographix.IBorderedDrawable;

/**
 * 
 * An arc going down then right. Different radius are used for upper and lower
 * edges. Top and right edges are not drawn
 * 
 * @author Benoit
 * 
 */
public class DownRightArcShape extends AbstractBorderedDrawable implements
		IBorderedDrawable {
	private float x1, x2, y1; // Top edge definition
	private float x3, y3, y4; // Right edge definition

	private Path edges;
	private Path body;

	public DownRightArcShape(float x1, float x2, float y1, float x3, float y3,
			float y4) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.x3 = x3;
		this.y3 = y3;
		this.y4 = y4;
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

	private void build(Path path, boolean isBody) {
		path.moveTo(x2, y1);

		// top-right arc
		RectF arc1 = new RectF(x2, y1 - (y3 - y1), x3 + (x3 - x2), y3);
		path.arcTo(arc1, 180, -90);
		if (isBody)
			path.lineTo(x3, y4);
		else
			path.moveTo(x3, y4);

		// bottom-left arc
		RectF arc2 = new RectF(x1, y1 - (y4 - y1), x3 + (x3 - x1), y4);
		path.arcTo(arc2, 90, 90);
		if (isBody)
			path.close();
	}

	@Override
	protected Path getEdgePath() {
		return edges;
	}

	@Override
	protected Path getBodyPath() {
		return body;
	}

}
