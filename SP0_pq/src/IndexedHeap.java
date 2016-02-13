import java.util.Comparator;

/**
 * 
 */

/**
 * @author Santosh Kompally
 *
 */
public class IndexedHeap<E> {

	Object[] arr;
	E arr1[];
	int maxLength;
	int size;
	Comparator<E> c;

	/**
	 * @param length
	 *            create a array of length+1 because first element is not used.
	 */
	IndexedHeap(int length, Comparator<E> c) {

		arr = new Object[length + 1];
		arr1 = (E[]) arr; // this is needed for the program to work.
		size = 0;
		this.c = c;
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
		E temp = arr1[position];
		if (c.compare(arr1[position], arr1[leftChild]) > 0) {

			temp = arr1[leftChild];
		}
		if (c.compare(temp, arr1[rightChild]) > 0) {

			temp = arr1[rightChild];
		}

		if (c.compare(temp, arr1[position]) != 0) {

			if (c.compare(temp, arr1[leftChild]) == 0) {

				arr1[leftChild] = arr1[position];
				arr1[position] = temp;
				((Vertex) arr1[leftChild]).setIndex(position);
				((Vertex) arr1[position]).setIndex(leftChild);
				perculateDown(leftChild);

			} else {

				arr1[rightChild] = arr1[position];
				arr1[position] = temp;
				((Vertex) arr1[rightChild]).setIndex(position);
				((Vertex) arr1[position]).setIndex(rightChild);
				perculateDown(rightChild);
			}

		}
	}

	void perculateUp(int position) {

		int temp = position;
		arr1[0] = arr1[position];

		while (c.compare(arr1[position / 2], arr1[0]) > 0) {

			arr1[position] = arr1[position / 2];
			((Vertex) arr1[position / 2]).setIndex(position);
			position = position / 2;
		}
		arr1[position] = arr1[0];
		((Vertex) arr1[temp]).setIndex(position);

	}

	/**
	 * @param val
	 *            adding values to the array.
	 */
	void add(E val) {

		if (size < arr1.length) {
			size++;
			arr1[size] = val;
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

		return arr1[0];
	}

}
