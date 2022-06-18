package luxoft.ch.salaryresolver;

import java.util.Set;

public enum Relation {

	EQUAL, LESS, GREATER, LESS_OR_EQUAL, GREATER_OR_EQUAL;

	public boolean isStrict() {
		return this == LESS || this == GREATER;
	}

	public boolean affectsMinimum() {
		return Set.of(EQUAL, GREATER, GREATER_OR_EQUAL).contains(this);
	}

	public boolean affectsMaximum() {
		return Set.of(EQUAL, LESS, LESS_OR_EQUAL).contains(this);
	}

}
