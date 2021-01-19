package image.recreation.shape.conversion;

import image.recreation.shape.ShapeSpecification;

import java.awt.*;
import java.awt.geom.Point2D;

public class TriangleShapeConversionFunction implements ShapeConversionFunction<Polygon> {
	@Override
	public Polygon convert(ShapeSpecification shapeSpecification, int width, int height) {
		Point2D.Float location = shapeSpecification.getLocation();
		float scale = shapeSpecification.getScale();

		Polygon triangle = new Polygon();
		triangle.addPoint((int) location.getX(), (int) location.getY());
		triangle.addPoint((int) (location.getX() * scale), (int) (location.getY() * scale));
		triangle.addPoint((int) ((width - location.getX()) * scale), (int) ((height - location.getY()) * scale));
		return triangle;
	}
}
