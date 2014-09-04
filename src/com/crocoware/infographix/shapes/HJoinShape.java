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

	private VSegment output; // TODO : translate output

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
	public HJoinShape(VSegment entry1, VSegment entry2, float width) {
		super();
		if (width == 0)
			throw new IllegalArgumentException("width==0");
		if (entry1.x != entry2.x)
			throw new IllegalArgumentException("entry1 and 2 are not aligned");
		// if ( TEST order entry1 < entry2)
		// throw new IllegalArgumentException("ratio>1");

		float a = entry1.getHeight();
		float b = entry2.getHeight();
		float dY = (entry2.y1 - entry1.y2) / 2;

		VSegment SBa = entry1.translate(width, dY);
		VSegment SBb = entry2.translate(width, -dY);

		setParts(new CurvedPipeShape(entry1, SBa), new CurvedPipeShape(entry2,
				SBb));

		output = new VSegment(entry1.x + width, entry1.y1 + dY, entry2.y2 - dY);
	}

	public VSegment getOutputSegment() {
		return output;
	}
}
