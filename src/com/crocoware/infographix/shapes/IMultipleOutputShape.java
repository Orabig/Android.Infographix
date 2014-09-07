package com.crocoware.infographix.shapes;

import com.crocoware.infographix.utils.Segment;

/**
 * This is implemented by Pipe Parts that has multiple outputs. These can be
 * used with Pipe.select() method.
 * 
 * @author Benoit
 * 
 */
public interface IMultipleOutputShape {

	Segment[] getOutputs();

	/**
	 * Some MultipleOutputShapes are composed of several shapes that would be
	 * able to be customized separately (colors, gradient...). In that case,
	 * this method is used. If these parts are not available, this method will
	 * return 'null', and the shape will only be customized as a whole.
	 * 
	 * Of course, the shapes must all own their output, so they are IOutputShape instances
	 * 
	 * @return an array of shapes, or null if not available
	 */
	IOutputShape[] getShapes();
}
