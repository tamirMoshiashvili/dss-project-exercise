package optimization;

import optimization.evaluation.EvaluationFunction;
import optimization.step.listener.OptimizationStepListener;
import termination.TerminationCriterion;

import java.util.Collection;

public class OptimizerBuilder<T> {
	private final Collection<T> solutions;
	private boolean isMinimization;
	private EvaluationFunction<T> evaluationFunction;
	private OptimizationAlgorithm<T> optimizationAlgorithm;
	private Collection<OptimizationStepListener<T>> listeners;
	private TerminationCriterion<Optimizer<T>> terminationCriterion;

	public OptimizerBuilder(Collection<T> solutions) {
		this.solutions = solutions;
	}

	public OptimizerBuilder<T> minimize() {
		this.isMinimization = true;
		return this;
	}

	public OptimizerBuilder<T> maximize() {
		this.isMinimization = false;
		return this;
	}

	public OptimizerBuilder<T> evaluationFunction(EvaluationFunction<T> evaluationFunction) {
		this.evaluationFunction = evaluationFunction;
		return this;
	}

	public OptimizerBuilder<T> optimizationAlgorithm(OptimizationAlgorithm<T> optimizationAlgorithm) {
		this.optimizationAlgorithm = optimizationAlgorithm;
		return this;
	}

	public OptimizerBuilder<T> listeners(Collection<OptimizationStepListener<T>> listeners) {
		this.listeners = listeners;
		return this;
	}

	public OptimizerBuilder<T> terminationCriterion(TerminationCriterion<Optimizer<T>> terminationCriterion) {
		this.terminationCriterion = terminationCriterion;
		return this;
	}

	public Optimizer<T> build() {
		return new OptimizerImpl<>(solutions, evaluationFunction, isMinimization,
				optimizationAlgorithm, listeners, terminationCriterion);
	}
}
