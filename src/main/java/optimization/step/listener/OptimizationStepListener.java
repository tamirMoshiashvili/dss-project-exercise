package optimization.step.listener;

import optimization.evaluation.OptimizationStepEvaluation;

public interface OptimizationStepListener<T> {
	void notifyOptimizationStepStarted();

	void notifyOptimizationStepEnded(OptimizationStepEvaluation<T> stepEvaluation);
}
