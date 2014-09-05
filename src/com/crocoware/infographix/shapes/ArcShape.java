package com.crocoware.infographix.shapes;

import android.graphics.Canvas;
import android.graphics.Path;
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
	 * Creates an arc which is "extruded" from the start segment. By default, this shape is a circle, but this can be changed
	 * if resized
	 * @param start
	 * @param center
	 * @param sweepAngle the angle of the arc, in degrees (clockwise)
	 */
	public ArcShape(Segment start, PointF center, float sweepAngle) {
		super();
		this.start = start;
		this.center = center;
		startAngle = start.getAngle();
		this.sweepAngle = sweepAngle;
		innerRadiusX = innerRadiusY = PointF.length(start.getX1() - center.x,
				start.getY1() - center.y);
		outerRadiusX = outerRadiusY = PointF.length(start.getX2() - center.x,
				start.getY2() - center.y);
		rebuild();
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

	@Override
	public void translate(float dx, float dy) {
		start.translate(dx, dy);
		center.offset(dx, dy);
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
		rebuild();
	}

	@Override
	protected void build(Path path, boolean isBody) {
		path.moveTo(start.getX1(), start.getY1());
		if (isBody)
			path.lineTo(start.getX2(), start.getY2());
		else
			path.moveTo(start.getX2(), start.getY2());

		// Outer arc
		RectF outerOval = getBounds();
		path.arcTo(outerOval, startAngle, sweepAngle);

		// TODO : Il faudrait calculer ici le premier point de arcTo si on affiche le edge 

		RectF innerOval = getInnerBounds();
		path.arcTo(innerOval, startAngle + sweepAngle, -sweepAngle);

		//			if (isBody)
		//				path.lineTo(xd, yd);
		//			else
		//				path.moveTo(xd, yd);
		//
		//			float xdc = (xc + xd) / 2;
		//			path.cubicTo(xdc, yd, xdc, yc, xc, yc);

		if (isBody)
			path.close();
	}

	private RectF getInnerBounds() {
		return new RectF(center.x - innerRadiusX, center.y - innerRadiusY,
				center.x + innerRadiusX, center.y + innerRadiusY);
	}
}
