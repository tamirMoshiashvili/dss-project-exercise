package optimization.algorithm.genetic.selection.roulette.wheel;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RouletteWheelFactory {
	static <T> List<T> createRouletteWheel(boolean isMinimization, Map<T, Float> fitnessScores) {
		fitnessScores = isMinimization ? convertToMaximizedScores(fitnessScores) : fitnessScores;
		int populationSize = fitnessScores.size();

		return FitnessToProbabilityConverter.convertToProbabilities(fitnessScores)
				.entrySet().stream()
				.map(individualProbabilityEntry -> createDuplications(individualProbabilityEntry, populationSize))
				.flatMap(Function.identity())
				.collect(Collectors.toList());
	}

	private static <T> Map<T, Float> convertToMaximizedScores(Map<T, Float> fitnessScores) {
		double maxFitness = getMaxFitness(fitnessScores);

		return fitnessScores.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, tFloatEntry -> (float) (tFloatEntry.getValue() - maxFitness)));
	}

	private static <T> double getMaxFitness(Map<T, Float> fitnessScores) {
		return fitnessScores.values().stream()
				.mapToDouble(Float::doubleValue)
				.max().orElseThrow();
	}

	private static <T> Stream<T> createDuplications(Map.Entry<T, Float> individualProbabilityEntry, int populationSize) {
		long numDuplications = (long) (individualProbabilityEntry.getValue() * populationSize) + 1;

		return Stream.generate(individualProbabilityEntry::getKey)
				.limit(numDuplications);
	}
}
