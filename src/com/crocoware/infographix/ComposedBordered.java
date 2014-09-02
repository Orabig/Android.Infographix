package com.crocoware.infographix;

import android.graphics.Path;

/**
 * Builds a bordered shape from many others
 * @author Benoit
 *
 */
public class ComposedBordered extends AbstractBorderedDrawable {
	
	private AbstractBorderedDrawable[] parts;
	
	public ComposedBordered(AbstractBorderedDrawable... parts){
		this.parts=parts;
	}

	@Override
	protected Path getEdgePath() {
		Path path = new Path();
		for (AbstractBorderedDrawable part : parts) {
			path.addPath(part.getEdgePath());
		}
		return path;
	}

	@Override
	protected Path getBodyPath() {
		Path path = new Path();
		for (AbstractBorderedDrawable part : parts) {
			path.addPath(part.getBodyPath());
		}
		return path;
	}

}
