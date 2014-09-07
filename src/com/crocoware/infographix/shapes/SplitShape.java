package com.crocoware.infographix.shapes;

import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;

import com.crocoware.infographix.ComposedBordered;
import com.crocoware.infographix.utils.Segment;
import com.crocoware.infographix.utils.Vector;

/**
 * This shape splits an "entry" segment into two subdivisions (given by ratio)
 * 
 * Once created, this shape can give its outputs with getOutputSegments() method
 * 
 * @author Benoit
 * 
 */
public class SplitShape extends ComposedBordered implements IPipelinePart,
		IMultipleOutputShape {

	private Segment outputs[]; // TODO : translate outputs

	/**
	 * Creates a split shape with 2 outputs
	 * 
	 * @param entry
	 *            the entry segment
	 * @param length
	 *            the length of the shape.
	 * @param ratio
	 *            The ratio of both output pipes
	 * @param gap
	 *            Distance between the tow output segments
	 */
	public SplitShape(Segment entry, float length, float ratio, float gap) {
		super();
		if (length <= 0)
			throw new IllegalArgumentException("length<=0");
		if (ratio < 0)
			throw new IllegalArgumentException("ratio<0");
		if (ratio > 1)
			throw new IllegalArgumentException("ratio>1");

		float height = entry.length();

		// Height of the first part
		float height1 = ratio * height;
		// Height of the second part
		float height2 = (1 - ratio) * height;

		float gap1 = (1 - ratio) * gap;
		float gap2 = ratio * gap;

		Vector down = entry.getVector().normalize();

		Vector toOutput1 = entry.getNormal().scale(length);
		Vector toOutput2 = (Vector) toOutput1.clone();

		Segment input1 = new Segment(entry.getA(), down.getScaled(height1));
		Segment input2 = new Segment(entry.getB().translate(-height2, down),
				down.getScaled(height2));

		toOutput1.translate(-gap1, down);
		toOutput2.translate(gap2 - height2, down);

		Segment output1 = new Segment(entry.getA().translate(toOutput1),
				down.getScaled(height1));
		Segment output2 = new Segment(entry.getB().translate(toOutput2),
				down.getScaled(height2));

		setParts(new PipeShape(input1, output1), new PipeShape(input2, output2));

		outputs = new Segment[] { output1, output2 };
	}

	/**
	 * Creates a split shape with N outputs (N is given by the length of
	 * ratio[])
	 * 
	 * @param entry
	 *            the entry segment
	 * @param length
	 *            the length of the shape.
	 * @param ratio
	 *            The ratio of each output pipes.
	 * @param gap
	 *            Distance between the tow output segments
	 */
	public SplitShape(Segment entry, float length, float[] ratio, float gap) {
		super();
		int N = ratio.length;
		if (length <= 0)
			throw new IllegalArgumentException("length<=0");
		// if (ratio < 0)
		// throw new IllegalArgumentException("ratio<0");
		// if (ratio > 1)
		// throw new IllegalArgumentException("ratio>1");

		float height = entry.length();

		// Height of the parts
		float[] heights = new float[N];
		for (int i = 0; i < N; i++)
			heights[i] = ratio[i] * height;

		float[] gaps = new float[N];
		for (int i = 0; i < N; i++)
			gaps[i] = 0;// (1 - ratio) * gap;

		Vector down = entry.getVector().normalize();

		Vector[] toOutputs = new Vector[N];
		toOutputs[0] = entry.getNormal().scale(length);
		for (int i = 1; i < N; i++)
			toOutputs[i] = (Vector) toOutputs[0].clone();

		Segment[] inputs = new Segment[N];
		float inputPosition = 0;
		for (int i = 0; i < N; i++) {
			inputs[i] = new Segment(
					entry.getA().translate(inputPosition, down),
					down.getScaled(heights[i]));
			inputPosition += heights[i];
		}

		// toOutput1.translate(-gap1, down);
		// toOutput2.translate(gap2 - height2, down);
		float outputOffset = -gap * (N - 1) / 2;
		for (int i = 0; i < N; i++) {
			toOutputs[i].translate(outputOffset, down);
			outputOffset += heights[i] + gap;
		}

		outputs = new Segment[N];
		for (int i = 0; i < N; i++)
			outputs[i] = new Segment(entry.getA().translate(toOutputs[i]),
					down.getScaled(heights[i]));

		PipeShape[] parts = new PipeShape[N];
		for (int i = 0; i < N; i++)
			parts[i] = new PipeShape(inputs[i], outputs[i]);
		setParts(parts);
	}

	public Segment[] getOutputs() {
		return outputs;
	}

	public Segment getOutputSegment(int n) {
		return outputs[n];
	}

	@Override
	public IOutputShape[] getShapes() {
		IOutputShape[] shapes = new IOutputShape[parts.size()];
		for (int i = 0; i < shapes.length; i++) {
			shapes[i] = (IOutputShape) parts.get(i);
		}
		return shapes;
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
