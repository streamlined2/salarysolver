package luxoft.ch.salaryresolver;

import java.util.Objects;

public class BoundaryPair {

	private Person person;
	private Boundary minimum;
	private Boundary maximum;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Boundary getMinimum() {
		return minimum;
	}

	public void setMinimum(Boundary minimum) {
		this.minimum = minimum;
	}

	public Boundary getMaximum() {
		return maximum;
	}

	public void setMaximum(Boundary maximum) {
		this.maximum = maximum;
	}

	@Override
	public int hashCode() {
		return person.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BoundaryPair pair) {
			return Objects.equals(person, pair.person);
		}
		return false;
	}

	@Override
	public String toString() {
		return "name: %s, salary: %s%s, %s%s".formatted(person.name(), getMinimum().getKind().minToString(),
				getMinimum().toString(), getMaximum().toString(), getMaximum().getKind().maxToString());
	}

}
