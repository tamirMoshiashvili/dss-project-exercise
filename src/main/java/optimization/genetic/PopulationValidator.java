package optimization.genetic;

import java.util.List;
import java.util.Objects;

class PopulationValidator {
	static <T> void validate(List<T> population) {
		Objects.requireNonNull(population, "Population can't be null");
		validatePopulationNotEmpty(population);
		validatePopulationContainsNonNullElements(population);
	}

	private static <T> void validatePopulationNotEmpty(List<T> population) {
		if (population.isEmpty()) {
			throw new IllegalArgumentException("Population size must be greater than 0");
		}
	}

	private static <T> void validatePopulationContainsNonNullElements(List<T> population) {
		if (population.stream().anyMatch(Objects::isNull)) {
			throw new IllegalArgumentException("Population must not contain null elements");
		}
	}
}
