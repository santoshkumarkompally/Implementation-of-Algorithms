import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author santosh kompally,Roshni,Rohini
 *
 */
public class BigNumberOperations {

	ArrayList<Integer> inputList;

	public BigNumberOperations(ArrayList<Integer> a) {

		this.inputList = a;
	}

	final int base = 10;

	/**
	 * using base as 10.
	 */
	public BigNumberOperations(String input) {

		inputList = new ArrayList<>();

		// adding signed bit to be stored in the beginning of the array list.
		// 1--> negative number.
		// 0--> positive number.
		if (input.charAt(0) == '-') {

			inputList.add(1);
		} else {

			inputList.add(0);
		}

		for (int i = input.length() - 1; i >= 0; i--) {
			if (input.charAt(i) != '-')
				inputList.add((input.charAt(i)) - 48);
			// this operation can be done because the input and output is always
			// in base 10.
		}

	}

	// adding a constructor for directly initializing the array list.

	/**
	 * @return String.
	 * 
	 *         This method converts the array list to string and returns it.
	 */
	public String toString() {

		String s = "";
		if (inputList.get(0) != 0) {
			s += '-';
		}

		for (int i = inputList.size() - 1; i > 0; i--) {

			s += inputList.get(i);
		}

		return s;
	}

	BigNumberOperations add(BigNumberOperations input1, BigNumberOperations input2) {

		// if both are negative of both are positive then we have to perform
		// addition other wise it is a subtraction

		if (input1.inputList.get(0) != input2.inputList.get(0)) {
			ArrayList<Integer> arr = new ArrayList<>();
			BigNumberOperations b = null;
			// here subtraction should be performed.
			if (input1.inputList.get(0) == -1) {

				if (biggerNumber(input1.inputList, input2.inputList) == 1) {
					arr = sub(input1.inputList, input2.inputList);
					arr.add(0, 1);
				} else {
					arr = sub(input2.inputList, input1.inputList);
					arr.add(0, 0);
				}

			} else {
				// this implies the second number is negative.

				if (biggerNumber(input1.inputList, input2.inputList) == 1) {
					arr = sub(input1.inputList, input2.inputList);
					arr.add(0, 0);
				} else {
					arr = sub(input2.inputList, input1.inputList);
					arr.add(0, 1);
				}

			}
			b = new BigNumberOperations(arr);
			return b;
		}

		ArrayList<Integer> output = add1(input1, input2);

		if (input1.inputList.get(0) == 1 && input2.inputList.get(0) == 1) {
			output.add(0, 1);
		} else {

			output.add(0, 0);
		}

		BigNumberOperations b = new BigNumberOperations(output);

		return b;

	}

	BigNumberOperations Subtract(BigNumberOperations input1, BigNumberOperations input2) {

		// int lenInput1 = input1.inputList.size();
		// int lenInput2 = input2.inputList.size();
		ArrayList<Integer> l1 = input1.inputList;
		ArrayList<Integer> l2 = input2.inputList;
		ArrayList<Integer> l = new ArrayList<>();
		// implementing for negative numbers.
		// we have to check for all the conditions if we are getting negative
		// numbers as part of our input.

		// a-b if b is negative and a is not negative then the operation is
		// addition.

		// case 1 1'st input is positive and 2'nd input is negative.
		if (l1.get(0) == 0 && l2.get(0) == 1) {
			ArrayList<Integer> output = add1(input1, input2);
			output.add(0, 0);
			return new BigNumberOperations(output);

		}

		if (l1.get(0) == 1 && l2.get(0) == 0) {

			ArrayList<Integer> output = add1(input1, input2);
			output.add(0, 1);
			return new BigNumberOperations(output);

		}

		if (biggerNumber(l1, l2) == 1) {

			l = sub(l1, l2);
		} else {

			l = sub(l2, l1);
		}

		String s = "";
		if (biggerNumber(l1, l2) == 2) {
			s += '-';
		}

		int counter = 0;
		for (int i = l.size() - 1; i >= 0; i--) {

			if (l.get(i) != 0 && counter == 0) {

				counter = 1;
			}
			if (counter != 0)
				s += l.get(i);
		}
		// if both the inputs are equal and we end up getting total 0 after
		// Subtraction then we have to add 0 and send.
		if (s.length() == 0) {
			s += "0";
		}
		BigNumberOperations b = new BigNumberOperations(s);

		return b;
	}

