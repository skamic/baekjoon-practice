package baekjoon;

import java.util.Scanner;

public class Main1000 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int a, b;

		do {
			a = sc.nextInt();
		} while (a <= 0 || a >= 10);
		do {
			b = sc.nextInt();
		} while (b <= 0 || b >= 10);

		System.out.println(a + b);

		sc.close();
	}
}
