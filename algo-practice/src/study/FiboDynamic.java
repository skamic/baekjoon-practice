package study;

public class FiboDynamic {

	public static void main(String[] args) {

	}

	public static int fiboDynamic1(int n) {
		int[] f = new int[n + 1];

		f[1] = 1;
		f[2] = 1;

		for (int i = 3; i <= n; ++i) {
			f[i] = f[i - 1] + f[i - 2];
		}

		return f[n];
	}

	public static int fiboDynamic2(int[] f, int n) {
		if (f[n] > 0) {
			return f[n];
		}

		if (n == 1 || n == 2) {
			f[n] = 1;
		} else {
			f[n] = fiboDynamic2(f, n - 1) + fiboDynamic2(f, n - 2);
		}

		return f[n];
	}
}