	ArrayList<Integer> add1(BigNumberOperations input1, BigNumberOperations input2) {

		int lenInput1 = input1.inputList.size();
		int lenInput2 = input2.inputList.size();
		int carry = 0;
		int value;
		ArrayList<Integer> inputList = new ArrayList<>();
		int loopLength = lenInput1 < lenInput2 ? lenInput1 : lenInput2;
		// add till one list is over.
		for (int i = 1; i < loopLength; i++) {
			value = input1.inputList.get(i) + input2.inputList.get(i) + carry;
			inputList.add(value % base);
			carry = value / base;
		}
		// add the remaining of the values to the new list whichever is larger

		if (lenInput1 > lenInput2) {

			for (int j = loopLength; j < lenInput1; j++) {
				value = input1.inputList.get(j) + carry;
				inputList.add(value % base);
				carry = value / base;
			}
		} else {

			for (int j = loopLength; j < lenInput2; j++) {
				value = input2.inputList.get(j) + carry;
				inputList.add(value % base);
				carry = value / base;
			}

		}
		// check for the carry value and then add if carry is not 0 at the last.

		if (carry != 0) {
			inputList.add(carry);

		}

		return inputList;
	}

	ArrayList<Integer> sub(ArrayList<Integer> input1, ArrayList<Integer> input2) {

		int borrow = 0;
		int value1 = 0, value2 = 0, value = 0;
		ArrayList<Integer> l = new ArrayList<>();
		int lenInput1 = input1.size();
		int lenInput2 = input2.size();

		for (int i = 1; i < lenInput2; i++) {
			value1 = input1.get(i);
			value2 = input2.get(i);
			if (value1 >= value2 + borrow) {

				value = value1 - value2 - borrow;
				borrow = 0;
			} else {

				value = value1 + base - value2 - borrow;
				borrow = 1;

			}

			l.add(value);

		}

		for (int j = lenInput2; j < lenInput1; j++) {

			value = input1.get(j) - borrow;

			if (value < 0) {

				value += base;
				borrow = 1;
			} else {

				borrow = 0;
			}
			l.add(value);
		}
		return l;
	}

	BigNumberOperations multiply(BigNumberOperations input1, BigNumberOperations input2) {

		// This is the main ArrayList
		ArrayList<Integer> l = new ArrayList<>();

		// Initialize the final array to 0.
		for (int i = 1; i < input1.inputList.size() + input2.inputList.size(); i++) {

			l.add(0);
		}
		ArrayList<Integer> temp = new ArrayList<>(); // for holding the output
														// of each iteration.
		int carryOuter = 0, outerIndex = 0, carryInner = 0;
		int value = 0, i, j;
		// iterate over both the loops.
		for (int i1 = 1; i1 < input1.inputList.size(); i1++) {
			i = input1.inputList.get(i1);
			for (int j1 = 1; j1 < input2.inputList.size(); j1++) {
				j = input2.inputList.get(j1);
				value = (i * j + carryInner) % base;
				carryInner = (i * j + carryInner) / base;
				temp.add(value);
			}
			if (carryInner != 0) {

				temp.add(carryInner);
			}

			carryOuter = 0;
			int position = outerIndex;
			while (temp.size() > 0) {

				value = l.get(position) + carryOuter + temp.remove(0);
				l.set(position, value % base);
				carryOuter = value / base;
				position++;
			}
			while (carryOuter != 0) {

				value = l.get(position) + carryOuter;
				l.set(position, value % base);
				carryOuter = value / base;
				position++;
			}

			// resetting carryInner and carryOuter.
			carryInner = 0;
			carryOuter = 0;
			outerIndex++;
		}

		// String for returning the value of inputs.
		String s = "";
		// conditions for handling negative numbers as well.
		if (input1.inputList.get(0) != input2.inputList.get(0)) {

			s += '-';
		}

		int counter = 0;
		for (int i2 = l.size() - 1; i2 >= 0; i2--) {

			if (l.get(i2) != 0)
				counter = 1;

			if (counter == 1) {
				s += l.get(i2);
			}
		}
		BigNumberOperations b3 = new BigNumberOperations(s);
		return b3;
	}

	BigNumberOperations power(BigNumberOperations b, long n) {

		BigNumberOperations temp = new BigNumberOperations(b.toString());
		if (n == 0) {
			return new BigNumberOperations("1");
		} else if (n == 1) {

			return b;
		} else {
			temp = power(temp, n / 2);

			if (n % 2 == 0) {
				return multiply(temp, temp);

			} else {

				return multiply(multiply(temp, temp), b);
			}

		}
	}

