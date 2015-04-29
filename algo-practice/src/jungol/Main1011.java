package jungol;

import java.util.Scanner;

public class Main1011 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int numOfStudents = sc.nextInt();
		int maxNumber = sc.nextInt();
		int personId = sc.nextInt();

		int[] numbers = new int[maxNumber + 1];
		int[] clapCount = new int[numOfStudents];

		for (int i = 1; i <= maxNumber; ++i) {
			numbers[i] = i;
		}

		for (int n = 2; n <= maxNumber; ++n) {
			if (numbers[n] == 0) {
				continue;
			}

			clapCount[(n - 1) % numOfStudents]++;

			for (int j = n + n; j <= maxNumber; j += n) {
				numbers[j] = 0;
			}
		}

		System.out.println(clapCount[personId]);

		sc.close();
	}
}
