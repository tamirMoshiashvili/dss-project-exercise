package image.recreation.shape.conversion;

import image.recreation.shape.ShapeSpecification;

import java.awt.geom.Ellipse2D;

public class CircleShapeConversionFunction implements ShapeConversionFunction<Ellipse2D.Float> {
	@Override
	public Ellipse2D.Float convert(ShapeSpecification shapeSpecification, int imageWidth, int imageHeight) {
		float x = (float) shapeSpecification.getLocation().getX();
		float y = (float) shapeSpecification.getLocation().getY();
		float size = Math.min(imageWidth, imageHeight) * shapeSpecification.getScale();

		return new Ellipse2D.Float(x, y, size, size);
	}
}