	int biggerNumber(ArrayList<Integer> l1, ArrayList<Integer> l2) {

		if (l1.size() > l2.size()) {

			return 1;
		} else if (l1.size() < l2.size()) {

			return 2;
		} else {
			for (int i = l1.size() - 1; i > 0; i--) {

				if (l1.get(i) > l2.get(i))
					return 1;
				else if (l2.get(i) > l1.get(i)) {
					return 2;
				}
			}
			return 1;
		}

	}

	BigNumberOperations power(BigNumberOperations n1, BigNumberOperations n2) {
		// long Base = 10;

		BigNumberOperations input1 = new BigNumberOperations(n1.inputList);
		BigNumberOperations input2 = new BigNumberOperations(n2.inputList);

		return pow(input1, input2);

	}

	BigNumberOperations pow(BigNumberOperations input1, BigNumberOperations input2) {

		long a0 = input2.inputList.get(1);
		BigNumberOperations x_s;
		if (input2.inputList.size() == 2) {
			return input1.power(input1, a0);
		} else {
			BigNumberOperations s = shift(input2);
			x_s = power(input1, s);
			return input1.multiply(input1.power(x_s, base), input1.power(input1, a0));
		}

	}

	public BigNumberOperations shift(BigNumberOperations n) {
		n.inputList.remove(1);
		return n;
	}

	public BigNumberOperations divide(BigNumberOperations dividend, BigNumberOperations divisor) {
		BigNumberOperations result;

		if (biggerNumber(dividend.inputList, divisor.inputList) == 2) {

			result = new BigNumberOperations("0");
			return result;
		} else {

			int len1 = dividend.inputList.size();
			int len2 = divisor.inputList.size();
			int index = len2;
			int j = 0;
			BigNumberOperations b;
			ArrayList<Integer> arr = new ArrayList<>();
			ArrayList<Integer> quotient = new ArrayList<>();
			// add a 0 initially to the list of arr.
			arr.add(0);
			// add 0 the quotient as well.
			quotient.add(0);
			for (int i = len2 - 1; i > 0; i--) {

				arr.add(dividend.inputList.get(len1 - i));
			}

			int counter = len2;
			while (counter <= len1) {

				if (biggerNumber(arr, divisor.inputList) == 1) {
					j = 0;
					// b is going as null we have to fix this. Array list is
					// going as null.
					b = new BigNumberOperations(arr);

					QuotientRemainder sol = binarySearch(b, divisor);
					quotient.add(1, sol.in1);
					// everything got divided.
					arr = sol.in2;
					if (arr.size() == 2 && arr.get(1) == 0) {

						arr.remove(1);
					}

					// implies the divident is bigger.

					// here the quotient wwill be generated and then arr will be
					// changed to the remainder part.
					// binary search has to be implemented for getting the
					// quotient and arr.

				} else {

					if (!(arr.size() == 1 && dividend.inputList.get(len1 - index) == 0)) {
						arr.add(1, dividend.inputList.get(len1 - index));
						j++;
					} else {

						if (index != len1)
							quotient.add(1, 0);
					}
					index++;
					counter++;
					if (j > 1) {
						quotient.add(1, 0);
					}
				}

			}
			result = new BigNumberOperations(quotient);
		}

		return result;
	}

	QuotientRemainder binarySearch(BigNumberOperations n1, BigNumberOperations n2) {

		int low = 1;
		int high = base;
		int mid = (high + low) / 2;
		BigNumberOperations middleElement;
		QuotientRemainder sol = null;

		while (true) {
			middleElement = new BigNumberOperations(String.valueOf(mid));
			// multiplication is generating wrong values here I am not aware why
			// is this happening as the normal multiplication seems to work.

			if (biggerNumber(n1.inputList, multiply(n2, new BigNumberOperations(String.valueOf(mid))).inputList) == 1
					&& biggerNumber(n1.inputList,
							multiply(n2, new BigNumberOperations(String.valueOf(mid + 1))).inputList) == 2) {
				// we have to mid value here which is the quotient..

				break;
			}
			BigNumberOperations bb = multiply(n2, middleElement);
			if (biggerNumber(n1.inputList, bb.inputList) == 1) {

				low = mid;
				mid = (low + high) / 2;

			}
			if (biggerNumber(n1.inputList, bb.inputList) == 2) {
				high = mid;
				mid = (low + high) / 2;

			}
			// condition where the mid is lower but the mid+1 is higher.
			// Quotient is mid.

		}
		// quotient is mid;
		// 2 mistakes are happening here.
		// BigNumberOperations remainder = (Subtract(n1, multiply(n2, new
		// BigNumberOperations(String.valueOf(mid)))));
		BigNumberOperations remainder = (Subtract(n1, multiply(n2, middleElement)));
		sol = new QuotientRemainder(mid, remainder.inputList);
		return sol;
	}

