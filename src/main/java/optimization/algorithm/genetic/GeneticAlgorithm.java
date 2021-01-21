package optimization.algorithm.genetic;

import optimization.OptimizationAlgorithm;
import optimization.evaluation.OptimizationStepEvaluation;
import optimization.algorithm.genetic.operator.GeneticOperator;
import optimization.algorithm.genetic.selection.Selector;
import optimization.algorithm.genetic.selection.SelectorFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface GeneticAlgorithm<T> extends OptimizationAlgorithm<T> {
	SelectorFactory<T> getSelectorFactory();

	CrossoverFunction<T> getCrossoverFunction();

	GeneticOperator<T> getGeneticOperator();

	int getElitismSize();

	@Override
	default Collection<T> optimize(OptimizationStepEvaluation<T> stepEvaluation) {
		int populationSize = stepEvaluation.getEvaluations().size();
		List<T> elitism = stepEvaluation.getBestSolutions(getElitismSize());
		Selector<T> selector = getSelectorFactory().create(stepEvaluation.getEvaluations(), stepEvaluation.isMinimization());

		return createNewPopulation(populationSize, elitism, selector);
	}

	private List<T> createNewPopulation(int populationSize, List<T> elitism, Selector<T> selector) {
		return Stream.of(elitism, createNewOffsprings(populationSize, selector))
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	private List<T> createNewOffsprings(int populationSize, Selector<T> selector) {
		return Stream.generate(() -> getCrossoverFunction().createOffspringBy(selector))
				.limit(populationSize - getElitismSize())
				.map(getGeneticOperator()::apply)
				.collect(Collectors.toList());
	}
}
