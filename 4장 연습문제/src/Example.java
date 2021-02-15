class SampleClass {
	private int id;
	public static int getId(){
		return id;
	}
	public static void setId(int id){
		this.id = id;
	}
}

public class Example {
	public static void main (){
		SampleClass obj = new SampleClass();
		obj.setId(10);
		System.out.println(obj.getId());
	}
}
