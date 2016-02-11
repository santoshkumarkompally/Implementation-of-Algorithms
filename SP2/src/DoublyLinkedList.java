
// Source taken from the Internet and modified accordingly
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> {

	private Node head;
	private Node tail;
	private int size;

	public DoublyLinkedList() {
		size = 0;
		head = null;
		tail = null;
	}

	private class Node {
		E element;
		Node next;
		Node prev;

		public Node(E element, Node next, Node prev) {
			this.element = element;
			this.next = next;
			this.prev = prev;
		}
	}

	/**
	 * returns the size of the linked list
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * return whether the list is empty or not
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * adds element at the starting of the linked list
	 * 
	 * @param element
	 */
	public void addFirst(E element) {
		Node tmp = new Node(element, head, null);
		if (head != null) {
			head.prev = tmp;
		}
		head = tmp;
		if (tail == null) {
			tail = tmp;
		}
		size++;
	}

	/**
	 * adds element at the end of the linked list
	 * 
	 * @param element
	 */
	public void addLast(E element) {

		if (tail == null) {

			System.out.println("both values inserted here are null" + tail.element);

		}
		Node tmp = new Node(element, null, tail);
		if (tail != null) {
			tail.next = tmp;
		}
		tail = tmp;
		if (head == null) {
			head = tmp;
		}
		size++;
	}

	/**
	 * this method removes element from the start of the linked list
	 * 
	 * @return
	 */
	public E removeFirst() {
		if (size == 0)
			throw new NoSuchElementException();
		Node tmp = head;
		System.out.println(tmp.next);
		head = head.next;
		if (head != null)
			head.prev = null;
		size--;
		return tmp.element;
	}

	/**
	 * this method removes element from the end of the linked list
	 * 
	 * @return
	 */
	public E removeLast() {
		if (size == 0)
			throw new NoSuchElementException();
		Node tmp = tail;
		tail = tail.prev;
		tail.next = null;
		size--;
		return tmp.element;
	}

}