package optimization;

import optimization.evaluation.EvaluationFunction;
import optimization.evaluation.OptimizationStepEvaluation;
import optimization.step.listener.OptimizationStepListener;
import termination.TerminationCriterion;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Optimizer<T> {
	Collection<T> getSolutions();

	void setSolutions(Collection<T> solutions);

	EvaluationFunction<T> getEvaluationFunction();

	boolean isMinimization();

	OptimizationAlgorithm<T> getOptimizationAlgorithm();

	Collection<OptimizationStepListener<T>> getListeners();

	TerminationCriterion<Optimizer<T>> getTerminationCriterion();

	default OptimizationStepEvaluation<T> optimize() {
		SolutionsValidator.validate(getSolutions());
		OptimizationStepEvaluation<T> stepEvaluation = new OptimizationStepEvaluation<>(isMinimization());

		do {
			notifyOptimizationStepStarted();
			Map<T, Float> evaluations = evaluateSolutions();
			stepEvaluation.setEvaluations(evaluations);
			Collection<T> newSolutions = getOptimizationAlgorithm().optimize(stepEvaluation);
			setSolutions(newSolutions);
			notifyStepOptimizationEnded(stepEvaluation);
		} while (!getTerminationCriterion().isDone(this));

		return stepEvaluation;
	}

	private void notifyOptimizationStepStarted() {
		getListeners().forEach(OptimizationStepListener::notifyOptimizationStepStarted);
	}

	private Map<T, Float> evaluateSolutions() {
		return getSolutions().parallelStream()
				.collect(Collectors.toMap(Function.identity(), getEvaluationFunction()::evaluate));
	}

	private void notifyStepOptimizationEnded(OptimizationStepEvaluation<T> stepEvaluation) {
		getListeners().forEach(listener -> listener.notifyOptimizationStepEnded(stepEvaluation));
	}
}
