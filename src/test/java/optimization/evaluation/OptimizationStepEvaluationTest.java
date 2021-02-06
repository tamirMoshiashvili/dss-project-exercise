package optimization.evaluation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class OptimizationStepEvaluationTest {
	@Test
	void givenMinimization_whenGetBestSolution_thenReturnLowest() {
		OptimizationStepEvaluation<Integer> stepEvaluation = createStepEvaluation(true);

		Assertions.assertEquals(1, stepEvaluation.getBestSolution());
	}

	@Test
	void givenMaximization_whenGetBestSolution_thenReturnGreatest() {
		OptimizationStepEvaluation<Integer> stepEvaluation = createStepEvaluation(false);

		Assertions.assertEquals(3, stepEvaluation.getBestSolution());
	}

	@Test
	void givenMinimization_whenGetTwoBestSolutions_thenReturnFirstTwoLowest() {
		OptimizationStepEvaluation<Integer> stepEvaluation = createStepEvaluation(true);

		Assertions.assertEquals(List.of(1, 2), stepEvaluation.getBestSolutions(2));
	}

	private OptimizationStepEvaluation<Integer> createStepEvaluation(boolean isMinimization) {
		OptimizationStepEvaluation<Integer> stepEvaluation = new OptimizationStepEvaluation<>(isMinimization);
		stepEvaluation.setEvaluations(getEvaluations());

		return stepEvaluation;
	}

	private Map<Integer, Float> getEvaluations() {
		return List.of(1, 2, 3).stream()
				.collect(Collectors.toMap(Function.identity(), Integer::floatValue));
	}
}
