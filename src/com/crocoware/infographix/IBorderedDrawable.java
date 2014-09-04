package com.crocoware.infographix;

import android.graphics.Canvas;

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
	 * @param width
	 * @param height
	 */
	public void resize(float left,float top,float width,float height);
}
