import java.util.Random;
import java.util.Scanner;
public class UPandDOWN {
	public static void main (String[] args){
		Scanner sc = new Scanner(System.in);
		Random r = new Random();
		
		do{
			int k = r.nextInt(100); //랜덤수 생성
		
			System.out.println("수를 결정하였습니다. 맞추어 보세요.\n0-99");
			int insert = -1;
			int i = 1; //도전횟수
		
			while(insert != k){
			System.out.println(i+">>"); 
			insert = sc.nextInt();
			
			if(insert > k) System.out.println("더 낮게");
			else if(insert < k) System.out.println("더 높게");
			else System.out.println("맞았습니다.\n다시시작하시겠습니까(y/n)>>");
			i++;
			}
		} while(sc.next().equals("y")); //if를 사용하려 했긴 했는데...
	}
}

