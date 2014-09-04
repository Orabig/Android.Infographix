package com.crocoware.infographix;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public abstract class AbstractBorderedDrawable implements IBorderedDrawable {
	private Paint bodyPaint;
	private Paint edgePaint;

	private Path edges;
	private Path body;

	public AbstractBorderedDrawable() {
		bodyPaint = new Paint();
		edgePaint = new Paint();

		bodyPaint.setColor(Color.RED);
		bodyPaint.setAntiAlias(false);
		bodyPaint.setStyle(Paint.Style.FILL);

		edgePaint.setColor(Color.BLACK);
		edgePaint.setAntiAlias(true);
		edgePaint.setStrokeWidth(4);
		edgePaint.setStyle(Paint.Style.STROKE);
	}

	public void draw(Canvas canvas) {
		Path bodyPath = getBodyPath();
		if (bodyPath != null)
			canvas.drawPath(bodyPath, bodyPaint);
		canvas.drawPath(getEdgePath(), edgePaint);
	}

	@Override
	public float getWidth() {
		return getRight() - getLeft();
	}

	@Override
	public float getHeight() {
		return getBottom() - getTop();
	}

	protected abstract void build(Path path, boolean isBody);

	protected final void build() {
		body = new Path();
		build(body, true);
		edges = new Path();
		build(edges, false);
	}

	/**
	 * @return the path of the edges of the shape
	 */
	protected Path getEdgePath() {
		return edges;
	}

	/**
	 * @return the path of the body of the shape. May be null
	 */
	protected Path getBodyPath() {
		return body;
	}
}
