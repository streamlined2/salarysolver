package luxoft.ch.salaryresolver;

import java.util.Objects;

import luxoft.ch.salaryresolver.Boundary.Kind;

public class Range {

	private final Person person;
	private Boundary minimum;
	private Boundary maximum;

	public Range(Person person) {
		this.person = person;
		this.minimum = Boundary.getMinimum();
		this.maximum = Boundary.getMaximum();
	}

	public Range(Person person, Boundary minimum, Boundary maximum) {
		this.person = person;
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public boolean isValid() {
		return getLength() > 0;
	}

	public int getLength() {
		return maximum.getMaximalSalary().value() - minimum.getMinimalSalary().value();
	}

	public Person getPerson() {
		return person;
	}

	public Boundary getMinimum() {
		return minimum;
	}

	public void setMinimum(Boundary minimum) {
		this.minimum = minimum;
	}

	public void upMinimum(Salary salary, Relation relation) {
		if (compareMinimum(salary, relation) < 0) {
			minimum = new Boundary(salary, Kind.getKindFor(relation));
		}
	}

	private int compareMinimum(Salary salary, Relation relation) {
		Salary minimalSalary = minimum.getMinimalSalary();
		if (relation.isStrict()) {
			return minimalSalary.compareTo(salary.getGreaterBy(1));
		} else {
			return minimalSalary.compareTo(salary);
		}
	}

	public Boundary getMaximum() {
		return maximum;
	}

	public void setMaximum(Boundary maximum) {
		this.maximum = maximum;
	}

	public void downMaximum(Salary salary, Relation relation) {
		if (compareMaximum(salary, relation) > 0) {
			maximum = new Boundary(salary, Kind.getKindFor(relation));
		}
	}

	private int compareMaximum(Salary salary, Relation relation) {
		Salary maximalSalary = maximum.getMaximalSalary();
		if (relation.isStrict()) {
			return maximalSalary.compareTo(salary.getLesserBy(1));
		} else {
			return maximalSalary.compareTo(salary);
		}
	}

	@Override
	public int hashCode() {
		return person.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Range pair) {
			return Objects.equals(person, pair.person);
		}
		return false;
	}

	@Override
	public String toString() {
		return "name: %s, salary: %s%s, %s%s".formatted(person.name(), getMinimum().kind().minToString(),
				getMinimum().toString(), getMaximum().toString(), getMaximum().kind().maxToString());
	}

}
