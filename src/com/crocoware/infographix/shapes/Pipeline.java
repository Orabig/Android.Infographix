package com.crocoware.infographix.shapes;

import java.util.HashMap;

import com.crocoware.infographix.Arrow;
import com.crocoware.infographix.ComposedBordered;
import com.crocoware.infographix.IBorderedDrawable;
import com.crocoware.infographix.utils.Segment;

/**
 * This class allow easy definition of a chain of shapes.
 * 
 * Implement a chain a follow :
 * 
 * 
 * // pipe = new Pipe(segment).forward(100).setArrow(...).setColor(...) //
 * .forward(100).setColor(...) // .goRight(90).split(5);
 * 
 * // pipe.getOutput(0).goLeft(50,90).forward(100).goRight(50,90)
 * //.forward(150).tag("label").write("Text").setTextColor(RED)
 * //.goLeft(90).setColorGradient(BLUE,RED)
 * //.forward(150).setColorGradient(RED,
 * GREEN).setArrow(INNER).forward(100).end();
 * 
 * // pipe.getOutput(1).line(100).round(5).write("label");
 * 
 * The constructor takes a segment which must be facing down if the pipeline
 * goes right.
 * 
 * Each directive "forward", "turnRight", "turnLeft", "turn", "split" or "join"
 * creates a new shape.
 * 
 * Each shape may be configured by adding calls to setters method. A tag() call
 * allows to reference a shape which can be later retrieved by
 * pipe.findByTag(tag)
 * 
 * @author Benoit
 * 
 */
public class Pipeline {

	// STATE variables : used while the pipe is being built.
	private ComposedBordered composed = new ComposedBordered();

	private Segment currentInput;
	private IPipelinePart currentShape;

	private HashMap<String, IPipelinePart> shapesByTag = new HashMap<String, IPipelinePart>();

	public Pipeline(Segment input) {
		currentInput = input;
	}

	// Shape creation directives

	/**
	 * Appends a shape to the pipe, going forward.
	 * 
	 * @param length
	 *            the length of the new shape
	 * @return
	 */
	public Pipeline forward(float length) {
		ensureInputAvailable();
		push(new PipeShape(currentInput, length));
		return this;
	}

	/**
	 * Appends a shape to the pipe, turning at the given angle.
	 * 
	 * @param angle
	 *            clockwise angle of the turn
	 * @param length
	 *            total length of the turn
	 * @return
	 */
	public Pipeline turn(float angle, float length) {
		ensureInputAvailable();
		push(new ArcShape(currentInput, angle, length));
		return this;
	}

	/**
	 * Appends a shape to the pipe, turning at the given angle. The inner radius
	 * equals the width of the input segment.
	 * 
	 * @param angle
	 *            clockwise angle of the turn
	 * @return
	 */
	public Pipeline turn(float angle) {
		ensureInputAvailable();
		push(new ArcShape(currentInput, angle));
		return this;
	}

	// Shortcuts for turning left/right
	public Pipeline turnLeft(float angle) {
		return turn(-angle);
	}

	public Pipeline turnLeft(float angle, float length) {
		return turn(-angle, length);
	}

	public Pipeline turnRight(float angle) {
		return turn(angle);
	}

	public Pipeline turnRight(float angle, float length) {
		return turn(angle, length);
	}

	/**
	 * Appends a shape splitting the pipe into 2 outputs
	 * 
	 * @param ratio
	 * @return
	 */
	public Pipeline split(float width, float ratio) {
		ensureInputAvailable();
		push(new SplitShape(currentInput, width, ratio,
				currentInput.getLength() / 2));
		return this;
	}

	public Pipeline joinAfter(Pipeline pipe, float width) {
		ensureInputAvailable();
		Segment output1 = ((IOutputShape) pipe.getCurrentPart()).getOutput();
		JoinShape joint = new JoinShape(output1, currentInput, width);
		push(joint);
		return this;
	}

	public Pipeline joinBefore(Pipeline pipe,  float width) {
		ensureInputAvailable();
		Segment output1 = ((IOutputShape) pipe.getCurrentPart()).getOutput();
		JoinShape joint = new JoinShape(currentInput, output1, width);
		push(joint);
		return this;
	}

