package optimization.genetic.selection.roulette.wheel;

import optimization.genetic.selection.Selector;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RouletteWheelSelector<T> implements Selector<T> {
	private Map<T, Float> fitnessScores;
	private boolean isMinimization;
	private List<T> rouletteWheel;
	private Random random;

	public RouletteWheelSelector(Map<T, Float> fitnessScores, boolean isMinimization) {
		this.fitnessScores = fitnessScores;
		this.isMinimization = isMinimization;
		this.rouletteWheel = RouletteWheelFactory.createRouletteWheel(isMinimization, fitnessScores);
		this.random = new Random();
	}

	@Override
	public Map<T, Float> getFitnessScores() {
		return fitnessScores;
	}

	@Override
	public boolean isMinimization() {
		return isMinimization;
	}

	@Override
	public T select() {
		return rouletteWheel.get(random.nextInt(rouletteWheel.size()));
	}
}
