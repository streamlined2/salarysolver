package luxoft.ch.salaryresolver;

public class Solver {

	private final RuleSet rules;

	public Solver(Rule... ruleList) {
		rules = new RuleSet(ruleList);
	}

	public RangeSet solve() {
		RangeSet rangeSet = new RangeSet(rules.getPersons());
		applySalaryRules(rangeSet);
		return rangeSet;
	}

	private void applySalaryRules(RangeSet rangeSet) {
		rules.getSalaryRules().forEach(rangeSet::applySalaryRule);
	}

	public static void main(String... args) {

		Solver solver = new Solver(new SalaryRule("Michael", Relation.GREATER, 200),
				new SalaryRule("Christopher", Relation.LESS, 2000));
		RangeSet rangeSet = solver.solve();
		System.out.println(rangeSet);

	}

}
