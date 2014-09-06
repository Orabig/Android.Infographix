package com.crocoware.infographix.shapes;

import com.crocoware.infographix.Arrow;
import com.crocoware.infographix.ComposedBordered;
import com.crocoware.infographix.IBorderedDrawable;

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

	// Customizatin methods

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
	 * Defines an arrow at the end of the latest shape inserted
	 * 
	 * @param arrow the arrow shape. Use constants of Arrow class
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
