/**
 * Simulated command line that 
 * navigates a folder tree and 
 * provides basic text manipulation 
 */
package FileSystemPackage;
import java.util.*;

public class CommandLine {
	HashMap<String, Command> commands; 
	FolderTree folders; 
	FolderTreeNode selectedFolder; 
	Stack<String> previousCommands; 
	Stack<String> nextCommands; 
	Boolean active; 
	
	/**
	 * Construct command line
	 */
	public CommandLine() {
		FolderTreeNode root = new FolderTreeNode(new Folder("root")); 
		folders = new FolderTree(root); 
		selectedFolder = folders.getRoot(); 
		commands = new HashMap<String, Command>(); 
		makeCommandMap(); 
		
		previousCommands = new Stack<String>();
		nextCommands = new Stack<String>(); 
		
		
	}
	
	/**
	 * Runs command line until unactivated
	 */
	public void RunCommandLine() {
		active = true; 
		
		while(active) {
			getNextInput(); 
		}
	}
	
	/**
	 * Exits command line
	 */
	private void Exit() {
		active = false; 
	}
	
	/**
	 * Get the current folder selected 
	 * @return selected folder
	 */
	private FolderTreeNode getSelectedFolder() {
		return selectedFolder;
	}

	/**
	 * Changes the selected folder
	 * @param selectedFolder folder node to set as selected 
	 */
	private void setSelectedFolder(FolderTreeNode selectedFolder) {
		this.selectedFolder = selectedFolder;
	}
	
	/**
	 * remove and return the last input written
	 * adds input to nextCommands
	 * @return last command ran
	 */
	private String popLastCommand() {
		if(previousCommands.isEmpty()) {
			return null; 
		}else {
			nextCommands.push(previousCommands.peek());
			return previousCommands.pop(); 
		}
		
	}
	
	/**
	 * Remove and return the last input that has been redone
	 * @return last command erased 
	 */
	private String redoCommand() {
		if(nextCommands.isEmpty()) {
			return null; 
		}else {
			return nextCommands.pop(); 
		}
	}
	
	/**
	 * Creates all commands and maps them to the 
	 * string users can use to access them
	 */
	private void makeCommandMap() {
		commands.put("cd", new GoToFolder()); 
		commands.put("mkdir", new MakeFolder()); 
		commands.put("help", new Help()); 
		commands.put("exit", new Exit()); 
		commands.put("back", new Back());
		commands.put("redo", new Next()); 
		commands.put("ll", new ListChildren()); 
		commands.put("par", new GoToParent()); 
		commands.put("mkfil", new MakeFile()); 
		commands.put("edit", new FileEditor()); 
	}
	
	/**
	 * Get a hashmap of all available commands
	 * @return mapping of accessors and commands 
	 */
	private HashMap<String, Command> getCommands(){
		return commands; 
	}
	
	/**
	 * Given a string input run command or inform 
	 * user that command does not exist. 
	 * Adds command to history. 
	 * @param input command information to run 
	 */
	private void executeInput(String input) {
		previousCommands.push(input);
		String com = getCommand(input); 
		String info = getInfo(input);
		
		if(commands.containsKey(com)) {
			commands.get(com).execute(info, this);
		}else {
			System.out.println("COMMAND NOT FOUND"); 
		}
	}
	
	/**
	 * Returns the string before a space 
	 * representing the command shortcut
	 * @param input input to parse 
	 * @return command portion of input
	 */
	private static String getCommand(String input) {
		int index = input.indexOf(" "); 
		String com; 
		
		if (index < 0) {
			com = input; 
			
		}else {
			com = input.substring(0, index); 
		}
		return com; 
	}
	
	/**
	 * Returns the string after a space 
	 * representing the additionally information 
	 * given to a command 
	 * @param input input to parse
	 * @return additional info of command
	 */
	private static String getInfo(String input) {
		int index = input.indexOf(" "); 
		
		String info;
		
		if (index < 0) {
		
			info = ""; 
			
		}else {
			info = input.substring(index + 1); 
		}
		
		return info; 
	}
	
