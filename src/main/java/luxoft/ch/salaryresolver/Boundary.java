package luxoft.ch.salaryresolver;

import java.util.Objects;

public class Boundary implements Comparable<Boundary> {

	public enum Kind {
		STRICT() {
			public String minToString() {
				return "(";
			}

			public String maxToString() {
				return ")";
			}
		},
		NON_STRICT() {
			public String minToString() {
				return "[";
			}

			public String maxToString() {
				return "]";
			}
		};

		public abstract String minToString();

		public abstract String maxToString();

	}

	private static final int MINIMUM = 1;
	private static final int MAXIMUM = 10_000;

	private Integer value;
	private Kind kind;

	public Boundary(Integer value, Kind kind) {
		if (value < MINIMUM) {
			throw new IllegalArgumentException("salary %d must be greater or equal to %d".formatted(value, MINIMUM));
		}
		if (value > MAXIMUM) {
			throw new IllegalArgumentException("salary %d must be less or equal to %d".formatted(value, MAXIMUM));
		}
		this.value = value;
		this.kind = kind;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		return "%d".formatted(value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, kind);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Boundary boundary) {
			return compareTo(boundary) == 0;
		}
		return false;
	}

	@Override
	public int compareTo(Boundary boundary) {
		return Integer.compare(value, boundary.value);
	}

}
