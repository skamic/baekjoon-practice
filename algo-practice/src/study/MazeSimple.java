package study;

import java.util.Scanner;

public class MazeSimple {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int n = 0;
		int[][] maze;
		int row = 1, col = 1;
		int[] order = new int[4];
		int currOrder = 0;
		int distance = 0;

		do {
			n = sc.nextInt();
		} while (n < 3 || n > 10);

		maze = new int[n + 1][n + 1];

		for (row = 1; row <= n; ++row) {
			String line = sc.next();
			
			for (col = 1; col <= n; ++col) {
				maze[row][col] = line.charAt(col-1) - '0';
			}
		}

		for (int i = 0; i < 4; ++i) {
			order[i] = sc.nextInt();
		}

		maze[row = 1][col = 1] = -1;
		while (true) {
			int direction = order[currOrder];

			if (direction == 1) { // 아래
				if (row + 1 <= n && maze[row + 1][col] == 0) {
					row += 1;
					distance++;

					maze[row][col] = -1;
				} else {
					if (row + 1 > n || maze[row + 1][col] == 1) {
						currOrder = (currOrder + 1) % 4;
					} else {
						break;
					}
				}
			} else if (direction == 2) { // 왼쪽
				if (col - 1 >= 1 && maze[row][col - 1] == 0) {
					col -= 1;
					distance++;

					maze[row][col] = -1;
				} else {
					if (col - 1 < 1 || maze[row][col - 1] == 1) {
						currOrder = (currOrder + 1) % 4;
					} else {
						break;
					}
				}
			} else if (direction == 3) { // 위
				if (row - 1 >= 1 && maze[row - 1][col] == 0) {
					row -= 1;
					distance++;

					maze[row][col] = -1;
				} else {
					if (row - 1 < 1 || maze[row - 1][col] == 1) {
						currOrder = (currOrder + 1) % 4;
					} else {
						break;
					}
				}
			} else if (direction == 4) { // 오른쪽
				if (col + 1 <= n && maze[row][col + 1] == 0) {
					col += 1;
					distance++;

					maze[row][col] = -1;
				} else {
					if (col + 1 > n || maze[row][col + 1] == 1) {
						currOrder = (currOrder + 1) % 4;
					} else {
						break;
					}
				}
			}
		}

		System.out.println(distance);

		sc.close();
	}
}
