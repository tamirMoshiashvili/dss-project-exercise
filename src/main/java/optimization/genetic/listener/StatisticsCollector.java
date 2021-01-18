package optimization.genetic.listener;

import lombok.extern.slf4j.Slf4j;
import optimization.genetic.GeneticAlgorithm;
import optimization.genetic.selection.Selector;

import java.time.Duration;
import java.time.Instant;

@Slf4j
public class StatisticsCollector<T> implements GenerationListener<T> {
	private Instant startTime;

	@Override
	public void notifyGenerationStarted() {
		startTime = Instant.now();
	}

	@Override
	public void notifyGenerationEnded(GeneticAlgorithm<T> geneticAlgorithm, Selector<T> selector) {
		T fittest = selector.getFittest();
		logGeneration(geneticAlgorithm, selector, fittest);
	}

	private void logGeneration(GeneticAlgorithm<T> geneticAlgorithm, Selector<T> selector, T fittest) {
		int generation = geneticAlgorithm.getGeneration();
		Float bestFitness = selector.getFitnessScores().get(fittest);
		double avgFitness = selector.getFitnessScores().values().stream().mapToDouble(Float::doubleValue).sum()
				/ selector.getFitnessScores().size();
		long duration = Duration.between(startTime, Instant.now()).toMillis();

		log.info("generation: {}, fittest: {}, avg-fitness: {}, duration: {}ms",
				generation, bestFitness, avgFitness, duration);
	}
}
