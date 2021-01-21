package optimization.algorithm.genetic.selection.roulette.wheel;

import optimization.algorithm.genetic.selection.Selector;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RouletteWheelSelector<T> implements Selector<T> {
	private List<T> rouletteWheel;
	private Random random;

	public RouletteWheelSelector(Map<T, Float> fitnessScores, boolean isMinimization) {
		this.rouletteWheel = RouletteWheelFactory.createRouletteWheel(isMinimization, fitnessScores);
		this.random = new Random();
	}

	@Override
	public T select() {
		return rouletteWheel.get(random.nextInt(rouletteWheel.size()));
	}
}
