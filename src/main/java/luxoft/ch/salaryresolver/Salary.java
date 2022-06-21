package luxoft.ch.salaryresolver;

public record Salary(Integer value) implements Comparable<Salary> {

	public static final Integer MINIMUM_VALUE = 1;
	public static final Integer MAXIMUM_VALUE = 10_000;

	public Salary {
		if (value < MINIMUM_VALUE) {
			value = MINIMUM_VALUE;
		}
		if (value > MAXIMUM_VALUE) {
			value = MAXIMUM_VALUE;
		}
	}

	@Override
	public int compareTo(Salary salary) {
		return Integer.compare(value, salary.value);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public Salary getGreaterBy(int increment) {
		return new Salary(value + increment);
	}

	public Salary getLesserBy(int decrement) {
		return new Salary(value - decrement);
	}

}
