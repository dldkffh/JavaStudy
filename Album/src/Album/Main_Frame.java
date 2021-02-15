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
	private String path_home = System.getProperty("user.home") + "\\Desktop";	// 바탕화면 경로
	MyTree top;
	private MyTree root;			// Tree의 Root
	private JTree tree;				// root 모델
		
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
		
//		setResizable(false); // 크기 고정
		setVisible(true);
	}
	
	void makeGUI() {
		this.addComponentListener(new Resize_Listener());
	    add(panel_2);
	}
	
	void init() {
		top = new MyTree(null, null); // MyFolder root 객체
		root = new MyTree("Images", path_home); // MyFolder root 객체
		tree = new JTree(top); // tree 생성
		model = (DefaultTreeModel) tree.getModel(); // 트리 모델
		
		model.insertNodeInto(root, top, top.getChildCount());
		
		panel_2 = new Pl_1(root, tree, getContentPane().getSize());
	}
	
	public void open_file() {			// 파일 열기        
        JFileChooser fileChooser = new JFileChooser(path_home);	// 바탕화면 참조
        
        String[] extension = { "bmp", "jpg", "gif", "jpeg", "png", "tiff" , "PNG", "JPG", "JPEG", "GIF"};	// 필터
        FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지 파일(bmp, jpg, gif, jpeg, png)", extension);
        fileChooser.setAcceptAllFileFilterUsed(false);	// 모든파일 표시 X
        fileChooser.addChoosableFileFilter(filter);
        
        //파일오픈 다이얼로그 를 띄움
        int result = fileChooser.showOpenDialog(this);
        
//        fileChooser.showSaveDialog(this);
         
        if (result == JFileChooser.APPROVE_OPTION) {
            //선택한 파일의 경로 반환
            File selectedFile = fileChooser.getSelectedFile();
             
            //경로 출력
            System.out.println(selectedFile);
        }
	}
	
	public void remove_tree() {	// 폴더 삭제
		TreePath selectionPath = tree.getSelectionPath();
		MyTree selectedNode = (MyTree) selectionPath.getLastPathComponent();
		if(!selectedNode.isRoot()) {
			if (selectedNode.remove_Folder())
				model.removeNodeFromParent(selectedNode);
		}
	}
	
	public void create_tree(String msg) {// 폴더 생성
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
	
	class RemoveFd_Listener implements ActionListener {	// 삭제 버튼
		public void actionPerformed(ActionEvent e) {
			JButton tmp = (JButton)e.getSource();
			remove_tree();
		}
	}
	class CreateFd_Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {	// 생성 버튼
			String msg = JOptionPane.showInputDialog("폴더명 입력");
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
	ImageIcon imgic = new ImageIcon("이미지 경로");
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

		// Thumb의 최대, 최소크기
    	getMaximumThumbSize().height = 100;
    	getMinimumThumbSize().height = 30;
	}

	@Override	// 위 화살표 버튼 
	protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override   // 아래 화살표 버튼 
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
    			// Thumb의 모양 설정
    @Override protected void paintThumb (Graphics g, JComponent c, Rectangle r) {
    	g.setColor(new Color(0x686868));
        g.fillRoundRect(r.x, r.y, 12, r.height,3,3);
    }			// Track의 모양 설정
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