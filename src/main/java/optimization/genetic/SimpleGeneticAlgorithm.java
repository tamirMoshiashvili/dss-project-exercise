package optimization.genetic;

import optimization.genetic.listener.GenerationListener;
import optimization.genetic.operator.GeneticOperator;
import optimization.genetic.selection.SelectorFactory;
import termination.TerminationCriterion;

import java.util.Collection;
import java.util.List;

public class SimpleGeneticAlgorithm<T> implements GeneticAlgorithm<T> {
	private Collection<T> population;
	private int generation;
	private boolean isMinimization;
	private FitnessFunction<T> fitnessFunction;
	private SelectorFactory<T> selectorFactory;
	private CrossoverFunction<T> crossoverFunction;
	private int elitismSize;
	private GeneticOperator<T> geneticOperator;
	private TerminationCriterion<GeneticAlgorithm<T>> terminationCriterion;
	private List<GenerationListener<T>> generationListeners;

	SimpleGeneticAlgorithm(Collection<T> population,
						   boolean isMinimization, FitnessFunction<T> fitnessFunction, SelectorFactory<T> selectorFactory,
						   CrossoverFunction<T> crossoverFunction, int elitismSize, GeneticOperator<T> geneticOperator,
						   TerminationCriterion<GeneticAlgorithm<T>> terminationCriterion,
						   List<GenerationListener<T>> generationListeners) {
		this.population = population;
		this.generation = 0;
		this.isMinimization = isMinimization;
		this.fitnessFunction = fitnessFunction;
		this.selectorFactory = selectorFactory;
		this.crossoverFunction = crossoverFunction;
		this.elitismSize = elitismSize;
		this.geneticOperator = geneticOperator;
		this.terminationCriterion = terminationCriterion;
		this.generationListeners = generationListeners;
	}

	@Override
	public Collection<T> getPopulation() {
		return population;
	}

	@Override
	public void setPopulation(Collection<T> population) {
		this.population = population;
	}

	@Override
	public FitnessFunction<T> getFitnessFunction() {
		return fitnessFunction;
	}

	@Override
	public boolean isMinimization() {
		return isMinimization;
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
	public TerminationCriterion<GeneticAlgorithm<T>> getTerminationCriterion() {
		return terminationCriterion;
	}

	@Override
	public int getGeneration() {
		return generation;
	}

	@Override
	public void incrementGeneration() {
		generation++;
	}

	@Override
	public int getElitismSize() {
		return elitismSize;
	}

	@Override
	public List<GenerationListener<T>> getGenerationListeners() {
		return generationListeners;
	}
}
