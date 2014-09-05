package com.crocoware.infographix.shapes;

import android.graphics.Path;

/**
 * Defines the size of an arrow, relative to the width of the pipe
 * 
 * @author Benoit
 * 
 */
public class Arrow {
	public final static Arrow STANDARD = new Arrow(1.4f, 0.9f);
	public final static Arrow WIDE = new Arrow(1.2f, 1.2f);
	public final static Arrow NARROW = new Arrow(2.2f, 0.5f);
	public final static Arrow INNER = new Arrow(0.4f, -0.3f);
	private float ahead;
	private float edges;

	public Arrow(float ahead, float edges) {
		this.ahead = ahead;
		this.edges = edges;
	}

	/**
	 * This method draws an arrow. The output segment is meant to be oriented
	 * such as the arrow goes right if the segment is oriented down.
	 * 
	 * @param output
	 * @param path the object to write to. The path is supposed to be at the first point of output
	 */
	protected void draw(Segment output, Path path) {
		float len=output.getLength();
		Vector right=output.getNormal();
		Vector down=output.getVector().normalize();
		float x=output.x1;
		float y=output.y1;
		float x2=output.x2;
		float y2=output.y2;
		float lenEdges=len*edges;
		float lenArrow=len*ahead;
		path.lineTo(x-down.dx*lenEdges, y-down.dy*lenEdges);
		path.lineTo((x+x2)/2+right.dx*lenArrow, (y+y2)/2+right.dy*lenArrow);
		path.lineTo(x2+down.dx*lenEdges, y2+down.dy*lenEdges);
		path.lineTo(x2,y2);
	}
}
