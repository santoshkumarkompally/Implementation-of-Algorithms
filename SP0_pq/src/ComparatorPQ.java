import java.util.Comparator;

/**
 * 
 */

/**
 * @author santoshkompally
 *
 */
public class ComparatorPQ implements Comparator<Vertex> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Vertex o1, Vertex o2) {
		return o1.weight - o2.weight;

	}

}
