package abcd;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class pl_cal extends JFrame {
	// ����ð� //
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
	int day = cal.get(Calendar.DATE);
	//// ����� �ӽ� �ð� ///
	Calendar tmp = Calendar.getInstance();
	int tmp_year = cal.get(Calendar.YEAR);
	int tmp_month = cal.get(Calendar.MONTH);
	int tmp_date = cal.get(Calendar.DATE);
	int mon_fir = tmp.get(Calendar.DAY_OF_WEEK) - 1; // ���泯¥�� ���� ����
	int mon_last = cal.getActualMaximum(Calendar.DATE); // ����� ���� ���ϼ�
	/// ��� �г� ///
	JPanel pl_0_status = new JPanel();
	JLabel lb_year = new JLabel(year + "��");
	JLabel lb_month = new JLabel(month + "��");
	JLabel lb_days = new JLabel(day + "��");

	JButton next_y = new JButton(">>");
	JButton next_m = new JButton(">");
	JButton previous_y = new JButton("<<");
	JButton previous_m = new JButton("<");
	JButton btn_td = new JButton("today : " + year + " - " + month + " - " + day);
	//////// �߾� �г� /////////
	JPanel pl_day = new JPanel();
	JLabel lb_day[] = new JLabel[7];
	String days[] = { "��", "��", "ȭ", "��", "��", "��", "��" };
	JButton btn_day[] = new JButton[42];
	//////// �ϴ� �г� ////////
	JPanel pl_list = new JPanel();
	JPanel pl_menu = new JPanel();
	JLabel lb_td = new JLabel(year + "�� " + month + "�� " + day + "�� " + doW(tmp.get(Calendar.DAY_OF_WEEK)));
	JButton btn_menu = new JButton("��������");
	subFrame sF;

	//////////////////// ���Ḯ��Ʈ ���� //////////////////////

	LinkedList list;
	JList jlist; // ���� ������ �ϴܺ� ����Ʈ
	DefaultListModel model; // ����Ʈ�� ��
	String node_name = year + "" + month + day; // ��� �����α� ���� ������ �̸� (�⵵����)

	private node first; // ����� ���

	pl_cal() {
		setTitle("Calendar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init(); // �ʱ�ȭ �ʿ��� �����鸸 �ʱ�ȭ

		list.insert(node_name); // ���Ḯ��Ʈ�� ���糯¥�� ��� ����

		makeGUI();

		setSize(500, 900);
		setLocation(200, 100);
		setResizable(false); // ũ�� ����
		setVisible(true);
	}

	void init() { // �ʿ��� ������ �ʱ�ȭ
		tmp.set(Calendar.DATE, 1);
		mon_fir = tmp.get(Calendar.DAY_OF_WEEK) - 1;

		list = new LinkedList(); // ���� ���� ����Ʈ ����
		jlist = new JList(new DefaultListModel()); // ����Ʈ �ʱ�ȭ
		model = (DefaultListModel) jlist.getModel(); // ����Ʈ�� �� �ʱ�ȭ
	}

	void setCal(int year, int month, int day) { // ��ü cal ����
		tmp.set(Calendar.YEAR, year);
		tmp.set(Calendar.MONTH, month);
		tmp.set(Calendar.DATE, day);
	}

	void makeGUI() {
		this.setLayout(new BorderLayout(0, 0));
		days(); // ��¥ ����â (���� ������ ��ܺ�) �ʱ�ȭ
		status(); // �⵵ �� ����â(���� ������ �߾Ӻ�) �ʱ�ȭ
		schedule(); // ���� ����(���� ������ �ϴܺ�) �ʱ�ȭ
		this.setBackground(Color.GRAY);
	}

	void schedule() { // ������ �ϴܺ�

		pl_menu.setLayout(new FlowLayout(FlowLayout.LEFT));
		pl_menu.add(lb_td, BorderLayout.WEST); // Ŭ���� ��¥�� ǥ���ϴ� ���̺�. �г��� ���ʿ� ����
		btn_menu.addActionListener(new btn_day_Listener()); // �������� ��ư�� ������ ����
		pl_menu.add(btn_menu, BorderLayout.EAST); // �ϴ� �гο� �������� ��ư ����

		pl_list.setLayout(new BorderLayout(5, 5)); // ���� 5,5�� borderlayout
		pl_list.add(pl_menu, BorderLayout.NORTH); // Ŭ���� ��¥�� ǥ���ϴ� �г�
		pl_list.add(new JScrollPane(jlist), BorderLayout.CENTER); // �г��� �ϴܺο�
																	// list ����
		pl_list.setPreferredSize(new Dimension(500, 230));
		add(pl_list, BorderLayout.SOUTH);
	}

	String doW(int num) { // �ǳ׹��� ������ ���ڰ��� �ѱ۷� ��ȯ
		String dow = "�Ͽ���";
		switch (num) {
		case 2:
			dow = "������";
			break;
		case 3:
			dow = "ȭ����";
			break;
		case 4:
			dow = "������";
			break;
		case 5:
			dow = "�����";
			break;
		case 6:
			dow = "�ݿ���";
			break;
		case 7:
			dow = "�����";
			break;
		}
		return dow;
	}

	void days() { // ���� ������ �߾Ӻ�
		pl_day.setLayout(new GridLayout(7, 7)); // ���Ͽ� �ش��ϴ� ���̺��� �����Ͽ� 7x7 �׸���
		pl_day.setBackground(Color.LIGHT_GRAY);
		for (int i = 0; i < 7; i++) { // ��~����ϱ��� �����ؼ� ���ٿ� ����
			lb_day[i] = new JLabel(days[i]);
			lb_day[i].setHorizontalAlignment(SwingConstants.CENTER);
			lb_day[i].setForeground(Color.WHITE);
			lb_day[i].setFont(new Font("���ü", Font.ITALIC, 20));
			pl_day.add(lb_day[i]);
		}
		lb_day[0].setForeground(Color.RED); // �Ͽ����� ������
		for (int i = 0; i < 42; i++) { // ���� ��¥�� �°� ��ư ��ġ ����
			if (i >= mon_fir && i < mon_last + mon_fir) { // mon_fir -> ���� �� ����
															// �ش��ϴ� 1���� ����,
															// last�� �� �ϼ�
				btn_day[i] = new JButton(new Integer(i - mon_fir + 1).toString()); // 1�Ϻ���
																					// ����
				pl_day.add(btn_day[i]);
			} else {
				btn_day[i] = new JButton(""); // �ش����� �ʴ� ��ư���� text�� ����� ��Ȱ��ȭ
				btn_day[i].setEnabled(false);
				pl_day.add(btn_day[i]);
			}
			btn_day[i].setBorderPainted(false); // �ܰ��� �����
			if ((i - mon_fir + 1) == cal.get(Calendar.DATE)) // ���� ��¥�� �ش��ϴ� ��ư��
																// £�� ȸ��
				btn_day[i].setBackground(Color.DARK_GRAY);
			else
				btn_day[i].setBackground(Color.LIGHT_GRAY); // �������� ���� ȸ��

			if (i % 7 == 0) {
				btn_day[i].setForeground(Color.RED); // �Ͽ��Ͽ� �ش��ϴ� ��ư���� ������
			} else
				btn_day[i].setForeground(Color.WHITE); // ������ �Ͼ��

			btn_day[i].setFont(new Font("���ü", Font.ITALIC, 18));
			btn_day[i].addActionListener(new btn_day_Listener());
		}
		add(pl_day, BorderLayout.CENTER);
	}

	void status() { // ���� ������ ��ܺ�.(����â)
		this.add(pl_0_status, BorderLayout.NORTH);
		pl_0_status.setLayout(null);
		pl_0_status.setPreferredSize(new Dimension(400, 100)); // ��� �г� ũ�� ���� ����

		pl_0_status.add(lb_year); // �⵵
		lb_year.setFont(new Font("���ü", Font.ITALIC, 30));
		lb_year.setBounds(115, 30, 100, 50);

		pl_0_status.add(lb_month); // ��
		lb_month.setFont(new Font("���ü", Font.ITALIC, 20));
		lb_month.setBounds(235, 33, 100, 50);

		pl_0_status.add(lb_days); // ��

		pl_0_status.add(next_m); // ���� �� ��ư
		next_m.setBounds(290, 45, 25, 25);
		next_m.addActionListener(new myListener());

		pl_0_status.add(next_y); // ���� �� ��ư
		next_y.setBounds(325, 45, 25, 25);
		next_y.addActionListener(new myListener());

		pl_0_status.add(previous_m); // ���� �� ��ư
		previous_m.setBounds(75, 45, 25, 25);
		previous_m.addActionListener(new myListener());

		pl_0_status.add(previous_y); // ���� �⵵ ��ư
		previous_y.setBounds(40, 45, 25, 25);
		previous_y.addActionListener(new myListener());

		pl_0_status.add(btn_td); // today ǥ�� ��ư
		btn_td.setBounds(30, 80, 150, 20);
		btn_td.setBackground(Color.CYAN);
		btn_td.setBorderPainted(false);
		btn_td.addActionListener(new myListener());

		pl_0_status.setBackground(Color.CYAN);
		pl_0_status.setPreferredSize(new Dimension(500, 130)); // ũ�����
	}

	class myListener implements ActionListener { // ��� �гο� ��ġ�� ��ư���� ������
		public void actionPerformed(ActionEvent e) {
			JButton tmp_bt = (JButton) e.getSource();
			if (tmp_bt.equals(next_m)) { // ���� �� ������ ��
				if (tmp_month == 11) {
					tmp_month = 0;
					tmp_year++;
				} else
					tmp_month += 1;
			} else if (tmp_bt.equals(next_y)) { // ���� �⵵ ������ ��
				tmp_year += 1;
			}
			if (tmp_bt.equals(previous_m)) { // ���� �� ������ ��
				if (tmp_month == 0) {
					tmp_month = 11;
					tmp_year--;
				} else
					tmp_month -= 1;
			} else if (tmp_bt.equals(previous_y)) {
				tmp_year -= 1;
			} else if (tmp_bt.equals(btn_td)) { // today ��ư ������ ��
				tmp_year = year; // ������ ���� �⵵�� �޷� ����
				tmp_month = month - 1;
				for (int i = 0; i < 42; i++)
					if (btn_day[i].getText().equals(day + 1))
						btn_day[i].setBackground(Color.DARK_GRAY); // ���� ��¥��
																	// �ش��ϴ� ��ư
																	// £�� ȸ������
				lb_td.setText(year + "�� " + month + "�� " + day + "�� " + doW(cal.get(Calendar.DAY_OF_WEEK)));
				// �ϴܺ� �󺧵� ���� ��¥�� �°� ����
			}
			tmp.set(Calendar.MONTH, tmp_month); // �⵵, ��¥ �ʱ�ȭ
			tmp.set(Calendar.YEAR, tmp_year);
			lb_year.setText(tmp_year + "��"); // �г� ��ܺ��� �� �� ����
			lb_month.setText((tmp_month + 1) + "��");
			setting();
		}
	}

	class btn_day_Listener implements ActionListener { // ��¥ ��ư�� ���� ��ư ������
		public void actionPerformed(ActionEvent e) {
			JButton tmp_bt = (JButton) e.getSource();
			if (!tmp_bt.equals(btn_menu)) { // �߾� ��¥�κ� ��ư�� Ŭ������ �� true
				node_name = tmp_year + "" + (tmp_month + 1) + tmp_bt.getText(); // ���õ�
																				// ��ư��
																				// �ش��ϴ�
																				// �����
																				// "�̸���"!!
																				// �����
				int tmp_day = new Integer(tmp_bt.getText()).intValue(); // Ŭ����
																		// ��¥
				for (int i = 0; i < 42; i++)
					btn_day[i].setBackground(Color.LIGHT_GRAY); // ��� ��ư ���� ȸ��
				setCal(tmp_year, tmp_month, tmp_day); // �޷� ��ü cal �ʱ�ȭ
				lb_td.setText(
						tmp_year + "�� " + (tmp_month + 1) + "�� " + tmp_day + "�� " + doW(tmp.get(Calendar.DAY_OF_WEEK)));
				// ���� �г� �ϴܺ� ��¥ �ʱ�ȭ
				tmp_bt.setBackground(Color.DARK_GRAY); // ���õ� ��ư £�� ȸ������
				tmp_date = tmp_day;

				model.clear(); // �������� â�� ����Ʈ �ʱ�ȭ
				if (list.search(node_name) == null) { // Ŭ���� ��ư�� ��尡 ���ٸ� ����
					list.insert(node_name);
				} else { // ��尡 �ִٸ� ����
					int index = list.search(node_name).count;
					if (index != 0) { // index�� 0�� �ƴϸ� ������ �ִٴ� �ǹ��̹Ƿ�
						for (int i = 0; i < list.search(node_name).count; i++) // �ش�
																				// �����
																				// ���
																				// ������
																				// ����
																				// ������
																				// ����Ʈ��
																				// ���
							model.addElement(list.search(node_name).cal[i]);
					}
				}
			} else { // ���� ���� â�� ���� ������!!
				sF = new subFrame(tmp_year, tmp_month + 1, tmp_date); // �������� â
																		// ����
				sF.tf.requestFocus(); // ���� ������ ��Ÿ������ �ؽ�Ʈ �ʵ尡 ��Ŀ���� ����
				for (int i = 0; i < list.search(node_name).count; i++) // �ش� �����
																		// ��� ����
																		// ���
					sF.model.addElement(list.search(node_name).cal[i]);

				sF.tf.addActionListener(new ActionListener() { // textfield ������
					public void actionPerformed(ActionEvent e) {
						if (!sF.tf.getText().isEmpty()) { // �ؽ�Ʈ �ʵ忡 ���� �ִٸ�
							String time = "";
							if (sF.time) {
								String hh = sF.h + "";
								String mm = sF.m + "";
								if (sF.h < 10)
									hh = "0" + sF.h;
								if (sF.m < 10)
									mm = "0" + sF.m;
								time = "( " + hh + " : " + mm + " ) ";
							}
							list.search(node_name).setCal(time + sF.tf.getText()); // ����
																					// ��¥
																					// ��忡
																					// �ؽ�Ʈ
																					// �ʵ忡
																					// �ִ�
																					// ������
																					// �߰�
							sF.tf.setText("");
							sF.tf.requestFocus();
							sF.model.clear(); // ���� ������ ����Ʈ �ʱ�ȭ
							for (int i = 0; i < list.search(node_name).count; i++) // �ش�
																					// �����
																					// ���
																					// ����
																					// ���
								sF.model.addElement(list.search(node_name).cal[i]);
						}
					}
				});

				for (int i = 0; i < 4; i++) {
					sF.btn[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JButton bt_tmp = (JButton) e.getSource();
							if (bt_tmp.equals(sF.btn[0])) { // �߰� ��ư �̺�Ʈ
								if (!sF.tf.getText().isEmpty()) { // �ؽ�Ʈ �ʵ忡 ����
																	// �ִٸ�
									String time = "";
									if (sF.time) // ���� �߰��� �� �ð� ��� �Ѵٸ� true
										time = "(" + sF.h + " : " + sF.m + ") ";
									list.search(node_name).setCal(time + sF.tf.getText()); // ����
																							// ��¥
																							// ��忡
																							// �ؽ�Ʈ
																							// �ʵ忡
																							// �ִ�
																							// ������
																							// �߰�
									sF.tf.setText(""); // �߰� �Ŀ� textfield�� Ŭ����
									sF.tf.requestFocus(); // Ŭ���� �� �ٷ� ���� ���� �ְ�
															// textfield�� ��Ŀ���� ��

									sF.model.clear(); // ���� ������ ����Ʈ �ʱ�ȭ
									for (int i = 0; i < list.search(node_name).count; i++) // �ش�
																							// �����
																							// ���
																							// ����
																							// ���
										sF.model.addElement(list.search(node_name).cal[i]);
								}
							} else if (!(bt_tmp.equals(sF.btn[3]))) { // ����, ����
																		// ��ư��
																		// ����
																		// �̺�Ʈ
								int idx = sF.list.getSelectedIndex();
								if (idx != -1) {
									if (bt_tmp.equals(sF.btn[1])) // ������ư�̶��
										sF.tf.setText(list.search(node_name).cal[idx]); // ���õ�
																						// ����Ʈ
																						// ��������
																						// textfield��
																						// �÷��ΰ�
									list.search(node_name).delCal(idx); // ����
									sF.model.clear(); // ���� ������ ����Ʈ �ʱ�ȭ
									for (int i = 0; i < list.search(node_name).count; i++) // �ش�
																							// �����
																							// ���
																							// ����
																							// ���
										sF.model.addElement(list.search(node_name).cal[i]);

									if (idx != 0)
										sF.list.setSelectedIndex(idx - 1); // ���õ�
																			// ����Ʈ
																			// ��������
																			// 0��
																			// �ƴ϶��
																			// ����
																			// ��
																			// ����
																			// ������
																			// ����
									else
										sF.tf.requestFocus();
								}
							} else { // ��� ��ư�� ���� �̺�Ʈ ����
								model.clear(); // ���� ������ ����Ʈ �ʱ�ȭ
								for (int i = 0; i < list.search(node_name).count; i++)
									model.addElement(list.search(node_name).cal[i]); // ����
																						// ������Ʈ��
																						// ����Ʈ
																						// ���
								sF.dispose(); // �ι�° ������ ����
								setting();
								for (int i = 0; i < 42; i++) {
									if (new Integer(tmp_date).toString().equals(btn_day[i].getText())) {
										btn_day[i].setBackground(Color.DARK_GRAY);
										break;
									}
								}
							}
						}
					});
				}
			}
		}
	}

	public void setting() { // �ٲ� �� ���� �°� ��¥ ��ư�� ��ġ �ʱ�ȭ(�߾� ù �ʱ�ȭ���� ��� ���)
		tmp.set(Calendar.DAY_OF_MONTH, 1);
		mon_fir = tmp.get(Calendar.DAY_OF_WEEK) - 1;
		mon_last = tmp.getActualMaximum(Calendar.DATE);
		for (int i = 0; i < 42; i++) {
			pl_day.remove(btn_day[i]);

			if ((i - mon_fir + 2) == day + 1 && (year == tmp_year) && (month - 1 == tmp_month))
				btn_day[i].setBackground(Color.DARK_GRAY);
			else
				btn_day[i].setBackground(Color.LIGHT_GRAY); // ������

			if (i >= mon_fir && i < mon_last + mon_fir) { // �ش� 1�Ϻ��� ���ϱ��� ��ġ�� �°�
															// ����
				btn_day[i].setText(new Integer(i - mon_fir + 1).toString());
				btn_day[i].setEnabled(true);
				pl_day.add(btn_day[i]);

			} else {
				btn_day[i].setText("");
				btn_day[i].setEnabled(false);
				pl_day.add(btn_day[i]);
			}
			if (isCal(tmp.get(Calendar.YEAR) + "" + (tmp.get(Calendar.MONTH) + 1) + btn_day[i].getText()))
				btn_day[i].setForeground(Color.BLUE);
			else if (i % 7 == 0)
				btn_day[i].setForeground(Color.RED); // ���� ����
			else
				btn_day[i].setForeground(Color.WHITE);

			btn_day[i].setBorderPainted(false);
			btn_day[i].setFont(new Font("���ü", Font.ITALIC, 18));
			btn_day[i].addActionListener(new btn_day_Listener());
		}
	}

	boolean isCal(String name) { // ������ �ֳ� ���� üũ
		if (list.search(name) != null)
			if (list.search(name).getcnt() > 0)
				return true;
		return false;
	}

	public static void main(String[] args) {
		new pl_cal();
	}
}