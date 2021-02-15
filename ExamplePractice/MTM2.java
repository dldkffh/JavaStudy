import java.lang.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class MTM2 {
	public static Thread1 tr1;
	public static Thread2 tr2;
	public static Thread3 tr3;

	class Thread1 extends Thread {
		public void run() {
			MergeSort(m - s + 1, s, m);
		}
	}

	class Thread2 extends Thread {
		public void run() {
			MergeSort(e - m, m + 1, e);
		}
	}

	class Thread3 extends Thread {
		@SuppressWarnings("deprecation")
		public void run() {
			try {
				tr1.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				tr2.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			tr1.stop();
			tr2.stop();
			Merge(size, s, m, e);
		}
	}

	private static int size;
	private static int m;
	private static int s;
	private static int e;
	private int[] target;
	
	void Merge(int size, int s, int m, int e) {
		for (int i = s; i <= e; i++)
			temp[i] = target[i];

		int tempLeft = s;
		int tempRight = m + 1;
		int current = s;

		while (tempLeft <= m && tempRight <= e) {
			if (temp[tempLeft] <= temp[tempRight]) {
				target[current] = temp[tempLeft];
				tempLeft++;
			} else {
				target[current] = temp[tempRight];
				tempRight++;
			}
			current++;
		}
		int remaing = m - tempLeft;
		for (int i = 0; i <= remaing; i++) {
			target[current + i] = temp[tempLeft + i];
		}
	}

	private static void MergeSort(int i, int j, int k) {
		if (s < e) {
			int m = (s + e) / 2;
			tr1.start();
			tr2.start();
			tr3.start();
			try {
				tr3.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				for (int i1 = 0; i1 < 10; i1++) {
					System.out.printf("%d ", target[i1]);
				}
			}
		}
	}

	int temp[];

	public static void main(String[] arg) {
		int target[] = { 3, 5, 7, 9, 1, 2, 4, 6, 8, 0 };
		MergeSort(10, 0, 9);
		for (int i = 0; i < 10; i++) {
			System.out.printf("%d ", target[i]);
		}
	}
}
