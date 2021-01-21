package termination;

public class LimitedEpochsTerminationCriterion<T> implements TerminationCriterion<T> {
	private int numEpochs;

	public LimitedEpochsTerminationCriterion(int numEpochs) {
		validateNumGenerations(numEpochs);
		this.numEpochs = numEpochs;
	}

	private void validateNumGenerations(int numGenerations) {
		if (numGenerations <= 0) {
			throw new IllegalArgumentException("Number of generations must be greater than 0, provided: " + numGenerations);
		}
	}

	@Override
	public boolean isDone(T object) {
		return --numEpochs <= 0;
	}
}
