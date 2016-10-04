import java.io.PrintWriter;

/**
 * 
 * OptimalBinarySearchTree is my implementation of an optimal binary search tree, using the algorithm
 * specified on page 402 of Introduction to Algorithms, 3rd edition, by Thomas H. Cormen. \
 * OptimalBinarySearchTree contains the data and methods necessary for an optimal BST. This method
 * depends on Node.java
 * 
 * Last Revision: September 23, 2016
 * 
 * @author Allen Bui | x339y958
 * @version 1
 * @category Dynamic Programming
 */
public class OptimalBinarySearchTree {
	
	// stores the keys of the tree in a sorted fashion (e from the book)
	private int[] keys;
	// stores the expected cost of the tree
	private double[][] expectedCosts;
	// stores the weights (w from the book)
	private double[][] weights;
	// stores the roots (r from the book)
	private int[][] roots;
	// the root of the binary search tree
	private Node root;
	
	private Long startTime;	// variables to store time in nanoseconds
	private Long endTime; 
	
	/**
	 * OptimalBinarySearchTree constructor. this constructor is in charge of
	 * getting invoking all of methods necessary in order to construct
	 * an optimal binary search tree with the given parameters
	 * 
	 * @param keys - an array of keys
	 * @param p - array of doubles that is the probabilities that Pi is searched for ( real nodes )
	 * @param q - array of doubles that is the probabilities that Qi is searched for ( dummy nodes ).
	 * @param n - the number of keys
	 */
	public OptimalBinarySearchTree( int[] keys, double[] p, double[] q, int n ) {
		this.keys = keys.clone();
		expectedCosts = new double[ n + 1 ][ n ];
		weights = new double[ n + 1 ][ n ];
		roots = new int[ n + 1 ][ n ];

		startTimer();
		// calculate the cost and tree structure
		OptimalBST(p, q, n);
		endTimer();

		// make the tree
		this.root = makeTree( 1, n - 1);
	}
	
	/**
	 * OptimaalBST does the calculations and populates the three matrices expectedCost,
	 * weights, and roots. this method takes O(n^3) time as we can easily see that the nested for loops
	 * are three deep and each loop index takes on at most n values. 
	 * 
	 * @param p - array of doubles of the probabilities that Pi is searched for ( real nodes )
	 * @param q - array of doubles of the probabilities that Qi is searched for ( dummy nodes )
	 * @param n - the number of keys
	 */
	private void OptimalBST( double[] p, double[] q, int n ) {
		for ( int i = 1; i < ( n + 1 ); i++ ) {
			expectedCosts[ i ][ i - 1 ] = q[ i - 1 ];
			weights[ i ][ i - 1 ] = q[ i - 1 ];
		}
		
		for ( int l = 1; l < n; l++ ) {
			for ( int i = 1; i < ( n - l + 1 ); i++ ) {
				int j = i + l - 1;
				expectedCosts[ i ][ j ] = Integer.MAX_VALUE;
				weights[ i ][ j ] = weights[ i ][ ( j - 1 ) ] + p[ j ] + q[ j ];
				
				for ( int r = i; r < j + 1; r++ ) {
					double t = expectedCosts[ i ][ ( r - 1 ) ] + expectedCosts[ ( r + 1 ) ][ j ] + weights[ i ][ j ];
					if ( t < expectedCosts[ i ][ j ] ) {
						expectedCosts[ i ][ j ] = t;
						roots[ i ][ j ] = r;
					}
				}
			}
		}
	}
	
	/**
	 * makeTree creates the optimal binary search tree after the calculations from OptimalBST have been performed
	 * 
	 * @param i - index i
	 * @param j - index j
	 * @return - Node which is used in the recursive formula to attach nodes, and to return the root to the originally called method
	 */
	private Node makeTree( int i, int j ) {

		if ( i > j ) {
			return null;
		} else {
			Node node = new Node( keys[ roots[ i ][ j ] - 1 ] );
			node.setLeftChild( makeTree( i, roots[ i ][ j ] - 1 ) );
			node.setRightChild( makeTree( roots[ i ][ j ] + 1, j ) );
			return node;
		}
	}
	
	public int[] getKeys() {
		return keys;
	}
	
	/**
	 * getExpectedCosts returns a 2D array of doubles which is the best expected costs for the OBST
	 * @return - 2D array of doubles that contains the best expected costs for the OBST
	 * 
	 */
	public double[][] getExpectedCosts() {
		return expectedCosts;
	}
	
	/**
	 * getWeights returns a 2D array of doubles which is the one time calculated weights
	 * 
	 * @return - 2D array of doubles that contain the weights 
	 */
	public double[][] getWeights() {
		return weights;
	}
	
	/**
	 * getRoots is an accessor for the 2D array of roots
	 * 
	 * @return int[][] roots which is the 2D array of roots
	 */
	public int[][] getRoots() {
		return roots;
	}
	
	/**
	 * preOrder is the method that invokes preOrderUtil. preOrder
	 * does not do the actual traversal, it just passes the root node
	 * since a recursive traversal needs the root as a parameter.
	 */
	public void preOrder() {
		preOrderUtil(root);
	}
	
