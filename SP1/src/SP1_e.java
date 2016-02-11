
import java.util.Scanner;

public class SP1_e<T extends Comparable<? super T>> {
	public class Entry<T> {
		T element;
		Entry<T> next;

		Entry(T x, Entry<T> nxt) {
			element = x;
			next = nxt;
		}
	}

	Entry<T> header, tail;
	int size;

	SP1_e() {
		header = new Entry<>(null, null);
		tail = null;
		size = 0;
	}

	void add(T x) {
		if (tail == null) {
			header.next = new Entry<>(x, header.next);
			tail = header.next;
		} else {
			tail.next = new Entry<>(x, tail.next);
			tail = tail.next;
		}
		size++;
	}

	/**
	 * @param k
	 *            k is used to rearrange elements of a singly linked list by
	 *            chaining together elements that are k apart.
	 */
	void multiUnzip(int k) {
		if (size < k) { // Too few elements. No change.
			return;
		}
		/**
		 * There are k chains each starting from head[i] where i=0 to k-1
		 * head[i]- stores the first element of the chains of elements tail[i] -
		 * stores the tail of the each of the chain of elements, initially
		 * tail[i]=head[i]
		 */

		// Suppressing Warnings because the program can compile without extra
		// arguments on command line.
		@SuppressWarnings("unchecked")
		Entry<T>[] tail = new Entry[k];
		// Suppressing Warnings because the program can compile without extra
		// arguments on command line.
		@SuppressWarnings("unchecked")
		Entry<T>[] head = new Entry[k];
		Entry<T> c = header.next;
		int state = 0;

		for (int i = 0; i < k; i++) {
			tail[i] = c;
			head[i] = c;
			c = c.next;
		}

		// state indicates the state of the finite state machine
		// state goes from 0 to k-1.
		// state = i indicates that the current element is added after tail[i]
		// (i=0,1,2...k-1).
		// Invariant: tail[i] is the tail of ith chain of elements where i=state
		// c is current element to be processed.

		while (c != null) {
			if (state < k - 1) {
				tail[state].next = c;
				tail[state] = c;
				c = c.next;
				state = state + 1;
			} else if (state == k - 1) {
				tail[state].next = c;
				tail[state] = c;
				c = c.next;
				state = state - k + 1;
			}
		}
		tail[k - 1].next = null; // setting the pointer of the tail of the last
									// sequence to null
		for (int i = 0; i < k - 1; i++) {
			tail[i].next = head[i + 1]; // connecting the tail of a chain with
										// the head of subsequent chain
										// iteratively
		}

	}

	void printList(String s) {
		System.out.print(s + " ");
		Entry<T> x = header.next;
		while (x != null) {
			System.out.print(x.element + " ");
			x = x.next;
		}
		System.out.println();
	}

	public static void main(String[] args) {

		// default length is 10 and the k value is 2.
		int n = 10, k = 2;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
			k = Integer.parseInt(args[1]);
		} else {
			Scanner sc = new Scanner(System.in);
			// Please enter the number of elements
			// you want to add in the list by entering the number in the
			// terminal (optional)
			n = sc.nextInt();
			k = sc.nextInt();
			sc.close();
		}

		SP1_e<Integer> L1 = new SP1_e<>();
		for (int i = 0; i <= n; i++) {
			L1.add(new Integer(i));
		}
		L1.printList("Input List: ");
		L1.multiUnzip(k);
		L1.printList("Output List: ");
	}
}