package optimization.genetic.selection.roulette.wheel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static optimization.genetic.selection.roulette.wheel.FitnessToProbabilityConverter.convertToProbabilities;

class FitnessToProbabilityConverterTest {
	@Test
	void givenFitnessScores_testConvertFitnessToProbability() {
		Map<Integer, Float> fitnessScores = getFitnessScores();
		Map<Integer, Float> probabilities = convertToProbabilities(fitnessScores);

		Assertions.assertEquals(1f / 6, probabilities.get(1));
		Assertions.assertEquals(2f / 6, probabilities.get(2));
		Assertions.assertEquals(3f / 6, probabilities.get(3));
	}

	@Test
	void givenFitnessScores_whenConvertToProbabilities_thenProbabilitiesSumToOne() {
		Map<Integer, Float> fitnessScores = getFitnessScores();
		double errorMargin = 0.01;

		Assertions.assertTrue(Math.abs(1 - sum(convertToProbabilities(fitnessScores))) < errorMargin);
		Assertions.assertTrue(Math.abs(1 - sum(convertToProbabilities(fitnessScores))) < errorMargin);
	}

	private Map<Integer, Float> getFitnessScores() {
		return List.of(1, 2, 3).stream()
				.collect(Collectors.toMap(Function.identity(), Integer::floatValue));
	}

	private double sum(Map<Integer, Float> probabilities) {
		return probabilities.values().stream()
				.mapToDouble(Float::doubleValue)
				.sum();
	}
}
