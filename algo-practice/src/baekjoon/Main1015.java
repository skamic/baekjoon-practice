package baekjoon;

import java.util.Arrays;
import java.util.Scanner;

public class Main1015 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int n;

		do {
			n = sc.nextInt();
		} while (n < 1 || n > 50);

		int[] arrayA = new int[n];
		int[] arrayB;
		int[] arrayP = new int[n];

		for (int i = 0; i < n; ++i) {
			arrayA[i] = sc.nextInt();
		}

		arrayB = arrayA.clone();
		Arrays.sort(arrayB);

		for (int i = 0; i < n; ++i) {
			int index = Arrays.binarySearch(arrayB, arrayA[i]);

			arrayP[i] = index;
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < n; ++i) {
			sb.append(arrayP[i]).append(" ");
		}
		sb.append("\n");

		System.out.println(sb);

		sc.close();
	}
}
