import java.util.Scanner;
public class IsItTriangle {
	public static void main(String[] args){
		int a = 0, b = 0, c = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("�ﰢ���� ������ ���̸� �Է��Ͻÿ� : ");
		a = sc.nextInt();
		b = sc.nextInt();
		c = sc.nextInt();
		
		if ((a+b)<c || (a+c)<b ||(b+c)<a){
			System.out.println("�ﰢ�� ������ ���̰� �� �� �����ϴ�.");
		}
		else{
			System.out.println("�ﰢ�� ������ ���̰� �� �� �ֽ��ϴ�.");
		}
	}
}
