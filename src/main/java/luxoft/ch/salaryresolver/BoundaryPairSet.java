package luxoft.ch.salaryresolver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BoundaryPairSet implements Iterable<BoundaryPair> {

	private final Set<BoundaryPair> pairs;

	public BoundaryPairSet(BoundaryPair... pairList) {
		pairs = new HashSet<>();
		pairs.addAll(Arrays.asList(pairList));
	}

	@Override
	public Iterator<BoundaryPair> iterator() {
		return pairs.iterator();
	}

}
