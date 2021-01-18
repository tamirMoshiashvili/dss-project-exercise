package optimization.genetic;

import optimization.genetic.operator.GeneticOperator;
import optimization.genetic.selection.SelectorFactory;
import termination.TerminationCriterion;

import java.util.Collection;

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
}
