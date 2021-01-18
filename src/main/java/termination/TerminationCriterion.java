package termination;

public interface TerminationCriterion<T> {
	boolean isDone(T object);
}
