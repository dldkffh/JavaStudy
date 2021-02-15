package abcd;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class pl_cal extends JFrame {
	// 현재시간 //
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
	int day = cal.get(Calendar.DATE);
	//// 변경된 임시 시간 ///
	Calendar tmp = Calendar.getInstance();
	int tmp_year = cal.get(Calendar.YEAR);
	int tmp_month = cal.get(Calendar.MONTH);
	int tmp_date = cal.get(Calendar.DATE);
	int mon_fir = tmp.get(Calendar.DAY_OF_WEEK) - 1; // 변경날짜의 시작 요일
	int mon_last = cal.getActualMaximum(Calendar.DATE); // 변경된 달의 총일수
	/// 상단 패널 ///
	JPanel pl_0_status = new JPanel();
	JLabel lb_year = new JLabel(year + "년");
	JLabel lb_month = new JLabel(month + "월");
	JLabel lb_days = new JLabel(day + "일");

	JButton next_y = new JButton(">>");
	JButton next_m = new JButton(">");
	JButton previous_y = new JButton("<<");
	JButton previous_m = new JButton("<");
	JButton btn_td = new JButton("today : " + year + " - " + month + " - " + day);
	//////// 중앙 패널 /////////
	JPanel pl_day = new JPanel();
	JLabel lb_day[] = new JLabel[7];
	String days[] = { "일", "월", "화", "수", "목", "금", "토" };
	JButton btn_day[] = new JButton[42];
	//////// 하단 패널 ////////
	JPanel pl_list = new JPanel();
	JPanel pl_menu = new JPanel();
	JLabel lb_td = new JLabel(year + "년 " + month + "월 " + day + "일 " + doW(tmp.get(Calendar.DAY_OF_WEEK)));
	JButton btn_menu = new JButton("일정관리");
	subFrame sF;

	//////////////////// 연결리스트 관련 //////////////////////

	LinkedList list;
	JList jlist; // 메인 프레임 하단부 리스트
	DefaultListModel model; // 리스트의 모델
	String node_name = year + "" + month + day; // 노드 구별두기 위한 노드들의 이름 (년도월일)

	private node first; // 노드의 헤드

	pl_cal() {
		setTitle("Calendar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init(); // 초기화 필요한 변수들만 초기화

		list.insert(node_name); // 연결리스트에 현재날짜로 노드 만듬

		makeGUI();

		setSize(500, 900);
		setLocation(200, 100);
		setResizable(false); // 크기 고정
		setVisible(true);
	}

	void init() { // 필요한 변수들 초기화
		tmp.set(Calendar.DATE, 1);
		mon_fir = tmp.get(Calendar.DAY_OF_WEEK) - 1;

		list = new LinkedList(); // 일정 연결 리스트 생성
		jlist = new JList(new DefaultListModel()); // 리스트 초기화
		model = (DefaultListModel) jlist.getModel(); // 리스트의 모델 초기화
	}

	void setCal(int year, int month, int day) { // 객체 cal 셋팅
		tmp.set(Calendar.YEAR, year);
		tmp.set(Calendar.MONTH, month);
		tmp.set(Calendar.DATE, day);
	}

	void makeGUI() {
		this.setLayout(new BorderLayout(0, 0));
		days(); // 날짜 상태창 (메인 프레임 상단부) 초기화
		status(); // 년도 월 상태창(메인 프레임 중앙부) 초기화
		schedule(); // 일정 관리(메인 프레임 하단부) 초기화
		this.setBackground(Color.GRAY);
	}

	void schedule() { // 프레임 하단부

		pl_menu.setLayout(new FlowLayout(FlowLayout.LEFT));
		pl_menu.add(lb_td, BorderLayout.WEST); // 클릭된 날짜를 표기하는 레이블. 패널의 왼쪽에 부착
		btn_menu.addActionListener(new btn_day_Listener()); // 일정관리 버튼에 리스너 부착
		pl_menu.add(btn_menu, BorderLayout.EAST); // 하단 패널에 일정관리 버튼 부착

		pl_list.setLayout(new BorderLayout(5, 5)); // 간격 5,5인 borderlayout
		pl_list.add(pl_menu, BorderLayout.NORTH); // 클릭된 날짜를 표기하는 패널
		pl_list.add(new JScrollPane(jlist), BorderLayout.CENTER); // 패널의 하단부에
																	// list 부착
		pl_list.setPreferredSize(new Dimension(500, 230));
		add(pl_list, BorderLayout.SOUTH);
	}

	String doW(int num) { // 건네받은 요일의 숫자값을 한글로 변환
		String dow = "일요일";
		switch (num) {
		case 2:
			dow = "월요일";
			break;
		case 3:
			dow = "화요일";
			break;
		case 4:
			dow = "수요일";
			break;
		case 5:
			dow = "목요일";
			break;
		case 6:
			dow = "금요일";
			break;
		case 7:
			dow = "토요일";
			break;
		}
		return dow;
	}

	void days() { // 메인 프레임 중앙부
		pl_day.setLayout(new GridLayout(7, 7)); // 요일에 해당하는 레이블을 포함하여 7x7 그리드
		pl_day.setBackground(Color.LIGHT_GRAY);
		for (int i = 0; i < 7; i++) { // 일~토요일까지 생성해서 윗줄에 부착
			lb_day[i] = new JLabel(days[i]);
			lb_day[i].setHorizontalAlignment(SwingConstants.CENTER);
			lb_day[i].setForeground(Color.WHITE);
			lb_day[i].setFont(new Font("고딕체", Font.ITALIC, 20));
			pl_day.add(lb_day[i]);
		}
		lb_day[0].setForeground(Color.RED); // 일요일은 빨갛게
		for (int i = 0; i < 42; i++) { // 현재 날짜에 맞게 버튼 위치 설정
			if (i >= mon_fir && i < mon_last + mon_fir) { // mon_fir -> 현재 년 월에
															// 해당하는 1일의 요일,
															// last는 총 일수
				btn_day[i] = new JButton(new Integer(i - mon_fir + 1).toString()); // 1일부터
																					// 생성
				pl_day.add(btn_day[i]);
			} else {
				btn_day[i] = new JButton(""); // 해당하지 않는 버튼들은 text를 지우고 비활성화
				btn_day[i].setEnabled(false);
				pl_day.add(btn_day[i]);
			}
			btn_day[i].setBorderPainted(false); // 외곽선 지우기
			if ((i - mon_fir + 1) == cal.get(Calendar.DATE)) // 오늘 날짜에 해당하는 버튼은
																// 짙은 회색
				btn_day[i].setBackground(Color.DARK_GRAY);
			else
				btn_day[i].setBackground(Color.LIGHT_GRAY); // 나머지는 밝은 회색

			if (i % 7 == 0) {
				btn_day[i].setForeground(Color.RED); // 일요일에 해당하는 버튼들은 빨갛게
			} else
				btn_day[i].setForeground(Color.WHITE); // 나머진 하얗게

			btn_day[i].setFont(new Font("고딕체", Font.ITALIC, 18));
			btn_day[i].addActionListener(new btn_day_Listener());
		}
		add(pl_day, BorderLayout.CENTER);
	}

	void status() { // 메인 프레임 상단부.(상태창)
		this.add(pl_0_status, BorderLayout.NORTH);
		pl_0_status.setLayout(null);
		pl_0_status.setPreferredSize(new Dimension(400, 100)); // 상단 패널 크기 강제 설정

		pl_0_status.add(lb_year); // 년도
		lb_year.setFont(new Font("고딕체", Font.ITALIC, 30));
		lb_year.setBounds(115, 30, 100, 50);

		pl_0_status.add(lb_month); // 월
		lb_month.setFont(new Font("고딕체", Font.ITALIC, 20));
		lb_month.setBounds(235, 33, 100, 50);

		pl_0_status.add(lb_days); // 일

		pl_0_status.add(next_m); // 다음 월 버튼
		next_m.setBounds(290, 45, 25, 25);
		next_m.addActionListener(new myListener());

		pl_0_status.add(next_y); // 다음 년 버튼
		next_y.setBounds(325, 45, 25, 25);
		next_y.addActionListener(new myListener());

		pl_0_status.add(previous_m); // 이전 월 버튼
		previous_m.setBounds(75, 45, 25, 25);
		previous_m.addActionListener(new myListener());

		pl_0_status.add(previous_y); // 이전 년도 버튼
		previous_y.setBounds(40, 45, 25, 25);
		previous_y.addActionListener(new myListener());

		pl_0_status.add(btn_td); // today 표시 버튼
		btn_td.setBounds(30, 80, 150, 20);
		btn_td.setBackground(Color.CYAN);
		btn_td.setBorderPainted(false);
		btn_td.addActionListener(new myListener());

		pl_0_status.setBackground(Color.CYAN);
		pl_0_status.setPreferredSize(new Dimension(500, 130)); // 크기고정
	}

	class myListener implements ActionListener { // 상단 패널에 위치한 버튼들의 리스너
		public void actionPerformed(ActionEvent e) {
			JButton tmp_bt = (JButton) e.getSource();
			if (tmp_bt.equals(next_m)) { // 다음 월 눌렀을 시
				if (tmp_month == 11) {
					tmp_month = 0;
					tmp_year++;
				} else
					tmp_month += 1;
			} else if (tmp_bt.equals(next_y)) { // 다음 년도 눌렀을 시
				tmp_year += 1;
			}
			if (tmp_bt.equals(previous_m)) { // 이전 월 눌렀을 시
				if (tmp_month == 0) {
					tmp_month = 11;
					tmp_year--;
				} else
					tmp_month -= 1;
			} else if (tmp_bt.equals(previous_y)) {
				tmp_year -= 1;
			} else if (tmp_bt.equals(btn_td)) { // today 버튼 눌렀을 때
				tmp_year = year; // 변수를 현재 년도와 달로 변경
				tmp_month = month - 1;
				for (int i = 0; i < 42; i++)
					if (btn_day[i].getText().equals(day + 1))
						btn_day[i].setBackground(Color.DARK_GRAY); // 현재 날짜에
																	// 해당하는 버튼
																	// 짙은 회색으로
				lb_td.setText(year + "년 " + month + "월 " + day + "일 " + doW(cal.get(Calendar.DAY_OF_WEEK)));
				// 하단부 라벨도 변경 날짜에 맞게 변경
			}
			tmp.set(Calendar.MONTH, tmp_month); // 년도, 날짜 초기화
			tmp.set(Calendar.YEAR, tmp_year);
			lb_year.setText(tmp_year + "년"); // 패널 상단부의 년 월 변경
			lb_month.setText((tmp_month + 1) + "월");
			setting();
		}
	}

	class btn_day_Listener implements ActionListener { // 날짜 버튼과 일정 버튼 리스너
		public void actionPerformed(ActionEvent e) {
			JButton tmp_bt = (JButton) e.getSource();
			if (!tmp_bt.equals(btn_menu)) { // 중앙 날짜부분 버튼을 클릭했을 때 true
				node_name = tmp_year + "" + (tmp_month + 1) + tmp_bt.getText(); // 선택된
																				// 버튼에
																				// 해당하는
																				// 노드의
																				// "이름만"!!
																				// 만들기
				int tmp_day = new Integer(tmp_bt.getText()).intValue(); // 클릭된
																		// 날짜
				for (int i = 0; i < 42; i++)
					btn_day[i].setBackground(Color.LIGHT_GRAY); // 모든 버튼 밝은 회색
				setCal(tmp_year, tmp_month, tmp_day); // 달력 객체 cal 초기화
				lb_td.setText(
						tmp_year + "년 " + (tmp_month + 1) + "월 " + tmp_day + "일 " + doW(tmp.get(Calendar.DAY_OF_WEEK)));
				// 메인 패널 하단부 날짜 초기화
				tmp_bt.setBackground(Color.DARK_GRAY); // 선택된 버튼 짙은 회색으로
				tmp_date = tmp_day;

				model.clear(); // 일정관리 창의 리스트 초기화
				if (list.search(node_name) == null) { // 클릭된 버튼의 노드가 없다면 생성
					list.insert(node_name);
				} else { // 노드가 있다면 실행
					int index = list.search(node_name).count;
					if (index != 0) { // index가 0이 아니면 일정이 있다는 의미이므로
						for (int i = 0; i < list.search(node_name).count; i++) // 해당
																				// 노드의
																				// 모든
																				// 일정을
																				// 메인
																				// 프레임
																				// 리스트에
																				// 출력
							model.addElement(list.search(node_name).cal[i]);
					}
				}
			} else { // 일정 관리 창에 대한 리스너!!
				sF = new subFrame(tmp_year, tmp_month + 1, tmp_date); // 일정관리 창
																		// 생성
				sF.tf.requestFocus(); // 서브 프레임 나타났을때 텍스트 필드가 포커스를 가짐
				for (int i = 0; i < list.search(node_name).count; i++) // 해당 노드의
																		// 모든 일정
																		// 출력
					sF.model.addElement(list.search(node_name).cal[i]);

				sF.tf.addActionListener(new ActionListener() { // textfield 리스너
					public void actionPerformed(ActionEvent e) {
						if (!sF.tf.getText().isEmpty()) { // 텍스트 필드에 뭐가 있다면
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
							list.search(node_name).setCal(time + sF.tf.getText()); // 선택
																					// 날짜
																					// 노드에
																					// 텍스트
																					// 필드에
																					// 있는
																					// 일정을
																					// 추가
							sF.tf.setText("");
							sF.tf.requestFocus();
							sF.model.clear(); // 서브 프레임 리스트 초기화
							for (int i = 0; i < list.search(node_name).count; i++) // 해당
																					// 노드의
																					// 모든
																					// 일정
																					// 출력
								sF.model.addElement(list.search(node_name).cal[i]);
						}
					}
				});

				for (int i = 0; i < 4; i++) {
					sF.btn[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JButton bt_tmp = (JButton) e.getSource();
							if (bt_tmp.equals(sF.btn[0])) { // 추가 버튼 이벤트
								if (!sF.tf.getText().isEmpty()) { // 텍스트 필드에 뭐가
																	// 있다면
									String time = "";
									if (sF.time) // 일정 추가할 때 시간 사용 한다면 true
										time = "(" + sF.h + " : " + sF.m + ") ";
									list.search(node_name).setCal(time + sF.tf.getText()); // 선택
																							// 날짜
																							// 노드에
																							// 텍스트
																							// 필드에
																							// 있는
																							// 일정을
																							// 추가
									sF.tf.setText(""); // 추가 후에 textfield를 클리어
									sF.tf.requestFocus(); // 클리어 후 바로 일정 쓸수 있게
															// textfield에 포커스를 줌

									sF.model.clear(); // 서브 프레임 리스트 초기화
									for (int i = 0; i < list.search(node_name).count; i++) // 해당
																							// 노드의
																							// 모든
																							// 일정
																							// 출력
										sF.model.addElement(list.search(node_name).cal[i]);
								}
							} else if (!(bt_tmp.equals(sF.btn[3]))) { // 수정, 삭제
																		// 버튼에
																		// 대한
																		// 이벤트
								int idx = sF.list.getSelectedIndex();
								if (idx != -1) {
									if (bt_tmp.equals(sF.btn[1])) // 수정버튼이라면
										sF.tf.setText(list.search(node_name).cal[idx]); // 선택된
																						// 리스트
																						// 아이템을
																						// textfield에
																						// 올려두고
									list.search(node_name).delCal(idx); // 지움
									sF.model.clear(); // 서브 프레임 리스트 초기화
									for (int i = 0; i < list.search(node_name).count; i++) // 해당
																							// 노드의
																							// 모든
																							// 일정
																							// 출력
										sF.model.addElement(list.search(node_name).cal[i]);

									if (idx != 0)
										sF.list.setSelectedIndex(idx - 1); // 선택된
																			// 리스트
																			// 아이템이
																			// 0이
																			// 아니라면
																			// 삭제
																			// 후
																			// 위의
																			// 아이템
																			// 선택
									else
										sF.tf.requestFocus();
								}
							} else { // 취소 버튼에 대한 이벤트 설정
								model.clear(); // 메인 프레임 리스트 초기화
								for (int i = 0; i < list.search(node_name).count; i++)
									model.addElement(list.search(node_name).cal[i]); // 새로
																						// 업데이트된
																						// 리스트
																						// 출력
								sF.dispose(); // 두번째 프레임 종료
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

	public void setting() { // 바뀐 년 월에 맞게 날짜 버튼들 위치 초기화(중앙 첫 초기화때랑 방식 비슷)
		tmp.set(Calendar.DAY_OF_MONTH, 1);
		mon_fir = tmp.get(Calendar.DAY_OF_WEEK) - 1;
		mon_last = tmp.getActualMaximum(Calendar.DATE);
		for (int i = 0; i < 42; i++) {
			pl_day.remove(btn_day[i]);

			if ((i - mon_fir + 2) == day + 1 && (year == tmp_year) && (month - 1 == tmp_month))
				btn_day[i].setBackground(Color.DARK_GRAY);
			else
				btn_day[i].setBackground(Color.LIGHT_GRAY); // 배경색깔

			if (i >= mon_fir && i < mon_last + mon_fir) { // 해당 1일부터 말일까지 위치에 맞게
															// 세팅
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
				btn_day[i].setForeground(Color.RED); // 글자 색깔
			else
				btn_day[i].setForeground(Color.WHITE);

			btn_day[i].setBorderPainted(false);
			btn_day[i].setFont(new Font("고딕체", Font.ITALIC, 18));
			btn_day[i].addActionListener(new btn_day_Listener());
		}
	}

	boolean isCal(String name) { // 일정이 있나 없나 체크
		if (list.search(name) != null)
			if (list.search(name).getcnt() > 0)
				return true;
		return false;
	}

	public static void main(String[] args) {
		new pl_cal();
	}
}