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

	public DownRightArcShape(HSegment start, VSegment to) {
		this.x1 = start.x1;
		this.x2 = start.x2;
		this.y1 = start.y;
		this.x3 = to.x;
		this.y3 = to.y1;
		this.y4 = to.y2;
		build();
	}

	protected void build(Path path, boolean isBody) {
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
	public float getLeft() {
		return x1;
	}

	@Override
	public float getRight() {
		return x3;
	}

	@Override
	public float getTop() {
		return y1;
	}

	@Override
	public float getBottom() {
		return y4;
	}

	@Override
	public void translate(float dx, float dy) {
		x1 += dx;
		x2 += dx;
		x3 += dx;
		y1 += dy;
		y3 += dy;
		y4 += dy;
		build();
	}

	@Override
	public void resize(float left, float top, float width, float height) {
		float ratioX = width / getWidth();
		float ratioY = height / getHeight();
		x1 *= ratioX;
		x2 *= ratioX;
		x3 *= ratioX;
		y1 *= ratioY;
		y3 *= ratioY;
		y4 *= ratioY;
		float dx = left - getLeft();
		float dy = top - getTop();
		translate(dx, dy);
	}

}