	BigNumberOperations modulus(BigNumberOperations n1, BigNumberOperations n2) {
		if (biggerNumber(n1.inputList, n2.inputList) == 2) {
			return n1;
		}
		BigNumberOperations quotient = new BigNumberOperations(divide(n1, n2).inputList);
		BigNumberOperations remainder = Subtract(n1, multiply(quotient, n2));
		return remainder;
	}

	BigNumberOperations squareRoot(BigNumberOperations b1) {

		BigNumberOperations right = b1;
		BigNumberOperations left = new BigNumberOperations("0");
		BigNumberOperations mid = null;
		BigNumberOperations divisor = new BigNumberOperations("2");

		while (biggerNumber(add(left, new BigNumberOperations("1")).inputList, right.inputList) == 2) {

			mid = divide(add(left, right), divisor);
			if (biggerNumber(multiply(mid, mid).inputList, b1.inputList) == 1) {

				right = mid;

			} else if (biggerNumber(multiply(mid, mid).inputList, b1.inputList) == 2) {

				left = mid;

			} else {
				return mid;
			}

		}
		return left;
	}

	BigNumberOperations convertToBase(BigNumberOperations b, int base) {

		ArrayList<Integer> temp = new ArrayList<>();
		temp.add(0, 0);
		BigNumberOperations newBase = new BigNumberOperations(String.valueOf(base));
		BigNumberOperations temp1;
		String s = "";
		while (biggerNumber(b.inputList, newBase.inputList) == 1) {

			temp1 = modulus(b, newBase);
			// convert the value above to number.
			for (int i = temp1.inputList.size() - 1; i > 0; i--) {
				s += temp1.inputList.get(i);
			}
			temp.add(Integer.valueOf(s));
			// re change s to empty after usage.
			s = "";
			b = divide(b, newBase);
		}
		temp.add(b.inputList.get(1));
		return new BigNumberOperations(temp);

	}

	public void printList() {
		System.out.print(base + ": ");
		for (int i = 1; i < inputList.size(); i++) {

			System.out.print(inputList.get(i) + " ");
		}

	}

	BigNumberOperations factorial(BigNumberOperations var) {
		BigNumberOperations temp = new BigNumberOperations(var.inputList);

		while (!(temp.inputList.size() == 2 && temp.inputList.get(1) == 1)) {

			temp = Subtract(temp, new BigNumberOperations("1"));
			var = multiply(var, temp);
		}
		return var;
	}

	BigNumberOperations operations(BigNumberOperations a, BigNumberOperations b, String temp) {
		if (temp.equals("/")) {
			a = divide(a, b);
		} else if (temp.equals("*")) {
			a = multiply(a, b);
		} else if (temp.equals("+")) {
			a = add(a, b);
		} else if (temp.equals("-")) {
			a = Subtract(a, b);
		}
		return a;
	}

	void level3Parser() {

		HashMap<String, BigNumberOperations> h = new HashMap<>();
		ArrayList<Character> arr = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		String result;
		String input;

		HashMap<String, Integer> h1 = new HashMap<>();
		h1.put("(", 5);
		h1.put(")", 5);
		h1.put("[", 5);
		h1.put("]", 5);
		h1.put("!", 4);
		h1.put("~", 4);
		h1.put("^", 3);
		h1.put("*", 2);
		h1.put("/", 2);
		h1.put("%", 2);
		h1.put("+", 1);
		h1.put("-", 1);
		Stack<String> s1 = new Stack<>();
		Stack<String> s2 = new Stack<>();
		while (sc.hasNextLine()) {

			String s = sc.nextLine();

			if (s.equals(""))
				break;

			String tok[] = s.split("=");
			result = tok[0];
			input = tok[1];
			// System.out.println(input);
			String c[] = input.split("");
			int i = 0;
			while (i < c.length) {
				if (h1.containsKey(c[i])) {
					String temp = null;
					if (!(s2.isEmpty())) {
						temp = s2.peek();
						if (h1.get(temp) >= h1.get(c[i])) {
							temp = s2.pop();
							s1.push(temp);
							// String in1 = String.valueOf(s1.pop());
							// String in2 = String.valueOf(s1.pop());
							// BigNumberOperations n1 = new
							// BigNumberOperations(in1);
							// BigNumberOperations n2 = new
							// BigNumberOperations(in2);
							// n1 = operations(n1,n2, temp);
						}
					}
					s2.push(c[i]);
				} else {
					s1.push(c[i]);
				}
				// System.out.println(c[i]);
				i++;
			}
			while (!(s2.isEmpty())) {
				s1.push(s2.pop());
			}
			System.out.println(s1);
		}

	}

