package Album;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import java.util.*;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.UINT_PTR;
import com.sun.jna.win32.*;

class Dialog_frame extends JDialog {
	private String path_home = System.getProperty("user.home") + "\\Desktop";
	MyImageIcon img;
	
	JPanel pl_img = new JPanel();
	JLabel lb_img;
	
	JPanel pl_data = new JPanel();
	JLabel lb_name;
	JLabel name;
	JLabel lb_length;
	JLabel length;
	JLabel lb_size;
	JLabel size;
	JLabel lb_date;
	JLabel date;
	
	
	JPanel pl_center = new JPanel();
	JScrollPane scroll;
	JTextArea comment; // �ڸ�Ʈ
	JButton savebtn = new JButton("����");
	
	
	JPanel pl_bottom = new JPanel();
	JLabel lb_path_img;
	JLabel lb_path_sound;
	JButton btn_sound = new JButton("����");
	
	Dialog_frame(JFrame frame, String title, MyImageIcon img) {
		super(frame, title, true);
		setLayout(null);
		this.img = new MyImageIcon(img.get_Name(), 0, img.get_Path());
		
		////////////////////////////////////////
		
		BufferedImage newImage = new BufferedImage(125, 145, BufferedImage.TYPE_INT_RGB );
    	Graphics gg= newImage.getGraphics();
    	gg.fillRect(0, 0, 125, 145);
    	gg.drawImage(img.getImage(), 0, 0, 125, 145, this);
    	this.img.setImage(newImage);
    	
		lb_img = new JLabel(this.img);
		lb_img.setBounds(0,0,130,150);
		lb_img.setBackground(Color.GRAY);
		lb_img.setOpaque(true);
		
		lb_name = new JLabel("���� �̸�");
		name = new JLabel("     " + img.get_Name());
		lb_length = new JLabel("���� ũ��");
		length = new JLabel("     " + (new Long(new File(img.get_Path()).length()/1024).toString()) + "KB");
		lb_size = new JLabel("���� ������");
		size = new JLabel("     " + img.getIconWidth() + "(w) X " + img.getIconHeight() + "(h)");
		lb_date = new JLabel("������ ���� ��¥");
		date = new JLabel("     " + img.get_Date());
		
		pl_data.add(lb_name);
		pl_data.add(name);
		pl_data.add(lb_length);
		pl_data.add(length);
		pl_data.add(lb_size);
		pl_data.add(size);
		pl_data.add(lb_date);
		pl_data.add(date);
		pl_data.setLayout(new GridLayout(8,1));
		pl_data.setBounds(132,0,200,150);
		pl_data.setBackground(Color.WHITE);
		
		/////////////////////////////////////////
		
		pl_center.setLayout(new BorderLayout());
		
		comment = new JTextArea(img.get_comment(),3,17); // �ڸ�Ʈ
		savebtn = new JButton("����");
		savebtn.setBackground(Color.WHITE);
		savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String c = comment.getText();
				img.set_Comment(c); //set_Comment�� �ϱ� ���� ����â�� �������� get_
			}
		});
		scroll = new JScrollPane(comment);
		scroll.getVerticalScrollBar().setUI(new MyScrollPane());
		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(12,getContentPane().getHeight()));
		
		pl_center.add(scroll, BorderLayout.CENTER);
		pl_center.add(savebtn, BorderLayout.EAST);
		
		pl_center.setBounds(0,150,283,130);
		pl_center.setBackground(Color.WHITE);
		
		/////////////////////////////////////////
		
		lb_path_img = new JLabel("���� ��� : " + img.get_Path());
		lb_path_img.setBounds(2,290,283,13);
		lb_path_img.setBackground(Color.WHITE);
		lb_path_img.setOpaque(true);
		
		lb_path_sound = new JLabel("�Ҹ� ��� : " + img.get_sound());
		lb_path_sound.setBounds(2,315,240,15);
		lb_path_sound.setBackground(Color.WHITE);
		lb_path_sound.setOpaque(true);
		
		btn_sound = new JButton("����");
		btn_sound.setBounds(245,315,35,15);
		btn_sound.setBackground(Color.WHITE);
		btn_sound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path_sound = open_file();
				lb_path_sound.setText(path_sound);
				img.set_sound(path_sound);
			}
		});
		
		/////////////////////////////////////////
		
		getContentPane().add(lb_img);
		getContentPane().add(pl_data);
		getContentPane().add(pl_center);
		getContentPane().add(lb_path_img);
		getContentPane().add(lb_path_sound);
		getContentPane().add(btn_sound);
		getContentPane().setBackground(Color.WHITE);
		
		setResizable(false);
		setSize(300, 380); //��¦ ������
		setVisible(true);
	}
	
	public String open_file() {			// ���� ����        
        JFileChooser fileChooser = new JFileChooser(path_home);	// ����ȭ�� ����
        
        String[] extension = {"wav", "WAV"};	// ����
        FileNameExtensionFilter filter = new FileNameExtensionFilter("�Ҹ� ���� (wav)", extension);
        fileChooser.setAcceptAllFileFilterUsed(false);	// ������� ǥ�� X
        fileChooser.addChoosableFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
         
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
             
            return selectedFile.getPath();
        }
        
        return null;
	}
}