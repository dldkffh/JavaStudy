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

public class Pl_1 extends JPanel {
	private String path_home = System.getProperty("user.home") + "\\Desktop";
	private static Point point = new Point();
	private MyTree root;			// Tree�� Root
	private MyTree node;			// �ӽ� ���
	private JTree tree;				// root ��
	
	private Pl_1_Tap tap;			// tap
	private Vector<String> favorite;

	private Panel_menu pl_menu;
	private Panel_Image pl_img;
	private MyImageIcon imgic;
	private MyImageIcon img_null;
	private JButton btn_visible;
	private int mode = 0;
	
	/////////////////// ũ�� ����
	Dimension dm_img;
	Dimension dm_pl;
	Dimension dm_tmp;
	double ratio_pl;					// ȭ�� ����
	double ratio_img;					// ������ ����
	double ratio;
	int x_img;
	int y_img;
	//////////////////////
	private Dimension size;			// Main_Frame�� ����Ʈ �� ������
	private int width;				// �̹����� width
	private int height;				// �̹����� height
	
	private DefaultTreeModel model;
	MyThread thread;
	MyTree selectedNode;
	/////////////////////
	private RemoteControl cont;
	private AudioInputStream stream;
	
	Pl_1(MyTree root, JTree tree, Dimension size){
		setLayout(null);
		this.size = size;
		width = size.width;
		height = size.height;
		
		this.setSize(700,400);
		
		makeGUI(root, tree);
		
		setVisible(true);
	}
	
	void makeGUI(MyTree root, JTree tree) {
		setBackground(Color.WHITE);
		
		init(root, tree);		// ���� �ʱ�ȭ
		set_event();			// �̺�Ʈ ����
		add(pl_menu);
		add(btn_visible);
		add(pl_img);
		add(tap);
	}
	
	void init(MyTree root, JTree tree) {
		this.root = root;								// MyFolder root ��ü
		this.node = root;
		this.selectedNode = root;
		this.tree = tree;								// tree ����
	    this.model = (DefaultTreeModel)tree.getModel();
	    this.favorite = root.get_Favorite();
	    
	    tree.expandPath(new TreePath(root.getPath()));	// root Ȯ��
	  
	    pl_menu = new Panel_menu();
		pl_menu.setBounds(size.width/5, 0, size.width - size.width/5, 23);
		
		tap = new Pl_1_Tap(root, tree, favorite);
		tap.setBounds(0, 0, size.width/5, size.height);
		
		btn_visible = new JButton("test");
		btn_visible.setBounds(size.width/5, size.height/2-50, 10, 100);
		
		cont = new RemoteControl((JFrame)this.getParent());
		cont.setAlwaysOnTop(true);
		cont.setVisible(false);
				
		set_img(root.get_Images().get(0), true);
	}
	
