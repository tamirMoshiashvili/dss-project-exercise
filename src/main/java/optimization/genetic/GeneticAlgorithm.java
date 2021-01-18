package optimization.genetic;

import optimization.genetic.operator.GeneticOperator;
import optimization.genetic.selection.Selector;
import optimization.genetic.selection.SelectorFactory;
import termination.TerminationCriterion;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface GeneticAlgorithm<T> {
	Collection<T> getPopulation();

	void setPopulation(Collection<T> population);

	FitnessFunction<T> getFitnessFunction();

	boolean isMinimization();

	SelectorFactory<T> getSelectorFactory();

	CrossoverFunction<T> getCrossoverFunction();

	GeneticOperator<T> getGeneticOperator();

	TerminationCriterion<GeneticAlgorithm<T>> getTerminationCriterion();

	int getGeneration();

	void incrementGeneration();

	int getElitismSize();

	default Selector<T> run() {
		Selector<T> selector;

		do {
			Map<T, Float> fitnessScores = calculateFitnessScores();
			selector = getSelectorFactory().create(fitnessScores, isMinimization());
			setPopulation(createNewPopulation(selector));
			incrementGeneration();
		} while (!getTerminationCriterion().isDone(this));

		return selector;
	}

	private Map<T, Float> calculateFitnessScores() {
		return getPopulation().parallelStream().distinct()
				.collect(Collectors.toMap(Function.identity(), getFitnessFunction()::evaluate));
	}

	private List<T> createNewPopulation(Selector<T> selector) {
		return Stream.of(selector.getElitism(getElitismSize()), createNewOffsprings(selector))
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	private List<T> createNewOffsprings(Selector<T> selector) {
		return Stream.generate(() -> getCrossoverFunction().createOffspringBy(selector))
				.limit(getPopulation().size() - getElitismSize())
				.map(getGeneticOperator()::apply)
				.collect(Collectors.toList());
	}
}