	void parseInput(Scanner sc) {

		class store {

			int lineNumber;
			String value;

			public store(int lineNumber, String value) {
				super();
				this.lineNumber = lineNumber;
				this.value = value;
			}

		}

		HashMap<String, BigNumberOperations> h = new HashMap<>();
		ArrayList<store> arr = new ArrayList<>();

		while (sc.hasNextLine()) {

			String s = sc.nextLine();

			if (s.equals(""))
				break;

			String tok[] = s.split(" ");

			arr.add(new store(Integer.valueOf(tok[0]), tok[1]));

		}
		sc.close();

		// we have the input in the form of array list in 2 parts 1 in form of
		// line number other in form of string.
		// a=a+b
		// 01234
		int i = 0;
		while (i < arr.size()) {
			store temp = arr.get(i);
			String token[] = temp.value.split("[-+*/=!^)?~!%]");

			// System.out.println(token[0] + token.length);
			if (token.length == 1 && temp.value.length() == 2) {

				// Square root condition but the value should already exist we
				// will write this case later.
				if (temp.value.charAt(1) == '~') {

					BigNumberOperations temp1 = h.get(String.valueOf(temp.value.charAt(0)));
					h.put(String.valueOf(temp.value.charAt(0)), squareRoot(temp1));
				} else if (temp.value.charAt(1) == '!') {
					BigNumberOperations temp1 = h.get(String.valueOf(temp.value.charAt(0)));
					h.put(String.valueOf(temp.value.charAt(0)), factorial(temp1));

				} else if (temp.value.charAt(1) == ')') {

					h.get(String.valueOf(temp.value.charAt(0))).printList();

				}

			} else if (token.length == 2 && temp.value.length() == 4
					&& (temp.value.charAt(3) == '~' || temp.value.charAt(3) == '!')) {

				if (temp.value.charAt(3) == '~') {

					BigNumberOperations temp1 = h.get(String.valueOf(temp.value.charAt(2)));
					h.put(String.valueOf(temp.value.charAt(0)), squareRoot(temp1));
				} else if (temp.value.charAt(3) == '!') {
					BigNumberOperations temp1 = h.get(String.valueOf(temp.value.charAt(2)));
					h.put(String.valueOf(temp.value.charAt(0)), factorial(temp1));

				}

			} else if (token.length == 2) {

				if (temp.value.charAt(1) == '?') {

					BigNumberOperations temp1 = h.get(String.valueOf(temp.value.charAt(0)));
					if (temp1.inputList.size() == 2 && temp1.inputList.get(1) == 0) {

					} else {

						// we have to go up in the array list or change the
						// index of i and the continue.
						int s = Integer.valueOf(token[1]);

						// iterate through the array list and then find the
						// value.
						int counter = 0;
						for (store temp2 : arr) {

							if (temp2.lineNumber == s) {
								break;

							}
							counter++;
						}
						// change the value of i to go up the loop.
						i = counter;
						continue;
					}

				} else {

					h.put(token[0], new BigNumberOperations(token[1]));
				}
			}

			else if (token.length == 3) {

				BigNumberOperations input1 = h.get(token[1]);
				BigNumberOperations input2 = h.get(token[2]);

				BigNumberOperations output;
				if (temp.value.charAt(3) == '+') {

					output = add(input1, input2);
					h.put(token[0], output);

				} else if (temp.value.charAt(3) == '-') {
					output = Subtract(input1, input2);
					h.put(token[0], output);

				} else if (temp.value.charAt(3) == '*') {
					output = multiply(input1, input2);
					h.put(token[0], output);

				} else if (temp.value.charAt(3) == '/') {
					output = divide(input1, input2);
					h.put(token[0], output);

				} else if (temp.value.charAt(3) == '%') {
					output = modulus(input1, input2);
					h.put(token[0], output);

				} else if (temp.value.charAt(3) == '^') {
					output = power(input1, input2);
					h.put(token[0], output);

				}

			} else {

				System.out.println(h.get(token[0]));
			}

			i++;

		}

	}

	public static void main(String[] args) throws FileNotFoundException {

		String number1 = "999";

		BigNumberOperations b1 = new BigNumberOperations(number1);

		Scanner sc;
		if (args.length == 1) {
			File f = new File(args[0]);
			sc = new Scanner(f);

		} else {

			sc = new Scanner(System.in);
		}

		b1.parseInput(sc);

	}

}
