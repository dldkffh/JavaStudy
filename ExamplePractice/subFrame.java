package abcd;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
 
public class subFrame extends JFrame{
	Container contentpane = getContentPane();
	
	int year = 0;
	int month = 0;
	int day = 0;
	public int h = 0;	// �ð�
	public int m = 0;	// ��
	boolean time = false;		// Ʈ��� �ð� ���
	boolean selected = false;
	
	//////////////////////////
	
	JPanel pl_total = new JPanel();
	JPanel pl_date = new JPanel();
	JLabel lb_date = new JLabel();
	
	///////////////////////////
	
	JPanel pl_middle = new JPanel();
	JPanel pl_t = new JPanel();	// �ð� ���� ��� ���Ƴ��� �г�
	
	JPanel pl_cb = new JPanel();
	String hour[] = {"00","01","02","03","04","05","06","07","08","09","10","11"};
	String min[] = new String[60];
	JComboBox cb_h = new JComboBox(hour);
	JComboBox cb_m;
	JPanel pl_rb = new JPanel();
	JRadioButton meridiem[] = new JRadioButton[3];	// 0: ����, 1 : ����, 2 : �ð� X
	
	JPanel pl_btn = new JPanel();	// ��� ��ư�� ���Ƴ��� �г�
	String str[] = {"�߰�","����","����","�ݱ�"};
	JButton btn[] = new JButton[4];	// 0 : �߰�, 1 : ����, 2 : ����, 3 : �ݱ�
	
	///////////////////////////
	
	JPanel pl_list = new JPanel();
	JTextField tf = new JTextField(); // ���� �Է�
	JList list;
	DefaultListModel model;
	
	subFrame(int year, int month, int day){
		setTitle("Main");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	// Xǥ ������ ���� �������� �Ȳ���
		this.year = year;
		this.month = month;
		this.day = day;
		list = new JList(new DefaultListModel());
		model = (DefaultListModel) list.getModel();
		
		makeGUI();
		
		setSize(450, 600);
		setVisible(true);
		this.setLocation(610, 100);
	}
	
	void makeGUI() {
		pl_total.setLayout(new BorderLayout());
		setN(); // ��¥ �г�
		setM(); // �߰� �г� (textfield, list)
		pl_total.add(pl_date, BorderLayout.NORTH);
		pl_total.add(pl_middle, BorderLayout.SOUTH);
		
		add(pl_total, BorderLayout.NORTH);
		
		setL();	// ����Ʈ
	}

	void setN() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, day);
		pl_date.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		lb_date.setText(year + "�� " + month + "�� " + day + "�� " + doW(cal.get(Calendar.DAY_OF_WEEK)));
		lb_date.setFont(new Font("���ü", Font.ITALIC, 25));
		
		pl_date.add(lb_date);
	}
	
	String doW(int num) {	// ���� �ѱ۷� ��ȯ
		String dow = "�Ͽ���";
		switch(num) {
		case 2: dow = "������";break;
		case 3: dow = "ȭ����";break;
		case 4: dow = "������";break;
		case 5: dow = "�����";break;
		case 6: dow = "�ݿ���";break;
		case 7: dow = "�����";break;
		}
		return dow;
	}
	
	void setM() {
		pl_middle.setLayout(new BorderLayout());	//  �߰� �г� ���̾ƿ� ����. �� : �ð� �г�, �� : ��ư �г�
		pl_t.setLayout(new BorderLayout());
		
		for(int i = 0 ; i < 60 ; i++) min[i] = new Integer(i).toString();	
		cb_m = new JComboBox(min);			// combobox �� �ʱ�ȭ
		pl_cb.add(cb_h);					// combobox �гο� �޺��ڽ� ��, �� �߰�
		pl_cb.add(cb_m);
		pl_t.add(pl_cb, BorderLayout.CENTER);
		cb_h.setEnabled(false);
		cb_h.addActionListener(new cbboxListener());
		cb_m.setEnabled(false);
		cb_m.addActionListener(new cbboxListener());
		
		
		//////// �ð� �г�  /////// (�� : ��, �� . �Ʒ� : ���� ��ư)
		
		ButtonGroup group = new ButtonGroup();
		meridiem[0] = new JRadioButton("����");
		meridiem[1] = new JRadioButton("����");
		meridiem[2] = new JRadioButton("�ð� X");
		
		for(int i = 0 ; i < 3; i++) {
			meridiem[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JRadioButton tmp = (JRadioButton)e.getSource();
					if (tmp.equals(meridiem[0])) {
						h = cb_h.getSelectedIndex() % 11;
						cb_h.setEnabled(true);
						cb_m.setEnabled(true);
						time = true;
					} else if (tmp.equals(meridiem[1])) {
						h = (cb_h.getSelectedIndex() % 11) + 12;
						cb_h.setEnabled(true);
						cb_m.setEnabled(true);
						time = true;
					}
					else {
						cb_h.setEnabled(false);
						cb_m.setEnabled(false);
						time = false;
					}
				}
			});
			meridiem[2].setSelected(true);
			group.add(meridiem[i]);
			pl_rb.add(meridiem[i]);
		}
		
		pl_t.add(pl_rb, BorderLayout.SOUTH);
		pl_middle.add(pl_t, BorderLayout.CENTER);
		
		///////  ��ư �г� (�Ʒ�)  ///////
		
		pl_btn.setLayout(new GridLayout(2,2));
		
		for(int i = 0 ; i < 4 ; i++) {
			btn[i] = new JButton(str[i]);
			pl_btn.add(btn[i]);
		}
		pl_middle.add(pl_btn, BorderLayout.EAST);
	}
	
	void setL() {
		pl_list.setLayout(new BorderLayout());
		pl_list.add(tf, BorderLayout.NORTH);
		pl_list.add(new JScrollPane(list), BorderLayout.CENTER);
		add(pl_list, BorderLayout.CENTER);
	}
	
	class cbboxListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JComboBox tmp = (JComboBox)e.getSource();
			if(tmp.equals(cb_h)) {
				if(meridiem[0].isSelected())
					h = cb_h.getSelectedIndex() % 11;
				else
					h = (cb_h.getSelectedIndex() % 11) + 12;
			}
			else m = tmp.getSelectedIndex();
		}
	}
}