	/**
	 * Display current folder location, 
	 * gets inputs from user and executes it
	 */
	public void getNextInput() {
		Scanner s = new Scanner(System.in); 
		System.out.print(getSelectedFolder().getLocationString() + "/");
		String input = s.nextLine(); 
		executeInput(input); 
	}
	
	/**
	 * Create and run the command line	
	 * @param args
	 */
	public static void main(String[] args) {
		CommandLine cl = new CommandLine(); 
		cl.RunCommandLine();
	}

	/**
	 * Command for selecting folder
	 */
	private class GoToFolder implements Command{
		
		/**
		 * Given a folder name, set selected folder to it 
		 * if it is a child of the current selection. If it 
		 * is not a child, inform the user.
		 * @param command folder name to locate
		 * @param cl command line to select folder
		 */
		public void execute (String command,CommandLine cl) {
			if(cl.getSelectedFolder().hasFolder(command)) {
				cl.setSelectedFolder(cl.getSelectedFolder().getFolder(command));
			}else {
				System.out.println("FOLDER NOT FOUND"); 
			}	
		}
		
		/**
		 * Provides an explanation of function of command
		 * @return function and usage of command
		 */
		public String info() {
			return "Changes the current the current folder to a child of selected folder";
		}
	}
	
	/**
	 * Command to change selected node to parent of current selection
	 * @author Fiona
	 *
	 */
	private class GoToParent implements Command{
		/**
		 * Set current node of command line to parent 
		 * @param command irrelevant
		 * @param cl command line 
		 */
		public void execute (String command,CommandLine cl) {
			cl.setSelectedFolder(cl.getSelectedFolder().getParent());
		}
		
		/**
		 * Provides an explanation of function of command
		 * @return function and usage of command
		 */
		public String info() {
			return "Go to the parent folder of selected folder.";
		}
	}
	
	/**
	 * Shows a list of children folders and files
	 * @author fiona
	 *
	 */
	private class ListChildren implements Command{
		/**
		 * Displays all files in folder, followed by 
		 * folders that are children in the folder tree
		 */
		public void execute (String command,CommandLine cl) {
			//displays files 
			Iterator<File> fileIter = cl.getSelectedFolder().getValue().getFileIterator(); 
			
			while(fileIter.hasNext()) {
				System.out.println(fileIter.next()); 
			}
			
			//displays children 
			Iterator<FolderTreeNode> nodeIter = cl.getSelectedFolder().getIterable();
			
			while (nodeIter.hasNext()){
				System.out.println(nodeIter.next()); 
			}
			
		}
		/**
		 * Provides an explanation of function of command
		 * @return function and usage of command
		 */
		public String info() {
			return "Lists the children and files of selected folder.";
		}
	}
	
	/**
	 * Creates a new folder and add it a child of selected
	 * @author fiona
	 *
	 */
	private class MakeFolder implements Command{
		
		/**
		 * Creates a new folder with given name and add it as a child to the current folder.
		 * If there is already a folder with the same name, 
		 * do not create folder and inform user. 
		 * If no name is given, inform user that a names needs to be provided. 
		 * @param command new name of folder 
		 * @param command line to execute command
		 */
		public void execute (String command,CommandLine cl) {
			if(cl.getSelectedFolder().hasFolder(command)) {
				System.out.println("Unable to make folder, " + command + " already exists");
			}else if(command == "") {
				System.out.println("Unable to make folder, " + command + " folder name needed");
			}
			else {
				cl.getSelectedFolder().addNewFolder(command); 
			}
				
		}
		
		/**
		 * provides an explanation of function of command
		 * @return function and usage of command
		 */
		public String info() {
			return "Creates a new folder and places it as child of new folder";
		}
	}
	
