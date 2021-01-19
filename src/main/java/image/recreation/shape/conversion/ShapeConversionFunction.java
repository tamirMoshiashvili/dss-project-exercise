package image.recreation.shape.conversion;

import image.recreation.shape.ShapeSpecification;

import java.awt.*;

@FunctionalInterface
public interface ShapeConversionFunction<T extends Shape> {
	T convert(ShapeSpecification shapeSpecification, int width, int height);
}
