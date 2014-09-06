package com.crocoware.infographix.shapes;

import com.crocoware.infographix.ComposedBordered;

/**
 * This shape joins two "entry" segments into an unique output
 * 
 * Once created, this shape can give its output with getOutputSegment() method
 * 
 * @author Benoit
 * 
 */
public class HJoinShape extends ComposedBordered {

	private Segment output; // TODO : translate output

	/**
	 * Creates a split shape.
	 * 
	 * @param entry
	 *            the entry segment
	 * @param width
	 *            the width of the shape. If width<0, the shape goes from right
	 *            to left
	 * @param ratio
	 *            The ratio of both output pipes
	 * @param gap
	 */
	public HJoinShape(Segment entry1, Segment entry2, float width) {
		super();
		if (width == 0)
			throw new IllegalArgumentException("width==0");
		if (entry1.getX1() != entry2.getX1())
			throw new IllegalArgumentException("entry1 and 2 are not aligned");
		// if ( TEST order entry1 < entry2)
		// throw new IllegalArgumentException("ratio>1");

		float dY = (entry2.y1 - entry1.y2) / 2;

		Segment SBa = new Segment(entry1);SBa.translate(width, dY);
		Segment SBb = new Segment(entry2);SBb.translate(width, -dY);

		setParts(new PipeShape(entry1, SBa), new PipeShape(entry2,
				SBb));

		output = new Segment(entry1.getX1() + width, entry1.y1 + dY,entry1.getX2() + width, entry2.y2 - dY);
	}

	public Segment getOutputSegment() {
		return output;
	}
	
	@Override
	public void translate(float dx, float dy) {
		output.translate(dx, dy);
		super.translate(dx, dy);
	}
}
