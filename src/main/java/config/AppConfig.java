package config;

import config.genetic.algorithm.GeneticAlgorithmConfig;
import config.image.recreation.ImageRecreationConfig;
import config.optimization.OptimizationConfig;
import lombok.Data;

@Data
public class AppConfig {
	private ImageRecreationConfig imageRecreation;
	private OptimizationConfig optimization;
	private GeneticAlgorithmConfig geneticAlgorithm;
}
