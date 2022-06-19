package luxoft.ch.salaryresolver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RuleSet {

	private final Set<Rule> rules;

	public RuleSet(Rule... ruleList) {
		rules = new HashSet<>();
		rules.addAll(Arrays.asList(ruleList));
	}

	public Set<Person> getPersons() {
		return rules.stream().flatMap(Rule::getRelatedPersons).collect(Collectors.toSet());
	}

	public Stream<SalaryRule> getSalaryRules() {
		return rules.stream().filter(SalaryRule.class::isInstance).map(SalaryRule.class::cast);
	}
	
	public Set<Person> getRelatedPersons() {
		return rules.stream().flatMap(Rule::getRelatedPersons).collect(Collectors.toSet());
	}

	public Stream<InterpersonalRule> getEqualityRulesFor(Person person) {
		return rules.stream().filter(InterpersonalRule.class::isInstance).map(InterpersonalRule.class::cast).filter(
				rule -> rule.getRelation() == Relation.EQUAL && rule.getRelatedPersons().anyMatch(person::equals));
	}

}
