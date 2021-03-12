package FileSystemPackage;

/**
 * Simulation of a text file 
 * @author fiona
 *
 */
public class File {
	String fileName; 
	String text; 
	Stack<String> previousVersions; 
	Stack<String> redoVersions; 
	
	/**
	 * Create file given name and starting text 
	 * @param filePrefix file name without .txt added 
	 * @param text starting text of file 
	 */
	public File(String filePrefix, String text) {
		fileName = filePrefix + ".txt"; 
		this.text = text; 
		previousVersions = new Stack<String>(); 
		
		redoVersions = new Stack<String>();
	}
	
	/**
	 * Create file given name
	 * @param filePrefix file name without .txt added 
	 */
	public File(String filePrefix) {
		this(filePrefix, ""); 
	}
	
	/**
	 * return name of file
	 * @return file name 
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * set file name
	 * @param fileName new name of file
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Get text in file
	 * @return text of file
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Set text of file 
	 * @param text text to set
	 */
	public void setText(String text) {
		previousVersions.push(this.text); 
		this.text = text;
	}
	
	/**
	 * Add to text after current text 
	 * @param text text segment to add 
	 */
	public void addText(String text) {
		previousVersions.push(this.text); 
		this.text += text;
	}
	
	/**
	 * Restore a previous version of file, 
	 * if no previous version exists, do nothing 
	 */
	public void undo() {
		if(!previousVersions.isEmpty()) {
			redoVersions.push(this.text);
			this.text = previousVersions.pop(); 
		}
	
	}
	
	/**
	 * Restore file to version before undo was called, 
	 * if undo has not been called, do nothing  
	 */
	public void redo() {
		if(!redoVersions.isEmpty()) {
			setText(redoVersions.pop());
		}
		 
	}
	/**
	 * Return file name 
	 */
	public String toString() {
		return fileName; 
	}
	
	/**
	 * Clear text and erase history 
	 */
	public void clear() {
		text = ""; 
		previousVersions.clear();
		redoVersions.clear();
	}
	/**
	 * Test file functions 
	 * @param args
	 */
	public static void main(String[] args) {
		File newFile = new File("file"); 
		newFile.addText("Hello");
		newFile.addText(" world");
		System.out.println(newFile.getText()); 
		newFile.undo(); 
		System.out.println(newFile.getText()); 
		newFile.redo();
		System.out.println(newFile.getText()); 
		newFile.undo();
		System.out.println(newFile.getText()); 
		newFile.undo();
		System.out.println(newFile.getText()); 
		newFile.redo();
		System.out.println(newFile.getText()); 
		newFile.redo();
		System.out.println(newFile.getText()); 
	}

}
