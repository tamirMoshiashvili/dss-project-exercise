package optimization.genetic.operator.mutation;

import optimization.genetic.operator.GeneticOperator;

public class FixedRateMutation<T> implements Mutation<T> {
	private double fixedMutationRate;
	private GeneticOperator<T> mutationFunction;

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
