
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class SP1_f<T extends Comparable<? super T>> {
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

	SP1_f() {
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

	void printList(String s) {
		System.out.print(s + " ");
		Entry<T> x = header.next;
		while (x != null) {
			System.out.print(x.element + " ");
			x = x.next;
		}
		System.out.println();
	}

	void reverseOrder_Recursive(Entry<T> current, Entry<T> last) {
		if (current.next != null) {
			Entry<T> temp = current.next;
			current.next = last;
			last = current;
			current = temp;
			reverseOrder_Recursive(current, last);
		} else {
			current.next = last;
			header.next = current;
		}

	}

	void reverseOrder_recursionCall() {
		if (header.next.next != null) {
			Entry<T> current = header.next.next;
			Entry<T> last = header.next;
			last.next = null;
			reverseOrder_Recursive(current, last);
		}
	}

	void reverseOrder_nonRecursive() {
		if (header.next.next != null) {
			Entry<T> current = header.next.next; // current points to the second
													// element of the list
			Entry<T> last = header.next; // last points to the first element
			last.next = null;
			Entry<T> temp;
			// current.next is the element to be processed in each while call
			// and current and last are changed accordingly
			while (current.next != null) {
				temp = current.next;
				current.next = last;
				last = current;
				current = temp;
			}
			current.next = last;
			header.next = current;
		}
	}

	/*
	 * param head is referencing to the the first element of the list. For each
	 * recursive call, the next element of the head is passed to the call.
	 */
	void printReverseRecursive(Entry<T> head) {
		if (head == null)
			return;

		// print list of head node
		printReverseRecursive(head.next);

		// After everything else is printed
		System.out.print(head.element + " ");
	}

	void printReverseNonRecursive() {
		System.out.print("\nPrinting in reverse order using non-recursive call: ");
		Deque<T> queue = new LinkedList<>();
		Entry<T> current = header.next;
		while (current != null) {
			queue.push(current.element);// pushing the elements into the
										// double-ended queue
			current = current.next;
		}

		while (!queue.isEmpty()) {
			System.out.print(queue.pop() + " ");// popping and printing the
												// elements from the queue in
												// FIFO order
		}
	}

	public static void main(String[] args) {
		int n = 10;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		} else {
			Scanner sc = new Scanner(System.in);
			n = sc.nextInt();
			// Please enter the number of elements you want to add in the list
			// by entering the number in the terminal (optional)
			sc.close();
		}

		SP1_f<Integer> L1 = new SP1_f<>();
		for (int i = 0; i <= n; i++) {
			L1.add(new Integer(i));
		}
		L1.printList("Input List: ");

		L1.reverseOrder_recursionCall();
		// reversing order using recursion
		L1.printList("Output using recursion to reverse the order: ");

		L1.reverseOrder_nonRecursive();
		// reversing order using non-recursive call
		L1.printList("Output using non-recursion to reverse the order: ");

		System.out.print("Printing in reverse order using recursive call: ");
		L1.printReverseRecursive(L1.header.next);
		// printing in reverse order using recursive call

		L1.printReverseNonRecursive();
		// printing in reverse order using non-recursive call
	}
}