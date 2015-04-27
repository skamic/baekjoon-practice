package study;

import java.util.Scanner;
import java.util.Stack;

public class CalcParentheses {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		String input = sc.nextLine();
		int length = input.length();

		Stack<Integer> stack = new Stack<Integer>();
		int result = 0;
		boolean invalid = false;

		for (int i = 0; i < length; ++i) {
			char c = input.charAt(i);

			if (c == '(') {
				stack.push(-1);
			} else if (c == '[') {
				stack.push(-2);
			} else if (c == ')' || c == ']') {
				int prev = 0;
				int value = 0;

				while (!stack.isEmpty()) {
					prev = stack.pop();

					if (prev > 0) {
						value += prev;
					} else {
						if (value == 0) {
							value = 1;
						}
						break;
					}
				}

				if ((prev == 0) || (prev == -1 && c == ']')
						|| (prev == -2 && c == ')')) {
					invalid = true;
					break;
				}

				if (prev == -1 && c == ')') {
					value = 2 * value;
					stack.push(value);
				} else if (prev == -2 && c == ']') {
					value = 3 * value;
					stack.push(value);
				}
			}
		}

		if (!invalid) {
			while (!stack.isEmpty()) {
				int prev = stack.pop();

				if (prev < 0) {
					result = 0;
					break;
				} else {
					result += prev;
				}
			}
		}

		System.out.println(result);

		sc.close();
	}
}
