import java.util.Random;
import java.util.Scanner;
public class UPandDOWN {
	public static void main (String[] args){
		Scanner sc = new Scanner(System.in);
		Random r = new Random();
		
		do{
			int k = r.nextInt(100); //������ ����
		
			System.out.println("���� �����Ͽ����ϴ�. ���߾� ������.\n0-99");
			int insert = -1;
			int i = 1; //����Ƚ��
		
			while(insert != k){
			System.out.println(i+">>"); 
			insert = sc.nextInt();
			
			if(insert > k) System.out.println("�� ����");
			else if(insert < k) System.out.println("�� ����");
			else System.out.println("�¾ҽ��ϴ�.\n�ٽý����Ͻðڽ��ϱ�(y/n)>>");
			i++;
			}
		} while(sc.next().equals("y")); //if�� ����Ϸ� �߱� �ߴµ�...
	}
}

