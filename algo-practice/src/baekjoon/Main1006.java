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

		int[][] enemies = new int[2][section];
		int[][] finish = new int[2][section];

		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < section; ++j) {
				enemies[i][j] = sc.nextInt();
				finish[i][j] = 0;
			}
		}

		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < section; ++j) {
				if (enemies[i][j] < member/2) {
					int max = 0;
					int a = enemies[i][j] + enemies[(i+1)%2][j];
					
					if (a <= member && a > max) {
						max = a;
					}
					
					int b = enemies[i][j] + enemies[i][(j-1+section)%section];
					
					if (b <= member && b > max) {
						max = b;
					}
				}
			}
		}

		sc.close();
	}
}
