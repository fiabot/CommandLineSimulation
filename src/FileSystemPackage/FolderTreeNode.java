package FileSystemPackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Node of a file hierarchy, 
 * does not allow files with duplicate names 
 * @author fiona
 *
 */
public class FolderTreeNode implements GenTreeNodeInterface<Folder> {
	
	public static final String INDENTSPACE = "  ";
	Folder value; 
	FolderTreeNode parent; 
	ArrayList<FolderTreeNode> children; 
	
	/**
	 * Create a new folder node given a folder and parent node 
	 * @param folder folder of node 
	 * @param parent parent of node 
	 */
	public FolderTreeNode(Folder folder, FolderTreeNode parent) {
		value = folder; 
		this.parent = parent; 
		children = new ArrayList<FolderTreeNode>(); 
	}
	
	/**
	 * Create a new Node given a folder 
	 * @param data folder of node 
	 */
	public FolderTreeNode(Folder data) {
		this(data, null);
	}
	
	
	/**
	 * Add a node to this on as a child
	 * Sets the parent of child to this node
	 * will not add child if it has the folder name  
	 * @param child node to add 
	 */
	@Override
	public boolean addChild(GenTreeNodeInterface<Folder> child) {
		if(hasFolder(child.getValue().getName())) {
			try {
				FolderTreeNode c = (FolderTreeNode) child; 
				c.setParent(this);
				children.add(c);
				return true; 
			}catch (Exception e) {
				return false; 
			}
			
		}else {
			
			return true; 
		}
	} 
	
	/**
	 * Add a new folder as a child given a folder name 
	 * Sets the parent of child to this node 
	 * will not add child if a folder with the same name is a child 
	 * @param folderName name of new child folder 
	 * @return new child node 
	 */
	public FolderTreeNode addNewFolder(String folderName) {
		Folder folder = new Folder(folderName); 
		return addChild(folder); 
	}
	
	/**
	 * Returns true if node has a child with given name
	 * @param folderName name of folder to look for 
	 * @return true if folder exists with folder name as child 
	 */
	public boolean hasFolder(String folderName) {
		Iterator<FolderTreeNode> iter = getIterable(); 
		boolean foundFile = false; 
		while(iter.hasNext() && !foundFile) {
			if(iter.next().getValue().getName().equals(folderName)) {
				foundFile = true; 
			}
		}
		
		return foundFile; 
			
	}
	
	/**
	 * Returns true if there is a folder with the same name as a child 
	 * @param folder folder to check for
	 * @return true if node has a folder with the same name
	 */
	public boolean hasFolder(Folder folder) {
		String folderName = folder.getName(); 
		Iterator<FolderTreeNode> iter = getIterable(); 
		boolean foundFile = false; 
		while(iter.hasNext() && !foundFile) {
			if(iter.next().getValue().getName().equals(folderName)) {
				foundFile = true; 
			}
		}
		
		return foundFile; 
			
	}
	
	/**
	 * Return a child node given folder name 
	 * returns null if there is no such folder 
	 * @param folderName name of folder to look for 
	 * @return child node with folder name 
	 */
	public FolderTreeNode getFolder(String folderName) {
		Iterator<FolderTreeNode> iter = getIterable(); 
		boolean foundFile = false; 
		FolderTreeNode node = null; 
		while(iter.hasNext() && !foundFile) {
			FolderTreeNode next = (FolderTreeNode) iter.next();
			if(next.getValue().getName().equals(folderName)) {
				foundFile = true; 
				node = next; 
				
			}
		}
		
		return node; 
	}
	
	/**
	 * Test folder tree 
	 * @param args
	 */
	public static void main (String[] args) {
		FolderTreeNode root = new FolderTreeNode(new Folder("Root")); 
		FolderTreeNode child = root.addChild(new Folder("Child1")); 
		
		System.out.println(root.addNewFolder("Child1")); 
		System.out.println(child.getLocationString());
	}
	
	
	
	/**
	 * Get the folder in the node
	 * @return folder of node 
	 */
	public Folder getValue() {
		return value;
	}
	
	/**
	 * Set folder of node
	 * @param value folder to set node to
	 */
	public void setValue(Folder value) {
		this.value = value;
	}
	
