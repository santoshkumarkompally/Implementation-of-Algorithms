import java.util.Comparator;

/**
 * 
 */

/**
 * @author Santosh Kompally
 *
 */
public class IndexedHeap implements Comparator<Vertex> {

	Vertex[] arr1;
	int maxLength;
	int size;

	/**
	 * @param length
	 *            create a array of length+1 because first element is not used.
	 */
	IndexedHeap(int length) {

		arr1 = new Vertex[length + 1];
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
		Vertex temp = arr1[position];
		if (compare(arr1[position], arr1[leftChild]) > 0) {

			temp = arr1[leftChild];
		}
		if (compare(temp, arr1[rightChild]) > 0) {

			temp = arr1[rightChild];
		}

		if (compare(temp, arr1[position]) != 0) {

			if (compare(temp, arr1[leftChild]) == 0) {

				arr1[leftChild] = arr1[position];
				arr1[position] = temp;
				arr1[leftChild].setIndex(position);
				arr1[position].setIndex(leftChild);
				perculateDown(leftChild);

			} else {

				arr1[rightChild] = arr1[position];
				arr1[position] = temp;
				arr1[rightChild].setIndex(position);
				arr1[position].setIndex(rightChild);
				perculateDown(rightChild);
			}

		}
	}

	void perculateUp(int position) {

		int temp = position;
		arr1[0] = arr1[position];

		while (compare(arr1[position / 2], arr1[0]) > 0) {

			arr1[position] = arr1[position / 2];
			arr1[position / 2].setIndex(position);
			position = position / 2;
		}
		arr1[position] = arr1[0];
		arr1[temp].setIndex(position);

	}

	/**
	 * @param val
	 *            adding values to the array.
	 */
	void add(Vertex val) {

		if (size < arr1.length) {
			size++;
			arr1[size] = val;
			perculateUp(size);

		} else {

			throw new IndexOutOfBoundsException();
		}
	}

	Vertex remove() {

		arr1[0] = arr1[1];
		arr1[1] = arr1[size];
		size--;
		perculateDown(1);

		return arr1[0];
	}

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
