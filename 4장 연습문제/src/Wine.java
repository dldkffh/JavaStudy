
public class Wine {
	private String manufacturer, name, country, region, kind;
	private int year, grade;
	
	Wine(String manufac, String n, String ct, String reg, String k, int y, int g){
		manufacturer = manufac;
		name = n; 
		country = ct;
		region = reg; 
		kind = k;
		year = y;
		grade = g;
	}
	Wine(String manufac, String n){
		manufacturer = manufac;
		name = n; 
	}
}