	/**
	 * Creates a new file with given name and add it to selected folder
	 * @author fiona
	 *
	 */
	private class MakeFile implements Command{
		/**
		 * Create a new file with given name and 
		 * add it to current folder. 
		 * If name is already a file or is empty, do not 
		 * add and inform user. 
		 * @param command name of new file 
		 * @param cl command line to execute command
		 */
		public void execute (String command,CommandLine cl) {
			Folder selected = cl.getSelectedFolder().getValue(); 
			if(selected.hasFile(command)) {
				System.out.println("Unable to make file, " + command + " already exists");
			}else if(command == "") {
				System.out.println("Unable to make file, " + command + " file name needed");
			}
			else {
				selected.addFile(command); 
			}
				
		}
		
		/**
		 * Provides an explanation of function of command
		 * @return function and usage of command
		 */
		public String info() {
			return "Creates a new file and adds it to selected folder";
		}
	}
	/**
	 * Edit a selected file
	 * @author fiona
	 *
	 */
	private class FileEditor implements Command{
		boolean active = false; 
		
		/**
		 * If file exists in current folder, 
		 * start file editing loop. 
		 * Otherwise inform user and exit command. 
		 * @param command name of file to edit 
		 * @param cl command line to execute command 
		 */
		public void execute (String command,CommandLine cl) {
			Folder selected = cl.getSelectedFolder().getValue(); 
			if(!selected.hasFile(command)) {
				System.out.println("Unable to edit file, " + command + " does not exists");
			}
			else {
				editFile(selected.getFile(command)); 
			}
				
		}
		
		/**
		 * Print out the list of available commands 
		 */
		private void help() {
			System.out.println("set: set the text value"); 
			System.out.println("add: add line to file"); 
			System.out.println("undo: bring back last version of file");
			System.out.println("redo: redo line that had been erased"); 
			System.out.println("exit: leave file editor"); 
			System.out.println("help: display available commands"); 
			System.out.println("display: display text file");
			System.out.println("clear: clear text file and erase history");
		}
		
		/**
		 * Display the text in a file
		 * @param file file to display text
		 */
		private void display(File file) {
			System.out.println(file.getText()); 
		}
		/**
		 * Set the text of file to text given, 
		 * adds new line on end. Display results
		 * @param text text to set file to 
		 * @param file file to set text on 
		 */
		private void set(String text, File file) {
			file.setText(text + "\n");
			System.out.println(file.getText()); 
		}
		
		/**
		 * Add a line of text to a given file, 
		 * displays results
		 * @param text line to add
		 * @param file file to add to
		 */
		private void add(String text, File file) {
			file.addText(text + "\n");
			System.out.println(file.getText()); 
		}
		
		/**
		 * Restore a previous version of a file, 
		 * displays results
		 * @param file file to undo
		 */
		private void undo (File file) {
			file.undo();
			System.out.println(file.getText()); 
		}
		
		/**
		 * Restore a version that was erased 
		 * @param file file to redo
		 */
		private void redo (File file) {
			 file.redo();
			 System.out.println(file.getText()); 
		}
		
		/**
		 * Exit file editor
		 */
		private void exit() {
			active = false; 
		}
		
		/**
		 * Erase text and clear history of a file
		 * @param file file to clear
		 */
		private void clear(File file) {
			file.clear();
		}
		
		/**
		 * While editor is active, get input from user 
		 * and executes commands 
		 * @param file
		 */
		public void editFile(File file) {
			active = true; 
			Scanner s = new Scanner(System.in); 
			while (active) {
				System.out.print("*"); 
				String input = s.nextLine(); 
				String com = getCommand(input);
				String info = getInfo(input); 
				
				if(com.equals("help")) {
					help();
				}else if (com.equals("set")) {
					set(info, file); 
				}else if (com.equals("add")) {
					add(info, file); 
				}else if (com.equals("undo")) {
					undo(file); 
				}else if (com.equals("redo")) {
					redo(file); 
				}else if (com.equals("exit")) {
					exit(); 
				}else if (com.equals("display")) {
					display(file); 
				}else if (com.equals("clear")) {
					clear(file); 
				}
			}
		}
		
