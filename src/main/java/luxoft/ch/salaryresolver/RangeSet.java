package luxoft.ch.salaryresolver;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import luxoft.ch.salaryresolver.Boundary.Kind;

public class RangeSet implements Iterable<Range> {

	private static final Comparator<Range> BY_LENGTH = Comparator.comparing(Range::getLength);

	private final Map<Person, Range> ranges;

	public RangeSet(Set<Person> persons) {
		ranges = new HashMap<>();
		for (var person : persons) {
			ranges.put(person, new Range(person));
		}
	}

	public boolean isValid() {
		for (var range : this) {
			if (!range.isValid()) {
				return false;
			}
		}
		return true;
	}

	public Optional<Range> getRange(Person person) {
		return Optional.ofNullable(ranges.get(person));
	}

	public void applySalaryRule(SalaryRule rule) {
		if (rule.getRelation().affectsMinimum()) {
			upMinimum(rule);
		}
		if (rule.getRelation().affectsMaximum()) {
			downMaximum(rule);
		}
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

	public void applyInterpersonalEqualsRule(Person person, Boundary minimum, Boundary maximum) {
		setMinimum(person, minimum);
		setMaximum(person, maximum);
	}

	public void setMinimum(Person person, Boundary minimum) {
		getRange(person).ifPresent(range -> {
			range.setMinimum(minimum);
			ranges.put(person, range);
		});
	}

	public void setMaximum(Person person, Boundary maximum) {
		getRange(person).ifPresent(range -> {
			range.setMaximum(maximum);
			ranges.put(person, range);
		});
	}

	public void applyInterpersonalCompareRule(InterpersonalRule rule, Range sampleRange) {
		getRange(rule.getAnotherPerson()).ifPresent(range -> {
			upMinimum(range, rule, newMinimum(sampleRange.getMinimum(), rule.getRelation()));
			downMaximum(range, rule, newMaximum(sampleRange.getMaximum(), rule.getRelation()));
		});
	}

	private static Boundary newMinimum(Boundary minimum, Relation relation) {
		if (relation.isStrict()) {
			return minimum.getGreaterBy(1);
		} else {
			return minimum;
		}
	}

	private static Boundary newMaximum(Boundary maximum, Relation relation) {
		if (relation.isStrict()) {
			return maximum.getGreaterBy(1);
		} else {
			return maximum;
		}
	}

	public void upMinimum(Range range, InterpersonalRule rule, Boundary minimum) {
		range.upMinimum(minimum.value(), rule.getRelation());
		ranges.put(range.getPerson(), range);
	}

	public void downMaximum(Range range, InterpersonalRule rule, Boundary maximum) {
		range.downMaximum(maximum.value(), rule.getRelation());
		ranges.put(range.getPerson(), range);
	}

	public Queue<Range> getRangesByLength() {
		return ranges.values().stream().collect(Collectors.toCollection(() -> new PriorityQueue<>(BY_LENGTH)));
	}

	@Override
	public String toString() {
		StringJoiner join = new StringJoiner("\n");
		ranges.forEach((person, range) -> join.add(range.toString()));
		return join.toString();
	}

	@Override
	public Iterator<Range> iterator() {
		return ranges.values().iterator();
	}

}
