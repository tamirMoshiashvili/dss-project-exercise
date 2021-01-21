package optimization.algorithm.genetic.selection.roulette.wheel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static optimization.algorithm.genetic.selection.roulette.wheel.RouletteWheelFactory.createRouletteWheel;

class RouletteWheelFactoryTest {
	@Test
	void givenMaximization_whenCreateRouletteWheel_thenCountFromLowerToGreater() {
		List<Integer> rouletteWheel = createRouletteWheel(false, getFitnessScores());
		long oneCount = count(rouletteWheel, 1);
		long twoCount = count(rouletteWheel, 2);
		long threeCount = count(rouletteWheel, 3);

		Assertions.assertTrue(oneCount <= twoCount);
		Assertions.assertTrue(twoCount <= threeCount);
	}

	@Test
	void givenMinimization_whenCreateRouletteWheel_thenCountFromGreaterToLower() {
		List<Integer> rouletteWheel = createRouletteWheel(true, getFitnessScores());
		long oneCount = count(rouletteWheel, 1);
		long twoCount = count(rouletteWheel, 2);
		long threeCount = count(rouletteWheel, 3);

		Assertions.assertTrue(threeCount <= twoCount);
		Assertions.assertTrue(twoCount <= oneCount);
	}

	private Map<Integer, Float> getFitnessScores() {
		return List.of(1, 2, 3).stream()
				.collect(Collectors.toMap(Function.identity(), Integer::floatValue));
	}

	private long count(List<Integer> rouletteWheel, int item) {
		return rouletteWheel.stream().filter(integer -> integer == item).count();
	}
}
