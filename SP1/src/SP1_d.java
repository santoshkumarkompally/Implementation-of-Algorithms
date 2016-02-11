
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

//Implementation  of MergeSort without recursion, by using a stack to simulate recursion.

public class SP1_d<T extends Comparable<? super T>> {
	// private variable to hold integers to be sorted
	private ArrayList<T> input_list;

	/**
	 * @param input
	 *            parameterized constructor to initialize input_list
	 */
	public SP1_d(ArrayList<T> input) {
		input_list = new ArrayList<T>();
		for (int i = 0; i < input.size(); i++) {
			input_list.add(input.get(i));
		}
	}

	/**
	 * sorts the input_list of the object with which its called
	 */
	public void sort() {

		split(input_list);

	}

	/**
	 * @param input_list
	 *            takes input_list as parameter and pushes the elements of the
	 *            MergeSortStackRecord object into a Stack until
	 *            MergeSortStackRecord object at the top of the stack has its
	 *            isSorted flag set to 0. At this point, merge() function is
	 *            called to do he sorting and merging
	 */
	private void split(ArrayList<T> input_list) {
		Stack<MergeSortStackRecord> msStack = new Stack<>();
		int left_index = 0, right_index = input_list.size() - 1;
		MergeSortStackRecord sRecord = new MergeSortStackRecord(false, left_index, right_index);
		msStack.push(sRecord);
		while (!msStack.empty()) {
			sRecord = msStack.pop();
			if (sRecord.getIsSorted()) {
				merge(input_list, sRecord.getLeftIndex(), sRecord.getRightIndex());
			} else {
				if (sRecord.getLeftIndex() < sRecord.getRightIndex()) {
					int mid = (sRecord.getLeftIndex() + sRecord.getRightIndex()) / 2;
					msStack.push(new MergeSortStackRecord(true, sRecord.getLeftIndex(), sRecord.getRightIndex()));
					msStack.push(new MergeSortStackRecord(false, sRecord.getLeftIndex(), mid));
					msStack.push(new MergeSortStackRecord(false, mid + 1, sRecord.getRightIndex()));
				}
			}
		}
	}

	/**
	 * @param input_list
	 * @param left_index
	 * @param right_index
	 *            Takes input_list and adds it into two ArrayList i.e. left_list
	 *            and right_list compares corresponding elements of the
	 *            left_list and the right_list and sets them to corresponding
	 *            element in the input_list. wWhen either left_list or
	 *            right_list get over, copy the rest of the elements the other
	 *            list back to the input_list in that same order(since it will
	 *            be sorted already)
	 */
	private void merge(ArrayList<T> input_list, int left_index, int right_index) {

		int mid = (left_index + right_index) / 2;
		ArrayList<T> left_list = new ArrayList<T>();
		for (int i = left_index; i <= mid; i++) {
			left_list.add(input_list.get(i));
		}
		// System.out.println("Left List");
		// System.out.println(left_list.toString());
		ArrayList<T> right_list = new ArrayList<T>();
		for (int i = mid + 1; i <= right_index; i++) {
			right_list.add(input_list.get(i));
		}
		// System.out.println("Right List");
		// System.out.println(right_list.toString());
		int left = 0;
		int right = 0;
		int index = left_index;

		// As long as neither the left_list nor the right_list has
		// been used up, keep taking the smaller of left_list(left_index)
		// or right_list(right_index) and adding it at input_list(index).
		while (left < left_list.size() && right < right_list.size()) {
			if ((left_list.get(left).compareTo(right_list.get(right))) <= 0) {
				input_list.set(index, left_list.get(left));
				left++;
			} else {
				input_list.set(index, right_list.get(right));
				right++;
			}
			index++;
		}

		// If the left_list is exhausted, copy the rest of the elements in the
		// right_list back the input_list (it will be in sorted order)
		if (left >= left_list.size()) {
			for (int i = right; i < right_list.size(); i++) {
				input_list.set(index, right_list.get(i));
				index++;
			}

		}
		// else copy the rest of the elements in the
		// left_list back the input_list (it will be in sorted order)
		else {
			for (int i = left; i < left_list.size(); i++) {
				input_list.set(index, left_list.get(i));
				index++;
			}
		}

	}

	/*
	 * Displays the first 100 elements of the input_list if the size of
	 * input_list >100 else displays all elements of the input_list
	 */
	public void display() {
		if (input_list.size() > 100) {
			System.out.print("[ ");
			for (int i = 0; i < 50; i++)
				System.out.print(input_list.get(i) + ", ");
			System.out.print("]\n");

		} else
			System.out.println(input_list.toString());
	}

	public static void main(String[] args) throws IOException {
		ArrayList<Integer> interger_list = new ArrayList<>();

		Scanner s;
		if (args.length > 0) {
			File f1 = new File(args[0]);
			// s = new Scanner(new File("StatckMergeSortInput.txt"));
			s = new Scanner(f1);
			while (s.hasNextInt()) {
				int a = s.nextInt();
				interger_list.add(a);
			}
			s.close();
		} else {
			System.out.println("Enter the number of elements to sort");
			s = new Scanner(System.in);
			int number_of_elements = s.nextInt();
			for (int i = number_of_elements; i >= 1; i--)
				interger_list.add(i);
		}

		SP1_d<Integer> mg = new SP1_d<>(interger_list);
		// Printing unsorted array
		System.out.println("Unsorted");
		mg.display();
		long startTime = System.currentTimeMillis();
		mg.sort();
		long endTime = System.currentTimeMillis();
		System.out.println("Run time of MergeSort = " + (endTime - startTime) + " milliseconds");

		// Printing sorted array
		System.out.println("Sorted");
		mg.display();
	}

}

/*
 * This class is created to store any data used in the (simulated) recursive
 * structure, along with any state information. here we push left and right
 * indicies on to the stack and isSorted flag is stored as an indicator to stop
 * (simulated) recursion or in this case stop the iterations of sorting and
 * merging
 */
class MergeSortStackRecord {
	private boolean isSorted;
	private int left_index;
	private int right_index;

	public MergeSortStackRecord(boolean sorted, int l_list, int r_list) {
		isSorted = sorted;
		left_index = l_list;
		right_index = r_list;
	}

	public int getLeftIndex() {
		return left_index;
	}

	public int getRightIndex() {
		return right_index;
	}

	public boolean getIsSorted() {
		return isSorted;
	}
}