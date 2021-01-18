package optimization.genetic.operator.mutation;

import optimization.genetic.operator.GeneticOperator;

public interface Mutation<T> extends GeneticOperator<T> {
	GeneticOperator<T> getMutationFunction();

	double getMutationRate();

	@Override
	default T apply(T individual) {
		return isNeedToMutate() ? getMutationFunction().apply(individual) : individual;
	}

	private boolean isNeedToMutate() {
		return Math.random() < getMutationRate();
	}
}
