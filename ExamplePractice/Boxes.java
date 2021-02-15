import java.io.*;
import java.util.*;

public class Boxes {
 
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File(".\\435_Boxes_input.txt"));
            int T = sc.nextInt();    
            
            long st = System.currentTimeMillis();
            
            for (int t  = 0; t < T; t++) {
                
                int m = sc.nextInt();
                int n = sc.nextInt();
               
                int[][] grid = new int[m][n];
                
                for (int i = 0; i < m; i++ )
                	for (int j = 0; j < n; j++) 
                		grid[i][j] = sc.nextInt();
                               
                int sum = 0;
                

 // grid[][]���� ������ 1�� ������ �Ʒ��� �ִ� 0�� ������ sum�� �����ϴ� �ڵ� ����
                
                for (int i = 0; i < m; i++ )
                	for (int j = 0; j < n; j++) 
                		if (grid[i][j] == 1) {
                			 for (int k = i+1; k < m; k++ )
                				 if (grid[k][j] == 0) sum++;
                		}

            System.out.println(sum);
            }
            
            long et = System.currentTimeMillis();
      	    System.out.println((et - st)+" ms");

            sc.close();
          
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }        
    }
}

