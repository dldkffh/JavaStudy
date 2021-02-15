import java.util.Scanner;
public class shillsup {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		int aver = 0, sum = 0, i = 1;
			for(i=1;;i++){
				if(sc.next().equals("\n"))
					break;
				else {
					int num = sc.nextInt();
					sum += num;
				}
			}
		aver = sum / i;
		System.out.print(aver);
	}
}
	