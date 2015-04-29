package study;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Study0427 {

	public static void main(String[] args) {
//		System.setIn(new FileInputStream("dfdfdfdffdf"));
		Scanner sc = new Scanner(System.in);
		
		sc.nextInt();

		sc.next();
		sc.nextLine();
		
		
		int input = sc.nextInt(2);

		int[] array = new int[100];
		MyClass[] array2 = new MyClass[10];

//		Arrays.sort(array2, new Comparator<MyClass>() {
//			@Override
//			public int compare(MyClass arg0, MyClass arg1) {
//				int tmp = arg0.getA() - arg1.getA();
//
//				if (tmp == 0) {
//					tmp = arg1.getB() - arg0.getB();
//				}
//
//				return tmp;
//			}
//		});

		// List<MyClass> list = Arrays.asList(array2);
		List<MyClass> list = new ArrayList<MyClass>();
		List<Point> list2 = new ArrayList<Point>();

		for (int i = 0; i < array2.length; ++i) {
			list.add(array2[i]);
		}

		for (int i = 0; i < array.length; ++i) {
			array[i] = i;
		}

		System.out.println(Arrays.toString(array));
		
		Collections.sort(list);
		Collections.sort(list2, new Comparator<Point>() {

			@Override
			public int compare(Point arg0, Point arg1) {
				// TODO Auto-generated method stub
				return 0;
			}
		});

		
		
		sc.close();
	}

	static class MyClass implements Comparable<MyClass> {
		private int a;
		private int b;

		public int getA() {
			return a;
		}

		public void setA(int a) {
			this.a = a;
		}

		public int getB() {
			return b;
		}

		public void setB(int b) {
			this.b = b;
		}

		@Override
		public int compareTo(MyClass arg0) {
			int tmp = this.a - arg0.getA();
			
			return tmp;
		}
	}

	static class Point implements Comparator<Point> {
		private int x;
		private int y;
		private boolean visited;
		private Point previous;
		
		@Override
		public int compare(Point arg0, Point arg1) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
}
