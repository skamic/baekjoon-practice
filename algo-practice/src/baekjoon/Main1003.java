package baekjoon;

import java.util.Scanner;

public class Main1003 {
	private static int ZERO_CALL_COUNT = 0;
	private static int ONE_CALL_COUNT = 0;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int testcase = sc.nextInt();

		while (testcase-- > 0) {
			int n = sc.nextInt();

			ZERO_CALL_COUNT = 0;
			ONE_CALL_COUNT = 0;

			fibonacci(n);

			System.out.println(ZERO_CALL_COUNT + " " + ONE_CALL_COUNT);
		}

		sc.close();
	}

	private static int fibonacci(int n) {
		if (n == 0) {
			ZERO_CALL_COUNT++;
			return 0;
		} else if (n == 1) {
			ONE_CALL_COUNT++;
			return 1;
		} else {
			return fibonacci(n - 1) + fibonacci(n - 2);
		}
	}
}
