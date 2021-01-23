package config.genetic.algorithm;

import lombok.Data;

@Data
public class GeneticAlgorithmConfig {
	private MutationConfig mutation;
	private int elitismSize;
}
