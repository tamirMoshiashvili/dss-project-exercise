package config.genetic.algorithm;

import lombok.Data;

@Data
public class GeneticAlgorithmConfig {
	private int populationSize;
	private MutationConfig mutation;
	private int elitismSize;
	private TerminationConfig termination;
}
