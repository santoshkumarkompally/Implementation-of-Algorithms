
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SP1_b {

	/**
	 * @param list1
	 * @param list2
	 * @param output
	 * @param base
	 *            adding 2 lists and output the result in the third list.
	 * 
	 */
	static void add(List<Integer> list1, List<Integer> list2, List<Integer> output, int base) {

		Iterator<Integer> x1 = list1.iterator();
		Iterator<Integer> x2 = list2.iterator();
		Integer carry = 0, val;

		Integer val1 = next(x1);
		Integer val2 = next(x2);

		while (val1 != null && val2 != null) {

			val = (val1 + val2 + carry);
			output.add(val % base);
			carry = val / base;
			val1 = next(x1);
			val2 = next(x2);
		}

		// Either the first or the second list will end.

		// If second list is small
		while (val1 != null) {

			output.add((val1 + carry) % base);
			carry = (carry + val1) / base;
			val1 = next(x1);
		}

		// if the first list is small

		while (val2 != null) {

			output.add((val2 + carry) % base);
			carry = (carry + val2) / base;
			val2 = next(x2);
		}

		// handle carry after all the lists are over.

		if (carry != 0) {

			output.add(carry);
		}

	}

	/**
	 * @param list1
	 * @param list2
	 * @param output
	 * @param base
	 * 
	 *            Subtract 2 lists and then return the output in the third list.
	 *            The assumption here is that the first list >= second list.
	 */
	static void subtract(List<Integer> list1, List<Integer> list2, List<Integer> output, int base) {

		Iterator<Integer> x1 = list1.iterator();
		Iterator<Integer> x2 = list2.iterator();
		Integer carry = 0, val;

		// assuming x is large than y.

		Integer val1 = next(x1);
		Integer val2 = next(x2);

		while (val1 != null && val2 != null) {

			if (val1 + carry >= val2) {

				output.add(val1 - val2 + carry);
				val1 = next(x1);
				val2 = next(x2);
				carry = 0;
			} else {

				val = (val1 + base + carry) - val2;
				output.add(val);
				carry = -1;
				val1 = next(x1);
				val2 = next(x2);
			}

		}

		// since first list is larger than second carry can be zero or more and
		// the second list is over.

		while (val1 != null) {

			if (val1 + carry >= 0) {

				output.add(val1 + carry);
				carry = 0;
				val1 = next(x1);
			} else {

				output.add(val1 + base + carry);
				carry = -1;
				val1 = next(x1);
			}

		}

	}

	static <T> T next(Iterator<T> it) {
		if (it.hasNext())
			return it.next();
		else
			return null;
	}

	public static void main(String[] args) throws FileNotFoundException {

		List<Integer> arr = new LinkedList<>();
		List<Integer> arr1 = new LinkedList<>();
		List<Integer> additionOutput = new LinkedList<>();
		List<Integer> subtractionOutput = new LinkedList<>();
		int base = 10;
		Scanner sc;
		if (args.length > 0) {

			File f1 = new File(args[0]);
			sc = new Scanner(f1);

		} else {

			sc = new Scanner(System.in);
		}

		int l1 = sc.nextInt(); // len of 1 list
		int l2 = sc.nextInt(); // len of 2 list;
		base = sc.nextInt();// base
		int ite1 = 0;
		int ite2 = 0;

		while (ite1 < l1) {
			arr.add(sc.nextInt());
			ite1++;
		}
		while (ite2 < l2) {
			arr1.add(sc.nextInt());
			ite2++;

		}

		// Addition of 2 lists.

		add(arr, arr1, additionOutput, base);
		System.out.println("Addition output");
		for (Integer temp : additionOutput) {

			System.out.print("->" + temp);

		}
		// Subtraction of 2 lists.
		subtract(arr, arr1, subtractionOutput, base);
		System.out.println();
		System.out.println("Subtraction output");
		for (Integer temp : subtractionOutput) {

			System.out.print("->" + temp);

		}
		sc.close();
	}

}
