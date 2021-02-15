import java.io.*;
import java.util.*;

public class Shell {
	static List<String> cmdlist = new ArrayList<String>();
	static List parameter = new ArrayList<>();
	static List<String> history = new ArrayList<String>();

	public static void main(String[] args) throws java.io.IOException {
		String commandLine;
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		// we break out with <control><C>
		BufferedOutputStream out = new BufferedOutputStream(System.out, 5);
		while (true) {
			// read what the user entered
			System.out.print("jsh>");
			commandLine = console.readLine();

			// add the list of commands to the list
			cmdlist.add("cat");
			cmdlist.add("history");
			cmdlist.add("ls");

			// if the user entered a return, just loop again
			if (commandLine.equals(""))
				continue;

			if (commandLine.equals("history")) {
				history();
				continue;
			}
			
			// When the user enters !!, run the previous command in the history.
			if (commandLine.equals("!!")) {
				if(1 > history.size())
					System.out.print("error : history is empty\n");
				else System.out.print(history.indexOf(history.size() -1) + "\n");
				continue;
			}
			
			// When the user enters !<integer value i>, run the ith command in the history

			
			else {
				String[] cmd = commandLine.split(" ");
				String first = cmd[0];
				// String second = cmd[1];
				// String third = cmd[2];
				
				if (cmdlist.contains(cmd[0])) {
					history.add(commandLine);
					if (cmd[0] == cmdlist.get(0)) cat();
					if (cmd[0] == cmdlist.get(2)) ls();
				} else
					System.out.print("Input error : java.io.IOException\n");
				
				continue;
			}

		}
	}

	/**
	 * The steps are: (1) parse the input to obtain the command and any
	 * parameters (2) create a ProcessBuilder object (3) start the process (4)
	 * obtain the output stream (5) output the contents returned by the command
	 */

	public static void cat() {
		System.out.print("cat!!\n");
	}

	// When the user enters the command history, you will print out the contents of the history of commands
	public static void history() {
		int hisnum = 0;
		for (String data : history)
			System.out.println(hisnum++ + " " + data);	
	}

	public static void ls() {
		System.out.print("ls!!\n");
	}

}
