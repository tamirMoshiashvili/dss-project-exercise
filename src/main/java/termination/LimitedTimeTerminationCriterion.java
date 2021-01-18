package termination;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class LimitedTimeTerminationCriterion<T> implements TerminationCriterion<T> {
	private Duration timeDuration;
	private Instant startTime;

	public LimitedTimeTerminationCriterion(Duration timeDuration) {
		this.timeDuration = Objects.requireNonNull(timeDuration, "Time Duration must be not null");
	}

	@Override
	public boolean isDone(Object object) {
		initializeStartTimeIfNull();
		return doesTimePassed();
	}

	private void initializeStartTimeIfNull() {
		if (startTime == null) {
			startTime = Instant.now();
		}
	}

	private boolean doesTimePassed() {
		return Duration.between(startTime, Instant.now()).compareTo(timeDuration) > 0;
	}
}
