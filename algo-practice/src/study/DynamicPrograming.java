package study;

public class DynamicPrograming {
	public static int rLCS(String x, String y, int m, int n) {
		if (m == 0 || n == 0) {
			return 0;
		} else if (x.charAt(m) == y.charAt(n)) {
			return rLCS(x, y, m - 1, n - 1) + 1;
		} else {
			return Math.max(rLCS(x, y, m - 1, n), rLCS(x, y, m, n - 1));
		}
	}

	public static int LCS(String x, String y, int m, int n) {
		int[][] c = new int[m + 1][n + 1];

		for (int i = 0; i <= m; ++i) {
			c[i][0] = 0;
		}

		for (int j = 0; j <= n; ++j) {
			c[0][j] = 0;
		}

		for (int i = 1; i <= m; ++i) {
			for (int j = 1; j <= n; ++j) {
				if (x.charAt(i) == y.charAt(j)) {
					c[i][j] = c[i - 1][j - 1] + 1;
				} else {
					c[i][j] = Math.max(c[i - 1][j], c[i][j - 1]);
				}
			}
		}

		return c[m][n];
	}
}
