package luxoft.ch.salaryresolver;

import java.util.Objects;

public record Boundary(Salary value, Kind kind) {

	public enum Kind {

		INCLUSIVE() {
			public String minToString() {
				return "[";
			}

			public String maxToString() {
				return "]";
			}
		},

		EXCLUSIVE() {
			public String minToString() {
				return "(";
			}

			public String maxToString() {
				return ")";
			}
		};

		public abstract String minToString();

		public abstract String maxToString();

		public static Kind getKindFor(Relation relation) {
			return relation.isStrict() ? EXCLUSIVE : INCLUSIVE;
		}

	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, kind);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Boundary boundary) {
			return Objects.equals(value, boundary.value) && kind == boundary.kind;
		}
		return false;
	}

	public Salary getMinimalSalary() {
		return switch (kind) {
		case EXCLUSIVE -> value().getGreaterBy(1);
		case INCLUSIVE -> value();
		};
	}

	public Salary getMaximalSalary() {
		return switch (kind) {
		case EXCLUSIVE -> value().getLesserBy(1);
		case INCLUSIVE -> value();
		};
	}

	public static Boundary getMinimum() {
		return new Boundary(new Salary(Salary.MINIMUM_VALUE), Kind.INCLUSIVE);
	}

	public static Boundary getMaximum() {
		return new Boundary(new Salary(Salary.MAXIMUM_VALUE), Kind.INCLUSIVE);
	}

}
