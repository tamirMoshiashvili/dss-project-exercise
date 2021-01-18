package termination;

import java.util.function.Function;

public class LimitedEpochsTerminationCriterion<T> implements TerminationCriterion<T> {
	private int numEpochs;
	private Function<T, Integer> epochSupplier;

	public LimitedEpochsTerminationCriterion(int numEpochs, Function<T, Integer> epochSupplier) {
		validateNumGenerations(numEpochs);
		this.numEpochs = numEpochs;
		this.epochSupplier = epochSupplier;
	}

	private void validateNumGenerations(int numGenerations) {
		if (numGenerations <= 0) {
			throw new IllegalArgumentException("Number of generations must be greater than 0, provided: " + numGenerations);
		}
	}

	@Override
	public boolean isDone(T object) {
		return epochSupplier.apply(object) >= numEpochs;
	}
}
