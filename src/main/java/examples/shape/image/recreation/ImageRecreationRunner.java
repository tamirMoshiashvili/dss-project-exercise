package examples.shape.image.recreation;

import examples.shape.image.recreation.genetic.AdditiveShapeSpecificationOperator;
import examples.shape.image.recreation.genetic.ShapeImageRecreationCrossoverFunction;
import examples.shape.image.recreation.optimization.ShapeImageRecreationDrawer;
import examples.shape.image.recreation.utils.ShapeSpecificationGenerator;
import image.recreation.ImageRecreation;
import image.recreation.shape.ShapeImageRecreation;
import image.recreation.shape.ShapeSpecification;
import image.recreation.shape.conversion.ShapeConversionFunction;
import lombok.extern.slf4j.Slf4j;
import optimization.OptimizationAlgorithm;
import optimization.Optimizer;
import optimization.OptimizerBuilder;
import optimization.algorithm.genetic.CrossoverFunction;
import optimization.algorithm.genetic.GeneticAlgorithmBuilder;
import optimization.algorithm.genetic.operator.GeneticOperator;
import optimization.algorithm.genetic.operator.mutation.FixedRateMutation;
import optimization.algorithm.genetic.selection.roulette.wheel.RouletteWheelSelector;
import optimization.step.listener.StatisticsLogger;
import termination.LimitedTimeTerminationCriterion;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static image.loss.BufferedImageMSE.calculateLoss;

@Slf4j
class ImageRecreationRunner {
	public static void main(String[] args) {
		AppProperties properties = new AppProperties();
		createOptimizer(properties).optimize();
	}

	private static Optimizer<ImageRecreation<ShapeSpecification>> createOptimizer(AppProperties properties) {
		BufferedImage targetImage = properties.getTargetImage();

		return new OptimizerBuilder<>(createInitialSolutions(properties))
				.minimize()
				.evaluationFunction(solution -> calculateLoss(targetImage, solution.recreateImage(targetImage.getWidth(), targetImage.getHeight())))
				.listeners(List.of(new StatisticsLogger<>(), new ShapeImageRecreationDrawer(targetImage)))
				.terminationCriterion(new LimitedTimeTerminationCriterion<>(properties.getDuration()))
				.optimizationAlgorithm(createOptimizationAlgorithm(properties))
				.build();
	}

	private static OptimizationAlgorithm<ImageRecreation<ShapeSpecification>> createOptimizationAlgorithm(AppProperties properties) {
		return new GeneticAlgorithmBuilder<ImageRecreation<ShapeSpecification>>()
				.selectorFactory(RouletteWheelSelector::new)
				.crossoverFunction(getCrossoverFunction(properties.getShapeConversionFunction()))
				.geneticOperator(getMutation(properties))
				.elitismSize(properties.getElitismSize())
				.build();
	}

	private static List<ImageRecreation<ShapeSpecification>> createInitialSolutions(AppProperties properties) {
		int width = properties.getTargetImage().getWidth();
		int height = properties.getTargetImage().getHeight();
		int initialSize = properties.getInitialShapeSpecificationsSize();
		ShapeConversionFunction<? extends Shape> shapeConversionFunction = properties.getShapeConversionFunction();
		int size = properties.getSolutionsSize();

		return Stream.generate(() ->
				new ShapeImageRecreation<>(ShapeSpecificationGenerator.createRandomShapeSpecifications(width, height, initialSize), shapeConversionFunction))
				.limit(size)
				.collect(Collectors.toList());
	}

	private static CrossoverFunction<ImageRecreation<ShapeSpecification>> getCrossoverFunction(
			ShapeConversionFunction<? extends Shape> shapeConversionFunction) {
		return new ShapeImageRecreationCrossoverFunction(shapeSpecifications ->
				new ShapeImageRecreation<>(shapeSpecifications, shapeConversionFunction));
	}

	private static FixedRateMutation<ImageRecreation<ShapeSpecification>> getMutation(AppProperties properties) {
		GeneticOperator<ImageRecreation<ShapeSpecification>> geneticOperator =
				new AdditiveShapeSpecificationOperator<>(properties.getTargetImage().getWidth(),
						properties.getTargetImage().getHeight(),
						properties.getShapeSpecificationThreshold(),
						properties.getShapeSpecificationDelta(),
						properties.getShapeConversionFunction());
		return new FixedRateMutation<>(properties.getMutationRate(), geneticOperator);
	}
}
