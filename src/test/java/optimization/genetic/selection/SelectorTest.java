package optimization.genetic.selection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SelectorTest {
	@Spy
	private Selector<Integer> selector;

	@Test
	void givenMinimization_whenGetFittest_thenReturnLowest() {
		when(selector.isMinimization()).thenReturn(true);
		when(selector.getFitnessScores()).thenReturn(getFitnessScores());

		Assertions.assertEquals(1, selector.getFittest());
	}

	@Test
	void givenMaximization_whenGetFittest_thenReturnGreatest() {
		when(selector.isMinimization()).thenReturn(false);
		when(selector.getFitnessScores()).thenReturn(getFitnessScores());

		Assertions.assertEquals(3, selector.getFittest());
	}

	@Test
	void givenMinimization_whenGetTwoElitism_thenReturnFirstTwoLowest() {
		when(selector.isMinimization()).thenReturn(true);
		when(selector.getFitnessScores()).thenReturn(getFitnessScores());

		Assertions.assertEquals(List.of(1, 2), selector.getElitism(2));
	}

	private Map<Integer, Float> getFitnessScores() {
		return List.of(1, 2, 3).stream()
				.collect(Collectors.toMap(Function.identity(), Integer::floatValue));
	}
}