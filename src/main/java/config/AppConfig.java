package config;

import config.genetic.algorithm.GeneticAlgorithmConfig;
import config.image.recreation.ImageRecreationConfig;
import lombok.Data;

@Data
public class AppConfig {
	private ImageRecreationConfig imageRecreation;
	private GeneticAlgorithmConfig geneticAlgorithm;
}
