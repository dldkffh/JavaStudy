import java.io.*;
import java.util.*;

public class Permutation_Graphs {

	public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File(".\\298_Permutation_input.txt"));
            int T = sc.nextInt();    
            
            long st = System.currentTimeMillis();
            
            for (int t  = 0; t < T; t++) {
                
                int m = sc.nextInt();
     
                int[][] grid = new int[2][m];
                
                for (int i = 0; i < 2; i++ )
                	for (int j = 0; j < m; j++) 
                		grid[i][j] = sc.nextInt();
                               
                int sum = 0;
                
                
                int[] match = new int[m];
                for (int i = 0; i < m; i++ )
                	for (int j = 0; j < m; j++) 
                		if (grid[0][i] == grid[1][j]) 
                			match[i] = j; 

 // grid[][2]에서 하여간 이것 저것 더하고 빼서 sum에 누적하는 코드 삽입
                for (int i = 0; i < m; i++ )
                	for (int j = 0; j < i; j++)  {
                		if (match[i] < match[j])
                			sum++;
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