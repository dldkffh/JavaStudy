package abcd;

public class LinkedList {

	private node first;

	// ���� ����Ʈ ������
	public LinkedList() {
		first = null;
	}

	// ���� ����Ʈ�� ������� Ȯ��
	public boolean isEmpty() {
		return (first == null);
	}

	// ����Ʈ�� �� �κп� ���� ����
	public void insert(String name) {
		node node = new node(name);
		node.nextNode = first;
		first = node;
	}

	// ����Ʈ�� ���� �� �κп��� ���Ҹ� ����
	public node delete() {
		node temp = first;
		first = first.nextNode;
		return temp;
	}

	// �̸�(�⵵����)���� ��� ã��
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