package luxoft.ch.salaryresolver;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Solver {

	private final RuleSet rules;

	public Solver(Rule... ruleList) {
		rules = new RuleSet(ruleList);
	}

	public RangeSet solve() {
		RangeSet rangeSet = new RangeSet(rules.getPersons());
		applySalaryRules(rangeSet);
		applyEqualityRules(rangeSet);
		return rangeSet;
	}

	private void applySalaryRules(RangeSet rangeSet) {
		rules.getSalaryRules().forEach(rangeSet::applySalaryRule);
	}

	private void applyEqualityRules(RangeSet rangeSet) {
		Queue<Range> rangeQueue = rangeSet.getRangesByLength();
		Range range;
		while ((range = rangeQueue.poll()) != null) {
			Set<InterpersonalRule> equalityRules = new HashSet<>();
			searchEqualityRulesFor(equalityRules, range.getPerson());
			for (var person : equalityRules.stream().flatMap(Rule::getRelatedPersons).collect(Collectors.toSet())) {
				rangeSet.applyInterpersonalEqualsRule(person, range.getMinimum(), range.getMaximum());
			}
		}
	}

	private void searchEqualityRulesFor(Set<InterpersonalRule> equalityRules, Person person) {
		Set<InterpersonalRule> personRules = rules.getEqualityRulesFor(person).collect(Collectors.toSet());
		boolean changed = equalityRules.addAll(personRules);
		if (changed) {
			Set<Person> persons = personRules.stream().flatMap(Rule::getRelatedPersons).collect(Collectors.toSet());
			for (var relatedPerson : persons) {
				searchEqualityRulesFor(equalityRules, relatedPerson);
			}
		}
	}

	public static void main(String... args) {

		Solver solver = new Solver(
				new InterpersonalRule("Andrew", Relation.EQUAL, "Nigel"),
				new InterpersonalRule("Nigel", Relation.EQUAL, "Christopher"),
				new InterpersonalRule("Christopher", Relation.EQUAL, "Radford"),
				new InterpersonalRule("Radford", Relation.EQUAL, "Andrew"),
				new SalaryRule("Michael", Relation.GREATER, 200),
				new SalaryRule("Christopher", Relation.LESS, 2000));
		RangeSet rangeSet = solver.solve();
		System.out.println(rangeSet);

	}

}
