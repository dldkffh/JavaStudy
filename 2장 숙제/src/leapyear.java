import java.util.Scanner;
public class leapyear {
	public static void main(String[]args){
		System.out.println("�⵵�� �Է��Ͻÿ�.");
		Scanner sc = new Scanner(System.in);
		int year = sc.nextInt();
		if (year % 4 == 0 && year % 100 !=0){
			System.out.println("���� �Դϴ�.");
		}
		else if(year % 100 ==0 && year % 400 != 0) {
			System.out.println("��� �Դϴ�.");
		}
		else if(year % 400 == 0) {
			System.out.println("���� �Դϴ�.");
		}
		else System.out.println("��� �Դϴ�.");
	}
}