	/**
	 * PreOrderUtil is the method that actually does the preorder traversal. It
	 * is called directly by preOrder to do the work, since a recursive preorder
	 * traversal needs a parameter.
	 * 
	 * @param treeNode - the current root node
	 */
	private void preOrderUtil( Node treeNode ) {
		if (treeNode != null) {
			System.out.println( "\nCurrent Node is " + treeNode.getKey() 
								+ "\n-----------------------------------------------\n");
			if ( treeNode.getLeftChild() != null ) {
				System.out.printf( "\tleft child of %d is %d\n", treeNode.getKey(), treeNode.getLeftChild().getKey() );
			} else {
				System.out.printf( "\tleft child of %d is NULL\n", treeNode.getKey());
			}
			
			if ( treeNode.getRightChild() != null ) {
				System.out.printf( "\tright child of %d is %d\n", treeNode.getKey(), treeNode.getRightChild().getKey() );
			} else {
				System.out.printf( "\tright child of %d is NULL\n", treeNode.getKey());
			}
			preOrderUtil( treeNode.getLeftChild() );
			preOrderUtil( treeNode.getRightChild() );
		}
	}
	
	/**
	 * preOrder is the method that invokes preOrderUtil. preOrder
	 * does not do the actual traversal, it just passes the root node
	 * since a recursive traversal needs the root as a parameter.
	 */
	public void preOrderToFile( PrintWriter out ) {
		preOrderToFileUtil( out, root );
	}
	
	/**
	 * PreOrderUtil is the method that actually does the preorder traversal. It
	 * is called directly by preOrder to do the work, since a recursive preorder
	 * traversal needs a parameter.
	 * 
	 * @param treeNode - the current root node
	 */
	private void preOrderToFileUtil( PrintWriter out, Node treeNode ) {
		if (treeNode != null) {
			out.println( "\nCurrent Node is " + treeNode.getKey() 
								+ "\n-----------------------------------------------\n");
			if ( treeNode.getLeftChild() != null ) {
				out.printf( "\tleft child of %d is %d\n", treeNode.getKey(), treeNode.getLeftChild().getKey() );
			} else {
				out.printf( "\tleft child of %d is NULL\n", treeNode.getKey());
			}
			
			if ( treeNode.getRightChild() != null ) {
				out.printf( "\tright child of %d is %d\n", treeNode.getKey(), treeNode.getRightChild().getKey() );
			} else {
				out.printf( "\tright child of %d is NULL\n", treeNode.getKey());
			}
			preOrderToFileUtil( out, treeNode.getLeftChild() );
			preOrderToFileUtil( out, treeNode.getRightChild() );
		}
	}
	
	/**
	 * inOrder is the method that invokes inOrderUtil. inOrder
	 * does not do the actual traversal, it just passes the root node
	 * since a recursive traversal needs the root as a parameter.
	 */
	public void inOrderToFile( PrintWriter out ) {
		inOrderToFileUtil( out, root );
	}
	
	/**
	 * inOrderUtil is the method that actually does the inorder traversal. It
	 * is called directly by inorder to do the work, since a recursive inorder
	 * traversal needs a parameter.
	 * 
	 * @param treeNode - the current root node
	 */
	private void inOrderToFileUtil( PrintWriter out, Node treeNode ) {

		if (treeNode != null) {

			inOrderToFileUtil( out, treeNode.getLeftChild() );

			out.println( "\nCurrent Node is " + treeNode.getKey() 
								+ "\n-----------------------------------------------\n");
			if ( treeNode.getLeftChild() != null ) {
				out.printf( "\tleft child of %d is %d\n", treeNode.getKey(), treeNode.getLeftChild().getKey() );
			} else {
				out.printf( "\tleft child of %d is NULL\n", treeNode.getKey());
			}
			
			if ( treeNode.getRightChild() != null ) {
				out.printf( "\tright child of %d is %d\n", treeNode.getKey(), treeNode.getRightChild().getKey() );
			} else {
				out.printf( "\tright child of %d is NULL\n", treeNode.getKey());
			}

			inOrderToFileUtil( out, treeNode.getRightChild() );
		}
	}

	/**
	 * preOrder is the method that invokes preOrderUtil. preOrder
	 * does not do the actual traversal, it just passes the root node
	 * since a recursive traversal needs the root as a parameter.
	 */
	public void inOrder() {
		inOrderUtil(root);
	}
	
	/**
	 * PreOrderUtil is the method that actually does the preorder traversal. It
	 * is called directly by preOrder to do the work, since a recursive preorder
	 * traversal needs a parameter.
	 * 
	 * @param treeNode - the current root node
	 */
	private void inOrderUtil( Node treeNode ) {

		if (treeNode != null) {

			inOrderUtil( treeNode.getLeftChild() );

			System.out.println( "\nCurrent Node is " + treeNode.getKey() 
								+ "\n-----------------------------------------------\n");

			if ( treeNode.getLeftChild() != null ) {
				System.out.printf( "\tleft child of %d is %d\n", treeNode.getKey(), treeNode.getLeftChild().getKey() );
			} else {
				System.out.printf( "\tleft child of %d is NULL\n", treeNode.getKey());
			}
			
			if ( treeNode.getRightChild() != null ) {
				System.out.printf( "\tright child of %d is %d\n", treeNode.getKey(), treeNode.getRightChild().getKey() );
			} else {
				System.out.printf( "\tright child of %d is NULL\n", treeNode.getKey());
			}

			inOrderUtil( treeNode.getRightChild() );
		}
	}

	/**
	 * getRuntime calculates the runtime of QuickSort (& randomized QuickSort)
	 * @return Long that is the runtime of QUickSort in nanoseconds
	 */
	public Long getRuntime() {
		return endTime - startTime;
	}
	
	private void startTimer() {
		startTime = System.nanoTime();
	}
	
	private void endTimer() {
		endTime = System.nanoTime();
	}
}

