package examples.shape.image.recreation;

import examples.shape.image.recreation.genetic.AdditiveShapeSpecificationOperator;
import examples.shape.image.recreation.genetic.ShapeImageRecreationCrossoverFunction;
import examples.shape.image.recreation.genetic.ShapeImageRecreationDrawer;
import examples.shape.image.recreation.utils.ShapeSpecificationGenerator;
import image.recreation.ImageRecreation;
import image.recreation.shape.ShapeImageRecreation;
import image.recreation.shape.ShapeSpecification;
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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static image.loss.BufferedImageMSE.calculateLoss;

@Slf4j
public class ImageRecreationRunner {
	public static void main(String[] args) {
		AppProperties properties = new AppProperties();
		createGeneticAlgorithm(properties).run();
	}

	private static GeneticAlgorithm<ImageRecreation<ShapeSpecification>> createGeneticAlgorithm(AppProperties properties) {
		BufferedImage targetImage = properties.getTargetImage();

		return new GeneticAlgorithmBuilder<>(createInitialPopulation(properties))
				.minimize()
				.fitnessFunction(individual -> calculateLoss(targetImage, individual.recreateImage(targetImage.getWidth(), targetImage.getHeight())))
				.selectorFactory(RouletteWheelSelector::new)
				.crossoverFunction(getCrossoverFunction(properties.getShapeConversionFunction()))
				.elitismSize(properties.getElitismSize())
				.geneticOperator(getMutation(properties))
				.terminationCriterion(new LimitedTimeTerminationCriterion<>(properties.getDuration()))
				.generationListeners(List.of(new StatisticsCollector<>(), new ShapeImageRecreationDrawer(targetImage)))
				.build();
	}

	private static List<ImageRecreation<ShapeSpecification>> createInitialPopulation(AppProperties properties) {
		int width = properties.getTargetImage().getWidth();
		int height = properties.getTargetImage().getHeight();
		int initialSize = properties.getInitialShapeSpecificationsSize();
		ShapeConversionFunction<? extends Shape> shapeConversionFunction = properties.getShapeConversionFunction();
		int size = properties.getPopulationSize();

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
