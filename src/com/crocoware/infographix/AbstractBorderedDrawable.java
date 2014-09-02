package com.crocoware.infographix;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public abstract class AbstractBorderedDrawable implements IBorderedDrawable {
	private Paint bodyPaint;
	private Paint edgePaint;

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

	/**
	 * @return the path of the edges of the shape
	 */
	protected abstract Path getEdgePath();

	/**
	 * @return the path of the body of the shape. May be null
	 */
	protected abstract Path getBodyPath();

	public void draw(Canvas canvas) {
		Path bodyPath = getBodyPath();
		if (bodyPath != null)
			canvas.drawPath(bodyPath, bodyPaint);
		canvas.drawPath(getEdgePath(), edgePaint);
	}
}
