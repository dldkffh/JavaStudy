import java.util.Scanner;
public class IsItTriangle {
	public static void main(String[] args){
		int a = 0, b = 0, c = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("삼각형의 세변의 길이를 입력하시오 : ");
		a = sc.nextInt();
		b = sc.nextInt();
		c = sc.nextInt();
		
		if ((a+b)<c || (a+c)<b ||(b+c)<a){
			System.out.println("삼각형 세변의 길이가 될 수 없습니다.");
		}
		else{
			System.out.println("삼각형 세변의 길이가 될 수 있습니다.");
		}
	}
}
