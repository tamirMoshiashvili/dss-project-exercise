package optimization.evaluation;

@FunctionalInterface
public interface EvaluationFunction<T> {
	Float evaluate(T object);
}
