package config.optimization;

import lombok.Data;

@Data
public class OptimizationConfig {
	private Integer solutionsSize;
	private TerminationConfig termination;
}
