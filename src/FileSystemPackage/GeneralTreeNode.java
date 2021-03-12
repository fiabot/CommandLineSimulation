package FileSystemPackage;

import java.util.ArrayList;
import java.util.Iterator;


public class GeneralTreeNode<T> implements GenTreeNodeInterface<T> {

	public static final String INDENTSPACE = "  ";
	T value; 
	GeneralTreeNode<T> parent; 
	ArrayList<GeneralTreeNode<T>> children; 
	
	
	/**
	 * Node constructor given value and parent
	 * @param data value of node 
	 * @param parent parent of node
	 */
	public GeneralTreeNode (T data, GeneralTreeNode<T> parent){
		value= data; 
		this.parent = parent; 
		children = new ArrayList<GeneralTreeNode<T>>();
	}
	
	/**
	 * Node constructor given value of node, 
	 * automatically a root with no parent
	 * @param data value of node
	 */
	public GeneralTreeNode (T data){
		this(data, null);
	}
	
	/**
	 * Get the data in the node
	 * @return value of node 
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Set data of node
	 * @param value date to set node to
	 * @return none
	 */
	public void setValue(T value) {
		this.value = value;
	}
	
	/**
	 * get parent of node
	 * @return parent node, null if root
	 */
	public GeneralTreeNode<T> getParent() {
		return parent;
	}
	
