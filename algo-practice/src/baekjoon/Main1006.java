package baekjoon;

import java.util.Scanner;

public class Main1006 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int testCase = sc.nextInt();
		int section, member, platoon;

		do {
			section = sc.nextInt();
		} while (section < 1 || section > 10000);
		platoon = section * 2;

		do {
			member = sc.nextInt();
		} while (member < 1 || member > 10000);

		int[] enemies = new int[section * 2];
		int[][] finish = new int[2][section];

		for (int i = 0; i < 2 * section; ++i) {
			enemies[i] = sc.nextInt();
		}

		for (int i = 0; i < 2 * section; ++i) {
			if (enemies[i] < member / 2) {
				int max = 0;
				int a = enemies[i] + enemies[i];

				if (a <= member && a > max) {
					max = a;
				}

				int b = enemies[i] + enemies[i];

				if (b <= member && b > max) {
					max = b;
				}
			}
		}

		sc.close();
	}
}
