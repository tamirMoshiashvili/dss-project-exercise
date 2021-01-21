package optimization;

import optimization.evaluation.OptimizationStepEvaluation;

import java.util.Collection;

public interface OptimizationAlgorithm<T> {
	Collection<T> optimize(OptimizationStepEvaluation<T> stepEvaluation);
}
