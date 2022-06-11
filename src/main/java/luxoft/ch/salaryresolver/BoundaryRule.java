package luxoft.ch.salaryresolver;

import java.util.Objects;

public class BoundaryRule extends Rule {

	private final Boundary salary;

	public BoundaryRule(String name, Relation relation, Boundary salary) {
		super(name, relation);
		this.salary = salary;
	}

	public Boundary getSalary() {
		return salary;
	}

	@Override
	public int hashCode() {
		return Objects.hash(person, relation, salary);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BoundaryRule rule) {
			return Objects.equals(person, rule.person) && Objects.equals(relation, rule.relation)
					&& Objects.equals(salary, rule.salary);
		}
		return false;
	}

}
