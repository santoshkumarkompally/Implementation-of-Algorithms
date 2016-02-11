import java.util.NoSuchElementException;

public class DLinkedList<E> {

	private class node {

		private E element;
		private node next;
		private node previous;

		node(E element, node next, node previous) {

			this.element = element;
			this.next = next;
			this.previous = previous;
		}

	}

	private node head;
	private node tail;
	private int size;

	public DLinkedList() {

		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	void insertFirst(E element) {

		node n = new node(element, null, null);

		if (head == null) {

			head = n;
			tail = n;
		} else {

			head.previous = n;
			n.next = head;
			head = n;

		}
		size++;
	}

	void insertAtLast(E element) {

		node n = new node(element, null, null);
		if (tail == null) {

			head = n;
			tail = n;

		} else {

			n.previous = tail;
			tail.next = n;
			tail = n;
		}
		size++;
	}

	public E removeFirst() {

		if (size == 0)
			throw new NoSuchElementException();

		if (size == 1) {
			size--;
			node temp = head;
			head = null;
			tail = null;
			return temp.element;

		} else {
			size--;
			node temp = head;
			head = head.next;
			head.previous = null;
			return temp.element;
		}

	}

	public E removeFromLast() {

		if (size == 0)
			throw new NoSuchElementException();

		node temp;
		if (size == 1) {

			temp = head;
			head = null;
			tail = null;

		} else {
			temp = tail;
			tail = tail.previous;
			tail.next = null;
		}
		size--;
		return temp.element;
	}

}
