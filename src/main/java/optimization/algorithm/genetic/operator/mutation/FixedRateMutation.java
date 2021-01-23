package optimization.algorithm.genetic.operator.mutation;

import optimization.algorithm.genetic.operator.GeneticOperator;

public class FixedRateMutation<T> implements Mutation<T> {
	private final double fixedMutationRate;
	private final GeneticOperator<T> mutationFunction;

	public FixedRateMutation(double fixedMutationRate, GeneticOperator<T> mutationFunction) {
		validateMutationRateRange(fixedMutationRate);
		this.fixedMutationRate = fixedMutationRate;
		this.mutationFunction = mutationFunction;
	}

	private void validateMutationRateRange(double mutationRate) {
		if (mutationRate < 0 || mutationRate > 1) {
			throw new IllegalArgumentException("Mutation-rate must be between 0 to 1, provided: " + mutationRate);
		}
	}

	@Override
	public GeneticOperator<T> getMutationFunction() {
		return mutationFunction;
	}

	@Override
	public double getMutationRate() {
		return fixedMutationRate;
	}
}
