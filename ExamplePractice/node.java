package abcd;

public class node {
	public int count = 0; // 넣을 곳을 의미
	public String name;
	public String cal[] = new String[100];
	public node nextNode;

	// 노드 생성자
	public node(String name) {
		this.name = name;
		for (int i = 0; i < 15; i++) {
			cal[i] = new String("");
		}
		count = 0;
	}

	public void setCal(String data) {
		cal[count] = data;
		count++;
		sort();
	}

	public void delCal(int idx) {
		count--;
		for (int i = idx + 1; i < count + 1; i++) {
			cal[idx++] = cal[i];
			if (i == count) {
				sort();
				break;
			}
		}
	}

	public void sort() { // 정렬
		for (int i = 0; i < count - 1; i++) {
			for (int j = i + 1; j < count; j++) {
				if (cal[i].compareTo(cal[j]) > 0) {
					String tmp = new String(cal[i]);
					cal[i] = new String(cal[j]);
					cal[j] = new String(tmp);
				}
			}
		}
	}

	public int getcnt() {
		return count;
	}
}