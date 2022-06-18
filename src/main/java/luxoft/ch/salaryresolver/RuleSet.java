package luxoft.ch.salaryresolver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class RuleSet implements Iterable<Rule> {

	private Set<Rule> rules;

	public RuleSet(Rule... ruleList) {
		rules = new HashSet<>();
		rules.addAll(Arrays.asList(ruleList));
	}

	@Override
	public Iterator<Rule> iterator() {
		return rules.iterator();
	}

	public Set<Person> getPersons() {
		return rules.stream().flatMap(Rule::getRelatedPersons).collect(Collectors.toSet());
	}

}
