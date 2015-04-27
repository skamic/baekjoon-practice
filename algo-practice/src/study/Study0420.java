package study;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Study0420 {
	private static int[][] maze;

	public static void main(String[] args) {

		maze = new int[100][100];

		findRoute();

		MyClass[] array = new MyClass[10];
		List<MyClass> list = new ArrayList<MyClass>();
		List<MyClass2> list2 = new ArrayList<MyClass2>();

		Arrays.sort(array);
		Collections.sort(list);
		Collections.sort(list2, new MyClass2());
		Collections.sort(list2, new Comparator<MyClass2>() {
			@Override
			public int compare(MyClass2 arg0, MyClass2 arg1) {
				// TODO Auto-generated method stub
				return 0;
			}
		});

		int[][] trees = new int[6][6]; // 1, 2, 3, 4, 5

		trees[1][2] = 2;

		// 00000111010101010101

		Scanner sc = new Scanner(System.in);

		int input = sc.nextInt(2);

		System.out.println(Integer.toBinaryString(15));

		int result = (int) Math.max(2, 1);

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 10; ++i) {
			sb.append(i).append(" ");
		}

		System.out.println(sb.toString());

		
		String inputString = "I am         a boy.";
		StringTokenizer st = new StringTokenizer(inputString, " ");
		
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
		}
		
		String [] tokenized = inputString.split(" ");
		
		int count = 0;
		inputString.contains("xxxxxx");
		for (int i=0 ; i<inputString.length() ; ++i) {
			String substring = inputString.substring(i);
			
			if (substring.contains("xxxxx")) {
				count++;
			}
		}
		
		System.out.println(count);
		
		int startIndex = inputString.indexOf("xxxxxxx");
		
		sc.close();
	}

	private static void findRoute() {

	}

	static class MyClass implements Comparable<MyClass> {
		int a;
		String b;

		@Override
		public int compareTo(MyClass arg0) {
			// int comp = this.a - arg0.a; // asc
			int comp = arg0.a - this.a; // desc

			if (comp == 0) {
				return this.b.compareTo(arg0.b);
			}

			return comp;
		}
	}

	static class MyClass2 implements Comparator<MyClass2> {

		@Override
		public int compare(MyClass2 arg0, MyClass2 arg1) {
			// TODO Auto-generated method stub
			return 0;
		}

	}
}
