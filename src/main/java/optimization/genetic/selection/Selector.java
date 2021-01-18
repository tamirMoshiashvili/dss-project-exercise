package optimization.genetic.selection;

import java.util.Map;

public interface Selector<T> {
	Map<T, Float> getFitnessScores();

	boolean isMinimization();

	T select();
}
