package study;

import java.util.Scanner;

public class GcdNLcm {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int a, b, aa, bb;
		
		do {
			a = sc.nextInt();
		} while (a < 1 || a > 10000);
		aa = a;
		
		do {
			b = sc.nextInt();
		} while (b < 1 || b > 10000);
		bb = b;
		
		
		int r = 0;
		
		while (true) {
			r = aa % bb;
			
			if (r == 0) {
				break;
			}
			
			aa = bb;
			bb = r;
		}
		
		System.out.println(bb);
		System.out.println(a * b / bb);
		
		sc.close();
	}
}
