package examples.bit.vector;

import optimization.genetic.GeneticAlgorithm;
import optimization.genetic.GeneticAlgorithmBuilder;
import optimization.genetic.listener.StatisticsCollector;
import optimization.genetic.operator.mutation.FixedRateMutation;
import optimization.genetic.selection.Selector;
import optimization.genetic.selection.roulette.wheel.RouletteWheelSelector;
import termination.LimitedEpochsTerminationCriterion;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BitVectorOptimization {
	public static void main(String[] args) {
		Selector<String> result = new GeneticAlgorithmBuilder<>(List.of("000", "001", "100", "110", "011"))
				.maximize()
				.fitnessFunction(str -> (float) str.chars().filter(i -> i == '1').count())
				.selectorFactory(RouletteWheelSelector::new)
				.crossoverFunction(selector -> selector.select().charAt(0) + selector.select().substring(1))
				.elitismSize(1)
				.geneticOperator(createStringMutation())
				.terminationCriterion(new LimitedEpochsTerminationCriterion<>(5, GeneticAlgorithm::getGeneration))
				.generationListeners(Collections.singletonList(new StatisticsCollector<>()))
				.build().run();

		result.getElitism(3).forEach(System.out::println);
	}

	private static FixedRateMutation<String> createStringMutation() {
		Random random = new Random();

		return new FixedRateMutation<>(0.4,
				s -> {
					int randomIndex = random.nextInt(s.length());
					char flippedChar = s.charAt(randomIndex) == '0' ? '1' : '0';
					return s.substring(0, randomIndex) + flippedChar + s.substring(randomIndex + 1);
				});
	}
}