	public Pipeline joinAfter(Pipeline pipe, String tag, float width) {
		ensureInputAvailable();
		Segment output1 = ((IOutputShape) pipe.getPartByTag(tag)).getOutput();
		JoinShape joint = new JoinShape(output1, currentInput, width);
		push(joint);
		return this;
	}

	public Pipeline joinBefore(Pipeline pipe, String tag, float width) {
		ensureInputAvailable();
		Segment output1 = ((IOutputShape) pipe.getPartByTag(tag)).getOutput();
		JoinShape joint = new JoinShape(currentInput, output1, width);
		push(joint);
		return this;
	}

	public Pipeline joinAfter(String tag, float width) {
		ensureInputAvailable();
		Segment output1 = ((IOutputShape) getPartByTag(tag)).getOutput();
		JoinShape joint = new JoinShape(output1, currentInput, width);
		push(joint);
		return this;
	}

	public Pipeline joinBefore(String tag, float width) {
		ensureInputAvailable();
		Segment output1 = ((IOutputShape) shapesByTag.get(tag)).getOutput();
		JoinShape joint = new JoinShape(currentInput, output1, width);
		push(joint);
		return this;
	}

	/**
	 * Selects an output when the latest shape has multiple outputs
	 * 
	 * @param n
	 * @return
	 */
	public Pipeline select(int n) {
		if (!(currentShape instanceof IMultipleOutputShape))
			throw new IllegalStateException("Cannot select on last shape '"
					+ currentShape.getClass().getSimpleName() + "'");
		try {
			IOutputShape[] parts = ((IMultipleOutputShape) currentShape)
					.getShapes();
			if (parts != null) {
				currentShape = parts[n];
				currentInput = parts[n].getOutput();
			} else {
				currentInput = ((IMultipleOutputShape) currentShape)
						.getOutputs()[n];
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalStateException("Cannot select on last shape '"
					+ currentShape.getClass().getSimpleName() + "' : '" + n
					+ "' is an incorrect index for outputs");
		}
		return this;
	}

	private IPipelinePart getPartByTag(String tag) {
		return shapesByTag.get(tag);
	}

	private IPipelinePart getCurrentPart() {
		return currentShape;
	}

	/**
	 * Saves the last shape into a new tag name. The state of the pipeline may
	 * be latter recalled with back()
	 * 
	 * @param tag
	 * @return
	 */
	public Pipeline tag(String tag) {
		shapesByTag.put(tag, currentShape);
		return this;
	}

	/**
	 * Retrieve the state of the pipeline saved with tag()
	 * 
	 * @param tag
	 * @return
	 */
	public Pipeline back(String tag) {
		currentShape = shapesByTag.get(tag);
		currentInput = currentShape instanceof IOutputShape ? ((IOutputShape) currentShape)
				.getOutput() : null;
		return this;
	}

	// utility methods

	private void ensureInputAvailable() {
		if (currentInput == null)
			throw new IllegalStateException("Cannot add to Pipe : last shape '"
					+ currentShape.getClass().getSimpleName()
					+ "' has no output");
	}

	public void push(IPipelinePart shape) {
		currentShape = shape;
		currentInput = shape instanceof IOutputShape ? ((IOutputShape) shape)
				.getOutput() : null;
		// By default, the first shape is closed
		if (composed.isEmpty())
			shape.setInputClosed(true);
		composed.push(shape);
	}

	// Customization methods

	public Pipeline setBodyGradient(int color1, int color2) {
		currentShape.setBodyGradient(color1, color2);
		return this;
	}

	public Pipeline setBodyColor(int color) {
		currentShape.setBodyColor(color);
		return this;
	}

	/**
	 * Closes the latest shape inserted (does nothing if an arrow is defined
	 * too)
	 * 
	 * @return
	 */
	public Pipeline close() {
		currentShape.setOutputClosed(true);
		return this;
	}

	/**
	 * Reverse the direction of the pipeline
	 * 
	 * @return
	 */
	public Pipeline reverse() {
		currentInput = currentInput.reverse();
		return this;
	}

	/**
	 * Defines an arrow at the end of the latest shape inserted
	 * 
	 * @param arrow
	 *            the arrow shape. Use constants of Arrow class
	 * @return
	 */
	public Pipeline setArrow(Arrow arrow) {
		currentShape.setOutputArrow(arrow);
		return this;
	}

	public IBorderedDrawable getDrawable() {
		return composed;
	}
}