	private void set_event() {
		tap.tap_dir.get_btn()[0].addActionListener(new CreateFd_Listener());
		tap.tap_dir.get_btn()[1].addActionListener(new RemoveFd_Listener());
		tap.tap_dir.get_btn()[2].addActionListener(new OpenFd_Listener());
		tap.tap_like.get_list().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					if (!tap.tap_like.get_list().isSelectionEmpty()) {
						set_img(tap.tap_like.get_img(), false);
						pl_menu.cb[1].setSelected(true);
					}
			}
		});
		tap.tap_img.get_list().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (!tap.tap_img.get_list().isSelectionEmpty())
						set_img(tap.tap_img.get_img(), false);
				}
			}
		});
		tap.tap_img.get_btn()[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File tmp_file = new File(open_img());
				selectedNode.copy_img(new MyImageIcon(tmp_file.getName(),
											  tmp_file.lastModified(),
											  tmp_file.getPath()));
				tap.tap_img.change_Image(selectedNode);
			}
		});
		pl_img.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (btn_visible.isVisible()) {
					if(mode == 1) pl_menu.setVisible(false);
					btn_visible.setVisible(false);
//					pl_control.setVisible(false);
				} else {
					if(mode == 1) pl_menu.setVisible(true);
					btn_visible.setVisible(true);
//					pl_control.setVisible(true);
				}
			}
		});
		tap.get_tap().addChangeListener(new ChangeListener() { // �� �̵��Ҷ�
			public void stateChanged(ChangeEvent e) {
				JTabbedPane tmp = (JTabbedPane) e.getSource();
				if (selectedNode()) {
					if (tmp.getSelectedIndex() == 1) {
						tap.tap_img.change_Image(selectedNode);
						tap.tap_img.set_node(selectedNode);
						if (tap.tap_img.is_img())
							set_img(tap.tap_img.get_img(), false);
						else set_img(null, false);
					} else if (tmp.getSelectedIndex() == 2) {
						tap.tap_like.change_Image(selectedNode);
						tap.tap_like.set_node(selectedNode);

						if (tap.tap_like.is_favorite())
							set_img(tap.tap_like.get_img(), false);
						else set_img(null, false);
					}
					node = selectedNode;
				}
				if(tmp.getSelectedIndex() == 0)
					pl_menu.cb[1].setEnabled(true);
				else if(tmp.getSelectedIndex() == 1)
					pl_menu.cb[1].setEnabled(true);
				else if(tmp.getSelectedIndex() == 2){
					pl_menu.cb[1].setSelected(true);
					pl_menu.cb[1].setEnabled(false);
				}
			}
		});
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && tap.get_tap().getSelectedIndex() == 0) {
					if (selectedNode()) {
						tap.tap_img.change_Image(selectedNode);
						tap.tap_like.change_Image(selectedNode);
						if (!selectedNode.get_Images().isEmpty())
							set_img(selectedNode.get_Images().get(0), false);
						else set_img(img_null, false);
						// tap.get_tap().setSelectedIndex(1);
						node = selectedNode;
					}
				}
			}
		});
		//////
		btn_visible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode ^= 1;
				if (mode == 1) { // ��üȭ��
					tap.setVisible(false);

					pl_img.setBounds(0, 0, size.width, size.height);
					pl_menu.setBounds(0, 0, size.width, 23);

					dm_img = new Dimension(imgic.getIconWidth(), imgic.getIconHeight());
					dm_pl = new Dimension(size.width, size.height);

					set_ratio(!pl_menu.cb[0].isSelected());
					pl_img.repaint();

					btn_visible.setLocation(0, btn_visible.getY());
//					pl_control.setBounds(pl_menu.getWidth()/2+pl_menu.getX()-100,size.height-50, 200, 50);
				} else if (mode == 0) { // ���� ũ��
					tap.setVisible(true);

					dm_img = new Dimension(imgic.getIconWidth(), imgic.getIconHeight());
					dm_pl = new Dimension(size.width - size.width / 5, size.height - 23);

					set_ratio(!pl_menu.cb[0].isSelected());

					btn_visible.setLocation(size.width / 5, size.height / 2 - 50);
					pl_img.setBounds(size.width / 5, 23, size.width, size.height - 23);
					pl_menu.setBounds(size.width / 5, 0, size.width - size.width / 5, 23);
//					pl_control.setBounds(pl_menu.getWidth()/2+pl_menu.getX()-100,size.height-50, 200, 50);
					pl_img.repaint();
				}
			}
		});
	}
	
	public void set_img(MyImageIcon img, boolean first) {
		if(first) {
			dm_pl = new Dimension(size.width - size.width/5, size.height-23);
			BufferedImage imgbuf_null = new BufferedImage(dm_pl.width, dm_pl.height, BufferedImage.TYPE_INT_RGB);
			Graphics gg = imgbuf_null.getGraphics();
			gg.setColor(Color.WHITE);
			gg.fillRect(0, 0, dm_pl.width, dm_pl.height);
			gg.setFont(new Font("Godic", Font.BOLD, 25));
			gg.setColor(Color.RED);
			gg.drawString("ǥ���� �̹����� �����ϴ�.", dm_pl.width/2 - 150, dm_pl.height/2);
			gg.setColor(Color.BLACK);
			gg.drawLine(0, 0, dm_pl.width, dm_pl.height);
			gg.drawLine(dm_pl.width, 0, 0, dm_pl.height);
			
			img_null = new MyImageIcon(null, 0, null);
			img_null.setImage(imgbuf_null);
			imgic = img_null;

			pl_img = new Panel_Image();
		    pl_img.setSize(size.width - size.width/5, size.height-23);
		    pl_img.setLocation(size.width/5, 23);
		}
		if(img != null) {
			if (!node.get_Images().isEmpty()) {
				imgic = img;
				if (img.get_favorite()) pl_menu.cb[1].setSelected(true);
				else pl_menu.cb[1].setSelected(false);

				dm_img = new Dimension(imgic.getIconWidth(), imgic.getIconHeight());
				dm_pl = new Dimension(size.width - size.width / 5, size.height - 23);

				set_ratio(!pl_menu.cb[0].isSelected());

				if (!first) pl_img.repaint();
			}
		} else {
			imgic = img_null;
			pl_img.repaint();
		}
	}
	
	private void set_ratio(boolean choose) {	// ���� ���߱� choose true -> �г� ������ �°�, false -> ���� ����
    	ratio_pl = (double)dm_pl.height / dm_pl.width;					// ȭ�� ����
    	ratio_img = (double)imgic.getIconHeight() / imgic.getIconWidth();	// ������ ����
    	ratio = 0;
    	if(!choose) ratio = 1;		// ���� ����
    	else if(ratio_pl >= ratio_img) ratio = dm_pl.getWidth() / dm_img.getWidth();
    	else ratio = dm_pl.getHeight() / dm_img.getHeight();
    	
    	width = (int)(ratio * imgic.getIconWidth());
		height = (int)(ratio * imgic.getIconHeight());
		
		x_img = (dm_pl.width - width) / 2;
		y_img = (dm_pl.height - height) / 2;
	}
	
	class RemoveFd_Listener implements ActionListener {	// ���� ��ư
		public void actionPerformed(ActionEvent e) {
			remove_tree();
		}
		
		private void remove_tree() {	// ���� ����
			TreePath selectionPath = tree.getSelectionPath();
			MyTree selectedNode = (MyTree) selectionPath.getLastPathComponent();
			if(!selectedNode.isRoot()) {
				if (selectedNode.remove_Folder())
					model.removeNodeFromParent(selectedNode);
			}
		}
	}
	
	class CreateFd_Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {	// ���� ��ư
			String msg = JOptionPane.showInputDialog("������ �Է�");
			if(msg != null)
			create_tree(msg);
		}
		
		private void create_tree(String msg) {// ���� ����
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
	}
	
	class OpenFd_Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {	// ���� ��ư
			String tmp = open_file();
			if(tmp != null)
			create_tree(tmp);
		}
		
		private void create_tree(String msg) {// ���� ����
			String name = msg.substring(msg.lastIndexOf("\\")+1);
			String path = msg.substring(0, msg.lastIndexOf("\\"));
			boolean test = true;
			
			if(test) {
				MyTree newNode = new MyTree(name, path);
				model.insertNodeInto(newNode, (MyTree)root.getParent(), root.getParent().getChildCount());
			}
		}
	}
	
	public String open_file() {			// ���� ����        
       JFileChooser fileChooser = new JFileChooser(path_home);	// ����ȭ�� ����
       fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
       int result = fileChooser.showOpenDialog(this);
         
       if (result == JFileChooser.APPROVE_OPTION) {
           File selectedFile = fileChooser.getSelectedFile();
           System.out.println(selectedFile);
           return selectedFile.getPath();
       }
       return null;
	}
	
	public String open_img() { // �̹��� ����
		JFileChooser fileChooser = new JFileChooser(path_home); // ����ȭ�� ����

		String[] extension = { "bmp", "jpg", "gif", "jpeg", "png", "tiff", "PNG", "JPG", "JPEG", "GIF" }; // ����
		FileNameExtensionFilter filter = new FileNameExtensionFilter("�̹��� ����(bmp, jpg, gif, jpeg, png)", extension);
		fileChooser.setAcceptAllFileFilterUsed(false); // ������� ǥ�� X
		fileChooser.addChoosableFileFilter(filter);

		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			return selectedFile.getPath();
		}
		return null;
	}
	
	class Panel_Image extends JPanel {
		Image img = imgic.getImage();
		
		public boolean isimg() {
			return (img != null);
		}
		
		public void paintComponent(Graphics g) {
//			super.paintComponent(g);
			img = imgic.getImage();
			g.setColor(Color.DARK_GRAY);					// �г� ���
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(img, x_img, y_img, width, height, this);
		}
	}
	
	public void resize_Pl(Dimension dm) {
		this.size = dm;
		
		if(mode == 1) {	// ��üȭ��
			dm_pl = new Dimension(size.width, size.height);
			set_ratio(!pl_menu.cb[0].isSelected());
			tap.setBounds(0, 0, size.width/5, size.height);
			pl_img.setBounds(0, 0, size.width, size.height);
			pl_menu.setBounds(0, 0, size.width, 23);
			btn_visible.setLocation(0, size.height/2-50);
//			pl_control.setBounds(pl_menu.getWidth()/2+pl_menu.getX()-100,size.height-50, 200, 50);
		} else {	// �г� ����
			tap.setBounds(0, 0, size.width/5, size.height);
			pl_menu.setBounds(size.width/5, 0, size.width - size.width/5, 23);
			btn_visible.setBounds(size.width/5, size.height/2-50, 10, 100);
			pl_img.setBounds(size.width / 5, 23, size.width - size.width/5, size.height - 23);
//			pl_control.setBounds(pl_menu.getWidth()/2+pl_menu.getX()-100,size.height-50, 200, 50);
			set_img(imgic, false);
		} 
	}
	
	/////////////////// �޴� �г�  /////////////////////////
	
	class Panel_menu extends JPanel{
		JButton btn[] = new JButton[6];
		JCheckBox cb[] = new JCheckBox[2];
		
		String str_btn[] = {"����", "�̵�", "���ȭ��", "�����̵��", "������", "����"};
		String str_cb[] = {"���� ũ��","���ã��"};
		
		Panel_menu(){
			setLayout(new GridLayout(1,7,0,0));
			setBackground(Color.WHITE);
			for(int i = 0 ; i < 6 ; i++) {
				btn[i] = new JButton(str_btn[i]);
//				btn[i].setBorderPainted(false);
				btn[i].setBackground(Color.WHITE);
				btn[i].setFont(new Font("���ʷҵ���",Font.BOLD, 17));
				btn[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton tmp = (JButton)e.getSource();
						if(imgic != img_null)
						if(tmp.equals(btn[0])) {	// ����
							if(tap.get_tap().getSelectedIndex() == 1
									&& !node.get_Images().isEmpty()) {	// �̹����ǿ��� �̹��� �ִ� ����� ��
								if(!tap.tap_img.get_list().isSelectionEmpty()) {	// ���� �̹��� ���� ��
									thread = new MyThread(tap.tap_img.get_list().getSelectedIndices(), 0);
									thread.start();
								} else JOptionPane.showMessageDialog(null, "���õ� �̹����� �����ϴ�.");
							} else JOptionPane.showMessageDialog(null, "�ش� ������ �̹����� �����ϴ�.");
						} else if(tmp.equals(btn[2])) {	// ���ȭ��
							img_resize();											// ������ ���ȭ�� �ػ󵵿� �°� ��������
							set_WallPaper(root.get_path() + "\\tmp.jpg");
							File remove = new File(root.get_path() + "\\tmp.jpg");	// �ӽ� ���� ����, ����
							remove.delete();
							dm_pl.setSize(dm_tmp);
					    	set_ratio(!pl_menu.cb[0].isSelected());
						} else if(tmp.equals(btn[1])) {	// �̵�
							if(tap.get_tap().getSelectedIndex() == 1
									&& !node.get_Images().isEmpty()) {	// �̹����ǿ��� �̹��� �ִ� ����� ��
								if(!tap.tap_img.get_list().isSelectionEmpty()) {	// ���� �̹��� ���� ��
									int cnt[] = tap.tap_img.get_list().getSelectedIndices();
									thread = new MyThread(cnt, 1);
									thread.start();
								} else JOptionPane.showMessageDialog(null, "���õ� �̹����� �����ϴ�.");
							} else JOptionPane.showMessageDialog(null, "�ش� ������ �̹����� �����ϴ�.");
						} else if(tmp.equals(btn[3])) {	// �����̵��
							String result = JOptionPane.showInputDialog("�ð� ������ �Է��ϼ��� (x.x��, ���� : ESC)");
							
							new SlideShow(selectedNode, new Double(result).doubleValue());
						} else if(tmp.equals(btn[4])) {	// ������
							cont.setVisible(true);
						} else if(tmp.equals(btn[5])) {	// ����
							new Dialog_frame(null, "��������", imgic);
						}
					}
					
				});
				add(btn[i]);
			}
			for(int i = 0 ; i < 2 ; i++) {
				cb[i] = new JCheckBox(str_cb[i]);
				cb[i].setBackground(Color.WHITE);
				cb[i].setFont(new Font("���ʷҵ���",Font.BOLD, 17));
				cb[i].setHorizontalAlignment(SwingConstants.CENTER);
				cb[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JCheckBox tmp = (JCheckBox)e.getSource();
						if(imgic != img_null)
						if(tmp.equals(cb[0])) {
							if(tmp.isSelected()) {
								set_ratio(false);
								pl_img.repaint();
							} else {
								set_ratio(true);
								pl_img.repaint();
							}
						} else {
							if(tmp.isSelected()) {
								if(!imgic.get_favorite()) {
									root.set_like(imgic.get_Path(), node);
									imgic.set_like(true);
								}
							} else {
								if(imgic.get_favorite()){
									root.set_dislike(imgic.get_Path(), node);
									imgic.set_like(false);
								}
							}
						}
					}
				});
				add(cb[i]);
			}
		}
	}
	
	/////////////////////////////// ��� ���� ���� /////////////////////////////
	
	public void img_resize() {	// ����ȭ�� �ش絵�� �°� ����
		dm_tmp = new Dimension();
		dm_tmp.setSize(dm_pl);
		dm_img = new Dimension(imgic.getIconWidth(), imgic.getIconHeight());
    	dm_pl = new Dimension(getScreenSize().width, getScreenSize().height);
    	
    	set_ratio(!pl_menu.cb[0].isSelected());
    	
    	ImageIcon tmp = new ImageIcon(imgic.get_Path());
    	Image tmp_img = tmp.getImage();
    	tmp.setImage(tmp_img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
    	
    	BufferedImage newImage = new BufferedImage(getScreenSize().width,
    			getScreenSize().height, BufferedImage.TYPE_INT_RGB );
    	Graphics gg= newImage.getGraphics();
    	gg.setColor(Color.BLACK);
    	gg.fillRect(0, 0, getScreenSize().width, getScreenSize().height);
    	gg.drawImage(tmp_img, x_img, y_img, width, height, this);
    	
    	try{
    		ImageIO.write(newImage, "jpg", new File(root.get_path() + "\\tmp.jpg"));
    	}catch(Exception e) {
    	}
	}

	public void set_WallPaper(String path){
		SPI.INSTANCE.SystemParametersInfo(new UINT_PTR(SPI.SPI_SETDESKWALLPAPER),
										new UINT_PTR(0),
										path,
										new UINT_PTR(SPI.SPIF_UPDATEINIFILE | SPI.SPIF_SENDWININICHANGE));}
	
	public static Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	public interface SPI extends StdCallLibrary {

		long SPI_SETDESKWALLPAPER = 20;
		long SPIF_UPDATEINIFILE = 0x01;
		long SPIF_SENDWININICHANGE = 0x02;

		@SuppressWarnings("serial")
		SPI INSTANCE = (SPI) Native.loadLibrary("user32", SPI.class, new HashMap<String, Object>() {
			{
				put(OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
				put(OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
			}
		});

		boolean SystemParametersInfo(UINT_PTR uiAction, UINT_PTR uiParam, String pvParam, UINT_PTR fWinIni);
	}
	class MyThread extends Thread{
		LinkedList<String> test = new LinkedList<String>();
		int n[];
		int mode;
		MyThread(int[] n, int mode){
			this.n = n;
			this.mode = mode;
			
			for(int i = 0 ; i < n.length ; i++)
				test.add(node.get_Images().get(n[i]).get_Name());
		}
		
		public void run() {
			model.reload();									// �� ���� �ʱ�ȭ
			for(int i = 0 ; i < tree.getRowCount() ; i++)
				tree.expandRow(i);
			
			tap.get_tap().setSelectedIndex(0);
			try {
				sleep(50);
				while (true) {
					sleep(100);
					if (selectedNode() && selectedNode != node) {
						for(int i : n) selectedNode.copy_img(node.get_Images().get(i));
						if(mode == 1)
							for(String i : test)
								node.remove_files(i);
						return;
					}
				}
			} catch (InterruptedException e) {
				return;
			}
		}
	}
	public boolean selectedNode() {
		TreePath selectionPath = tree.getSelectionPath();
		if (selectionPath != null) {
			selectedNode = (MyTree) selectionPath.getLastPathComponent();
			return true;
		}
		return false;
	}
	
	class SlideShow extends JFrame {
		Container contentPane;
		JLabel pic;
		Timer tm; // Ÿ�̸�
		int x = 0;
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();
		MyTree node;
	     
		ArrayList list;

		String[] picad;

		public SlideShow(MyTree node, double time) {
			this.node = node;
			this.setUndecorated(true);
		    device.setFullScreenWindow(this);
			contentPane = getContentPane();
			contentPane.setLayout(new BorderLayout());
			contentPane.setBackground(Color.BLACK); // ������ ��׶��带 ����������

			list = new ArrayList();
			if(this.node == null) {
				dispose();
				return;
			}
			for(MyImageIcon i : this.node.get_Images())
				list.add(i.get_Path());
			
			picad = new String[list.size()];
			picad = (String[]) list.toArray(picad);
			
			
			pic = new JLabel();

			// �����̵� ���� 0~
			SetImageSize(picad.length - 1);

			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON1) {
						SetImageSize(x);
						x += 1;
						if (x >= list.size())
							x = 0;
					}
				}
			});
			
			tm = new Timer((int)(time*1000), new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SetImageSize(x);
					x += 1;
					if (x >= list.size())
						x = 0;
				}
			});
			tm.start();
			
			setSize(getScreenSize().width, getScreenSize().height); // ������ ����� ȭ�鿡 ����

			contentPane.add(pic, BorderLayout.CENTER);
			addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ESCAPE) 
						dispose();
				}
			});
			this.requestFocus();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		}

		// �̹��� ���̴� ����
		public void SetImageSize(int i) {
			ImageIcon icon = new ImageIcon(picad[i]);
			Image img = icon.getImage(); // �̹��� ��������
			Image newImg = img.getScaledInstance(getScreenSize().width, getScreenSize().height,
					Image.SCALE_SMOOTH); // �̹��� ũ�� �״�� �̹��� �׸���
			ImageIcon newImc = new ImageIcon(newImg);
			pic.setIcon(newImc);
		}
	}
	class RemoteControl extends JDialog{
		JButton btn_control[];
		JPanel pl = new JPanel();
		JLabel lb = new JLabel();
		Clip clip;
		String str[] = {"<<", ">", "| |", ">>"};
		
		RemoteControl(JFrame f) {
			super(f);
			setUndecorated(true);
			setLayout(null);
			pl.setLayout(new GridLayout(4,1, 0, 0));
			
			btn_control = new JButton[4];
			for (int i = 0; i < 4; i++) {
				btn_control[i] = new JButton(str[i]);
				btn_control[i].setBorderPainted(false);
				btn_control[i].setBackground(Color.WHITE);
				pl.add(btn_control[i]);
			}
			for (int i = 0; i < 4; i++) {
				btn_control[i].addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						JButton tmp = (JButton) e.getSource();
						int length = selectedNode.get_Images().size();
						int idx = -1;
						for (int j = 0; j < length; j++) {
							if (selectedNode.get_Images().get(j) == imgic) {
								idx = j;
								break;
							}
						}
						if (tmp.equals(btn_control[0])) { // left
							if (idx != -1) {
								if (idx == 0)
									idx = length - 1;
								else
									idx--;
								if (mode == 1) {
									// dm_pl = size;
									imgic = selectedNode.get_Images().get(idx);
									set_ratio(!pl_menu.cb[0].isSelected());
									pl_img.repaint();
								} else
									set_img(selectedNode.get_Images().get(idx), false);
							}
						} else if (tmp.equals(btn_control[1])) { // play
							if (imgic.get_sound() != null) {
								try {
									stream = AudioSystem.getAudioInputStream(new File(imgic.get_sound()));
									clip = AudioSystem.getClip();
									clip.open(stream);
									clip.start();
									btn_control[2].setEnabled(true);
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							} else {
								JOptionPane.showMessageDialog(null, "�Ҹ������� �����ϴ�.");
							}
						} else if (tmp.equals(btn_control[2])) { // stop
							if(btn_control[2].isEnabled()) {
								clip.stop();
								btn_control[2].setEnabled(false);
							}
						} else {
							if (idx != -1) {
								if (idx == length - 1)
									idx = 0;
								else
									idx++;
								if (mode == 1) {
									imgic = selectedNode.get_Images().get(idx);
									set_ratio(!pl_menu.cb[0].isSelected());
									pl_img.repaint();
								} else
									set_img(selectedNode.get_Images().get(idx), false);
							}
						}
					}
				});	
			}
			btn_control[2].setEnabled(false);
			
			lb.setBounds(0, 0, 50, 10);
			lb.setBackground(Color.LIGHT_GRAY);
			lb.setOpaque(true);
			add(lb);
			lb.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					point.x = e.getX();
					point.y = e.getY();
					if (e.getButton() == MouseEvent.BUTTON3)
						dispose();
				}
			});
			lb.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
					Point p = getLocation();
					setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
				}
			});
			pl.setBounds(0,10,50,190);
			add(pl);
			setLocationRelativeTo(null);
			setSize(50,200);
			setResizable(false);
			setLocation(size.width - 50, size.height / 2 - 100);
			setVisible(true);
		}
	}
}