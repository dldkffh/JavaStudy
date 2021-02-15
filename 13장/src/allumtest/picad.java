package allumtest;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class picad {
	private static String path_home = System.getProperty("user.home") + "\\Desktop";

	public static void main(String[] args) {

		ArrayList list = new ArrayList();
		try {
			BufferedReader in = new BufferedReader(new FileReader(path_home + "\\Images\\System.txt"));
			String s;
			do {
				if ((s = in.readLine()) != null)
					if (s.indexOf("[favorite]") != -1)
						break;
			} while (true);

			do {
				if ((s = in.readLine()) == null)
					break;
				if (s.indexOf("[f_end]") != -1)
					break;
				list.add(s);
			} while (true);

		} catch (IOException e) {
			System.err.println(e); // 에러가 있다면 메시지 출력
		}

		String[] picad = new String[list.size()];
		{
			picad = (String[]) list.toArray(picad);
		}
		System.out.println(picad[0]);
		System.out.println(picad[1]);
		System.out.println(picad[6]);
		System.out.println(picad.length);

	}

}
