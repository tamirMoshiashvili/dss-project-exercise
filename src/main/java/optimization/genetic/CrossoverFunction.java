package optimization.genetic;

import optimization.genetic.selection.Selector;

@FunctionalInterface
public interface CrossoverFunction<T> {
	T createOffspringBy(Selector<T> selector);
}
