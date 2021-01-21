package optimization.algorithm.genetic;

import optimization.algorithm.genetic.operator.GeneticOperator;
import optimization.algorithm.genetic.selection.SelectorFactory;

public class SimpleGeneticAlgorithm<T> implements GeneticAlgorithm<T> {
	private SelectorFactory<T> selectorFactory;
	private CrossoverFunction<T> crossoverFunction;
	private GeneticOperator<T> geneticOperator;
	private int elitismSize;

	SimpleGeneticAlgorithm(SelectorFactory<T> selectorFactory, CrossoverFunction<T> crossoverFunction,
						   GeneticOperator<T> geneticOperator, int elitismSize) {
		this.selectorFactory = selectorFactory;
		this.crossoverFunction = crossoverFunction;
		this.geneticOperator = geneticOperator;
		this.elitismSize = elitismSize;
	}

	@Override
	public SelectorFactory<T> getSelectorFactory() {
		return selectorFactory;
	}

	@Override
	public CrossoverFunction<T> getCrossoverFunction() {
		return crossoverFunction;
	}

	@Override
	public GeneticOperator<T> getGeneticOperator() {
		return geneticOperator;
	}

	@Override
	public int getElitismSize() {
		return elitismSize;
	}
}
