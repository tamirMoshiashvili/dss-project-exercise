package examples.shape.image.recreation;

import config.ConfigurationLoader;
import image.recreation.shape.conversion.CircleShapeConversionFunction;
import image.recreation.shape.conversion.EllipseShapeConversionFunction;
import image.recreation.shape.conversion.ShapeConversionFunction;
import image.recreation.shape.conversion.TriangleShapeConversionFunction;
import lombok.Data;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Properties;

@Data
class AppProperties {
	private int populationSize;
	private BufferedImage targetImage;
	private int initialShapeSpecificationsSize;
	private int elitismSize;
	private int shapeSpecificationDelta;
	private int shapeSpecificationThreshold;
	private double mutationRate;
	private ShapeConversionFunction<? extends Shape> shapeConversionFunction;

	AppProperties() {
		Properties properties = ConfigurationLoader.load("shape-image-recreation.properties");

		this.populationSize = Integer.parseInt(properties.getProperty("genetic-algorithm.population-size"));
		this.targetImage = ImageUtils.loadImage(properties.getProperty("image-recreation.target-image-path"));
		this.elitismSize = Integer.parseInt(properties.getProperty("genetic-algorithm.elitism-size"));
		this.mutationRate = Double.parseDouble(properties.getProperty("genetic-algorithm.mutation.rate"));
		this.initialShapeSpecificationsSize = Integer.parseInt(properties.getProperty("image-recreation.shape.specifications.initial-size"));
		this.shapeSpecificationDelta = Integer.parseInt(properties.getProperty("image-recreation.shape.specifications.delta"));
		this.shapeSpecificationThreshold = Integer.parseInt(properties.getProperty("image-recreation.shape.specifications.threshold"));
		this.shapeConversionFunction = getShapeConversionFunctionOfShapeName(properties.getProperty("image-recreation.shape.conversion-function"));
	}

	private static ShapeConversionFunction<? extends Shape> getShapeConversionFunctionOfShapeName(String shape) {
		shape = shape.toLowerCase();
		switch (shape) {
			case "ellipse":
				return new EllipseShapeConversionFunction();
			case "circle":
				return new CircleShapeConversionFunction();
			case "triangle":
				return new TriangleShapeConversionFunction();
		}
		throw new IllegalArgumentException("Given shape has no shape-conversion-function: " + shape);
	}
}
