package baekjoon;

import java.util.Scanner;

public class Main1004 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int testcase = sc.nextInt();

		while (testcase-- > 0) {
			int[][] point = new int[2][2];

			for (int i = 0; i < 2; ++i) {
				for (int j = 0; j < 2; ++j) {
					point[i][j] = sc.nextInt();
				}
			}

			int n = sc.nextInt();
			int[][] planet = new int[n][3];

			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < 3; ++j) {
					planet[i][j] = sc.nextInt();
				}
			}

			int planetMoveCount = 0;

			for (int pl = 0; pl < n; ++pl) {
				int xx = planet[pl][0];
				int yy = planet[pl][1];
				int r = planet[pl][2];
				int count = 0;

				for (int po = 0; po < 2; ++po) {
					int x = point[po][0];
					int y = point[po][1];

					double distance = Math.sqrt(Math.pow(x - xx, 2)
							+ Math.pow(y - yy, 2));

					if (r > distance) {
						count++;
					}
				}

				if (count == 1) {
					planetMoveCount++;
				}
			}

			System.out.println(planetMoveCount);
		}

		sc.close();
	}
}
