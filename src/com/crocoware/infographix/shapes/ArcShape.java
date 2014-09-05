package com.crocoware.infographix.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.SweepGradient;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.crocoware.infographix.AbstractBorderedDrawable;

public class ArcShape extends AbstractBorderedDrawable {

	private Segment start;
	private PointF center;
	private float startAngle;
	private float sweepAngle;
	private float innerRadiusX;
	private float innerRadiusY;
	private float outerRadiusX;
	private float outerRadiusY;

	/**
	 * Creates an arc which is "extruded" from the start segment. By default,
	 * this shape is a circle, but this can be changed if resized
	 * 
	 * @param start
	 * @param center
	 * @param sweepAngle
	 *            the angle of the arc, in degrees (clockwise)
	 */
	public ArcShape(Segment start, PointF center, float sweepAngle) {
		super();
		this.start = start;
		this.center = center;
		startAngle = start.getAngle();
		this.sweepAngle = sweepAngle;
		computeRadius(start, center);
		rebuild();
	}

	private void computeRadius(Segment start, PointF center) {
		innerRadiusX = innerRadiusY = PointF.length(start.getX1() - center.x,
				start.getY1() - center.y);
		outerRadiusX = outerRadiusY = PointF.length(start.getX2() - center.x,
				start.getY2() - center.y);
	}

	@Override
	public float getLeft() {
		return center.x - outerRadiusX;
	}

	@Override
	public float getRight() {
		return center.x + outerRadiusX;
	}

	@Override
	public float getTop() {
		return center.y - outerRadiusY;
	}

	@Override
	public float getBottom() {
		return center.y + outerRadiusY;
	}

	/**
	 * Defines a shader which will cover the sweep of the arc.
	 * 
	 * @param color1
	 * @param color2
	 */
	public void setSweepShader(int color1, int color2) {
		int[] colors = new int[3];
		float[] pos = new float[3];
		float sweep = this.sweepAngle;
		if (sweep < 0) {
			pos[0] = 0;
			pos[1] = 1 + sweep / 360;
			pos[2] = 1;
		} else {
			pos[0] = 0;
			pos[1] = sweep / 360;
			pos[2] = 1;
		}
		colors[0] = color2;
		colors[1] = color2;
		colors[2] = color1;
		SweepGradient shader = new SweepGradient(center.x, center.y, colors,
				pos);
		Matrix localM = new Matrix();
		shader.getLocalMatrix(localM);
		localM.postRotate(start.getAngle(), center.x, center.y);
		shader.setLocalMatrix(localM);
		this.setBodyShader(shader);
	}

	@Override
	public void translate(float dx, float dy) {
		start.translate(dx, dy);
		center.offset(dx, dy);
		// TODO : Ne fonctionne pas !!
		// computeRadius(start, center);
		rebuild();
	}

	@Override
	public void resize(float left, float top, float width, float height) {
		// Calcul de la position de start dans le bounding box
		float rx1 = (start.x1 - center.x) / outerRadiusX;
		float rx2 = (start.x2 - center.x) / outerRadiusX;
		float ry1 = (start.y1 - center.y) / outerRadiusY;
		float ry2 = (start.y2 - center.y) / outerRadiusY;
		outerRadiusX = width / 2;
		outerRadiusY = height / 2;
		center.x = left + outerRadiusX;
		center.y = top + outerRadiusY;
		// Calcul de la nouvelle position de start
		start.x1 = center.x + rx1 * outerRadiusX;
		start.x2 = center.x + rx2 * outerRadiusX;
		start.y1 = center.y + ry1 * outerRadiusY;
		start.y2 = center.y + ry2 * outerRadiusY;
		computeRadius(start, center);
		rebuild();
	}

	@Override
	protected void build(Path path, boolean isBody) {
		// if (!isBody)path.addRect(getBounds(), Direction.CW);
		path.moveTo(start.getX1(), start.getY1());
		if (isBody)
			path.lineTo(start.getX2(), start.getY2());
		else
			path.moveTo(start.getX2(), start.getY2());

		// Outer arc
		RectF outerOval = getBounds();
		path.arcTo(outerOval, startAngle, sweepAngle);

		// TODO : Il faudrait calculer ici le premier point de arcTo si on
		// affiche le edge

		RectF innerOval = getInnerBounds();
		path.arcTo(innerOval, startAngle + sweepAngle, -sweepAngle);

		Log.e("test", "bounds: " + outerOval + " start=" + startAngle
				+ " sweep=" + sweepAngle);
		// if (isBody)
		// path.lineTo(xd, yd);
		// else
		// path.moveTo(xd, yd);
		//
		// float xdc = (xc + xd) / 2;
		// path.cubicTo(xdc, yd, xdc, yc, xc, yc);

		if (isBody)
			path.close();
	}

	private RectF getInnerBounds() {
		return new RectF(center.x - innerRadiusX, center.y - innerRadiusY,
				center.x + innerRadiusX, center.y + innerRadiusY);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Log.e("test", "draw : " + getBounds());
	}
}
