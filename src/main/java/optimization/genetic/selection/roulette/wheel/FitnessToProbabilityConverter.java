package optimization.genetic.selection.roulette.wheel;

import java.util.Map;
import java.util.stream.Collectors;

class FitnessToProbabilityConverter {
	static <T> Map<T, Float> convertToProbabilities(Map<T, Float> fitnessScores) {
		double fitnessSum = calculateFitnessSum(fitnessScores);

		return fitnessScores.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey,
						individualFitnessEntry -> (float) (individualFitnessEntry.getValue() / fitnessSum)));
	}

	private static <T> double calculateFitnessSum(Map<T, Float> fitnessScores) {
		return fitnessScores.values().stream()
				.mapToDouble(Float::doubleValue)
				.sum();
	}
}
