package luxoft.ch.salaryresolver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

	public Optional<Range> getRange(Person person) {
		return Optional.ofNullable(ranges.get(person));
	}

	public void setRange(Range range) {
		ranges.put(range.getPerson(), range);
	}

}
