package com.crocoware.infographix;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;

public abstract class AbstractBorderedDrawable implements IBorderedDrawable {
	private Paint bodyPaint;
	private Paint edgePaint;

	private Path edges;
	private Path body;

	public AbstractBorderedDrawable() {
	}

	public void draw(Canvas canvas) {
		Path bodyPath = getBodyPath();
		if (bodyPath != null)
			canvas.drawPath(bodyPath, getBodyPaint());
		canvas.drawPath(getEdgePath(), getEdgePaint());
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

	protected final void rebuild() {
		body = null;
		edges = null;
	}

	/**
	 * @return the path of the edges of the shape
	 */
	protected Path getEdgePath() {
		if (edges == null) {
			edges = new Path();
			build(edges, false);
		}
		return edges;
	}

	/**
	 * @return the path of the body of the shape. May be null if the shape is a
	 *         segment only
	 */
	protected Path getBodyPath() {
		if (body == null) {
			body = new Path();
			build(body, true);
		}
		return body;
	}

	protected Paint getBodyPaint() {
		if (bodyPaint == null) {
			bodyPaint = new Paint();

			bodyPaint.setColor(Color.BLUE);
			bodyPaint.setAntiAlias(false);
			bodyPaint.setStyle(Paint.Style.FILL);
		}
		return bodyPaint;
	}

	protected Paint getEdgePaint() {
		if (edgePaint == null) {
			edgePaint = new Paint();

			edgePaint.setColor(Color.BLACK);
			edgePaint.setAntiAlias(true);
			edgePaint.setStrokeWidth(4);
			edgePaint.setStyle(Paint.Style.STROKE);
		}
		return edgePaint;
	}

	// Delegates some Paint methods. Useful with Composed shapes

	@Override
	public void setBodyARGB(int a, int r, int g, int b) {
		getBodyPaint().setARGB(a, r, g, b);
	}

	@Override
	public void setBodyAlpha(int arg0) {
		getBodyPaint().setAlpha(arg0);
	}

	@Override
	public void setBodyColor(int arg0) {
		getBodyPaint().setColor(arg0);
	}

	@Override
	public void setBodyShader(Shader shader) {
		getBodyPaint().setShader(shader);
	}

	@Override
	public void setEdgeARGB(int a, int r, int g, int b) {
		getEdgePaint().setARGB(a, r, g, b);
	}

	@Override
	public void setEdgeAlpha(int a) {
		getEdgePaint().setAlpha(a);
	}

	@Override
	public void setEdgeColor(int color) {
		getEdgePaint().setColor(color);
	}

	@Override
	public void setEdgePathEffect(PathEffect effect) {
		getEdgePaint().setPathEffect(effect);
	}

	@Override
	public void setEdgeWidth(float width) {
		getEdgePaint().setStrokeWidth(width);
	}

	public RectF getBounds() {
		return new RectF(getLeft(), getTop(), getRight(), getBottom());
	}

}
