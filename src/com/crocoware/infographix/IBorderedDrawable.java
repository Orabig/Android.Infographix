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
}
