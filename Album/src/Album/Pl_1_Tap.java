package Album;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.*;

import Album.Tap_Images.Listener_btn;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

public class Pl_1_Tap extends JPanel {
	private MyTree root;
	private JTree tree;

	public Tap_Directory tap_dir;
	public Tap_Images tap_img;
	public Tap_Like tap_like;

	private JTabbedPane pl_tap;
	
	MyTree selectedNode;

	Pl_1_Tap(MyTree root, JTree tree, Vector<String> favorite) {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		pl_tap = new JTabbedPane();

		this.root = root;
		this.tree = tree;

		tap_dir = new Tap_Directory(root, tree);
		tap_img = new Tap_Images(root);
		tap_like = new Tap_Like(root, favorite);

		pl_tap.addTab("Tree", tap_dir);
		pl_tap.addTab("Images", tap_img);
		pl_tap.addTab("Like", tap_like );
		
		tap_img.get_btn()[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pl_tap.getSelectedIndex() == 1)
					pl_tap.setSelectedIndex(0);
			}
		});
		add(pl_tap);
	}
	
	protected JTabbedPane get_tap() {
		return pl_tap;
	}
	
	protected JTree get_tree() {
		return tree;
	}

	class Listener_Tap_Directory extends MouseAdapter { // Tap 1에서 폴더 더블클릭하면 이미지 변함
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2 && pl_tap.getSelectedIndex() == 0) {
				if (selectedNode()){
					tap_img.change_Image(selectedNode);
					tap_like.change_Image(selectedNode);
					pl_tap.setSelectedIndex(1);
				}
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
}

class Tap_Images extends JPanel {	// 2번째 탭
	private MyTree node; // 현재 폴더

	private LinkedList<MyImageIcon> images; // 폴더의 이미지들

	private DefaultListModel list_model = new DefaultListModel();
	private JList list = new JList(list_model);

	private JButton btn[] = new JButton[3];
	private JPanel pl_btn = new JPanel();

	Tap_Images(MyTree node) {
		this.setLayout(new BorderLayout());
		init(node);
	}
	
	public void set_node(MyTree node) {
		this.node = node;
	}
	
	public boolean is_img() {
		return !images.isEmpty();
	}
	
	public MyImageIcon get_img() {
		if(list.isSelectionEmpty()) {
			return images.get(0);
		}
		return images.get(list.getSelectedIndex());
	}

	public JList get_list() {
		return list;
	}
	
	public MyTree get_node() {
		return node;
	}

	void init(MyTree node) {
		list.setCellRenderer(new ListEntryCellRenderer());

		change_Image(node);

		btn[0] = new JButton("open");
		btn[1] = new JButton("remove");
		btn[2] = new JButton("back");
		btn[1].addActionListener(new Listener_btn());
		pl_btn.add(btn[0]);
		pl_btn.add(btn[1]);
		pl_btn.add(btn[2]);

		add(pl_btn, BorderLayout.SOUTH);

		add(new JScrollPane(list));
	}

	public void change_Image(MyTree node) { // 리스트를 매개변수 루트의 이미지들로 초기화
		this.node = node;
		this.images = node.get_Images();

		list_model.clear();

		if(images != null)
		for (int i = 0; i < images.size(); i++) {
			ImageIcon test = new ImageIcon(images.get(i).get_Path());
			Image tmp = test.getImage();
			tmp = tmp.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			test.setImage(tmp);

			list_model.addElement(new ListEntry(images.get(i).get_Name(), test));
		}
	}

	public JButton[] get_btn() {
		return btn;
	}

	class Listener_btn implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton btn_tmp = (JButton) e.getSource();
			if (btn_tmp.equals(btn[0])) {
				if (!list.isSelectionEmpty()) {
					node.remove_file(list.getSelectedIndex());
					change_Image(node);
				}
			}
		}
	}

}

class Tap_Directory extends JPanel {	// 1번째 탭
	MyTree root;
	JButton btn[] = new JButton[3];
	Tap_Directory(MyTree root, JTree tree) {
        this.root = root;
        JScrollPane a = new JScrollPane(tree);
        this.setLayout(new BorderLayout());
        
        add(a);     //프레임에 붙임
        
        JPanel pl_btn = new JPanel();

        btn[0] = new JButton("create");
        btn[1] = new JButton("remove");
        btn[2] = new JButton("open");
        pl_btn.add(btn[0]);
        pl_btn.add(btn[1]);
        pl_btn.add(btn[2]);
        
        add(pl_btn, BorderLayout.SOUTH);
	}
	
	public JButton[] get_btn() {
		return btn;
	}
}

class Tap_Like extends JPanel {
	private MyTree node; // 현재 폴더
	private Vector<String> favorite;

	private LinkedList<MyImageIcon> images; // 폴더의 이미지들

	private DefaultListModel list_model = new DefaultListModel();
	private JList list = new JList(list_model);

	Tap_Like(MyTree node, Vector<String> favorite) {
		this.favorite = favorite;
		this.setLayout(new BorderLayout());
		init(node);
	}
	
	public void set_node(MyTree node) {
		this.node = node;
	}
	
	public boolean is_favorite() {
		return !list_model.isEmpty();
	}

	public JList get_list() {
		return list;
	}
	
	public MyImageIcon get_img() {
		if(list.isSelectionEmpty()) {
			if(images.isEmpty())
				return null;
			else return images.get(0);
		}
		return images.get(list.getSelectedIndex());
	}


	public MyTree get_node() {
		return node;
	}

	void init(MyTree node) {
		list.setCellRenderer(new ListEntryCellRenderer());

		change_Image(node);
		
		add(new JScrollPane(list));
	}

	public void change_Image(MyTree node) { // 리스트를 매개변수 루트의 이미지들로 초기화
		if(!node.isRoot()) {
			this.node = node;
			this.images = new LinkedList<MyImageIcon>();

			for(int i = 0 ; i < node.get_Favorite().size() ; i++) {
				File tmp = new File(node.get_Favorite().elementAt(i));
				this.images.add(new MyImageIcon(tmp.getName(), tmp.lastModified(), tmp.getPath()));
			}


			list_model.clear();

			for (int i = 0; i < node.get_Favorite().size(); i++) {
				ImageIcon test = new ImageIcon(node.get_Favorite().elementAt(i));
				Image tmp = test.getImage();
				tmp = tmp.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
				test.setImage(tmp);

				list_model.addElement(new ListEntry(node.get_Favorite().elementAt(i)
						.substring(node.get_Favorite().elementAt(i).lastIndexOf("\\") + 1), test));
			}
		}
	}
}

class ListEntry {
	private String value;
	private ImageIcon icon;

	public ListEntry(String value, ImageIcon icon) {
		this.value = value;
		this.icon = icon;
	}

	public String getValue() {
		return value;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public String toString() {
		return value;
	}
}

class ListEntryCellRenderer extends JLabel implements ListCellRenderer {
	private JLabel label;

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {

		ListEntry entry = (ListEntry) value;
		if (entry.toString().length() > 10) {
			setText(entry.toString().substring(0, 10) + "...");
		} else
			setText(entry.toString());
		setIcon(entry.getIcon());

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		setHorizontalAlignment(JLabel.CENTER);
		setHorizontalTextPosition(JLabel.CENTER);
		setVerticalTextPosition(JLabel.BOTTOM);

		setFont(list.getFont());
		setOpaque(true);
		return this;
	}
}