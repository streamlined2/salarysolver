package luxoft.ch.salaryresolver;

import java.util.HashSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import static luxoft.ch.salaryresolver.Relation.*;

public class Solver {

	private final RuleSet rules;

	public Solver(Rule... ruleList) {
		rules = new RuleSet(ruleList);
	}

	public RangeSet solve() {
		RangeSet rangeSet = new RangeSet(rules.getPersons());
		applySalaryRules(rangeSet);
		applyEqualityRules(rangeSet);
		applyComparisonRules(rangeSet);
		return rangeSet;
	}

	private void applySalaryRules(RangeSet rangeSet) {
		rules.getSalaryRules().forEach(rangeSet::applySalaryRule);
	}

	private void applyEqualityRules(RangeSet rangeSet) {
		Queue<Range> rangeQueue = rangeSet.getRangesByLength();
		Range range;
		Set<InterpersonalRule> equalityRules = new HashSet<>();
		while ((range = rangeQueue.poll()) != null) {
			equalityRules.clear();
			searchEqualityRulesFor(equalityRules, range.getPerson());
			for (var person : getPersonsFor(equalityRules)) {
				rangeSet.applyInterpersonalEqualsRule(person, range.getMinimum(), range.getMaximum());
			}
		}
	}

	private Set<Person> getPersonsFor(Set<InterpersonalRule> rules) {
		return rules.stream().flatMap(Rule::getRelatedPersons).collect(Collectors.toSet());
	}

	private void searchEqualityRulesFor(Set<InterpersonalRule> equalityRules, Person person) {
		Set<InterpersonalRule> personRules = rules.getEqualityRulesFor(person).collect(Collectors.toSet());
		boolean changed = equalityRules.addAll(personRules);
		if (changed) {
			for (var relatedPerson : getPersonsFor(personRules)) {
				searchEqualityRulesFor(equalityRules, relatedPerson);
			}
		}
	}

	private void applyComparisonRules(RangeSet rangeSet) {
		Queue<Range> rangeQueue = rangeSet.getRangesByLength();
		Range range;
		Set<InterpersonalRule> comparisonRules = new HashSet<>();
		while ((range = rangeQueue.poll()) != null) {
			comparisonRules.clear();
			searchComparisonRulesFor(comparisonRules, rangeSet, range);
		}
	}

	private void searchComparisonRulesFor(Set<InterpersonalRule> comparisonRules, RangeSet rangeSet, Range range) {
		Set<InterpersonalRule> personRules = rules.getComparisonRulesFor(range.getPerson()).collect(Collectors.toSet());
		boolean changed = comparisonRules.addAll(personRules);
		if (changed) {
			for (var rule : personRules) {
				rangeSet.applyInterpersonalCompareRule(rule, range);
			}
			for (var relatedPerson : getPersonsFor(personRules)) {
				Optional<Range> relatedRange = rangeSet.getRange(relatedPerson);
				if (relatedRange.isPresent()) {
					searchComparisonRulesFor(comparisonRules, rangeSet, relatedRange.get());
				}
			}
		}
	}

	public static void main(String... args) {

		Solver solver = new Solver(
				new InterpersonalRule("Michael", LESS, "Louis"),
				new InterpersonalRule("Louis", LESS, "Cliff"),
				new InterpersonalRule("Cliff", LESS, "Maria"),
				new InterpersonalRule("Maria", LESS, "Andrew"),
				new InterpersonalRule("Andrew", EQUAL, "Nigel"),
				new InterpersonalRule("Nigel", EQUAL, "Christopher"),
				new InterpersonalRule("Christopher", EQUAL, "Radford"),
				new InterpersonalRule("Radford", EQUAL, "Andrew"),
				new SalaryRule("Michael", GREATER, 200), 
				new SalaryRule("Christopher", LESS, 2000));
		RangeSet rangeSet = solver.solve();
		if (rangeSet.isValid()) {
			System.out.println(rangeSet);
		} else {
			System.out.println("No solution");
		}

	}

}
