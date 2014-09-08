Android.Infographix
===================

Infographix library for Android.
This library is build on top of Android [Canvas Framework](http://developer.android.com/guide/topics/graphics/2d-graphics.html) and allows very easy generation of diagrams and "infographic-like" charts, like this one :

![Full Sample output](https://raw.githubusercontent.com/Orabig/Android.Infographix.Test/master/doc/sample1.png)

Example
-------

The test project may be downloaded on [Google Play](https://play.google.com/store?hl=fr). It shows several samples made by the library. The source code is on [GitHub](https://github.com/Orabig/Android.Infographix.Test).

For example, the following piece of code :

		// Give me a T
		Segment startT = new Segment(40, 40, 40, 70);
		float len1 = 20 + 40 * cycle;
		float len2 = 80 - len1;
		letterT1 = new Pipeline(startT).forward(len1).setArrow(Arrow.SIMPLE)
				.setBodyColor(Color.LTGRAY).forward(len2)
				.setBodyColor(Color.MAGENTA).turnRight(180, 0)
				.setBodyGradient(Color.MAGENTA, Color.WHITE).forward(25, 15)
				.turnLeft(80 + 20 * cycle, 0).forward(60, 20).close()
				.getDrawable();

		letterT1.draw(canvas);


will produce this output :

![Letter T](https://raw.githubusercontent.com/Orabig/Android.Infographix.Test/master/doc/letterT.png)
