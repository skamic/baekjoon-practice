package study;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Permutation {

	public static void main(String[] args) {
		try {
			System.setIn(new FileInputStream(""));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scanner sc = new Scanner(System.in);

		String a = Integer.toBinaryString(5);

		List<AClass> aList = new ArrayList<AClass>();
		List<BClass> bList = new ArrayList<BClass>();
		AClass[] aArray = new AClass[5];

		Arrays.sort(aArray);

		Collections.sort(aList);
		Collections.sort(bList, new BClass());
		Collections.sort(bList, new Comparator<BClass>() {

			@Override
			public int compare(BClass arg0, BClass arg1) {
				// TODO Auto-generated method stub
				return 0;
			}
		});

		int answer = 0;
		// ////////////////////////////////////////////////

		// ///////////////////////////////////////////////

		System.out.println(answer);
		
		sc.close();
	}

	static class AClass implements Comparable<AClass> {

		@Override
		public int compareTo(AClass arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	static class BClass implements Comparator<BClass> {
		private int a;
		private String b;

		@Override
		public int compare(BClass arg0, BClass arg1) {
			if (arg0.a == arg1.a) {
				return arg0.b.compareTo(arg1.b);
			}

			return arg0.a - arg1.a;
		}

	}
}
