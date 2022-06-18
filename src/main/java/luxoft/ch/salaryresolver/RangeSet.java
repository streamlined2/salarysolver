package luxoft.ch.salaryresolver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

public class RangeSet implements Iterable<Range> {

	private final Map<Person, Range> ranges;

	public RangeSet(Set<Person> persons) {
		ranges = new HashMap<>();
		for (var person : persons) {
			ranges.put(person, new Range(person, Boundary.getMinimum(), Boundary.getMaximum()));
		}
	}

	@Override
	public Iterator<Range> iterator() {
		return ranges.values().iterator();
	}

	private Optional<Range> getRange(Person person) {
		return Optional.ofNullable(ranges.get(person));
	}

	public void upMinimum(SalaryRule rule) {
		var person = rule.getPerson();
		getRange(person).ifPresent(range -> {
			range.upMinimum(rule.getSalary(), rule.getRelation());
			ranges.put(person, range);
		});
	}

	public void downMaximum(SalaryRule rule) {
		var person = rule.getPerson();
		getRange(person).ifPresent(range -> {
			range.downMaximum(rule.getSalary(), rule.getRelation());
			ranges.put(person, range);
		});
	}

	public void applySalaryRule(SalaryRule rule) {
		if (rule.getRelation().affectsMinimum()) {
			upMinimum(rule);
		}
		if (rule.getRelation().affectsMaximum()) {
			downMaximum(rule);
		}
	}

	@Override
	public String toString() {
		StringJoiner join = new StringJoiner("\n");
		ranges.forEach((person, range) -> join.add(range.toString()));
		return join.toString();
	}

}
