/**
 * Node class describes a Node. it contains the data and methods of a node
 * 
 * Last Revision: September 23, 2016
 * 
 * @author Allen Bui | x339y958
 * @version 1
 * @category Dynamic Programming
 */
public class Node {
	// integer key value
	private int key;
	// right child of the node
	private Node rightChild;
	// left child of the node
	private Node leftChild;

	/**
	 * Node constructor needs at least one value, which is the key
	 * 
	 * @param key - Integer value of the key
	 */
	Node( int key ) {
		this.key = key;
		leftChild = null;
		rightChild = null;
	}

	/**
	 * setLeftChild sets the left child of a node
	 *  
	 * @param leftChild - Node which is the leftchild of the node
	 */
	public void setLeftChild( Node leftChild ) {
		this.leftChild = leftChild;
	}
	
	/**
	 * setRightChild sets the right child of a node
	 * 
	 * @param rightChild - Node which is the right child of the node
	 */
	public void setRightChild( Node rightChild ) {
		this.rightChild = rightChild;
	}
	
	/**
	 * getKey is the accessor method that returns the integer key of the node
	 * 
	 * @return - int value which is the key of the node
	 */
	public int getKey() {
		return key;
	}
	
	/**
	 * getLeftChild returns the left child of a node
	 * 
	 * @return - Node which is the left child
	 */
	public Node getLeftChild() {
		return leftChild;
	}
	
	/**
	 * getRightChild returns the right child of a node
	 * 
	 * @return - Node which is the right child
	 */
	public Node getRightChild() {
		return rightChild;
	}
}

