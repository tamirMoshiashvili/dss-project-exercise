package image.recreation.shape;

import image.recreation.DrawingFunction;
import image.recreation.ImageInitializationFunction;
import image.recreation.ImageRecreation;
import image.recreation.shape.conversion.ShapeConversionFunction;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class ShapeImageRecreation<T extends Shape> implements ImageRecreation<ShapeSpecification> {
	private List<ShapeSpecification> shapeSpecifications;
	private ShapeConversionFunction<T> shapeConversionFunction;

	public ShapeImageRecreation(List<ShapeSpecification> shapeSpecifications,
								ShapeConversionFunction<T> shapeConversionFunction) {
		this.shapeSpecifications = shapeSpecifications;
		this.shapeConversionFunction = shapeConversionFunction;
	}

	@Override
	public ImageInitializationFunction getImageInitializationFunction() {
		return (width, height) -> new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
	}

	@Override
	public List<ShapeSpecification> getItems() {
		return shapeSpecifications;
	}

	@Override
	public DrawingFunction<ShapeSpecification> getDrawingFunction() {
		return (shapeSpecification, image) -> {
			Graphics2D graphics = (Graphics2D) image.getGraphics();

			graphics.setColor(shapeSpecification.getColor());
			graphics.setTransform(createAffineTransform(shapeSpecification));
			graphics.fill(shapeConversionFunction.convert(shapeSpecification, image.getWidth(), image.getHeight()));
		};
	}

	private AffineTransform createAffineTransform(ShapeSpecification shapeSpecification) {
		Point2D.Float location = shapeSpecification.getLocation();
		return AffineTransform.getRotateInstance(shapeSpecification.getOrientation(), location.getX(), location.getY());
	}
}
