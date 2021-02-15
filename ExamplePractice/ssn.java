import java.util.Scanner;
public class ssn {
	public static void main(String[]args){
		System.out.println("주민버호를 입력하시오.");
		Scanner a = new Scanner(System.in).useDelimiter("\\s|-");
		int ssnum = a.nextInt();
		System.out.println(ssnum);
	}
}
