import java.lang.Thread;

class Thread1 extends Thread {
	// run() �޼��� �������̵�
	public void run() {
		// �����忡�� ������ �۾�
		tripleTFunction("Thread 1");
	}
}

class Thread2 extends Thread {
	public void run() {
		tripleTFunction("Thread 2");
	}
}

public class LPU {
	// Main() �Լ����� ��ӹ��� ������ Ŭ������ ������, ����
	//....
	public static void main(String [] arg){
	//....
		Thread1 t1 = new Thread1();
		Thread2 t2 = new Thread2(); 
		t1.start(); 
		t2.start(); 
		}

	//��ó:http:// ra2kstar.tistory.com/130 [�ʺ������� �̾߱�.]
}
