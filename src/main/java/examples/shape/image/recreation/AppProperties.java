package examples.shape.image.recreation;

import config.AppConfig;
import config.ConfigurationLoader;
import config.genetic.algorithm.GeneticAlgorithmConfig;
import config.image.recreation.ImageRecreationConfig;
import config.image.recreation.ShapeSpecificationConfig;
import config.optimization.OptimizationConfig;
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
	private BufferedImage targetImage;
	private int initialShapeSpecificationsSize;
	private int shapeSpecificationDelta;
	private int shapeSpecificationThreshold;
	private ShapeConversionFunction<? extends Shape> shapeConversionFunction;
	private int solutionsSize;
	private Duration duration;
	private double mutationRate;
	private int elitismSize;

	AppProperties() {
		AppConfig config = ConfigurationLoader.load("application.yml");

		initializeImageRecreationProperties(config.getImageRecreation());
		initializeOptimizationProperties(config.getOptimization());
		initializeGeneticAlgorithmProperties(config.getGeneticAlgorithm());
	}

	private void initializeImageRecreationProperties(ImageRecreationConfig irConfig) {
		this.targetImage = ImageUtils.loadImage(irConfig.getTargetImagePath());
		ShapeSpecificationConfig ssConfig = irConfig.getShape().getSpecifications();
		this.initialShapeSpecificationsSize = ssConfig.getInitialSize();
		this.shapeSpecificationDelta = ssConfig.getDelta();
		this.shapeSpecificationThreshold = ssConfig.getThreshold();
		this.shapeConversionFunction = getShapeConversionFunctionOfShapeName(irConfig.getShape().getConversionFunction());
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

	private void initializeOptimizationProperties(OptimizationConfig optimizationConfig) {
		this.solutionsSize = optimizationConfig.getSolutionsSize();
		this.duration = getDurationFromDescription(optimizationConfig.getTermination().getTime());
	}

	private static Duration getDurationFromDescription(String timeLimit) {
		return Duration.parse("PT" + timeLimit);
	}

	private void initializeGeneticAlgorithmProperties(GeneticAlgorithmConfig gaConfig) {
		this.mutationRate = gaConfig.getMutation().getRate();
		this.elitismSize = gaConfig.getElitismSize();
	}
}
