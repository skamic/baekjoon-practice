package baekjoon;

import java.util.Scanner;

public class Main1002 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int testcase = sc.nextInt();

		for (int i = 0; i < testcase; ++i) {
			int x1 = sc.nextInt();
			int y1 = sc.nextInt();
			int r1 = sc.nextInt();
			int x2 = sc.nextInt();
			int y2 = sc.nextInt();
			int r2 = sc.nextInt();
			double r3 = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));

			if (r3 > r1 + r2) {
				System.out.println(0);
			} else if (r3 == r1 + r2) {
				System.out.println(1);
			} else {
				if (x1 == x2 && y1 == y2) {
					if (r1 == r2) {
						System.out.println(-1);
					} else {
						System.out.println(0);
					}
				} else {
					if (r3 > Math.abs(r1 - r2)) {
						System.out.println(2);
					} else if (r3 == Math.abs(r1 - r2)) {
						System.out.println(1);
					} else {
						System.out.println(0);
					}
				}
			}
		}

		sc.close();
	}
}
