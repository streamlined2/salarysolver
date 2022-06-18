package luxoft.ch.salaryresolver;

public enum Relation {

	EQUAL, LESS, GREATER, LESS_OR_EQUAL, GREATER_OR_EQUAL;

	public static boolean isStrict(Relation relation) {
		return relation == LESS || relation == GREATER;
	}

}
