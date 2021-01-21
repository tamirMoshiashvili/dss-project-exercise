package optimization.algorithm.genetic;

import optimization.algorithm.genetic.selection.Selector;

@FunctionalInterface
public interface CrossoverFunction<T> {
	T createOffspringBy(Selector<T> selector);
}
