package optimization.evaluation;

import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class OptimizationStepEvaluation<T> {
	private int step;
	private boolean isMinimization;
	private Map<T, Float> evaluations;

	public OptimizationStepEvaluation(boolean isMinimization) {
		this.step = 0;
		this.isMinimization = isMinimization;
	}

	public void setEvaluations(Map<T, Float> evaluations) {
		this.evaluations = evaluations;
		step++;
	}

	public T getBestSolution() {
		Comparator<? super Float> comparator = getComparator();

		return evaluations.entrySet().stream()
				.min((entry1, entry2) -> comparator.compare(entry1.getValue(), entry2.getValue()))
				.orElseThrow()
				.getKey();
	}

	public List<T> getBestSolutions(int size) {
		Comparator<? super Float> comparator = getComparator();

		return evaluations.entrySet().stream()
				.sorted((entry1, entry2) -> comparator.compare(entry1.getValue(), entry2.getValue()))
				.limit(size)
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	private Comparator<Float> getComparator() {
		return isMinimization ? Comparator.naturalOrder() : Comparator.reverseOrder();
	}
}
