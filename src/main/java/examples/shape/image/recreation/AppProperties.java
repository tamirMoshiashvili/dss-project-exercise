package examples.shape.image.recreation;

import config.AppConfig;
import config.ConfigurationLoader;
import config.genetic.algorithm.GeneticAlgorithmConfig;
import config.image.recreation.ImageRecreationConfig;
import config.image.recreation.ShapeSpecificationConfig;
import image.recreation.shape.conversion.CircleShapeConversionFunction;
import image.recreation.shape.conversion.EllipseShapeConversionFunction;
import image.recreation.shape.conversion.ShapeConversionFunction;
import image.recreation.shape.conversion.TriangleShapeConversionFunction;
import lombok.Data;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.Duration;

@Data
class AppProperties {
	private int populationSize;
	private BufferedImage targetImage;
	private int elitismSize;
	private double mutationRate;
	private Duration duration;
	private int initialShapeSpecificationsSize;
	private int shapeSpecificationDelta;
	private int shapeSpecificationThreshold;
	private ShapeConversionFunction<? extends Shape> shapeConversionFunction;

	AppProperties() {
		AppConfig config = ConfigurationLoader.load("application.yml");

		initializeImageRecreationProperties(config.getImageRecreation());
		initializeGeneticAlgorithmProperties(config.getGeneticAlgorithm());
	}

	private void initializeGeneticAlgorithmProperties(GeneticAlgorithmConfig gaConfig) {
		this.populationSize = gaConfig.getPopulationSize();
		this.elitismSize = gaConfig.getElitismSize();
		this.mutationRate = gaConfig.getMutation().getRate();
		this.duration = getDurationFromDescription(gaConfig.getTermination().getTime());
	}

	private void initializeImageRecreationProperties(ImageRecreationConfig irConfig) {
		this.targetImage = ImageUtils.loadImage(irConfig.getTargetImagePath());
		ShapeSpecificationConfig ssConfig = irConfig.getShape().getSpecifications();
		this.initialShapeSpecificationsSize = ssConfig.getInitialSize();
		this.shapeSpecificationDelta = ssConfig.getDelta();
		this.shapeSpecificationThreshold = ssConfig.getThreshold();
		this.shapeConversionFunction = getShapeConversionFunctionOfShapeName(irConfig.getShape().getConversionFunction());
	}

	private static Duration getDurationFromDescription(String timeLimit) {
		return Duration.parse("PT" + timeLimit);
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
