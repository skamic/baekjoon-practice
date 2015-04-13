package baekjoon;

import java.util.Scanner;

public class Main1008 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		double a, b;

		do {
			a = sc.nextInt();
		} while (a <= 0 || a >= 10);
		do {
			b = sc.nextInt();
		} while (b <= 0 || b >= 10);

		double result = a / b;

		System.out.format("%1.10f", result);

		sc.close();
	}
}
