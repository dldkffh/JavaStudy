package Album;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.tree.*;

import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class Main_Frame extends JFrame {
	private String path_home = System.getProperty("user.home") + "\\Desktop";	// ����ȭ�� ���
	MyTree top;
	private MyTree root;			// Tree�� Root
	private JTree tree;				// root ��
		
	private DefaultTreeModel model;
	
	private Container contentpane = getContentPane();
	
	private Pl_1 panel_2;
	
	private CardLayout cd = new CardLayout();
		
	Main_Frame(){
		setTitle("Album");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(cd);
		setSize(1400, 800);
		setVisible(true);
		init();
		makeGUI();
		
//		setResizable(false); // ũ�� ����
		setVisible(true);
	}
	
	void makeGUI() {
		this.addComponentListener(new Resize_Listener());
	    add(panel_2);
	}
	
	void init() {
		top = new MyTree(null, null); // MyFolder root ��ü
		root = new MyTree("Images", path_home); // MyFolder root ��ü
		tree = new JTree(top); // tree ����
		model = (DefaultTreeModel) tree.getModel(); // Ʈ�� ��
		
		model.insertNodeInto(root, top, top.getChildCount());
		
		panel_2 = new Pl_1(root, tree, getContentPane().getSize());
	}
	
	public void open_file() {			// ���� ����        
        JFileChooser fileChooser = new JFileChooser(path_home);	// ����ȭ�� ����
        
        String[] extension = { "bmp", "jpg", "gif", "jpeg", "png", "tiff" , "PNG", "JPG", "JPEG", "GIF"};	// ����
        FileNameExtensionFilter filter = new FileNameExtensionFilter("�̹��� ����(bmp, jpg, gif, jpeg, png)", extension);
        fileChooser.setAcceptAllFileFilterUsed(false);	// ������� ǥ�� X
        fileChooser.addChoosableFileFilter(filter);
        
        //���Ͽ��� ���̾�α� �� ���
        int result = fileChooser.showOpenDialog(this);
        
//        fileChooser.showSaveDialog(this);
         
        if (result == JFileChooser.APPROVE_OPTION) {
            //������ ������ ��� ��ȯ
            File selectedFile = fileChooser.getSelectedFile();
             
            //��� ���
            System.out.println(selectedFile);
        }
	}
	
	public void remove_tree() {	// ���� ����
		TreePath selectionPath = tree.getSelectionPath();
		MyTree selectedNode = (MyTree) selectionPath.getLastPathComponent();
		if(!selectedNode.isRoot()) {
			if (selectedNode.remove_Folder())
				model.removeNodeFromParent(selectedNode);
		}
	}
	
	public void create_tree(String msg) {// ���� ����
		String name = msg;
		boolean test = true;
		
		TreePath selectionPath = tree.getSelectionPath();
		MyTree selectedNode = (MyTree) selectionPath.getLastPathComponent();
		for(MyTree i : selectedNode.get_Folder())
			if(name.equals(i.get_name())) test = false;
		if(test) {
			MyTree newNode = new MyTree(name, selectedNode.get_path());	
			selectedNode.get_Folder().add(newNode);
			model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
		}
	}
	
	class RemoveFd_Listener implements ActionListener {	// ���� ��ư
		public void actionPerformed(ActionEvent e) {
			JButton tmp = (JButton)e.getSource();
			remove_tree();
		}
	}
	class CreateFd_Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {	// ���� ��ư
			String msg = JOptionPane.showInputDialog("������ �Է�");
			if(msg != null)
			create_tree(msg);
		}
	}
	
	public static void main(String[] args) {
		new Main_Frame();
	}
	class Resize_Listener extends ComponentAdapter{
		public void componentResized(ComponentEvent e) {
			JFrame tmp = (JFrame)e.getSource();
			panel_2.resize_Pl(contentpane.getSize());
		}
	}
}

class MyScrollPane extends BasicScrollBarUI {
	ImageIcon imgic = new ImageIcon("�̹��� ���");
	Image img = imgic.getImage();
	
	protected void configureScrollBarColors() {
		thumbRect.width = 0;
		trackRect.width = 0;

		thumbColor = new Color(0x686868);
		thumbDarkShadowColor = new Color(0xEDA900);
		thumbHighlightColor = new Color(0xEDA900);
		thumbLightShadowColor = new Color(0xEDA900);
		
		trackColor = new Color(0xFFFFFF);
		trackHighlightColor = new Color(0x120232);

		// Thumb�� �ִ�, �ּ�ũ��
    	getMaximumThumbSize().height = 100;
    	getMinimumThumbSize().height = 30;
	}

	@Override	// �� ȭ��ǥ ��ư 
	protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override   // �Ʒ� ȭ��ǥ ��ư 
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton jbutton = new JButton();
        jbutton.setPreferredSize(new Dimension(0, 0));
        jbutton.setMinimumSize(new Dimension(0, 0));
        jbutton.setMaximumSize(new Dimension(0, 0));
        return jbutton;
    }
    			// Thumb�� ��� ����
    @Override protected void paintThumb (Graphics g, JComponent c, Rectangle r) {
    	g.setColor(new Color(0x686868));
        g.fillRoundRect(r.x, r.y, 12, r.height,3,3);
    }			// Track�� ��� ����
    @Override protected void paintTrack (Graphics g, JComponent c, Rectangle r) {
    	g.setColor(new Color(0xFFFFFF));
        g.fillRect(2,0,8,r.height);
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