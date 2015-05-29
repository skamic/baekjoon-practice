package jungol;

import java.util.Scanner;

public class Main1161 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int pCount = sc.nextInt();

		move(pCount, 1, 2, 3);

		sc.close();
	}

	private static void move(int pCount, int from, int middle, int to) {
		if (pCount == 1) {
			System.out.format("%d : %d -> %d\n", pCount, from, to);
		} else {
			move(pCount - 1, from, to, middle);
			System.out.format("%d : %d -> %d\n", pCount, from, to);
			move(pCount - 1, middle, from, to);
		}
	}
}
