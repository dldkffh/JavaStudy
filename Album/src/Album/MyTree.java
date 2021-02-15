package Album;
import java.awt.*;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;

public class MyTree extends DefaultMutableTreeNode {
	private String name; 						// ���� �̸�
	private String path;						// ���� ���� ���
	
	private File file;							// ���� ����
	private File[] files;
	
	private LinkedList<MyTree> list_fd;			// ���� ����
	private LinkedList<MyImageIcon> list_img;	// ���� ���� ���� �̹���
	
	private File system;					// ���ã�� ��ΰ� ����� �ؽ�Ʈ ����
	private Vector<String> favorite_str;	// ���ã�� ��θ� ������ Vector
	
	private boolean favorite;
	private boolean info;
	private boolean sounds;
		
	MyTree(String name, String path){
		super(name);
		if(name != null)
		init(name, path);					// ������ �ʱ�ȭ
	}
	
	private void init(String name, String path) {
		favorite = false;
		info = false;
		sounds = false;
		
		this.name = name;
		this.path = path + "\\" + name;
		
		system = new File(this.path + "\\System.txt");
		favorite_str = new Vector<String>();
		
		try {
			if(!system.exists()) {					// system ���� ������ ����
				BufferedWriter file_system = new BufferedWriter(new FileWriter(system));
				file_system.write("[favorite]" + System.lineSeparator()
									+ "[f_end]" + System.lineSeparator()
									+ "[sounds]" + System.lineSeparator()
									+ "[s_end]" + System.lineSeparator()
									+ "[info]" + System.lineSeparator()
									+ "[i_end]" + System.lineSeparator());
			    file_system.close();
			} else {
				FileReader file_system = new FileReader(system);
				BufferedReader string_buf = new BufferedReader(file_system);
				String tmp = "";
				String str_info = "";
				while ((tmp = string_buf.readLine()) != null) {
					if(tmp.equals("[f_end]")) favorite = false;
					
					if(favorite) favorite_str.add(tmp);
					
					if(tmp.equals("[favorite]")) favorite = true;
				}
				favorite = false;
				string_buf.close();
				file_system.close();
			}
		} catch (IOException e) {}
		
				
		file = new File(this.path);			// ���� ����
		if(!file.exists()) file.mkdir();	// ���� ������ ����
		files = file.listFiles();			// �������� ���ϵ�� �������� ���ڿ� �迭
		
		list_fd = new LinkedList<MyTree>();
		list_img = new LinkedList<MyImageIcon>();
		
		if(files != null)
		for (File i : files) {
			if (i.isDirectory()) { 	// ������ MyFolder ����Ʈ�� ����
					MyTree tmp = new MyTree(i.getName(), this.path);
					list_fd.add(tmp);			// ����Ʈ�� �߰�
					add(tmp);				// ���� Ʈ���� �߰�
				}
			else if (i.isFile()) {	// �����̸� Ȯ���� �˻��ؼ� �̹����� ����
				String[] extension = { "bmp", "jpg", "gif", "jpeg", "png", "tiff" , "PNG", "JPG", "JPEG", "GIF"};
				for (int j = 0; j < extension.length; j++) {
					if (i.getName().endsWith("." + extension[j])) {
						list_img.add(new MyImageIcon(i.getName(),			// �̸�
									 i.lastModified(),						// ������ ���� ��¥
								  	 i.getPath()));							// ���
						}
				}
			}
		}
		for(int i = 0 ; i < list_img.size() ; i++) {	// favorite vector���� ���ҵ� ������ �ش� �̹��� favorite üũ
			for(int j = 0 ; j < favorite_str.size() ; j++) {
				if(list_img.get(i).get_Path().equals(favorite_str.elementAt(j))) {
					list_img.get(i).set_like(true);
				}
			}
		}
		
		try {
			FileReader file_system = new FileReader(system);
			BufferedReader string_buf = new BufferedReader(file_system);
			String tmp = "";
			while ((tmp = string_buf.readLine()) != null) {
				if(tmp.equals("[i_end]")) info = false;
				else if(tmp.equals("[s_end]")) sounds = false;
					
				if(info) {
					for(MyImageIcon i : list_img) {
						if(tmp.equals(i.get_Path())) { 
							String str_info = "";
							while((tmp = string_buf.readLine()) != null)
								if(!tmp.equals("[end]"))
									str_info += tmp + System.lineSeparator();
								else {
									i.set_Comment(str_info);
									break;
								}
						}
					}
				}
				else if(sounds) {
					for(MyImageIcon i : list_img) {
						if(tmp.equals(i.get_Path())) {
							tmp = string_buf.readLine();
							i.set_sound(tmp);
						}
					}
				}
			
				if(tmp.equals("[info]")) {
					info = true;
					sounds = false;
				} else if(tmp.equals("[sounds]")) {
					info = false;
					sounds = true;
				}
			}
			info = false;
			sounds = false;
			
			string_buf.close();
			file_system.close();
		} catch (IOException e) {}
		
		create_Folder(name);
	}
	
	public Vector<String> get_Favorite() {
		return favorite_str;
	}
	
	public LinkedList<MyImageIcon> get_Images(){	// ���� ������ �̹��� �迭 ��ȯ
		return list_img;
	}
	public LinkedList<MyTree> get_Folder(){			// ���� ������ �̹��� �迭 ��ȯ
		return list_fd;
	}
	
	public String get_name() {
		return this.name;
	}
	
	public String get_path() {
		return this.path;
	}
	
