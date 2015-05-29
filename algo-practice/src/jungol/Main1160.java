package jungol;

import java.util.Scanner;

public class Main1160 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int a = sc.nextInt();
		int b = sc.nextInt();
		int gcd = 0;
		int lcm = 0;

		int max = Math.max(a, b);
		int min = Math.min(a, b);

		while (true) {
			int r = max % min;

			if (r == 0) {
				gcd = min;
				break;
			}

			max = min;
			min = r;
		}

		lcm = a * b / gcd;

		System.out.println(gcd);
		System.out.println(lcm);

		sc.close();
	}
}
