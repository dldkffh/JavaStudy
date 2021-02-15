import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class JTextFieldandJComboBox extends JFrame{
	Container contentPane;
	String [] names = {};
	
	JTextFieldandJComboBox() {
		setTitle("JTextField andJ ComboBox");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		
		contentPane.add(new JTextField(10));
		JComboBox strCombo = new JComboBox(names);
		contentPane.add(strCombo);

		
		strCombo.addKeyListener(new KeyListener() {
			public void KeyListener(ActionEvent e) {
				if(getKeyCode(e) == KeyEvent.VK_ENTER)
					;
			}
			private int getKeyCode(ActionEvent e) {
				// TODO Auto-generated method stub
				return 0;
			}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
			
		});
		
		setSize(300, 300);
		setVisible(true);
		
	}
	
	
	public static void main(String[] args) {
		new JTextFieldandJComboBox();
	}
}
