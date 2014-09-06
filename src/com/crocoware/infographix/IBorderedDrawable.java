package com.crocoware.infographix;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Shader;

/**
 * Represents a drawable item which has border. Thus, the drawing is made with
 * two successive calls : drawShape() and drawEdge()
 * 
 * @author Benoit
 * 
 */
public interface IBorderedDrawable {

	public void draw(Canvas canvas);

	public float getWidth();

	public float getHeight();

	public float getLeft();

	public float getRight();

	public float getTop();

	public float getBottom();

	public void translate(float dx, float dy);

	/**
	 * Resize the shape to fit the new coordinates
	 * 
	 * @param width
	 * @param height
	 */
	public void resize(float left, float top, float width, float height);

	public void setEdgeWidth(float width);

	public void setEdgePathEffect(PathEffect effect);

	public void setEdgeColor(int color);

	public void setEdgeAlpha(int a);

	public void setEdgeARGB(int a, int r, int g, int b);

	public void setBodyColor(int arg0);

	public void setBodyShader(Shader shader);

	public void setBodyAlpha(int arg0);

	public void setBodyARGB(int a, int r, int g, int b);
	
	public void setOutputArrow(Arrow outputArrow);

	public Path getBodyPath();

	public Path getEdgePath();

	public abstract void setOutputClosed(boolean isOutputClosed);

	public abstract boolean isOutputClosed();

	public abstract void setInputClosed(boolean isInputClosed);

	public abstract boolean isInputClosed();
}
