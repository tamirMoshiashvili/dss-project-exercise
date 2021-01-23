package examples.bit.vector;

import optimization.OptimizationAlgorithm;
import optimization.OptimizerBuilder;
import optimization.algorithm.genetic.GeneticAlgorithmBuilder;
import optimization.algorithm.genetic.operator.mutation.FixedRateMutation;
import optimization.algorithm.genetic.selection.roulette.wheel.RouletteWheelSelector;
import optimization.evaluation.OptimizationStepEvaluation;
import optimization.step.listener.StatisticsLogger;
import termination.LimitedEpochsTerminationCriterion;

import java.util.Collections;
import java.util.List;
import java.util.Random;

class BitVectorOptimization {
	public static void main(String[] args) {
		OptimizationStepEvaluation<String> result = new OptimizerBuilder<>(List.of("000", "001", "100", "110", "011"))
				.maximize()
				.evaluationFunction(str -> (float) str.chars().filter(i -> i == '1').count())
				.listeners(Collections.singletonList(new StatisticsLogger<>()))
				.terminationCriterion(new LimitedEpochsTerminationCriterion<>(5))
				.optimizationAlgorithm(createOptimizationAlgorithm())
				.build().optimize();

		result.getBestSolutions(3).forEach(System.out::println);
	}

	private static OptimizationAlgorithm<String> createOptimizationAlgorithm() {
		return new GeneticAlgorithmBuilder<String>()
				.selectorFactory(RouletteWheelSelector::new)
				.crossoverFunction(selector -> selector.select().charAt(0) + selector.select().substring(1))
				.geneticOperator(createStringMutation())
				.elitismSize(1)
				.build();
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
