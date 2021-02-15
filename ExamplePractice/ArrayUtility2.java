package ch4CheckTime;

public class ArrayUtility2 {
	static int [] concat(int[] s1, int[] s2){
		int[] k = new int[s1.length + s2.length];
		int i = 0;
		for (; i<s1.length; i++)
			k[i] = s1[i];
		for (int j=0; i<k.length; i++, j++)
			k[i] = s2[j];
		
		return k;
	}
	
	static int [] remove(int[] s1, int[] s2){
		int[] k = new int[s1.length + s2.length];
		for (int i=0; i<s1.length; i++)
			k[i] = 0;
		
		return k;
	}
	
	public static void main (String args[])  {
		int e[] = {1, 2, 3, 4};
		int j[] = {9, 6, 3};
		
		int m[] = concat (e, j);
		int n[] = remove (e, j);
		for (int k=0; k<m.length; k++) System.out.println(m[k]);
		for (int k=0; k<n.length; k++) System.out.println(n[k]);
	}
}
