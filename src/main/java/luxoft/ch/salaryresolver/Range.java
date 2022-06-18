package luxoft.ch.salaryresolver;

import java.util.Objects;

import luxoft.ch.salaryresolver.Boundary.Kind;

public class Range {

	private final Person person;
	private Boundary minimum;
	private Boundary maximum;

	public Range(Person person, Boundary minimum, Boundary maximum) {
		this.person = person;
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public Person getPerson() {
		return person;
	}

	public Boundary getMinimum() {
		return minimum;
	}

	public void upMinimum(Salary salary, Relation relation) {
		if (compareMinimum(salary, relation) < 0) {
			minimum = new Boundary(salary, Kind.getKindFor(relation));
		}
	}

	private int compareMinimum(Salary salary, Relation relation) {
		Salary minimalSalary = minimum.getMinimalSalary();
		if (Relation.isStrict(relation)) {
			return minimalSalary.compareTo(salary.getGreaterBy(1));
		} else {
			return minimalSalary.compareTo(salary);
		}
	}

	public Boundary getMaximum() {
		return maximum;
	}

	public void downMaximum(Salary salary, Relation relation) {
		if (compareMaximum(salary, relation) > 0) {
			maximum = new Boundary(salary, Kind.getKindFor(relation));
		}
	}

	private int compareMaximum(Salary salary, Relation relation) {
		Salary maximalSalary = maximum.getMaximalSalary();
		if (Relation.isStrict(relation)) {
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
	
	public static void main(String[] args) {
		Person person = new Person("John Smith");
		Range range = new Range(person, new Boundary(new Salary(20), Kind.INCLUSIVE), new Boundary(new Salary(100), Kind.INCLUSIVE));
		System.out.println(range);
		range.upMinimum(new Salary(10), Relation.EQUAL);
		System.out.println(range);
		range.upMinimum(new Salary(20), Relation.EQUAL);
		System.out.println(range);
		range.upMinimum(new Salary(21), Relation.EQUAL);
		System.out.println(range);
		range.upMinimum(new Salary(22), Relation.LESS);
		System.out.println(range);
		range.upMinimum(new Salary(23), Relation.LESS_OR_EQUAL);
		System.out.println(range);
		range.upMinimum(new Salary(24), Relation.LESS_OR_EQUAL);
		System.out.println(range);
		range.upMinimum(new Salary(30), Relation.GREATER);
		System.out.println(range);
		range.downMaximum(new Salary(99), Relation.LESS_OR_EQUAL);
		System.out.println(range);
		range.downMaximum(new Salary(100), Relation.LESS);
		System.out.println(range);
		range.downMaximum(new Salary(99), Relation.LESS);
		System.out.println(range);
		range.downMaximum(new Salary(98), Relation.LESS);
		System.out.println(range);
		range.downMaximum(new Salary(80), Relation.LESS_OR_EQUAL);
		System.out.println(range);
		range.downMaximum(new Salary(85), Relation.LESS_OR_EQUAL);
		System.out.println(range);
		range.downMaximum(new Salary(70), Relation.LESS);
		System.out.println(range);
	}

}
