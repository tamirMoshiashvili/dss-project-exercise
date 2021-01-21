package optimization.algorithm.genetic;

import optimization.algorithm.genetic.operator.GeneticOperator;
import optimization.algorithm.genetic.selection.SelectorFactory;

public class GeneticAlgorithmBuilder<T> {
	private SelectorFactory<T> selectorFactory;
	private CrossoverFunction<T> crossoverFunction;
	private GeneticOperator<T> geneticOperator;
	private int elitismSize;

	public GeneticAlgorithmBuilder<T> selectorFactory(SelectorFactory<T> selectorFactory) {
		this.selectorFactory = selectorFactory;
		return this;
	}

	public GeneticAlgorithmBuilder<T> crossoverFunction(CrossoverFunction<T> crossoverFunction) {
		this.crossoverFunction = crossoverFunction;
		return this;
	}

	public GeneticAlgorithmBuilder<T> geneticOperator(GeneticOperator<T> geneticOperator) {
		this.geneticOperator = geneticOperator;
		return this;
	}

	public GeneticAlgorithmBuilder<T> elitismSize(int elitismSize) {
		this.elitismSize = elitismSize;
		return this;
	}

	public GeneticAlgorithm<T> build() {
		return new SimpleGeneticAlgorithm<>(selectorFactory, crossoverFunction, geneticOperator, elitismSize);
	}
}
