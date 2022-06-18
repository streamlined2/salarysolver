package luxoft.ch.salaryresolver;

import java.util.Objects;

public class SalaryRule extends Rule {

	private final Salary salary;

	public SalaryRule(String name, Relation relation, Integer value) {
		super(name, relation);
		this.salary = new Salary(value);
	}

	public Salary getSalary() {
		return salary;
	}

	@Override
	public int hashCode() {
		return Objects.hash(person, relation, salary);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SalaryRule rule) {
			return Objects.equals(person, rule.person) && Objects.equals(relation, rule.relation)
					&& Objects.equals(salary, rule.salary);
		}
		return false;
	}

}