	/**
	 * Get parent of node
	 * @return parent node, null if root
	 */
	public FolderTreeNode getParent() {
		return parent;
	}
	
	/**
	 * Set a node as parent to this one
	 * @param par node to make parent 
	 * @return return true if successful
	 */
	public boolean setParent(GenTreeNodeInterface<Folder> par) {
		try {
			FolderTreeNode p = (FolderTreeNode) par; 
			parent = p; 
			return true; 
		}catch (Exception e){
			return false; 
		}
		
	}
	
	/**
	 * returns true if nodes is at the top of the tree
	 * @return true if node has no parent 
	 */
	@Override
	public boolean isRoot() {
		return parent == null; 
	}
	
	/**
	 * returns if node does not have any children
	 * @return true if node is leaf 
	 */
	@Override
	public boolean isLeaf() {
		return numberOfChildren() == 0; 
	}
	
	/**
	 * return the number of children in node
	 * @return number of child nodes 
	 */
	@Override
	public int numberOfChildren() {
		return children.size(); 
	}
	
	/**
	 * get the node at the given index
	 * @param index location of node
	 * @return node at location 
	 */
	public FolderTreeNode getChild(int index) throws  IndexOutOfBoundsException{
		if (index < 0 || index >= numberOfChildren()) {
			throw new  IndexOutOfBoundsException("Index " + index + " is out of bounds!");
		}else {
			return children.get(index);
		}
		
	}
	
	/**
	 * returns the child with a given value 
	 * returns null if child does not exist
	 * @param value value of child to look for
	 * @return child node with given value, null if does not exist
	 */
	public FolderTreeNode getChild(Folder value){
		boolean foundChild = false; 
		FolderTreeNode outChild = null; 
		int index = 0; 
		while (!foundChild && index < numberOfChildren()) {
			FolderTreeNode child = getChild(index); 
			if(child.getValue().equals(value)) {
				foundChild = true; 
				outChild = child;
			}
			index ++; 
		}
		
		return outChild; 
	}
	
	/**
	 * get an array of all children of the node
	 * @return child node array
	 */
	@Override
	public FolderTreeNode[] getChildren() {
		FolderTreeNode[] returnArr = new FolderTreeNode[numberOfChildren()];
		children.toArray(returnArr); 
		return returnArr;
	}
	
	/**
	 * Returns the node with given value if it is somewhere below this node
	 * returns null if node is not in tree below this one
	 * @param value value of node to get
	 * @return node with value if below tree, else null 
	 */
	@Override
	public FolderTreeNode getElement(Folder value){
		if(this.getValue().equals(value)) {
			return this;
		}else {
			boolean foundChild = false; 
			int index = 0; 
			while (!foundChild && index < numberOfChildren()) {
				FolderTreeNode child = getChild(index); 
				if(child.getValue().equals(value)) {
					foundChild = true; 
					return child; 
				}else {
					FolderTreeNode childEle = child.getElement(value); 
					if (childEle != null) {
					
						foundChild = true; 
						return childEle; 
					}
				}
				
				index ++; 
			}		
		}
		return null; 
	}

	
	/**
	 * Returns if node is one of it's children
	 * @param node node to look for
	 * @return true if node is a child 
	 */
	@Override
	public boolean hasChild(GenTreeNodeInterface<Folder> node) {
		try {
			FolderTreeNode n = (FolderTreeNode) node; 
			return children.contains(node);
		}catch (Exception e) {
			return false; 
		}
		
	}
	
	/**
	 * Returns if there is a child that contains this value
	 * @param value value to look for
	 * @return true if value is found in children 
	 */
	@Override
	public boolean hasChild(Folder value) {
		boolean foundChild = false; 
		int index = 0; 
		while (!foundChild && index < numberOfChildren()) {
			FolderTreeNode child = getChild(index); 
			if(child.getValue().equals(value)) {
				foundChild = true; 
			}
			index ++; 
		}
		return foundChild;
	}
	
	/**
	 * Return true if node is element or element is anywhere below it
	 * @param element node to look for
	 * @return true if element is in the tree below this node
	 */
	
