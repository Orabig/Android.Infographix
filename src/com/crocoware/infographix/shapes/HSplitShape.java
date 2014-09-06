package com.crocoware.infographix.shapes;

import com.crocoware.infographix.ComposedBordered;

/**
 * This shape splits an "entry" segment into two subdivisions (given by ratio)
 * 
 * Once created, this shape can give its outputs with getOutputSegments() method
 * 
 * @author Benoit
 * 
 */
public class HSplitShape extends ComposedBordered {

	private Segment outputs[]; // TODO : translate outputs

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
	public HSplitShape(Segment entry, float width, float ratio, float gap) {
		super();
		if (width == 0)
			throw new IllegalArgumentException("width==0");
		if (ratio < 0)
			throw new IllegalArgumentException("ratio<0");
		if (ratio > 1)
			throw new IllegalArgumentException("ratio>1");
		// On découpe l'espace H en bouts
		// float X0=entry.x;
		float Y0 = entry.y1;
		float Y1 = entry.y2;
		// Calcul des positions
		float XB = entry.getX1(); // TODO : was getX on VSeg
		float XC = XB + width;

		float Ha = (Y1 - Y0) * ratio;
		float YB1 = Y0 + Ha;

		// Ce calcul répartit équitablement la largeur totale en deux parties
		// (ratio) et (1-ratio).
		float YCa1 = Y0 - gap * (1 - ratio);
		float YCa2 = YB1 - gap * (1 - ratio);
		float YCa3 = YB1 + gap * (ratio);
		float YCa4 = Y1 + gap * (ratio);

		Segment SBa = new Segment(XB, Y0, XB, YB1);// TODO : was VSeg
		Segment SBb = new Segment(XB, YB1, XB, Y1);// TODO : was VSeg

		Segment SCa = new Segment(XC, YCa1, XC, YCa2);// TODO : was VSeg
		Segment SCb = new Segment(XC, YCa3, XC, YCa4);// TODO : was VSeg

		setParts(new PipeShape(SBa, SCa), new PipeShape(SBb, SCb));

		outputs = new Segment[] { SCa, SCb };
	}

	public Segment[] getOutputSegments() {
		return outputs;
	}

	public Segment getOutputSegments(int n) {
		return outputs[n];
	}
}
