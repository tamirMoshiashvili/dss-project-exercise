package image.recreation.shape.conversion;


import image.recreation.shape.ShapeSpecification;

import java.awt.geom.Ellipse2D;

public class EllipseShapeConversionFunction implements ShapeConversionFunction<Ellipse2D.Float> {
	@Override
	public Ellipse2D.Float convert(ShapeSpecification shapeSpecification, int width, int height) {
		height = width == height ? height / 2 : height;
		float x = (float) shapeSpecification.getLocation().getX();
		float y = (float) shapeSpecification.getLocation().getY();

		return new Ellipse2D.Float(x, y, width * shapeSpecification.getScale(), height * shapeSpecification.getScale());
	}
}
