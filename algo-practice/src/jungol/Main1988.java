package jungol;

import java.util.Scanner;

public class Main1988 {
	private static long[] moveCount;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int pCount = sc.nextInt();
		long k = sc.nextLong();

		moveCount = new long[pCount + 1];
		calculateMoveCount(pCount);

		int from = 1;
		int middle = 2;
		int to = 3;
		int index = pCount;

		while (true) {
			if (k <= moveCount[index - 1]) {
				int tmp = middle;

				middle = to;
				to = tmp;
			} else if (k == moveCount[index - 1] + 1) {
				break;
			} else {
				k -= (moveCount[index - 1] + 1);

				int tmp = middle;

				middle = from;
				from = tmp;
			}

			index--;
		}

		System.out.format("%d : %d -> %d\n", index, from, to);

		sc.close();
	}

	private static void calculateMoveCount(int pCount) {
		for (int i = 1; i <= pCount; ++i) {
			if (i == 1) {
				moveCount[i] = 1;
			} else {
				moveCount[i] = 2 * moveCount[i - 1] + 1;
			}
		}
	}
}
