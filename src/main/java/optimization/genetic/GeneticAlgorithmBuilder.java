package optimization.genetic;

import optimization.genetic.listener.GenerationListener;
import optimization.genetic.operator.GeneticOperator;
import optimization.genetic.selection.SelectorFactory;
import termination.TerminationCriterion;

import java.util.List;

public class GeneticAlgorithmBuilder<T> {
	private List<T> population;
	private boolean isMinimization;
	private FitnessFunction<T> fitnessFunction;
	private SelectorFactory<T> selectorFactory;
	private CrossoverFunction<T> crossoverFunction;
	private int elitismSize;
	private GeneticOperator<T> geneticOperator;
	private TerminationCriterion<GeneticAlgorithm<T>> terminationCriterion;
	private List<GenerationListener<T>> generationListeners;

	public GeneticAlgorithmBuilder(List<T> population) {
		PopulationValidator.validate(population);
		this.population = population;
	}

	public GeneticAlgorithmBuilder<T> minimize() {
		this.isMinimization = true;
		return this;
	}

	public GeneticAlgorithmBuilder<T> maximize() {
		this.isMinimization = false;
		return this;
	}

	public GeneticAlgorithmBuilder<T> fitnessFunction(FitnessFunction<T> fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
		return this;
	}

	public GeneticAlgorithmBuilder<T> selectorFactory(SelectorFactory<T> selectorFactory) {
		this.selectorFactory = selectorFactory;
		return this;
	}

	public GeneticAlgorithmBuilder<T> crossoverFunction(CrossoverFunction<T> crossoverFunction) {
		this.crossoverFunction = crossoverFunction;
		return this;
	}

	public GeneticAlgorithmBuilder<T> elitismSize(int elitismSize) {
		this.elitismSize = elitismSize;
		return this;
	}

	public GeneticAlgorithmBuilder<T> geneticOperator(GeneticOperator<T> geneticOperator) {
		this.geneticOperator = geneticOperator;
		return this;
	}

	public GeneticAlgorithmBuilder<T> terminationCriterion(TerminationCriterion<GeneticAlgorithm<T>> terminationCriterion) {
		this.terminationCriterion = terminationCriterion;
		return this;
	}

	public GeneticAlgorithmBuilder<T> generationListeners(List<GenerationListener<T>> generationListeners) {
		this.generationListeners = generationListeners;
		return this;
	}

	public GeneticAlgorithm<T> build() {
		return new SimpleGeneticAlgorithm<>(population,
				isMinimization, fitnessFunction, selectorFactory,
				crossoverFunction, elitismSize, geneticOperator, terminationCriterion,
				generationListeners);
	}
}
