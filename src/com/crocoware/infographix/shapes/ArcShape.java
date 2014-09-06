package com.crocoware.infographix.shapes;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.Log;

import com.crocoware.infographix.AbstractBorderedDrawable;

public class ArcShape extends AbstractBorderedDrawable implements IOutputShape,
		IPipelinePart {

	private Segment start, output;
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
		this.start = new Segment(sweepAngle > 0 ? start.reverse() : start);
		this.center = new PointF(center.x, center.y);
		startAngle = this.start.getAngle();
		this.sweepAngle = sweepAngle;
		computeRadius(start, center);
		computeOutput();
		rebuild();
	}

	/**
	 * Creates an arc which is "extruded" from the start segment. By default,
	 * this shape is a circle, but this can be changed if resized
	 * 
	 * @param currentInput
	 * @param angle
	 * @param length
	 *            the total length of the arc
	 */
	public ArcShape(Segment start, float angle, float length) {
		this(start, computeCenterFor(start, angle, length), angle);
	}

	/**
	 * Creates an arc which is "extruded" from the start segment. By default,
	 * this shape is a circle, but this can be changed if resized
	 * 
	 * @param currentInput
	 * @param angle
	 * @param length
	 *            the total length of the arc
	 */
	public ArcShape(Segment start, float angle) {
		this(start, computeCenterFor(start, angle), angle);
	}

	private static PointF computeCenterFor(Segment start, float angle,
			float length) {
		// Compute innerRadius from length
		// Radius = innerRadius + width/2
		// length = Radius * angle (rad)
		float angleRad = angle * (float) Math.PI / 180;
		float innerRadius = length / Math.abs(angleRad) - start.getLength() / 2;
		if (innerRadius < 0)
			innerRadius = 0;
		return getCenterAtRadius(start, angle, innerRadius);
	}

	private static PointF computeCenterFor(Segment start, float angle) {
		return getCenterAtRadius(start, angle, start.getLength());
	}

	private static PointF getCenterAtRadius(Segment start, float angle,
			float innerRadius) {
		Vector dir = start.getVector().normalize();
		if (angle < 0)
			return new PointF(start.x1 - dir.dx * innerRadius, start.y1
					- dir.dy * innerRadius);
		else
			return new PointF(start.x2 + dir.dx * innerRadius, start.y2
					+ dir.dy * innerRadius);
	}

	private void computeRadius(Segment start, PointF center) {
		innerRadiusX = innerRadiusY = PointF.length(start.getX1() - center.x,
				start.getY1() - center.y);
		outerRadiusX = outerRadiusY = PointF.length(start.getX2() - center.x,
				start.getY2() - center.y);
	}

	private void computeOutput() {
		output = start.rotate(center, sweepAngle);
		if (sweepAngle > 0)
			output = output.reverse();
	}

	public Segment getOutput() {
		return output;
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

	private int[] sweepShader = null;

	/**
	 * Defines a shader which will cover the sweep of the arc.
	 * 
	 * @param color1
	 * @param color2
	 */
	public void setSweepShader(int color1, int color2) {
		// The sweepshader must be saved if when translate the shape
		sweepShader = new int[] { color1, color2 };

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
		restoreSweepShader();
		rebuild();
	}

	private void restoreSweepShader() {
		if (sweepShader != null) {
			setSweepShader(sweepShader[0], sweepShader[1]);
		}
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
		restoreSweepShader();
		rebuild();
	}

	@Override
	protected void build(Path path, boolean isBody) {
		// if (!isBody)path.addRect(getBounds(), Direction.CW);
		path.moveTo(start.getX1(), start.getY1());
		if (isBody|| isInputClosed()) // TODO : Fix start position...
			path.lineTo(start.getX2(), start.getY2());
		else if (sweepAngle > 0)
			path.moveTo(start.x1, start.y1);
		else
			path.moveTo(start.x2, start.y2);

		// Outer arc
		RectF outerOval = getBounds();
		path.arcTo(outerOval, startAngle, sweepAngle);
drawOutput(path, output.reverse(), isBody); // TODO : There too
//		if (isBody||isOutputClosed()) {
//			path.lineTo(output.x1, output.y1);
//		}else{
//			path.moveTo(output.x1, output.y1);
//		}
		RectF innerOval = getInnerBounds();
		path.arcTo(innerOval, startAngle + sweepAngle, -sweepAngle);

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
	public void setBodyGradient(int color1, int color2) {
		setSweepShader(color1, color2);
	}
}
