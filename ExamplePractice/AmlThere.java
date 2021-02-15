import java.util.Scanner;
public class AmIThere {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.print("���� x��ǥ�� y��ǥ�� ���ʷ� �Է��Ͻÿ�. : ");
		int x = sc.nextInt();
		int y = sc.nextInt();

		if (x>=50 && x<=100 && y>=50 && y<=100){
			System.out.print("�簢�� �ȿ� �ֽ��ϴ�.");
		}
		else {
			System.out.print("�簢�� �ȿ� �����ϴ�.");
		}
	}
}
