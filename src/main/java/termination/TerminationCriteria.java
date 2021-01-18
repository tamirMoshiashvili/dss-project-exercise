package termination;

import java.util.List;

public class TerminationCriteria<T> implements TerminationCriterion<T> {
	private List<TerminationCriterion<T>> terminationCriteria;

	public TerminationCriteria(List<TerminationCriterion<T>> terminationCriteria) {
		this.terminationCriteria = terminationCriteria;
	}

	@Override
	public boolean isDone(T object) {
		return terminationCriteria.stream()
				.anyMatch(terminationCriterion -> terminationCriterion.isDone(object));
	}
}
