import java.util.Scanner;
public class clap369 {
	public static void main(String[] args){
		System.out.print("1~99 ������ ������ �Է��Ͻÿ�>>");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		int num1 = num / 10;
		int num2 = num - num1 * 10;
		
		if(num1 % 3 == 0 || num2 % 3 == 0){
			if(num1 % 3 == 0 && num2 % 3 == 0){
				System.out.print("�ڼ�¦¦");
			}
			else System.out.print("�ڼ�¦");
		}
		else;
	}
}
