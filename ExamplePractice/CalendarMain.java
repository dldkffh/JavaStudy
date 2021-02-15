import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class CalendarMain extends JFrame {
	Calendar cal = Calendar.getInstance();
	CardLayout cd = new CardLayout();
	Container contentpane = getContentPane();
	
	////////////////////////////////////
	
	int year = cal.get(Calendar.YEAR);
	int month;
	
	////////////////////////////////////
	
	All_sch fr_2;
	
	Calendar_1 pl_1 = new Calendar_1(year);
	Calendar_2 pl_2 = new Calendar_2();
	Calendar_3 pl_3 = new Calendar_3();
	
	CalendarMain(){
		setTitle("Calendar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		makeGUI();
		
		setSize(500, 500);
		setLocation(400, 200);
		setResizable(false); // 크기 고정
		setVisible(true);
	}
	
	void makeGUI() {
		setLayout(cd);
		
		set_pl_3();
		set_pl_1();
		set_pl_2();
		
		add(pl_3);		
		add(pl_1);
		add(pl_2);
	}
	
	void set_pl_3() {													// 달력
		pl_3.btn_year.addActionListener(new ActionListener() {			// 년도 설정으로 이동
			public void actionPerformed(ActionEvent e) {
				pl_1.year = pl_3.tmp_year;
				pl_1.btn_Update();
				setSize(500, 350);
				cd.next(contentpane);
			}
		});
		pl_3.btn_month.addActionListener(new ActionListener() {			// 달 설정으로 이동
			public void actionPerformed(ActionEvent e) {
				setSize(500, 350);
				cd.last(contentpane);
			}
		});
	}
	
	void set_pl_1() {
		for(int i = 0 ; i < 12 ; i++) {
			pl_1.btn_year[i].addActionListener(new ActionListener() {	// 년도 버튼 이벤트
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton)e.getSource();
					year = new Integer(btn.getText()).intValue();
					pl_3.setapi(year);
					cd.next(contentpane);
				}
			});
		}
		pl_1.btn_td.addActionListener(new pl_3_init_Listener());		// 오늘 버튼
	}
	
	void set_pl_2() {													// 달 패널
		for(int i = 0 ; i < 12 ; i++) {									
			pl_2.btn_month[i].addActionListener(new ActionListener() {	// 달 버튼 클릭시
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton)e.getSource();
					month = new Integer(btn.getText()).intValue();
					pl_3.init();
					pl_3.shift_day(year, month, 1);
					setSize(500, 00);
					cd.first(contentpane);
				}
			});
		}
		pl_2.btn_td.addActionListener(new pl_3_init_Listener());		// 오늘 버튼
	}
	
	class pl_3_init_Listener implements ActionListener{					// 오늘 버튼 이벤트
		public void actionPerformed(ActionEvent e) {
			pl_3.init();
			pl_3.shift_day(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1);
			setSize(500, 900);
			//pl_3.showList(0);
			cd.first(contentpane);
		}
	}
	
	public static void main(String[] args) {
		new CalendarMain();
	}
}

class TimerThread extends Thread{
	JButton timerButton;
	
	public TimerThread(JButton timerButton) {
		this.timerButton = timerButton;
	}
	public void run() {
		while(true) {
			timerButton.setText(Calendar.getInstance().getTime().toLocaleString());
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e){
				return;
			}
		}
	}
}