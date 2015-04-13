package baekjoon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main1005 {
	private static int[] BUILD_TIME;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int testcase = sc.nextInt();

		while (testcase-- > 0) {
			int numOfBuilding = sc.nextInt();
			int numOfRule = sc.nextInt();
			Building[] building = new Building[numOfBuilding + 1];

			BUILD_TIME = new int[numOfBuilding + 1];
			for (int i = 1; i <= numOfBuilding; ++i) {
				building[i] = new Building(i, sc.nextInt());
				BUILD_TIME[i] = -1;
			}

			for (int i = 0; i < numOfRule; ++i) {
				int prev = sc.nextInt();
				int next = sc.nextInt();

				building[next].addRule(building[prev]);
			}

			int bNum = sc.nextInt();

			int buildTime = calculateBuildTime(building, bNum);

			System.out.println(buildTime);
		}

		sc.close();
	}

	private static int calculateBuildTime(Building[] building, int bNum) {
		Building b = building[bNum];

		if (BUILD_TIME[bNum] == -1) {
			if (b.getRules().size() == 0) {
				BUILD_TIME[bNum] = b.getBuildTime();
			} else {
				int maxTime = 0;

				for (Building pb : b.getRules()) {
					int time = calculateBuildTime(building, pb.getNumber());

					if (time > maxTime) {
						maxTime = time;
					}
				}

				BUILD_TIME[bNum] = b.getBuildTime() + maxTime;
			}
		}

		return BUILD_TIME[bNum];
	}

	static class Building {
		private int number;
		private int buildTime;
		private List<Building> rules = new ArrayList<Building>();

		public Building() {
			super();
		}

		public Building(int number, int buildTime) {
			super();
			this.number = number;
			this.buildTime = buildTime;
		}

		public int getBuildTime() {
			return buildTime;
		}

		public void setBuildTime(int buildTime) {
			this.buildTime = buildTime;
		}

		public List<Building> getRules() {
			return rules;
		}

		public void setRules(List<Building> rules) {
			this.rules = rules;
		}

		public void addRule(Building b) {
			this.rules.add(b);
		}

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public String printRule() {
			StringBuffer sb = new StringBuffer();

			for (Building b : rules) {
				sb.append(b.getNumber()).append(" ");
			}

			return sb.toString();
		}

		@Override
		public String toString() {
			return "Building #" + number + " [" + buildTime + "], rules ["
					+ printRule() + "]";
		}
	}
}
