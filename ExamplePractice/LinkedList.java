package abcd;

public class LinkedList {

	private node first;

	// 연결 리스트 생성자
	public LinkedList() {
		first = null;
	}

	// 현재 리스트가 비었는지 확인
	public boolean isEmpty() {
		return (first == null);
	}

	// 리스트의 앞 부분에 원소 삽입
	public void insert(String name) {
		node node = new node(name);
		node.nextNode = first;
		first = node;
	}

	// 리스트의 가장 앞 부분에서 원소를 삭제
	public node delete() {
		node temp = first;
		first = first.nextNode;
		return temp;
	}

	// 이름(년도월일)으로 노드 찾기
	public node search(String name) {
		node current = first;
		while (current != null) {
			if (current.name.equals(name))
				return current;
			current = current.nextNode;
		}
		return null;
	}
}