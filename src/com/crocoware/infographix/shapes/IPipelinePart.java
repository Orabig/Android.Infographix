package com.crocoware.infographix.shapes;

import android.graphics.PathEffect;

import com.crocoware.infographix.IBorderedDrawable;

public interface IPipelinePart extends IBorderedDrawable {

	public void setBodyAlpha(int alpha);

	public void setBodyColor(int color);

	public void setBodyGradient(int color1, int color2);

	public void setEdgeWidth(float width);

	public void setEdgeARGB(int a, int r, int g, int b);

	public void setEdgeAlpha(int alpha);

	public void setEdgeColor(int color);

	public void setEdgePathEffect(PathEffect effect);

}
