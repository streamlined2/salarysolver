package luxoft.ch.salaryresolver;

import java.util.Objects;

public class InterpersonalRule extends Rule {

	private final Person anotherPerson;

	public InterpersonalRule(String name, Relation relation, String anotherName) {
		super(name, relation);
		anotherPerson = new Person(anotherName);
	}

	public Person getAnotherPerson() {
		return anotherPerson;
	}

	@Override
	public int hashCode() {
		return Objects.hash(person, relation, anotherPerson);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof InterpersonalRule rule) {
			return Objects.equals(person, rule.person) && Objects.equals(relation, rule.relation)
					&& Objects.equals(anotherPerson, rule.anotherPerson);
		}
		return false;
	}
	
}
