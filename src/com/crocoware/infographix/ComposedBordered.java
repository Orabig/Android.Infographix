package com.crocoware.infographix;

import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Shader;

import com.crocoware.infographix.shapes.IPipelinePart;

/**
 * Builds a bordered shape from many others
 * 
 * @author Benoit
 * 
 */
public class ComposedBordered extends AbstractBorderedDrawable {

	protected ArrayList<IBorderedDrawable> parts;

	public ComposedBordered(IPipelinePart... parts) {
		setParts(parts);
	}

	/**
	 * Redefines the composed shape with a new set of parts
	 * 
	 * @param parts
	 */
	public void setParts(IPipelinePart... parts) {
		this.parts = new ArrayList<IBorderedDrawable>(parts.length);
		this.parts.addAll(Arrays.asList(parts));
	}

	public void push(IPipelinePart part) {
		parts.add(part);
	}

	@Override
	public Path getEdgePath() {
		Path path = new Path();
		for (IBorderedDrawable part : parts) {
			path.addPath(part.getEdgePath());
		}
		return path;
	}

	@Override
	public Path getBodyPath() {
		Path path = new Path();
		for (IBorderedDrawable part : parts) {
			path.addPath(part.getBodyPath());
		}
		return path;
	}

	@Override
	public float getLeft() {
		float left = Float.POSITIVE_INFINITY;
		for (IBorderedDrawable part : parts) {
			if (left > part.getLeft())
				left = part.getLeft();
		}
		return left;
	}

	@Override
	public float getRight() {
		float right = Float.NEGATIVE_INFINITY;
		for (IBorderedDrawable part : parts) {
			if (right < part.getRight())
				right = part.getRight();
		}
		return right;
	}

	@Override
	public float getTop() {
		float top = Float.POSITIVE_INFINITY;
		for (IBorderedDrawable part : parts) {
			if (top > part.getTop())
				top = part.getTop();
		}
		return top;
	}

	@Override
	public float getBottom() {
		float bottom = Float.NEGATIVE_INFINITY;
		for (IBorderedDrawable part : parts) {
			if (bottom < part.getBottom())
				bottom = part.getBottom();
		}
		return bottom;
	}

	@Override
	public void translate(float dx, float dy) {
		for (IBorderedDrawable part : parts) {
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
		for (IBorderedDrawable part : parts) {
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
		for (IBorderedDrawable part : parts) {
			part.setBodyARGB(a, r, g, b);
		}
	}

	@Override
	public void setBodyAlpha(int arg0) {
		for (IBorderedDrawable part : parts) {
			part.setBodyAlpha(arg0);
		}
	}

	@Override
	public void setBodyColor(int arg0) {
		for (IBorderedDrawable part : parts) {
			part.setBodyColor(arg0);
		}
	}

	@Override
	public void setBodyShader(Shader shader) {
		for (IBorderedDrawable part : parts) {
			part.setBodyShader(shader);
		}
	}

	@Override
	public void setEdgeARGB(int a, int r, int g, int b) {
		for (IBorderedDrawable part : parts) {
			part.setEdgeARGB(a, r, g, b);
		}
	}

	@Override
	public void setEdgeAlpha(int a) {
		for (IBorderedDrawable part : parts) {
			part.setEdgeAlpha(a);
		}
	}

	@Override
	public void setEdgeColor(int color) {
		for (IBorderedDrawable part : parts) {
			part.setEdgeColor(color);
		}
	}

	@Override
	public void setEdgePathEffect(PathEffect effect) {
		for (IBorderedDrawable part : parts) {
			part.setEdgePathEffect(effect);
		}
	}

	@Override
	public void setEdgeWidth(float width) {
		for (IBorderedDrawable part : parts) {
			part.setEdgeWidth(width);
		}
	}

	@Override
	public void setInputClosed(boolean isInputClosed) {
		super.setInputClosed(isInputClosed);
		for (IBorderedDrawable part : parts) {
			part.setInputClosed(isInputClosed);
		}
	}

	@Override
	public void setOutputClosed(boolean isOutputClosed) {
		super.setOutputClosed(isOutputClosed);
		for (IBorderedDrawable part : parts) {
			part.setOutputClosed(isOutputClosed);
		}
	}

	@Override
	public void draw(Canvas canvas) {
		int size = parts.size();
		// Draw parts in reverse order (arrows need this)
		for (int i = 0; i < size; i++) {
			parts.get(size - i - 1).draw(canvas);
		}
	}

	public boolean isEmpty() {
		return parts.isEmpty();
	}
}
