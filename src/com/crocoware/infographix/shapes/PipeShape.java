package com.crocoware.infographix.shapes;

import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader.TileMode;

import com.crocoware.infographix.AbstractBorderedDrawable;
import com.crocoware.infographix.IBorderedDrawable;

/**
 * This shape is defined by an input (any segment) and an output.
 * 
 * Edges are drawn on sides, not on start and end of the pipe.
 * 
 * @author Benoit
 * 
 */
public class PipeShape extends AbstractBorderedDrawable implements
		IBorderedDrawable, IOutputShape, IPipelinePart {
	// A ======== B
	// . / / / / /.
	// ./ / / / / .
	// . / / / / /.
	// ./ / / / / .
	// C ======== D

	// Input points are AC
	private float xa, ya;
	private float xc, yc;
	// Output points are BD
	private float xb, yb;
	private float xd, yd;

	// Should border be straight or sloped ?
	private boolean isStraight = false;
	private float curveStrength = 0.5f;

	public PipeShape(Segment input, Segment output) {
		// A-B
		xa = input.x1;
		ya = input.y1;
		xc = input.x2;
		yc = input.y2;
		xb = output.x1;
		yb = output.y1;
		xd = output.x2;
		yd = output.y2;
	}

	public PipeShape(Segment input, float length) {
		// A-B
		xa = input.x1;
		ya = input.y1;
		xc = input.x2;
		yc = input.y2;
		Vector normal = input.getNormal();
		xb = xa + normal.dx * length;
		yb = ya + normal.dy * length;
		xd = xc + normal.dx * length;
		yd = yc + normal.dy * length;
		isStraight = true;
	}

	@Override
	public void resize(float left, float top, float width, float height) {
		float ratioX = width / getWidth();
		float ratioY = height / getHeight();
		translate(-getLeft(), -getTop());
		xa *= ratioX;
		xb *= ratioX;
		xc *= ratioX;
		xd *= ratioX;
		ya *= ratioY;
		yb *= ratioY;
		yc *= ratioY;
		yd *= ratioY;
		translate(left, top);
	}

	@Override
	public void translate(float dx, float dy) {
		xa += dx;
		xb += dx;
		xc += dx;
		xd += dx;
		ya += dy;
		yb += dy;
		yc += dy;
		yd += dy;
		rebuild();
	}

	@Override
	public float getLeft() {
		return Math.min(Math.min(xa, xb), Math.min(xc, xd));
	}

	@Override
	public float getTop() {
		return Math.min(Math.min(ya, yb), Math.min(xc, xd));
	}

	@Override
	public float getRight() {
		return Math.max(Math.max(xa, xb), Math.max(xc, xd));
	}

	@Override
	public float getBottom() {
		return Math.max(Math.max(ya, yb), Math.max(xc, xd));
	}

	protected void build(Path path, boolean isBody) {
		if (isStraight())
			drawStraight(path, isBody);
		else
			drawCurved(path, isBody);
	}

	private void drawStraight(Path path, boolean isBody) {
		path.moveTo(xa, ya);
		path.lineTo(xb, yb);
		drawOutput(path, getOutput(), isBody);
		path.lineTo(xc, yc);
		drawInput(path, xa, ya, isBody);
	}

	private void drawCurved(Path path, boolean isBody) {
		Vector inputNormal = getInput().getNormal();
		Vector outputNormal = getOutput().getNormal();
		// Distance between the center of input and output (pre-* strength)
		float distance = curveStrength
				* PointF.length(xa + xc - xb - xd, ya + yc - yb - yd) / 2;
		// Input/output slopes (tangents)
		float idx = inputNormal.dx * distance;
		float idy = inputNormal.dy * distance;
		float odx = outputNormal.dx * distance;
		float ody = outputNormal.dy * distance;

		path.moveTo(xa, ya);

		path.cubicTo(xa + idx, ya + idy, xb - odx, yb - ody, xb, yb);

		drawOutput(path, getOutput(), isBody);

		path.cubicTo(xd - odx, yd - ody, xc + idx, yc + idy, xc, yc);

		drawInput(path, xa, ya, isBody);
	}

	public boolean isStraight() {
		return isStraight;
	}

	public void setStraight(boolean isStraight) {
		this.isStraight = isStraight;
	}

	public Segment getInput() {
		return new Segment(xa, ya, xc, yc);
	}

	public Segment getOutput() {
		return new Segment(xb, yb, xd, yd);
	}

	@Override
	public void setBodyGradient(int color1, int color2) {
		this.setBodyShader(new LinearGradient(xa, ya, xb, yb, color1, color2,
				TileMode.CLAMP));
	}
}
