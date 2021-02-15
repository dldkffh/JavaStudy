import java.util.Scanner;
public class printString {
	public static void main(String[]args){
		String str = "한글데이터 변환하기";
		char[] chr = str.toCharArray();
		String change = "";

		char cr = 'A';
		change = String.valueOf(cr);
		change = Character.toString(cr);
		change = new Character(cr).toString();

		System.out.println(change);
		
		System.out.println('a' -'z');
		System.out.println('A' -'a');

	}
}