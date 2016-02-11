
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SP1_a {

	/**
	 * @param output
	 * @param list1
	 * @param list2
	 * 
	 *            Takes 2 lists as input and returns the intersection of 2
	 *            lists.
	 */

	static <T extends Comparable<T>> void interectionOfTwoLists(List<T> output, List<T> list1, List<T> list2) {

		Iterator<T> i1 = list1.iterator();
		Iterator<T> i2 = list2.iterator();

		T val1 = next(i1);
		T val2 = next(i2);
		while (val1 != null && val2 != null) {

			if (val1.compareTo(val2) > 0) {

				val2 = next(i2);

			} else if (val1.compareTo(val2) < 0) {

				val1 = next(i1);
			} else {
				output.add(val1);
				val1 = next(i1);
				val2 = next(i2);

			}

		}

	}

	/**
	 * @param output
	 * @param list1
	 * @param list2
	 * 
	 *            Returns the union of 2 lists.
	 */
	static <T extends Comparable<T>> void unionOfTwoLists(List<T> output, List<T> list1, List<T> list2) {

		Iterator<T> i1 = list1.iterator();
		Iterator<T> i2 = list2.iterator();

		T val1 = next(i1);
		T val2 = next(i2);
		while (val1 != null && val2 != null) {

			if (val1.compareTo(val2) > 0) {

				output.add(val2);
				val2 = next(i2);

			} else if (val1.compareTo(val2) < 0) {
				output.add(val1);
				val1 = next(i1);
			} else {
				output.add(val1);
				val1 = next(i1);
				val2 = next(i2);

			}

		}
		// if list 1 is remaining iterate till list 1 is over and add the values
		// to the output.
		while (val1 != null) {
			output.add(val1);
			val1 = next(i1);

		}

		// if list 1 is remaining iterate till list 1 is over and add the values
		// to the output.
		while (val2 != null) {
			output.add(val2);
			val2 = next(i2);

		}

	}

	/**
	 * @param output
	 * @param list1
	 * @param list2
	 * 
	 *            Generates difference of 2 sets l1-l2 implies (Elements in l1
	 *            which are not there in l2.)
	 */
	static <T extends Comparable<T>> void differenceOfTwoLists(List<T> output, List<T> list1, List<T> list2) {

		Iterator<T> i1 = list1.iterator();
		Iterator<T> i2 = list2.iterator();

		T val1 = next(i1);
		T val2 = next(i2);
		while (val1 != null && val2 != null) {

			if (val1.compareTo(val2) > 0) {
				val2 = next(i2);

			} else if (val1.compareTo(val2) < 0) {
				output.add(val1);
				val1 = next(i1);
			} else {
				val1 = next(i1);
				val2 = next(i2);

			}

		}

		while (val1 != null) {
			output.add(val1);
			val1 = next(i1);

		}

	}

	static <T> T next(Iterator<T> it) {
		if (it.hasNext())
			return it.next();
		else
			return null;
	}

	public static void main(String[] args) throws FileNotFoundException {

		Scanner sc;
		if (args.length > 0) {

			File f1 = new File(args[0]);
			sc = new Scanner(f1);

		} else {

			sc = new Scanner(System.in);
		}

		ArrayList<Integer> a1 = new ArrayList<>();

		LinkedList<Integer> a2 = new LinkedList<>();

		int l1 = sc.nextInt();
		int l2 = sc.nextInt();

		int ite1 = 0;
		int ite2 = 0;

		while (ite1 < l1) {
			a1.add(sc.nextInt());
			ite1++;
		}
		while (ite2 < l2) {
			a2.add(sc.nextInt());
			ite2++;

		}
		LinkedList<Integer> output = new LinkedList<>();
		LinkedList<Integer> output1 = new LinkedList<>();
		LinkedList<Integer> output2 = new LinkedList<>();

		SP1_a.unionOfTwoLists(output, a1, a2);
		SP1_a.interectionOfTwoLists(output1, a1, a2);
		SP1_a.differenceOfTwoLists(output2, a1, a2);

		System.out.println("union of 2 lists is:");
		for (int i = 0; i < output.size(); i++) {

			System.out.println(output.get(i) + "  ");
		}

		System.out.println("intersection of 2 lists is:");
		for (int i = 0; i < output1.size(); i++) {

			System.out.println(output1.get(i) + "  ");
		}

		System.out.println("difference of 2 lists is:");
		for (int i = 0; i < output2.size(); i++) {

			System.out.println(output2.get(i) + "  ");
		}

		sc.close();
	}

}
