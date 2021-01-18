package optimization.genetic.selection;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface Selector<T> {
	Map<T, Float> getFitnessScores();

	boolean isMinimization();

	T select();

	default T getFittest() {
		Comparator<? super Float> comparator = getComparator();

		return getFitnessScores().entrySet().stream()
				.min((entry1, entry2) -> comparator.compare(entry1.getValue(), entry2.getValue()))
				.orElseThrow()
				.getKey();
	}

	default List<T> getElitism(int elitismSize) {
		Comparator<? super Float> comparator = getComparator();

		return getFitnessScores().entrySet().stream()
				.sorted((entry1, entry2) -> comparator.compare(entry1.getValue(), entry2.getValue()))
				.limit(elitismSize)
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	private Comparator<Float> getComparator() {
		return isMinimization() ? Comparator.naturalOrder() : Comparator.reverseOrder();
	}
}
