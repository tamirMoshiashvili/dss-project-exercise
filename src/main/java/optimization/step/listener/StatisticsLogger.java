package optimization.step.listener;

import lombok.extern.slf4j.Slf4j;
import optimization.evaluation.OptimizationStepEvaluation;

import java.time.Duration;
import java.time.Instant;

@Slf4j
public class StatisticsLogger<T> implements OptimizationStepListener<T> {
	private Instant startTime;

	@Override
	public void notifyOptimizationStepStarted() {
		startTime = Instant.now();
	}

	@Override
	public void notifyOptimizationStepEnded(OptimizationStepEvaluation<T> stepEvaluation) {
		logGeneration(stepEvaluation);
	}

	private void logGeneration(OptimizationStepEvaluation<T> stepEvaluation) {
		int step = stepEvaluation.getStep();
		Float bestScore = stepEvaluation.getEvaluations().get(stepEvaluation.getBestSolution());
		double avgFitness = stepEvaluation.getEvaluations().values().stream().mapToDouble(Float::doubleValue).sum()
				/ stepEvaluation.getEvaluations().size();
		long duration = Duration.between(startTime, Instant.now()).toMillis();

		log.info("step: {}, best: {}, avg-score: {}, duration: {}ms",
				step, bestScore, avgFitness, duration);
	}
}
