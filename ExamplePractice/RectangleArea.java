import java.util.Scanner;
public class RectangleArea {
	public static void main(String[] args){
		System.out.println("�� �� (x1, x2), (y1, y2)�� ��ǥ�� �Է��Ͻÿ�>>");
		Scanner sc = new Scanner(System.in);
		int x1 = sc.nextInt();
		int x2 = sc.nextInt();
		int y1 = sc.nextInt();
		int y2 = sc.nextInt();
		
		if(((x1 >= 50 && x1 <=100)&&(x1 >= 50 && x2 <=100)) || ((x1 >= 50 && y1 <=100)&&(x1 >= 50 && y2 <=100)))
			System.out.println("�簢���� ��Ĩ�ϴ�.");
		else if( ( (x1 < 50 && x2 < 100) || (x1 < 100 && x2 < 50) ) && ( (y1 > 100 && y2 > 50) || (y1 > 50 && y2 > 100) ) )
			System.out.println("�簢���� ��Ĩ�ϴ�.");
		else if( ( (y1 < 50 && y2 < 100) || (y1 < 100 && y2 < 50) ) && ( (x1 > 100 && x2 > 50) || (x1 > 50 && x2 > 100) ) )
			System.out.println("�簢���� ��Ĩ�ϴ�.");
		else if( ( (x1 < 50 && x2 > 50) || (x1 < 100 && x2 > 100) ) && ( (y1 > 50 && y2 < 50) || (y1 > 100 && y2 < 100) ) )
			System.out.println("�簢���� ��Ĩ�ϴ�.");
		else if( ( (y1 < 50 && y2 > 50) || (y1 < 100 && y2 > 100) ) && ( (x1 > 50 && x2 < 50) || (x1 > 100 && x2 < 100) ) )
			System.out.println("�簢���� ��Ĩ�ϴ�.");
		else System.out.println("�簢���� ��ġ�� �ʽ��ϴ�.");
		
	}
}
