import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Calendar_2 extends JPanel{
	Color mainWhite = new Color(0xF5F4F3);
	Calendar cal = Calendar.getInstance();
	
	//////////////////////////////////
	
	JPanel pl_top = new JPanel();		// 상단 상태 패널
	
	JButton btn_td = new JButton();
	TimerThread th = new TimerThread(btn_td);
	
	//////////////////////////////////
	
	JPanel pl_bottom = new JPanel();		// 하단 버튼 패널
	JButton btn_month[] = new JButton[12];
	
	Calendar_2(){
		setLayout(new BorderLayout(0,0));
		
		init();
		
		makeGUI();

		setSize(500, 350);
		setLocation(200, 100);
		setVisible(true);
		
		th.start();
	}
	
	void init() {
		for(int i = 0 ; i < 12 ; i++) {
			btn_month[i] = new JButton((i + 1) + "");
			btn_month[i].setBorderPainted(true);
			if(i == cal.get(Calendar.MONTH))
				btn_month[i].setBackground(Color.LIGHT_GRAY);
			else btn_month[i].setBackground(mainWhite);
			btn_month[i].setForeground(Color.BLACK);
		}
	}
	
	void makeGUI(){
		setTop();
		setBottom();
	}
	
	void setTop() {
		pl_top.setPreferredSize(new Dimension(500,40));
		pl_top.setLayout(null);
		
		btn_td.setFont(new Font("고딕체", Font.BOLD, 20));
		btn_td.setBounds(-10, 0, 280, 40);
		btn_td.setBackground(mainWhite);
		btn_td.setForeground(Color.BLACK);
		btn_td.setBorderPainted(false);
		
		pl_top.add(btn_td);
		pl_top.setBackground(mainWhite);
		
		add(pl_top, BorderLayout.NORTH);
	}
	
	void setBottom() {
		pl_bottom.setLayout(new GridLayout(3,4));
		for(int i = 0 ; i < 12 ; i++)
			pl_bottom.add(btn_month[i]);
		
		add(pl_bottom, BorderLayout.CENTER);
	}
	
	class yearListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
		}
	}
}