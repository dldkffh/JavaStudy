package ch4CheckTime;

public class Example{
	public static void main(String[] args) {
		Boy b = new Boy();
		b.setAge(12);
		System.out.println(b.getAge());
	}
}


class Boy {
	private int age; 
	Boy () {}
	Boy (int a) {
		setAge(a);
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}


