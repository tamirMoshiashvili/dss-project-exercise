package optimization.algorithm.genetic.operator;

@FunctionalInterface
public interface GeneticOperator<T> {
	T apply(T individual);
}
