package optimization;

import java.util.Collection;
import java.util.Objects;

class SolutionsValidator {
	static <T> void validate(Collection<T> solutions) {
		Objects.requireNonNull(solutions, "Solutions can't be null");
		validateSolutionsNotEmpty(solutions);
		validateSolutionsContainsNonNullElements(solutions);
	}

	private static <T> void validateSolutionsNotEmpty(Collection<T> solutions) {
		if (solutions.isEmpty()) {
			throw new IllegalArgumentException("Solutions size must be greater than 0");
		}
	}

	private static <T> void validateSolutionsContainsNonNullElements(Collection<T> solutions) {
		if (solutions.stream().anyMatch(Objects::isNull)) {
			throw new IllegalArgumentException("Solutions must not contain null elements");
		}
	}
}
