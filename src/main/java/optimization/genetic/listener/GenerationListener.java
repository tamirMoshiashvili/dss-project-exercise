package optimization.genetic.listener;

import optimization.genetic.GeneticAlgorithm;
import optimization.genetic.selection.Selector;

public interface GenerationListener<T> {
	void notifyGenerationStarted();

	void notifyGenerationEnded(GeneticAlgorithm<T> geneticAlgorithm, Selector<T> selector);
}
