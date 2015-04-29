package jungol;

import java.util.Scanner;

public class Main2299 {
	private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static String invalidStatus;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int numOfPeople = sc.nextInt();
		int line = sc.nextInt();
		String[] lineStatus = new String[line];
		String[] orders = new String[line + 1];
		orders[line] = sc.next();

		makeInvalidStatus(numOfPeople);
		orders[0] = alphabet.substring(0, numOfPeople);

		for (int i = 0; i < line; ++i) {
			lineStatus[i] = sc.next();
		}

		int index = 0;

		for (int i = 0; i < line; ++i) {
			String currStatus = lineStatus[i];

			if (currStatus.startsWith("?")) {
				index = i;
				break;
			}

			char[] nextOrder = new char[numOfPeople];
			orders[i].getChars(0, numOfPeople, nextOrder, 0);

			for (int j = 0; j < numOfPeople - 1; ++j) {
				if (currStatus.charAt(j) == '-') {
					char temp = nextOrder[j];
					nextOrder[j] = nextOrder[j + 1];
					nextOrder[j + 1] = temp;
				}
			}

			orders[i + 1] = new String(nextOrder);
		}

		for (int i = line; i > index + 1; --i) {
			String prevStatus = lineStatus[i - 1];

			char[] prevOrder = new char[numOfPeople];
			orders[i].getChars(0, numOfPeople, prevOrder, 0);

			for (int j = 0; j < numOfPeople - 1; ++j) {
				if (prevStatus.charAt(j) == '-') {
					char temp = prevOrder[j];
					prevOrder[j] = prevOrder[j + 1];
					prevOrder[j + 1] = temp;
				}
			}

			orders[i - 1] = new String(prevOrder);
		}

		char[] resultOrder = new char[numOfPeople - 1];
		boolean invalid = false;
		String a = orders[index];
		String b = orders[index + 1];

		for (int i = 0; i < numOfPeople - 1; ++i) {
			if (Math.abs(i - b.indexOf(a.substring(i, i + 1))) > 1) {
				invalid = true;
				break;
			}

			if (a.charAt(i) == b.charAt(i + 1)
					&& a.charAt(i + 1) == b.charAt(i)) {
				resultOrder[i] = '-';

				if (i + 1 < numOfPeople - 1) {
					resultOrder[++i] = '*';
				}
			} else {
				resultOrder[i] = '*';
			}
		}

		System.out.println(invalid ? invalidStatus : new String(resultOrder));

		sc.close();
	}

	private static void makeInvalidStatus(int numOfPeople) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < numOfPeople - 1; ++i) {
			sb.append("x");
		}

		invalidStatus = sb.toString();
	}
}
