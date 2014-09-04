package com.crocoware.infographix;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Shader;

/**
 * Builds a bordered shape from many others
 * 
 * @author Benoit
 * 
 */
public class ComposedBordered extends AbstractBorderedDrawable {

	private AbstractBorderedDrawable[] parts;

	public ComposedBordered(AbstractBorderedDrawable... parts) {
		setParts(parts);
	}

	/**
	 * Redefines the composed shape with a new set of parts
	 * 
	 * @param parts
	 */
	public void setParts(AbstractBorderedDrawable... parts) {
		this.parts = parts;
	}

	@Override
	protected Path getEdgePath() {
		Path path = new Path();
		for (AbstractBorderedDrawable part : parts) {
			path.addPath(part.getEdgePath());
		}
		return path;
	}

	@Override
	protected Path getBodyPath() {
		Path path = new Path();
		for (AbstractBorderedDrawable part : parts) {
			path.addPath(part.getBodyPath());
		}
		return path;
	}

	@Override
	public float getLeft() {
		float left = Float.POSITIVE_INFINITY;
		for (AbstractBorderedDrawable part : parts) {
			if (left > part.getLeft())
				left = part.getLeft();
		}
		return left;
	}

	@Override
	public float getRight() {
		float right = Float.NEGATIVE_INFINITY;
		for (AbstractBorderedDrawable part : parts) {
			if (right < part.getRight())
				right = part.getRight();
		}
		return right;
	}

	@Override
	public float getTop() {
		float top = Float.POSITIVE_INFINITY;
		for (AbstractBorderedDrawable part : parts) {
			if (top > part.getTop())
				top = part.getTop();
		}
		return top;
	}

	@Override
	public float getBottom() {
		float bottom = Float.NEGATIVE_INFINITY;
		for (AbstractBorderedDrawable part : parts) {
			if (bottom < part.getBottom())
				bottom = part.getBottom();
		}
		return bottom;
	}

	@Override
	public void translate(float dx, float dy) {
		for (AbstractBorderedDrawable part : parts) {
			part.translate(dx, dy);
		}
	}

	@Override
	public void resize(float left, float top, float width, float height) {
		// Initial positions
		float left1 = getLeft();
		float top1 = getTop();
		float width1 = getWidth();
		float height1 = getHeight();
		float ratioX = width / width1;
		float ratioY = height / height1;
		for (AbstractBorderedDrawable part : parts) {
			float newX = (part.getLeft() - left1) * ratioX + left;
			float newY = (part.getTop() - top1) * ratioY + top;
			float newW = part.getWidth() * ratioX;
			float newH = part.getHeight() * ratioY;
			part.resize(newX, newY, newW, newH);
		}
	}

	@Override
	protected void build(Path path, boolean isBody) {
		throw new IllegalAccessError(
				"build() should not be called on ComposedBordered class");
	}

	@Override
	public void setBodyARGB(int a, int r, int g, int b) {
		for (AbstractBorderedDrawable part : parts) {
			part.setBodyARGB(a, r, g, b);
		}
	}

	@Override
	public void setBodyAlpha(int arg0) {
		for (AbstractBorderedDrawable part : parts) {
			part.setBodyAlpha(arg0);
		}
	}

	@Override
	public void setBodyColor(int arg0) {
		for (AbstractBorderedDrawable part : parts) {
			part.setBodyColor(arg0);
		}
	}

	@Override
	public void setBodyShader(Shader shader) {
		for (AbstractBorderedDrawable part : parts) {
			part.setBodyShader(shader);
		}
	}

	@Override
	public void setEdgeARGB(int a, int r, int g, int b) {
		for (AbstractBorderedDrawable part : parts) {
			part.setEdgeARGB(a, r, g, b);
		}
	}

	@Override
	public void setEdgeAlpha(int a) {
		for (AbstractBorderedDrawable part : parts) {
			part.setEdgeAlpha(a);
		}
	}

	@Override
	public void setEdgeColor(int color) {
		for (AbstractBorderedDrawable part : parts) {
			part.setEdgeColor(color);
		}
	}

	@Override
	public void setEdgePathEffect(PathEffect effect) {
		for (AbstractBorderedDrawable part : parts) {
			part.setEdgePathEffect(effect);
		}
	}

	@Override
	public void setEdgeWidth(float width) {
		for (AbstractBorderedDrawable part : parts) {
			part.setEdgeWidth(width);
		}
	}

	@Override
	public void draw(Canvas canvas) {
		for (AbstractBorderedDrawable part : parts) {
			part.draw(canvas);
		}
	}
}
