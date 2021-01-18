package optimization.genetic.selection;

import java.util.Map;

@FunctionalInterface
public interface SelectorFactory<T> {
	Selector<T> create(Map<T, Float> fitnessScores, boolean isMinimization);
}
