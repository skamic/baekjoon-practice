package study;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrimeNumber {

	private static final int MAX_VAL = 1000000;

	public static void main(String[] args) {

		int[] number = new int[MAX_VAL + 1];

		// init
		for (int i = 2; i <= MAX_VAL; ++i) {
			number[i] = i;
		}

		long startTime = System.currentTimeMillis();
		
		// 에라스토테네스의 체
		for (int i = 2; i <= MAX_VAL; ++i) {
			if (number[i] != 0) {
				for (int j = i + i; j <= MAX_VAL; j += i) {
					number[j] = 0;
				}
			}
		}
		
		long endTime = System.currentTimeMillis() - startTime;

		List<Integer> primeNumber = new ArrayList<Integer>();
		for (int i = 2; i <= MAX_VAL; ++i) {
			if (number[i] != 0) {
				primeNumber.add(i);
			}
		}

		int index = Collections.binarySearch(primeNumber, 5000);
		// -(index)-1
		// index == -5 => 5000이 들어갈 위치는 4
		// primeNumber.get(3), primeNumber.get(4)
	}
}
