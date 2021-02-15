import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.awt.*;
import java.util.*;

public class Calendar_1 extends JPanel{
	Color mainWhite = new Color(0xF5F4F3);
	//////////////////////////////////
	
	JPanel pl_top = new JPanel();		// 상단 상태 패널

	URL url_r = getClass().getResource("rright.png");
	URL url_l = getClass().getResource("lleft.png");
	
	ImageIcon right = new ImageIcon(url_r);
	ImageIcon left = new ImageIcon(url_l);
	JButton btn_left = new JButton();	// 왼쪽
	JButton btn_right = new JButton();	// 오른쪽
	
	JButton btn_td = new JButton();
	TimerThread th = new TimerThread(btn_td);
	
	//////////////////////////////////
	
	
	JPanel pl_bottom = new JPanel();		// 하단 버튼 패널
	JButton btn_year[] = new JButton[12];
	
	int year;	
	int min;
	
	Calendar_1(int year){
		setLayout(new BorderLayout(0,0));
		
		makeGUI(year);
		
		setSize(500, 350);
		setLocation(200, 100);
		setVisible(true);
		
		th.start();
	}
	
	void makeGUI(int year){
		this.year = year;
		min = year - (year % 10);
		for(int i = 0 ; i < 12 ; i++) {
			btn_year[i] = new JButton((min + i) + "");
			btn_year[i].setBorderPainted(true);
			if((min + i) == Calendar.getInstance().get(Calendar.YEAR))  // 올해라면
				btn_year[i].setBackground(Color.LIGHT_GRAY);
			else btn_year[i].setBackground(mainWhite);
			btn_year[i].setForeground(Color.BLACK);
		}
		
		setTop();
		setBottom();
	}
	
	public void btn_Update() {
		this.year = year;
		min = year - (year % 10);
		for(int i = 0 ; i < 12 ; i++) {
			btn_year[i].setText((min + i) + "");
			if((min + i) == Calendar.getInstance().get(Calendar.YEAR))  // 올해라면
				btn_year[i].setBackground(Color.LIGHT_GRAY);
			else btn_year[i].setBackground(mainWhite);
		}
	}
	
	void setTop() {
		pl_top.setPreferredSize(new Dimension(500,40));
		pl_top.setLayout(null);
		
		btn_td.setFont(new Font("고딕체", Font.BOLD, 20));
		btn_td.setBounds(-10, 0, 280, 40);
	
		btn_td.setBackground(mainWhite);
		btn_td.setForeground(Color.BLACK);
		btn_td.setBorderPainted(false);
		
		btn_left.setBounds(398, 6, 30, 30);
		btn_left.addActionListener(new btnListener());
		btn_left.setBackground(mainWhite);
		btn_left.setBorderPainted(false);
		btn_left.setIcon(left);
		
		btn_right.setBounds(442, 6, 30, 30);
		btn_right.addActionListener(new btnListener());
		btn_right.setBackground(mainWhite);
		btn_right.setBorderPainted(false);
		btn_right.setIcon(right);
		
		pl_top.add(btn_left);
		pl_top.add(btn_right);
		pl_top.add(btn_td);
		pl_top.setBackground(mainWhite);
		
		add(pl_top, BorderLayout.NORTH);
	}
	
	void setBottom() {
		pl_bottom.setLayout(new GridLayout(3,4));
		for(int i = 0 ; i < 12 ; i++)
			pl_bottom.add(btn_year[i]);
		
		add(pl_bottom, BorderLayout.CENTER);
	}
	
	class btnListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int tmp;
			if(e.getSource() == btn_right) tmp = min+12;
			else tmp = min-12;
			for (int i = 0; i < 12; i++) {
				btn_year[i].setText((tmp + i) + "");
				if((tmp + i) == Calendar.getInstance().get(Calendar.YEAR))  // 올해라면
					btn_year[i].setBackground(Color.LIGHT_GRAY);
				else btn_year[i].setBackground(mainWhite);
			}
			min = tmp;
		}
	}
	
	class yearListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
		}
	}
}
