package FileSystemPackage;
/**
 * A tree made up of folder tree nodes 
 * @author fiona
 *
 */
public class FolderTree {
	FolderTreeNode root; 
	/**
	 * Create an empty tree
	 */
	public FolderTree() {
		root = null; 
	}
	
	/**
	 *Create a tree with a given root node 
	 * @param root node of root
	 */
	public FolderTree(FolderTreeNode root) {
		this.root = root; 
	}
	
	/**
	 * return root 
	 * @return root of tree
	 */
	public FolderTreeNode getRoot() {
		return root;
	}
	
	/**
	 * Set the root of the tree 
	 * @param root new root of tree 
	 */
	public void setRoot(FolderTreeNode root) {
		this.root = root;
	}
	
	/**
	 * Returns true if tree is empty 
	 * @return if root is null
	 */
	public boolean isEmpty() {
		return root == null; 
	}
	
	/**
	 * remove all elements from tree 
	 */
	public void clear() {
		root = null;
	}

}
