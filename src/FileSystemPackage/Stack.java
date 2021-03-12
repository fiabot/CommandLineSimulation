package FileSystemPackage;
import java.util.*;

/**
 * Stack implementation using a linked list 
 * @author fiona
 *
 * @param <T> generic object of stack 
 */
public class Stack<T> implements StackInterface<T> {
	Node firstNode; 
	
	/**
	 * Create an empty stack 
	 */
	public Stack() {
		firstNode = null; 
	}
	
	/** Adds a new entry to the top of this stack.
    @param newEntry  An object to be added to the stack. */
	@Override
	public void push(T newEntry) {
		Node newNode = new Node(newEntry); 
		if(firstNode == null) {
			firstNode = newNode; 
		}else {
			newNode.setNext(firstNode);
			firstNode = newNode; 
		}
		
	}
	
	 /** Removes and returns this stack's top entry.
    @return  The object at the top of the stack.
   @throws  EmptyStackException if the stack is empty before
    the operation. */
	@Override
	public T pop() {
		if(isEmpty()) {
			throw new EmptyStackException(); 
		}else {
			T data = firstNode.getData(); 
			firstNode = firstNode.getNext(); 
			return data; 
		}
	}
	
	/** Retrieves this stack's top entry.
    @return  The object at the top of the stack.
    @throws  EmptyStackException if the stack is empty. */
	@Override
	public T peek() {
		if(isEmpty()) {
			throw new EmptyStackException(); 
		}else {
			T data = firstNode.getData(); 
			return data; 
		}
	}
	
	 /** Detects whether this stack is empty.
    @return  True if the stack is empty. */
	@Override
	public boolean isEmpty() {
		return firstNode == null;
	}
	
	/** Removes all entries from this stack. */
	@Override
	public void clear() {
		firstNode = null; 
		
	}
	
	private class Node{
		T data; 
		Node next; 
		
		private Node(T data, Node next) {
			this.data = data; 
			this.next = next; 
		}
		
		private Node(T data) {
			this(data,null); 
		}
		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
		public Node getNext() {
			return next;
		}
		public void setNext(Node next) {
			this.next = next;
		}
		
	}
	public static void main(String[] args) {
		Stack stack = new Stack(); 
		stack.push("Hello");
		stack.push("World");
		System.out.println(stack.peek()); 
		System.out.println(stack.pop()); 
		System.out.println(stack.pop());
	}
}
	
