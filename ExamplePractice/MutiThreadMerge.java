import java.lang.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class MutiThreadMerge {
	public static Thread1 tr1;
	public static Thread2 tr2;
	public static Thread3 tr3;
	private static int[] target;

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

	int size;
	int m;
	int s;
	int e;

	private static void MergeSort(int size, int s, int e) {
		if (s < e) {
			int m = (s + e) / 2;
			MergeSort(m - s + 1, s, m);
			MergeSort(e - m, m + 1, e);
			Merge(size, s, m, e);
		}
	}

	static void Merge(int size, int s, int m, int e) {
		int temp[] = null;
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

	public static void main(String[] arg) {
		int target[] = { 3, 5, 7, 9, 1, 2, 4, 6, 8, 0 };

		tr1.start();
		tr2.start();
		tr3.start();

		for (int i = 0; i < 10; i++) {
			System.out.printf("%d ", target[i]);
		}

	}
}
