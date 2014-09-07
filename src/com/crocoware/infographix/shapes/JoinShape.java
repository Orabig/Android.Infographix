package com.crocoware.infographix.shapes;

import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;

import com.crocoware.infographix.ComposedBordered;
import com.crocoware.infographix.utils.Position;
import com.crocoware.infographix.utils.Segment;
import com.crocoware.infographix.utils.Vector;

/**
 * This shape joins two "entry" segments into an unique output
 * 
 * Once created, this shape can give its output with getOutputSegment() method
 * 
 * @author Benoit
 * 
 */
public class JoinShape extends ComposedBordered implements IPipelinePart,
		IOutputShape {

	private Segment output; // TODO : translate output

	/**
	 * Creates a join shape.
	 * 
	 * @param entry
	 *            the entry segment
	 * @param length
	 *            the length of the shape. If the inputs are not aligned,
	 *            strange behaviours may occur.
	 * @param ratio
	 *            The ratio of both output pipes
	 * @param gap
	 */
	public JoinShape(Segment entry1, Segment entry2, float length) {
		super();
		if (length == 0)
			throw new IllegalArgumentException("width==0");

		Vector right1 = entry1.getNormal().scale(length);
		Vector right2 = entry2.getNormal().scale(length);
		Position outputCenter1 = entry1.getCenter().translate(right1);
		Position outputCenter2 = entry2.getCenter().translate(right2);

		float width1 = entry1.length();
		float width2 = entry2.length();
		float width = width1 + width2;
		float angle = (entry1.angle() + entry2.angle()) / 2;

		Position outputCenter = Position.getCenterOf(outputCenter1, outputCenter2);
		Vector dir = Vector.createFromAngle(width, angle);
		output = Segment.createFromCenter(outputCenter, dir);
		dir.normalize().scale(width1);
		Segment output1 = new Segment(output.getA(), dir);
		dir.normalize().scale(width2);
		Segment output2 = new Segment(output1.getB(), dir);

		setParts(new PipeShape(entry1, output1), new PipeShape(entry2, output2));
	}

	public Segment getOutput() {
		return output;
	}

	@Override
	public void translate(float dx, float dy) {
		output.translate(dx, dy);
		super.translate(dx, dy);
	}

	@Override
	public void setBodyGradient(int color1, int color2) {
		PipeShape pipe1 = (PipeShape) parts.get(0);
		Segment input1 = pipe1.getInput();
		Segment output1 = pipe1.getOutput();
		this.setBodyShader(new LinearGradient(input1.x1, input1.y1, output1.x1,
				output1.y1, color1, color2, TileMode.CLAMP));
	}
}