	/**
	 * Set a node as parent to this one
	 * @param par node to make parent 
	 * @return return true if successful
	 */
	public boolean setParent(GenTreeNodeInterface<T> par) {
		try {
			GeneralTreeNode<T>p = (GeneralTreeNode<T>) par; 
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
	public GeneralTreeNode<T> getChild(int index) throws  IndexOutOfBoundsException{
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
	public GeneralTreeNode<T> getChild(T value){
		boolean foundChild = false; 
		GeneralTreeNode<T> outChild = null; 
		int index = 0; 
		while (!foundChild && index < numberOfChildren()) {
			GeneralTreeNode<T> child = getChild(index); 
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
	public GeneralTreeNode<T>[] getChildren() {
		GeneralTreeNode<T>[] returnArr = new GeneralTreeNode[numberOfChildren()];
		children.toArray(returnArr); 
		return returnArr;
	}
	
	/**
	 * returns the node with given value if it is somewhere below this node
	 * returns null if node is not in tree below this one
	 * @param value value of node to get
	 * @return node with value if below tree, else null 
	 */
	//@Override
	public GeneralTreeNode<T> getElement(T value){
		if(this.getValue().equals(value)) {
			return this;
		}else {
			boolean foundChild = false; 
			int index = 0; 
			while (!foundChild && index < numberOfChildren()) {
				GeneralTreeNode<T> child = getChild(index); 
				if(child.getValue().equals(value)) {
					foundChild = true; 
					return child; 
				}else {
					GeneralTreeNode<T> childEle = child.getElement(value); 
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
	 * returns if node is one of it's children
	 * @param node node to look for
	 * @return true if node is a child 
	 */
	@Override
	public boolean hasChild(GenTreeNodeInterface<T> node) {
		try {
			GeneralTreeNode<T> n = (GeneralTreeNode<T>) node; 
			return children.contains(node);
		}catch (Exception e) {
			return false; 
		}
		
	}
	
	/**
	 * returns if there is a child that contains this value
	 * @param value value to look for
	 * @return true if value is found in children 
	 */
	@Override
	public boolean hasChild(T value) {
		boolean foundChild = false; 
		int index = 0; 
		while (!foundChild && index < numberOfChildren()) {
			GeneralTreeNode<T> child = getChild(index); 
			if(child.getValue().equals(value)) {
				foundChild = true; 
			}
			index ++; 
		}
		return foundChild;
	}
	
	/**
	 * return true if node is element or element is anywhere below it
	 * @param element node to look for
	 * @return true if element is in the tree below this node
	 */
	
	public boolean hasElement(GeneralTreeNode<T> element) {
		if(this.equals(element)) {
			return true;
		}else {
			boolean foundChild = false; 
			int index = 0; 
			while (!foundChild && index < numberOfChildren()) {
				GeneralTreeNode<T> child = getChild(index); 
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
	 * return true if node contains value or if any node below it contains the value 
	 * @param element value to look for
	 * @return true if element is found anywhere in the tree below this node
	 */
	public boolean hasElement(T element) {
		if(this.getValue().equals(element)) {
			return true;
		}else {
			boolean foundChild = false; 
			int index = 0; 
			while (!foundChild && index < numberOfChildren()) {
				GeneralTreeNode<T> child = getChild(index); 
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
	 * Add a node to this on as a child
	 * Sets the parent of child to this node
	 * @param child node to add 
	 */
	@Override
	public boolean addChild(GenTreeNodeInterface<T> child) {
		try {
			GeneralTreeNode<T> c = (GeneralTreeNode<T>) child; 
			c.setParent(this);
			children.add(c);
			return true; 
		}catch (Exception e) {
			return false; 
		}
		
	}
	
	/**
	 * Create and add a note that contains value as a child
	 * Set the parent of child to this node
	 * @param value value of child node to add 
	 * @return node of child added 
	 */
	@Override
	public GeneralTreeNode<T> addChild(T value) {
		GeneralTreeNode<T> child = new GeneralTreeNode<T>(value, this);
		children.add(child); 
		return child; 
		
	}
	
	/**
	 * remove a child with a certain value 
	 * returns true if successful
	 * if there are more then one child with the value, remove one of them
	 * @param value value of child to remove
	 * @return true if child was successfully removed, false if no child node had value
	 */
	@Override
	public boolean removeChild(T value) {
		boolean foundChild = false; 
		GeneralTreeNode childToRemove = null; 
		int index = 0; 
		while (!foundChild && index < numberOfChildren()) {
			GeneralTreeNode<T> child = getChild(index); 
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
	public boolean removeChild(GenTreeNodeInterface<T> node) {
		// TODO Auto-generated method stub
		return children.remove(node);
	}
	
	public String toString() {
		return getValue().toString();
	}
	
	/**
	 * Testing of tree node
	 */
	public static void main (String[] args) {
		GeneralTreeNode<String> node = new GeneralTreeNode<>("Root");
		node.addChild("Child1");
		node.addChild("Child2");
		node.addChild("Child 4"); 
		GeneralTreeNode<String> child = node.getChild(0);
		child.addChild("Child4");
		GeneralTreeNode<String> child2 = child.getChild(0);
		Iterator<GeneralTreeNode<String>> iter = node.getIterable(); 
		
		while (iter.hasNext()){
			System.out.println(iter.next());
		}
	}
	
	public void displayTree() {
		System.out.println(this); 
		for(int i =0; i < numberOfChildren(); i++) {
			getChild(i).displayTree(INDENTSPACE);
		}
	}
	
	public void displayTree(String header) {
		System.out.println(header + this); 
		for(int i =0; i < numberOfChildren(); i++) {
			getChild(i).displayTree(INDENTSPACE + header);
		}
	}

	//@Override
	public Iterator<GeneralTreeNode<T>> getIterable() {
		// TODO Auto-generated method stub
		return new ChildIterator(this);
	}

	private class ChildIterator implements Iterator<GeneralTreeNode<T>>{
		int index; 
		GeneralTreeNode node; 
		public ChildIterator(GeneralTreeNode<T> n ) {
			index= 0; 
			node = n; 
		}
		@Override
		public boolean hasNext() {
			return index < node.numberOfChildren(); 
		}

		@Override
		public GeneralTreeNode<T> next() {
			GeneralTreeNode<T> child = node.getChild(index); 
			index ++; 
			return child;
		}
		
	}

	

	
}
