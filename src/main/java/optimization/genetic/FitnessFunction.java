package optimization.genetic;

@FunctionalInterface
public interface FitnessFunction<T> {
	Float evaluate(T individual);
}
