import java.util.Scanner;
public class leapyear {
	public static void main(String[]args){
		System.out.println("년도를 입력하시오.");
		Scanner sc = new Scanner(System.in);
		int year = sc.nextInt();
		if (year % 4 == 0 && year % 100 !=0){
			System.out.println("윤년 입니다.");
		}
		else if(year % 100 ==0 && year % 400 != 0) {
			System.out.println("평년 입니다.");
		}
		else if(year % 400 == 0) {
			System.out.println("윤년 입니다.");
		}
		else System.out.println("평년 입니다.");
	}
}
