package optimization.evaluation;

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
class OptimizationStepEvaluationTest {
	@Spy
	private OptimizationStepEvaluation<Integer> stepEvaluation;

	@Test
	void givenMinimization_whenGetBestSolution_thenReturnLowest() {
		when(stepEvaluation.isMinimization()).thenReturn(true);
		when(stepEvaluation.getEvaluations()).thenReturn(getEvaluations());

		Assertions.assertEquals(1, stepEvaluation.getBestSolution());
	}

	@Test
	void givenMaximization_whenGetBestSolution_thenReturnGreatest() {
		when(stepEvaluation.isMinimization()).thenReturn(false);
		when(stepEvaluation.getEvaluations()).thenReturn(getEvaluations());

		Assertions.assertEquals(3, stepEvaluation.getBestSolution());
	}

	@Test
	void givenMinimization_whenGetTwoBestSolutions_thenReturnFirstTwoLowest() {
		when(stepEvaluation.isMinimization()).thenReturn(true);
		when(stepEvaluation.getEvaluations()).thenReturn(getEvaluations());

		Assertions.assertEquals(List.of(1, 2), stepEvaluation.getBestSolutions(2));
	}

	private Map<Integer, Float> getEvaluations() {
		return List.of(1, 2, 3).stream()
				.collect(Collectors.toMap(Function.identity(), Integer::floatValue));
	}
}
