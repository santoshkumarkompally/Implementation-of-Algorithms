import java.util.Comparator;

/**
 * 
 */

/**
 * @author santoshkompally
 *
 */
public class ComparatorQ implements Comparator<Edge> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Edge o1, Edge o2) {
		return o1.weight - o2.weight;

	}

}