	public boolean set_like(String path, MyTree node) {
		MyTree tmp = node;
		
		while (true) {
			try {
				for (int i = 0; i < tmp.favorite_str.size(); i++)
					if (tmp.favorite_str.elementAt(i).equals(path))
						return false;
				tmp.favorite_str.add(path);

				String txt = "";
				String temp;
				FileReader rd = new FileReader(new File(tmp.get_path() + "\\System.txt"));
				BufferedReader read_buf = new BufferedReader(rd);
				while((temp = read_buf.readLine()) != null) {
					if(temp.equals("[favorite]")) favorite = true;
					if(!favorite) txt += temp + System.lineSeparator();
					if(temp.equals("[f_end]" )) favorite = false;
				}
				rd.close();
				read_buf.close();
				
				FileWriter wt = new FileWriter(new File(tmp.get_path() + "\\System.txt"));
				BufferedWriter write_buf = new BufferedWriter(wt);
				
				write_buf.write(txt);
				write_buf.write("[favorite]" + System.lineSeparator());
				for (int i = 0; i < tmp.favorite_str.size(); i++) {
						write_buf.write(tmp.favorite_str.elementAt(i) + System.lineSeparator());
				}
				write_buf.write("[f_end]");
				write_buf.flush();
				wt.close();
				write_buf.close();
			} catch (IOException e) {}
			if (!((MyTree)tmp.getParent()).isRoot())
				tmp = (MyTree) tmp.getParent();
			else
				break;
		}
		return true;
	}
	
	public boolean set_dislike(String path, MyTree node) {
		MyTree tmp = node;
		while (true) {
			try {
				for (int i = 0; i < tmp.favorite_str.size(); i++)
					if (tmp.favorite_str.elementAt(i).equals(path))
						tmp.favorite_str.remove(i);

				String txt = "";
				String temp;
				FileReader rd = new FileReader(new File(tmp.get_path() + "\\System.txt"));
				BufferedReader read_buf = new BufferedReader(rd);
				while((temp = read_buf.readLine()) != null) {
					if(temp.equals("[favorite]")) favorite = true;
					if(!favorite) txt += temp + System.lineSeparator();
					if(temp.equals("[f_end]" )) favorite = false;
				}
				rd.close();
				read_buf.close();
				
				FileWriter wt = new FileWriter(new File(tmp.get_path() + "\\System.txt"));
				BufferedWriter write_buf = new BufferedWriter(wt);
				
				write_buf.write(txt);
				write_buf.write("[favorite]" + System.lineSeparator());
				for (int i = 0; i < tmp.favorite_str.size(); i++) {
						write_buf.write(tmp.favorite_str.elementAt(i) + System.lineSeparator());
				}
				write_buf.write("[f_end]");
				write_buf.flush();
				wt.close();
				write_buf.close();
			} catch (IOException e) {}
			if (!((MyTree)tmp.getParent()).isRoot())
				tmp = (MyTree) tmp.getParent();
			else
				break;
		}
		return true;
	}
	
	public boolean create_Folder(String name) {
		for(MyTree i : list_fd)		// �̸� �ߺ� ����
			if(i.name.equals(name)) return false;
		
		File tmp_file = new File(path);
		tmp_file.mkdir();
		if(!this.isRoot())
		((MyTree)this.getParent()).list_fd.add(this);
		
		return true;
	}
	
	public boolean remove_Folder() {
		if (!this.isRoot()) {	// ��Ʈ �ƴѰ͸�
			if (file.exists())	// ������� ���� ����
				for (MyTree i : list_fd)
					i.remove_Folder(); // ���� ���� ��������� ����

			System.gc();
			
			if (file.delete()) {
				file.delete();
				this.removeAllChildren();
				int n = 0;
				for (MyTree i : ((MyTree) this.getParent()).get_Folder())
					if (i.equals(this)) {
						((MyTree) this.getParent()).get_Folder().remove(n);
						break;
					}
			} else return false;
		}
		return true;
	}
	
	public boolean remove_file(int idx) {
		File tmp_file = new File(path + "\\" + list_img.get(idx).get_Name());
		if(tmp_file.isFile()) {
			tmp_file.delete();
			list_img.remove(idx);
		}
		return true;
	}
	
	public boolean remove_files(String name) {
		File tmp_file = new File(path + "\\" + name);
		if(tmp_file.isFile()) {
			tmp_file.delete();
			for(int i = 0 ; i < list_img.size() ; i++) {
				if(list_img.get(i).get_Name().equals(name)) {
					list_img.remove(i);
					break;
				}
			}
		}
		return true;
	}
	
	public void add_Folder() {
		for(int i = 0 ; i < list_fd.size() ; i++) {
			add(list_fd.get(i));
		}
	}
	
	public boolean copy_img(MyImageIcon img) {
		for(MyImageIcon i : list_img) {
			if(i.get_Name().equals(img.get_Name())) {
		    	return false;
			}
		}
		Image tmp_img = img.getImage();
    	
    	BufferedImage newImage = new BufferedImage(img.getIconWidth(),
    			img.getIconHeight(), BufferedImage.TYPE_INT_RGB );
    	Graphics gg= newImage.getGraphics();
    	gg.drawImage(tmp_img, 0, 0, img.getIconWidth(), img.getIconHeight(), null);
    	
    	try{
    		ImageIO.write(newImage, "jpg", new File(path + "\\" + img.get_Name()));
    		img.set_path(path + "\\" + img.get_Name());
    		list_img.add(img);
    		return true;
    	}catch(Exception e) {
    		return false;
    	}
	}
}