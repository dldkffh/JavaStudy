import java.lang.Thread;

class Thread1 extends Thread {
	// run() 메서드 오버라이딩
	public void run() {
		// 스레드에서 실행할 작업
		tripleTFunction("Thread 1");
	}
}

class Thread2 extends Thread {
	public void run() {
		tripleTFunction("Thread 2");
	}
}

public class LPU {
	// Main() 함수에서 상속받은 쓰레드 클레스를 선언후, 시작
	//....
	public static void main(String [] arg){
	//....
		Thread1 t1 = new Thread1();
		Thread2 t2 = new Thread2(); 
		t1.start(); 
		t2.start(); 
		}

	//출처:http:// ra2kstar.tistory.com/130 [초보개발자 이야기.]
}
