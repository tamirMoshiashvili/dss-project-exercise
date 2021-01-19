package examples.shape.image.recreation;

import examples.shape.image.recreation.genetic.AdditiveShapeSpecificationOperator;
import examples.shape.image.recreation.genetic.ShapeImageRecreationCrossoverFunction;
import examples.shape.image.recreation.genetic.ShapeImageRecreationDrawer;
import examples.shape.image.recreation.utils.ShapeSpecificationGenerator;
import image.recreation.ImageRecreation;
import image.recreation.shape.ShapeImageRecreation;
import image.recreation.shape.ShapeSpecification;
import image.recreation.shape.conversion.EllipseShapeConversionFunction;
import image.recreation.shape.conversion.ShapeConversionFunction;
import lombok.extern.slf4j.Slf4j;
import optimization.genetic.CrossoverFunction;
import optimization.genetic.GeneticAlgorithm;
import optimization.genetic.GeneticAlgorithmBuilder;
import optimization.genetic.listener.StatisticsCollector;
import optimization.genetic.operator.GeneticOperator;
import optimization.genetic.operator.mutation.FixedRateMutation;
import optimization.genetic.selection.roulette.wheel.RouletteWheelSelector;
import termination.LimitedTimeTerminationCriterion;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static image.loss.BufferedImageMSE.calculateLoss;

@Slf4j
public class ImageRecreationRunner {
	public static void main(String[] args) {
		BufferedImage targetImage = ImageUtils.loadImage("images\\mona-lisa-1.png");
		createGeneticAlgorithm(targetImage).run();
	}

	private static GeneticAlgorithm<ImageRecreation<ShapeSpecification>> createGeneticAlgorithm(BufferedImage targetImage) {
		int populationSize = 32;
		int width = targetImage.getWidth();
		int height = targetImage.getHeight();
		int initialSize = 1;
		int elitismSize = 4;
		int delta = 1;
		int threshold = 100;
		double mutationRate = 0.5;
		ShapeConversionFunction<? extends Shape> shapeConversionFunction = new EllipseShapeConversionFunction();

		return new GeneticAlgorithmBuilder<>(createInitialPopulation(populationSize, width, height, initialSize, shapeConversionFunction))
				.minimize()
				.fitnessFunction(individual -> calculateLoss(targetImage, individual.recreateImage(width, height)))
				.selectorFactory(RouletteWheelSelector::new)
				.crossoverFunction(getCrossoverFunction(shapeConversionFunction))
				.elitismSize(elitismSize)
				.geneticOperator(getMutation(width, height, delta, threshold, mutationRate, shapeConversionFunction))
				.terminationCriterion(new LimitedTimeTerminationCriterion<>(Duration.ofMinutes(5)))
				.generationListeners(List.of(new StatisticsCollector<>(), new ShapeImageRecreationDrawer(targetImage)))
				.build();
	}

	private static List<ImageRecreation<ShapeSpecification>> createInitialPopulation(
			int size, int width, int height, int initialSize, ShapeConversionFunction<? extends Shape> shapeConversionFunction) {
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

	private static FixedRateMutation<ImageRecreation<ShapeSpecification>> getMutation(
			int width, int height, int delta, int threshold, double mutationRate, ShapeConversionFunction<? extends Shape> shapeConversionFunction) {
		GeneticOperator<ImageRecreation<ShapeSpecification>> geneticOperator =
				new AdditiveShapeSpecificationOperator<>(width, height, threshold, delta, shapeConversionFunction);
		return new FixedRateMutation<>(mutationRate, geneticOperator);
	}
}