		/**
		 * Provides an explanation of function of command
		 * @return function and usage of command
		 */
		public String info() {
			return "Edit a selected file";
		}
	}
	
	/**
	 * Displays available commands
	 * @author fiona
	 *
	 */
	private class Help implements Command{
		
		/**
		 * Display available commands and their functions 
		 */
		public void execute (String command,CommandLine cl) {
			for(String key: cl.getCommands().keySet()) {
				System.out.println(key + ": " + cl.getCommands().get(key).info()); 
			}
				
		}
		
		/**
		 * Provides an explanation of function of command
		 * @return function and usage of command
		 */
		public String info() {
			return "Displays the available commands and their functions";
		}
	}
	
	/**
	 * Leave command line
	 * @author fiona
	 *
	 */
	private class Exit implements Command{
		/**
		 * Set command line to inactive 
		 */
		public void execute (String command,CommandLine cl) {
			cl.Exit();
		}
		
		/**
		 * Provides an explanation of function of command
		 * @return function and usage of command
		 */
		public String info() {
			return "Exits command line";
		}
	}
	
	/**
	 * Execute line that ran given lines ago
	 * @author fiona
	 *
	 */
	private class Back implements Command{
		
		/**
		 * Remove history for given number of commands 
		 * and execute command given number of lines ago. 
		 * If string is not a number, command does not exist,
		 * or is a back command do not execute and inform user
		 * @param command number of lines as string
		 * @param cl command line to execute on 
		 */
		public void execute (String command,CommandLine cl) {
			try {
				int num = Integer.parseInt(command); 
				
				int index = 0; 
				String input = cl.popLastCommand(); 
				while (input != null && index < num) {
					
					input = cl.popLastCommand(); 
					index += 1; 
				}
				if(input == null) {
					System.out.println("UNABLE TO EXECUTE, INPUT DOES NOT EXIST");
				}
				else if (CommandLine.getCommand(input).equals("back")){
					System.out.println("UNABLE TO EXECUTE, CANNOT PROCESS BACK COMMANDS");
				}
				else {
					System.out.println(getSelectedFolder().getLocationString() + "/" + input); 
					cl.executeInput(input);
				}
				
				
			} catch (Exception e){
				System.out.println("INVALID INPUt, PLEASE TYPE A NUMBER");
			}
		}
		
		/**
		 * Provides an explanation of function of command
		 * @return function and usage of command
		 */
		public String info() {
			return "Executes line written X lines ago. Erases them from history.";
		}
	}
	
	/**
	 * Redoes commands that were erased using the back command
	 * @author fiona
	 *
	 */
	private class Next implements Command{
		
		/**
		 * Redoes a command that was erased, given the amount of 
		 * erasures to go through. 
		 * If input is not a number, command to execute does not 
		 * exist, or is a next command do not execute and inform user. 
		 * @param command number of erasures to go back to 
		 * @param cl command line to execute on 
		 */
		public void execute (String command,CommandLine cl) {
			try {
				int num = Integer.parseInt(command); 
				
				int index = 0; 
				String input = cl.popLastCommand(); 
				while (input != null && index < num) {
					
					input = cl.redoCommand(); 
					index += 1; 
				}
				if(input == null) {
					System.out.println("UNABLE TO EXECUTE, INPUT DOES NOT EXIST");
				}
				else if (CommandLine.getCommand(input).equals("redo")){
					System.out.println("UNABLE TO EXECUTE, CANNOT PROCESS redo COMMANDS");
				}
				else {
					System.out.println(getSelectedFolder().getLocationString() + "/" + input); 
					cl.executeInput(input);
				}
				
				
			} catch (Exception e){
				System.out.println("INVALID INPUT, PLEASE TYPE A NUMBER");
			}
		}
		
		/**
		 * Provides an explanation of function of command
		 * @return function and usage of command
		 */
		public String info() {
			return "Re-does commands that have been erased.";
		}
	}
	
	
	
}

