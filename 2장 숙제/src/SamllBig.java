import java.util.Scanner;
public class SamllBig {
	public static void main(String[]args){
		Scanner sc = new Scanner(System.in);
		System.out.println("영어 한글자 입력 : ");
		char me = (char) sc.nextInt();
		
		if (me >= 'a' && me <= 'z'){
			System.out.print(me - ('a' - 'A'));	
		}
		else if (me >= 'A' && me <= 'Z'){
			System.out.print(me + ('a' - 'A'));
		}
		else {
			System.out.print("영문자가 아닙니다.");	
		}
	}
}
