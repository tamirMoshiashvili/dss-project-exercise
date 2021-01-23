package optimization;

import lombok.Getter;
import optimization.evaluation.EvaluationFunction;
import optimization.step.listener.OptimizationStepListener;
import termination.TerminationCriterion;

import java.util.Collection;

@Getter
public class OptimizerImpl<T> implements Optimizer<T> {
	private Collection<T> solutions;
	private final boolean isMinimization;
	private final EvaluationFunction<T> evaluationFunction;
	private final OptimizationAlgorithm<T> optimizationAlgorithm;
	private final Collection<OptimizationStepListener<T>> listeners;
	private final TerminationCriterion<Optimizer<T>> terminationCriterion;

	OptimizerImpl(Collection<T> solutions, EvaluationFunction<T> evaluationFunction, boolean isMinimization,
				  OptimizationAlgorithm<T> optimizationAlgorithm, Collection<OptimizationStepListener<T>> listeners,
				  TerminationCriterion<Optimizer<T>> terminationCriterion) {
		this.solutions = solutions;
		this.evaluationFunction = evaluationFunction;
		this.isMinimization = isMinimization;
		this.optimizationAlgorithm = optimizationAlgorithm;
		this.listeners = listeners;
		this.terminationCriterion = terminationCriterion;
	}

	@Override
	public void setSolutions(Collection<T> solutions) {
		this.solutions = solutions;
	}
}
