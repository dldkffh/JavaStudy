package Album;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;

public class MyImageIcon extends ImageIcon {
	private String name; 					// 파일이름
	private String path;					// 현재 폴더 경로
	private Calendar date = Calendar.getInstance();					// 파일 생성 날짜
	private String comment;					// 파일 메모
	private boolean favorite = false;
	private File sound;
	private String path_fd;
	
	MyImageIcon(String name, long date, String path){
		super(path);
		this.name = name;
		this.date.setTimeInMillis(date);
		this.path = path;
		if(path != null)
		this.path_fd = path.substring(0,path.lastIndexOf("\\"));
	}
	
	public void set_like(boolean favorite) {
		this.favorite = favorite;
	}
	
	public void set_sound(String sound) {
		this.sound = new File(sound);
		
		try {
			String txt = "";
			String temp = "";
			String all = "";
			boolean b_sd = false;
			FileReader rd = new FileReader(new File(path_fd + "\\System.txt"));
			BufferedReader read_buf = new BufferedReader(rd);
			while ((temp = read_buf.readLine()) != null) {
				if (temp.equals("[sounds]")) b_sd = true;
				if(!b_sd) all += temp + System.lineSeparator();	// sound 항목을 제외한 모든 텍스트파일
				else if (temp.equals("[s_end]")) {						// Sound 항목의 끝을 만나면
					txt += path + System.lineSeparator();
					txt += sound + System.lineSeparator();
					txt += "[s_end]" + System.lineSeparator();
					b_sd = false;
				}
				else {
					if(temp.equals(path)) {				// 이미 해당파일 sound가 있으면 삭제
						temp = read_buf.readLine();
					}
					else if(!temp.equals("[sounds]")) txt += temp + System.lineSeparator();	// Sound 항목이면 txt에 추가
				}
			}
			rd.close();
			read_buf.close();

			FileWriter wt = new FileWriter(new File(path_fd + "\\System.txt"));
			BufferedWriter write_buf = new BufferedWriter(wt);

			write_buf.write(all);
			write_buf.write("[sounds]" + System.lineSeparator());
			write_buf.write(txt);
			write_buf.flush();
			
			wt.close();
			write_buf.close();
		} catch (IOException e) {
		}
	}
	
	public void del_sound() {
		this.sound.delete();
		
		try {
			String temp;
			String all = "";
			boolean b_sd = false;
			FileReader rd = new FileReader(new File(path_fd + "\\System.txt"));
			BufferedReader read_buf = new BufferedReader(rd);
			while ((temp = read_buf.readLine()) != null) {
				if (temp.equals(sound.getPath())) b_sd = true;
				if(!b_sd) all += temp + System.lineSeparator();	// sound 항목을 제외한 모든 텍스트파일
				else {
					temp = read_buf.readLine();				// 해당 이미지의 sound 항목이면 넘어감
					b_sd = false;
				}
			}
			rd.close();
			read_buf.close();

			FileWriter wt = new FileWriter(new File(path_fd + "\\System.txt"));
			BufferedWriter write_buf = new BufferedWriter(wt);

			write_buf.write(all);
			write_buf.flush();
			
			wt.close();
			write_buf.close();
		} catch (IOException e) {
		}
	}
	
	public void set_path(String path) {
		this.path = path;
		this.path_fd = path.substring(path.lastIndexOf("\\")+1);
	}
	
	public boolean get_favorite() {
		return favorite;
	}
	 
	public void set_Comment(String comment) {
		this.comment = comment;

		try {
			String txt = "";
			String temp = "";
			String all = "";
			boolean b_cmt = false;
			FileReader rd = new FileReader(new File(path_fd + "\\System.txt"));
			BufferedReader read_buf = new BufferedReader(rd);
			while ((temp = read_buf.readLine()) != null) {
				
				if (temp.equals("[info]"))	b_cmt = true;
				if(!b_cmt) all += temp + System.lineSeparator();	// 
				else {
					if(temp.equals("[info]")) temp = read_buf.readLine();
					if(temp.equals(path)) {	// 찾는 경로가 나왔다면
						while(((temp = read_buf.readLine()) != null)) {
							if(temp.equals("[end]")) break;
						}
					}
					else if(temp.equals("[i_end]")) {
						txt += path + System.lineSeparator();
						txt += comment + System.lineSeparator();
						txt += "[end]" + System.lineSeparator();
						txt += "[i_end]" + System.lineSeparator();
						b_cmt = false;
					}
					else if(!temp.equals("")) txt += temp + System.lineSeparator();
				}
			}
			rd.close();
			read_buf.close();

			FileWriter wt = new FileWriter(new File(path_fd + "\\System.txt"));
			BufferedWriter write_buf = new BufferedWriter(wt);

			write_buf.write(all);
			write_buf.write("[info]" + System.lineSeparator());
			write_buf.write(txt);
			write_buf.flush();
			
			wt.close();
			write_buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String get_Path() {
		return this.path; 
	}
	
	public String get_comment() {
		return this.comment;
	}
	
	public String get_sound() {
		if(sound == null) return null;
		return this.sound.getPath(); 
	}
	
	public String get_Name() {
		return this.name; 
	}
	
	public String get_Date() {
		return (this.date.get(Calendar.YEAR) + " / " 
				+ (date.get(Calendar.MONTH)+1) + " / "
				+ date.get(Calendar.DATE)); 
	}
}