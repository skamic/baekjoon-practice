package study;

import java.util.Scanner;

public class Solution052301 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int n = sc.nextInt();
		char[][] board = new char[n + 1][n + 1];
		int numOfPieces = sc.nextInt();
		int posX = sc.nextInt();
		int posY = sc.nextInt();
		int catchCount = 0;

		board[posX][posY] = 'H';

		for (int i = 0; i < numOfPieces - 1; ++i) {
			board[sc.nextInt()][sc.nextInt()] = 'T';
		}

		if (posX - 2 >= 1 && board[posX - 1][posY] != 'T') {
			if (posY - 1 >= 1 && board[posX - 2][posY - 1] == 'T') {
				catchCount++;
			}
			if (posY + 1 <= n && board[posX - 2][posY + 1] == 'T') {
				catchCount++;
			}
		}
		if (posX + 2 <= n && board[posX + 1][posY] != 'T') {
			if (posY - 1 >= 1 && board[posX + 2][posY - 1] == 'T') {
				catchCount++;
			}
			if (posY + 1 <= n && board[posX + 2][posY + 1] == 'T') {
				catchCount++;
			}
		}
		if (posY - 2 >= 1 && board[posX][posY - 1] != 'T') {
			if (posX - 1 >= 1 && board[posX - 1][posY - 2] == 'T') {
				catchCount++;
			}
			if (posX + 1 <= n && board[posX + 1][posY - 2] == 'T') {
				catchCount++;
			}
		}
		if (posY + 2 >= 1 && board[posX][posY + 1] != 'T') {
			if (posX - 1 >= 1 && board[posX - 1][posY + 2] == 'T') {
				catchCount++;
			}
			if (posX + 1 <= n && board[posX + 1][posY + 2] == 'T') {
				catchCount++;
			}
		}

		System.out.println(catchCount);

		sc.close();
	}
}
