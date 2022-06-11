package luxoft.ch.salaryresolver;

import java.util.Objects;

public abstract class Rule {

	protected final Person person;
	protected final Relation relation;

	protected Rule(String name, Relation relation) {
		person = new Person(name);
		this.relation = relation;
	}

	public Person getPerson() {
		return person;
	}

	public Relation getRelation() {
		return relation;
	}

}
