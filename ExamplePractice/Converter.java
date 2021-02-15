package ch4CheckTime;

public class Converter {
	static final int INCHES_PER_FOOT = 12;
	static double convertFeetToInches (double feet) {
		return feet * INCHES_PER_FOOT;
	}
	static double convertInchesToFeet (double inches) {
		return inches * INCHES_PER_FOOT;
	}
	
	public static void main(String args[]){
		Converter cvrt = new Converter();
		double inches = cvrt.convertFeetToInches(12.5);
	}
}
