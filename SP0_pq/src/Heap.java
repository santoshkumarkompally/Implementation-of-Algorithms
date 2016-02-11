import java.util.Comparator;

/**
 * 
 */

/**
 * @author santoshkompally
 *
 */
public class Heap<E> implements Comparator<Edge> {

	Object[] arr1;
	int maxLength;
	int size;

	/**
	 * @param length
	 *            create a array of length+1 because first element is not used.
	 */
	Heap(int length) {

		arr1 = new Object[length + 1];
		size = 0;
	}

	int size() {

		return size;

	}

	void perculateDown(int position) {

		int leftChild = position, rightChild = position;
		if (position * 2 <= size)
			leftChild = position * 2;
		if (position * 2 + 1 <= size)
			rightChild = position * 2 + 1;
		Object temp = (Edge) arr1[position];
		if (compare((Edge) arr1[position], (Edge) arr1[leftChild]) > 0) {

			temp = arr1[leftChild];
		}
		if (compare((Edge) temp, (Edge) arr1[rightChild]) > 0) {

			temp = arr1[rightChild];
		}

		if (compare((Edge) temp, (Edge) arr1[position]) != 0) {

			if (compare((Edge) temp, (Edge) arr1[leftChild]) == 0) {

				arr1[leftChild] = arr1[position];
				arr1[position] = temp;
				perculateDown(leftChild);

			} else {

				arr1[rightChild] = arr1[position];
				arr1[position] = temp;
				perculateDown(rightChild);
			}

		}
	}

	void perculateUp(int position) {

		arr1[0] = arr1[position];

		while (compare((Edge) arr1[position / 2], (Edge) arr1[0]) > 0) {

			arr1[position] = arr1[position / 2];
			position = position / 2;
		}
		arr1[position] = arr1[0];
	}

	/**
	 * @param val
	 *            adding values to the array.
	 */
	void add(E val) {

		if (size < arr1.length) {
			size++;
			arr1[size] = (E) val;

			perculateUp(size);

		} else {

			throw new IndexOutOfBoundsException();
		}
	}

	E remove() {
		arr1[0] = arr1[1];
		arr1[1] = arr1[size];
		size--;
		perculateDown(1);
		return (E) arr1[0];
	}

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
