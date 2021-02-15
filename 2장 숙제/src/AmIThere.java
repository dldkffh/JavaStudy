import java.util.Scanner;
public class AmIThere {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.print("점의 x좌표와 y좌표를 차례로 입력하시오. : ");
		int x = sc.nextInt();
		int y = sc.nextInt();

		if (x>=50 && x<=100 && y>=50 && y<=100){
			System.out.print("사각형 안에 있습니다.");
		}
		else {
			System.out.print("사각형 안에 없습니다.");
		}
	}
}
