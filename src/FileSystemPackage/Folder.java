package FileSystemPackage;
import java.util.*;

/**
 * Representation of a folder 
 * that can contain files 
 * @author fiona
 *
 */
public class Folder {
	String name; 
	ArrayList<File> files; 
	
	/**
	 * Construct folder given name
	 * @param name name of folder
	 */
	public Folder(String name) {
		this.name = name; 
		files = new ArrayList<File>(); 
	}
	/**
	 * Get name of folder
	 * @return folder name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Change the name of the folder
	 * @param name new name of folder 
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Return the name of the folder 
	 */
	public String toString() {
		return name; 
	}
	
	/**
	 * Return true if file is in folder, 
	 * will return true if has file with fileName 
	 * with a .txt at the end
	 * @param fileName name of file to look for (can either end with .txt or not)
	 * @return true if fileName is a file within the folder 
	 */
	public boolean hasFile(String fileName) {
		for(File file: files) {
			if(file.getFileName().equals(fileName) || file.getFileName().equals(fileName + ".txt")) {
				return true; 
			}
		}
		return false; 
	}
	
	/**
	 * Create and add a new file given a fileName.
	 * Will not add file it there is already a file
	 * by that name in folder and will return false. 
	 * @param filePrefix begining portion of fileName without .txt at end
	 * @return if file was successfully added. 
	 */
	public boolean addFile(String filePrefix) {
		if(hasFile(filePrefix + ".txt")) {
			return false; 
		}else {
			files.add(new File(filePrefix)); 
			return true; 
		}
	}
	/**
	 * Create and add a new file given a fileName and text.
	 * Will not add file it there is already a file
	 * by that name in folder and will return false. 
	 * @param filePrefix begining portion of fileName without .txt at end
	 * @parem text starting text of file
	 * @return if file was successfully added. 
	 */
	public boolean addFile(String filePrefix, String text) {
		if(hasFile(filePrefix + ".txt")) {
			return false; 
		}else {
			files.add(new File(filePrefix, text)); 
			return true; 
		}
	}
	
	/**
	 * Get a file in folder given a name, 
	 * will return null if file does not exist
	 * @param fileName name of file in folder (can either have .txt or not)
	 * @return file with fileName if found, otherwise null
	 */
	public File getFile(String fileName) {
		for(File file: files) {
			if(file.getFileName().equals(fileName) || file.getFileName().equals(fileName + ".txt")) {
				return file; 
			}
		}
		return null; 
	}
	
	/**
	 * return a file at the given index
	 * @param index index of file to look at
	 * @return file at index 
	 */
	private File getFile(int index) {
		return files.get(index); 
	}
	/**
	 * return amount of files in folder
	 * @return amount of files 
	 */
	public int numberOfFiles() {
		return files.size();
	}
	
	/**
	 * Get an iterator of all the files 
	 * in the folder starting at the first entry
	 * @return iterator of all files 
	 */
	public Iterator<File> getFileIterator() {
		return new FileIterator(); 
	}
	
	/**
	 * iterator for files in folder 
	 * @author fiona
	 *
	 */
	private class FileIterator implements Iterator<File>{
		int index; 
		/**
		 * create iterator and start index at 0
		 */
		public FileIterator() {
			index = 0; 
		}
		
		/**
		 * return true if there are remaining files 
		 */
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return index < numberOfFiles();
		}
		
		/**
		 * Get the next file 
		 */
		@Override
		public File next() {
			File file = getFile(index); 
			index ++; 
			return file;
		}
		
	}

}