	public boolean hasElement(FolderTreeNode element) {
		if(this.equals(element)) {
			return true;
		}else {
			boolean foundChild = false; 
			int index = 0; 
			while (!foundChild && index < numberOfChildren()) {
				FolderTreeNode child = getChild(index); 
				if(child.equals(element)) {
					foundChild = true; 
				}else if (child.hasElement(element)) {
					foundChild = true; 
				}
				index ++; 
			}
			return foundChild;
		}
		
	}
	
	/**
	 * Return true if node contains value or if any node below it contains the value 
	 * @param element value to look for
	 * @return true if element is found anywhere in the tree below this node
	 */
	public boolean hasElement(Folder element) {
		if(this.getValue().equals(element)) {
			return true;
		}else {
			boolean foundChild = false; 
			int index = 0; 
			while (!foundChild && index < numberOfChildren()) {
				FolderTreeNode child = getChild(index); 
				if(child.getValue().equals(element)) {
					foundChild = true; 
				}else if (child.hasElement(element)) {
					foundChild = true; 
				}
				index ++; 
			}
			return foundChild;
		}
	}
	
	
	/**
	 * Create and add a note that contains value as a child
	 * Set the parent of child to this node
	 * @param value value of child node to add 
	 * @return node of child added 
	 */
	@Override
	public FolderTreeNode addChild(Folder folder) {
		if (hasFolder(folder)) {
			return null; 
		}else {
			FolderTreeNode child = new FolderTreeNode(folder, this);
			children.add(child); 
			return child; 
		}
		
		
	}
	
	/**
	 * Remove a child with a certain value 
	 * Returns true if successful
	 * If there are more then one child with the value, remove one of them
	 * @param value value of child to remove
	 * @return true if child was successfully removed, false if no child node had value
	 */
	@Override
	public boolean removeChild(Folder value) {
		boolean foundChild = false; 
		FolderTreeNode childToRemove = null; 
		int index = 0; 
		while (!foundChild && index < numberOfChildren()) {
			FolderTreeNode child = getChild(index); 
			if(child.getValue().equals(value)) {
				foundChild = true; 
				childToRemove = child; 
			}
			index ++; 
		}
		if(foundChild) {
			children.remove(childToRemove);
		}
		return foundChild;
	}
	
	/**
	 * Removes a node if it is a child of this node 
	 * returns true if successful 
	 * if there duplicate nodes removes one of them
	 * @param node child node to remove
	 * @return true if child was successfully removed, false if node was not a child
	 */
	@Override
	public boolean removeChild(GenTreeNodeInterface<Folder> node) {
		// TODO Auto-generated method stub
		return children.remove(node);
	}
	
	/**
	 * Return the string of the folder 
	 */
	public String toString() {
		return getValue().toString();
	}
	
	/**
	 * Return a string of all ancestors with "/" symbol seperating them 
	 * @return location of node within tree as string 
	 */
	public String getLocationString() {
		if (getParent() != null) {
			return getParent().getLocationString() + "/" + toString(); 
		}else {
			return toString();
		}
		
	}

	/**
	 * Get an iterator for all children 
	 * @return children iterator starting at first child 
	 */
	public Iterator<FolderTreeNode> getIterable() {
		// TODO Auto-generated method stub
		return new ChildIterator(this);
	}
	
	/**
	 * Iterate through children of tree 
	 * @author fiona
	 *
	 */
	private class ChildIterator implements Iterator<FolderTreeNode>{
		int index; 
		FolderTreeNode node; 
		/**
		 * Create iterator starting at index 0 
		 * @param n node that iterator operates on
		 */
		public ChildIterator(FolderTreeNode n ) {
			index= 0; 
			node = n; 
		}
		
		/**
		 * Returns true if there are still children left 
		 */
		@Override
		public boolean hasNext() {
			return index < node.numberOfChildren(); 
		}
		
		/**
		 * Return next child 
		 * @throws NoSuchElementException when there is not a next element 
		 */
		@Override
		public FolderTreeNode next() {
			if(!hasNext()) {
				throw new NoSuchElementException(); 
			}else {
				FolderTreeNode child = node.getChild(index); 
				index ++; 
				return child;
			}
			
		}
		
	}

}
