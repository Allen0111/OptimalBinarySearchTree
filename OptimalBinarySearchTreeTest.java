import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

/**
 * OptimalBinarySearchTreeTest is the test program for the optimal binary search tree
 *
 * Last Revision: September 23, 2016
 * 
 * @author Allen Bui | x339y958
 * @version 1
 * @category Dynamic Programming
 */
public class OptimalBinarySearchTreeTest {

	// random number generator
	private static Random generator = new Random();
	private static final String OUTPUT_FILE = "OptimalBinarySearchTreeOutput.txt";
	private static final boolean APPEND = true;
	private static boolean preorderOutput;
	private static int traversalOrder = 0;
	
	/**
	 * @param args - Not Applicable
	 */
	public static void main(String[] args) {
		
		// scanner to read from std in
		Scanner userInput = new Scanner(System.in);
		
		// variables used to fetch and store user input
		boolean validInput = false;
		int choice = 0;
		String input = null;
		
		// get user input, continue to loop until user enters a valid integer option
		while ( !validInput ) {
			System.out.print("\nOptimal Binary Search Tree Test\n" 
								+ "\n\t1) Run the default tests ( case from the book )"
								+ "\n\t2) Run custom test\n\nPlease make a selection: ");
			input = userInput.nextLine();
			try {
				choice = Integer.parseInt( input );
				if (choice < 1 || choice > 2 ) throw new NumberFormatException();
				validInput = true;
			}
			catch ( NumberFormatException nfe ){
				System.out.println("\nYour options are 1 or 2. Try Again\n");
			}
		}

		// get user input, continue to loop until user enters a valid integer option
		validInput = false;
		while ( !validInput ) {
			System.out.print("\nWould you like to output the final tree traversal to a text file? ( c, e, and w matrices are logged by default )" 
								+ "\n\t1) Yes, print the PREORDER traversal to file"
								+ "\n\t2) Yes, print the INORDER traversal to file"
								+ "\n\t3) No, Do not print the traversal to file"
								+ "\nPlease enter choice 1, 2 or 3: ");
			input = userInput.nextLine();
			try {
				int outputOption = Integer.parseInt( input );
				if (outputOption < 1 || outputOption > 3 ) throw new NumberFormatException();
				validInput = true;
				if ( outputOption == 1) {
					preorderOutput = true;
					traversalOrder = 1;
				} else if (outputOption == 2 ) {
					traversalOrder = 2;
					preorderOutput = true;
				} else {
					traversalOrder = 0;
					preorderOutput = false;
				}
			}
			catch ( NumberFormatException nfe ){
				System.out.println("\nYour options are 1 or 2. Try Again\n");
			}
		}
		
		// invoke the appropriate method according to user's choice
		switch ( choice ) {
			case 1: 
				executeDefault();
				break;
			case 2:
				executeCustom( userInput );
				break;
			default:
				System.out.println("Oops");
				break;
		}
		
		userInput.close();			//close scanner
	}
	
	/**
	 * executeDefault runs the case that is from the book
	 */
	private static void executeDefault() {
		
		int n = 5;
		int[] keys = { 1, 2, 3, 4, 5 }; 
		double[] p = { 0.00, 0.15, 0.10, 0.05, 0.10, 0.20 };
		double[] q = { 0.05, 0.10, 0.05, 0.05, 0.05, 0.10 };
		
		OptimalBinarySearchTree oBst = new OptimalBinarySearchTree( keys, p, q, ++n );
		printResults( oBst );
	}
	
	/**
	 * executeCustom allows user to enter custom options for the OBST test
	 * @param userInput
	 */
	private static void executeCustom( Scanner userInput ) {
		
		boolean validInput = false;
		String input = null;
		int choice = 0;		
		int n = 0;

		while ( !validInput ) {
			try {
				System.out.print("\nPlease select an option: \n" 
						+ "\n\t1) Generate random probabilities and keys"
						+ "\n\t2) Input custom probabilities and keys\n\nPlease make a selection: ");
				input = userInput.nextLine();
				choice = Integer.parseInt( input );
				if ( choice < 1 || choice > 2 ) throw new NumberFormatException();
				validInput = true;
			}
			catch (NumberFormatException nfe ) {
				System.out.println("\nYour options are 1 or 2. Try Again\n");
			}
		}
		
		validInput = false;
		while ( !validInput ) {
			try {
					System.out.print("\nPlease enter the number of nodes in the tree (min = 1, max = 100000): ");
					input = userInput.nextLine();
					n = Integer.parseInt(input);
					n++;
					if ( n < 1 || n > 100001 ) throw new NumberFormatException();
					validInput = true;		
			}
			catch( NumberFormatException nfe ) {
				System.out.println("\nYour entry was invalid. Please enter an integer between 1 & 100000");
			}
		}
		
		switch (choice ) {
		
			case 1:
				generateRandom( n );
				break;
				
			case 2:
				customInput( n, userInput );
				break;
		}
	}
	
	private static void customInput( int n, Scanner userInput ) {
		
		double[] p = new double[n];
		double[] q = new double[n];
		double probabilitySum = 0;
		int[] keys = new int[n - 1];
		
		int choice = 0;
		boolean badInput = true;
		boolean probEqualToOne = false;
		String choiceStr;
		
		int m = 0;
		
		while ( !probEqualToOne ){
			while ( n > m ) {
				
				boolean validInput = true;
				
				if ( m == 0 ) { // first input
					
					badInput = true;
					
					// loop until user enters the right input
					while ( badInput ) {
						System.out.print("\nEnter the probability that the key being searched for is less than any real key in the tree (dummy key 0): ");
						choiceStr = userInput.nextLine();
						try {
							q[m] = Double.parseDouble( choiceStr );
							if ( q[m] < 0 || q[m] > 1 ) throw new NumberFormatException();
							p[m] = 0.0;
							probabilitySum += q[m];
							badInput = false;
						} catch ( NumberFormatException nfe ) {
							System.out.println("\n\nYour input did not correspond to a probability value. your input must be a value less than 1 and greater than 0\n");
						}	
					}
					// bad input, correct it
					if ( (int)(probabilitySum * 100) > 100  || probabilitySum <  0 ) {
						badInput = true;
						while ( badInput ) {
							System.out.println("\n\nERROR: the value of all probabilities must sum to 1 and must not contain negative probabilities. You have broken this rule.");
							System.out.println("\n\t1) start new input\n\t2) end program");
							choiceStr = userInput.nextLine();
							try {
								choice = Integer.parseInt(choiceStr);
								badInput = false;
							} catch ( NumberFormatException nfe ) {
								System.out.println("Your selection must be in the form of an integer value. 1 to start input over, 2 to end program.\n");
							}
						}
					} else { // successful, so increment
						m++;
					}
					
				} else { // not first input
					
					badInput = true;
					
					// get key value
					while ( badInput ) {
						System.out.printf( "\nEnter key # %d (integer values only): ", m );
						choiceStr = userInput.nextLine();
						try {
							keys[ m - 1 ] = Integer.parseInt(choiceStr);
							if ( m > 1 ) {
								if ( keys[m - 1] < keys[m - 2] ) throw new NumberFormatException();
							}
							badInput = false;
						} catch (NumberFormatException nfe) {
							System.out.println("You have violated one of the following rules:\n\n" +
									"\n\t1) keys for this OBST are integer values\n\t2) keys must be in placed in ascending order\n" );
						}
					}
					
					// get p value
					if ( validInput ) {
						badInput = true;
						while ( badInput ) {
							System.out.printf( "Enter the probability for key %d being searched ( Remaining probability: %f ): ", keys[ m - 1 ], ( 1 - probabilitySum ) );
							choiceStr = userInput.nextLine();
							try {
								p[m] = Double.parseDouble(choiceStr);
								probabilitySum += p[m];
								if ( ( int )( probabilitySum * 100 ) > 100 || p[m] < 0 ) throw new NumberFormatException();
								badInput = false;
							} catch (NumberFormatException nfe) {
								badInput = true;
								while ( badInput ) {
									System.out.println("\n\nERROR: the value of all probabilities must sum to 1 and must not contain negative probabilities. You have broken this rule.");
									System.out.println("\n\t1) start new input\n\t2) end program");
									choiceStr = userInput.nextLine();
									try {
										choice = Integer.parseInt(choiceStr);
										if ( choice == 1 ) {
											m = 0;
											probabilitySum = 0;
											badInput = false;
											validInput = false;
											continue;
										} else {
											System.out.print("program ending with exit value 0");
											System.exit(0);
										}
										badInput = false;
									} catch ( NumberFormatException nf ) {
										System.out.println("Your selection must be in the form of an integer value. 1 to start input over, 2 to end program.\n");
									}
								}
							}
						}	
					}
					
					// get q value
					if ( validInput ) {
						badInput = true;
						while ( badInput ) {
							System.out.printf( "Enter the probability of a search between values of %d and %d ( dummy key ) ( Remaining probability: %f ):", ( m <= 2 ) ? Integer.MIN_VALUE : keys[ m - 2 ], keys[ m - 1 ], ( 1 - probabilitySum ) );
							choiceStr = userInput.nextLine();
							try {
								q[m] = Double.parseDouble(choiceStr);
								probabilitySum += q[m];
								if ( (int)( probabilitySum * 100 ) > 100 || q[m] < 0 ) throw new NumberFormatException();
								badInput = false;
							} catch (NumberFormatException nfe) {
								badInput = true;
								while ( badInput ) {
									System.out.printf("\n\nERROR: the value of all probabilities must sum to 1 and must not contain negative probabilities. Current Probability: %f\t\t Last probability Entered: %f\n", probabilitySum, q[m]);
									System.out.println("\n\t1) start new input\n\t2) end program");
									choiceStr = userInput.nextLine();
									try {
										choice = Integer.parseInt(choiceStr);
										if ( choice == 1 ) {
											m = 0;
											probabilitySum = 0;
											badInput = false;
											validInput = false;
											continue;
										} else {
											System.out.print("program ending with exit value 0");
											System.exit(0);
										}
										badInput = false;
									} catch ( NumberFormatException nf ) {
										System.out.println("Your selection must be in the form of an integer value. 1 to start input over, 2 to end program.\n");
									}
								}
							}	
						}
					}
					
					if ( validInput ) {
						m++;
					}
				}
			}
			System.out.println("Probability = " + probabilitySum);
			if ( (int) ( probabilitySum * 100 ) != 100 ) {
				System.out.println("\n\nERROR: Sum of probabilities did not equal 1\n\t1) try again\n\t2)quit");
				choiceStr = userInput.nextLine();
				try {
					choice = Integer.parseInt(choiceStr);
					if (choice == 1 ) {
						m = 0;
						probabilitySum = 0;
						badInput = false;
						continue;
					} else {
						System.out.print("program ending with exit value 0");
						System.exit(0);
					}
					badInput = false;
				} catch ( NumberFormatException nf ) {
					System.out.println("Your selection must be in the form of an integer value. 1 to start input over, 2 to end program.\n");
				}
			} else {
				probEqualToOne = true;
			}
		}
		
		OptimalBinarySearchTree oBst = new OptimalBinarySearchTree( keys, p, q, n );
		printResults(oBst);
	}
	
	private static void generateRandom( int n ) {
		
		double[] p = new double[n];
		double[] q = new double[n];
		int[] keys = new int[n - 1];
		
		// the sum of probabilities must be 1, so we need to keep track of the sum,
		// and then divide all probabilities by the sum
		double sumOfPandQ = 0;
		
		// generate random keys and probabilities
		for( int i =  0; i < n; i++ ) {
			if ( i == 0 ) {
				p[i] = 0;
				keys[i] = generator.nextInt();
				q[i] = generator.nextDouble();
				sumOfPandQ += q[i];
			} else {
				if ( i != n - 1 ) {
					keys[i] = generator.nextInt();	
				}
				p[i] = generator.nextDouble();
				sumOfPandQ += p[i];
				q[i] = generator.nextDouble();
				sumOfPandQ += q[i];
			}
		}
		
		// the array must be sorted inorder to preserve the binary search property
		Arrays.sort(keys);
		
		// to ensure that the sum of all probabilities is 1, we need to divide all 
		// assigned probabilities by the sum of all probabilities
		for ( int i = 0; i < n; i++ ) {
			
			if ( p[ i ] != 0 ) {
				p[i] = p[i] / sumOfPandQ;
			}
			
			if ( q[ i ] != 0 ) {
				q[i] = q[i] / sumOfPandQ;
			}
		}
		
		OptimalBinarySearchTree oBst = new OptimalBinarySearchTree( keys, p, q, n );
		printResults( oBst );
	}
	
	public static void printResults( OptimalBinarySearchTree oBst ) {

		// Printing out the cost, weight, and roots
		System.out.println("\nKEY ARRAY\n-------------------------------");
		int[] keys = oBst.getKeys();
		for ( int i = 0; i < keys.length; i++ ) {
			System.out.printf( "%10d, ", keys[i] );					
		}
		System.out.println("\n");
		
		// Printing out the cost, weight, and roots
		System.out.println("\nCOST MATRIX\n-------------------------------");
		double[][]costs = oBst.getExpectedCosts();
		for ( double[] cost : costs ) {
			for ( int c = 0; c < cost.length; c++ )
			{
				if ( c == ( cost.length - 1 ) ) {
					System.out.printf( "%10.3f\n", cost[c] );	
				} else {
					System.out.printf( "%10.3f", cost[c] );					
				}
			}
		}
		
		double[][] weights = oBst.getWeights();
		System.out.println("\nWEIGHT MATRIX\n-------------------------------");
		for ( double[] weight : weights ) {
			for ( int w = 0; w < weight.length; w++ )
			{
				if ( w == ( weight.length - 1 ) ) {
					System.out.printf( "%10.3f\n", weight[w] );	
				} else {
					System.out.printf( "%10.3f", weight[w] );					
				}
			}
		}
		
		int[][] roots = oBst.getRoots();
		System.out.println("\nROOT MATRIX\n-------------------------------");
		for ( int[] root : roots ) {
			for ( int r = 0; r < root.length; r++ )
			{
				if ( r == ( root.length - 1 ) ) {
					System.out.printf( "%5d\n", root[ r ] );
				} else {
					System.out.printf( "%5d", root[ r ] );					
				}
			}
		}
		
		outputToFile( oBst );
		
		if (traversalOrder == 1 ) {
			// print the tree in a preorder format
			System.out.println("\nBeginning Preorder Traversal\n-----------------------------------");
			oBst.preOrder();
			System.out.println("\nEnd of Preorder Traversal\n");
		} else if (traversalOrder == 2) {
			// print the tree in a inOrder format
			System.out.println("\nBeginning inorder Traversal\n-----------------------------------");
			oBst.inOrder();
			System.out.println("\nEnd of inorder Traversal\n");
		}	
	}
	
	private static void outputToFile( OptimalBinarySearchTree oBst ) {
		
		PrintWriter out = null;
		
		try {
			
			 out = new PrintWriter(new BufferedWriter(new FileWriter(OUTPUT_FILE, APPEND)));
			 
			// Printing out the cost, weight, and roots
			out.printf("\nOptimal Binary Search Tree Execution Time for n = %d keys\n--------------------------------------------------------------\n", oBst.getKeys().length );
			out.println( oBst.getRuntime() + " nanoseconds!");
		
			// Printing out the cost, weight, and roots
			out.println("\nKEY ARRAY\n-------------------------------");
			int[] keys = oBst.getKeys();
			for ( int i = 0; i < keys.length; i++ ) {
				out.printf( "%10d, ", keys[i] );					
			}
			out.println("\n");
			
			// Printing out the cost, weight, and roots
			out.println("\nCOST MATRIX\n-------------------------------");
			double[][]costs = oBst.getExpectedCosts();
			for ( double[] cost : costs ) {
				for ( int c = 0; c < cost.length; c++ )
				{
					if ( c == ( cost.length - 1 ) ) {
						out.printf( "%10.3f\n", cost[c] );	
					} else {
						out.printf( "%10.3f", cost[c] );					
					}
				}
			}
			
			double[][] weights = oBst.getWeights();
			out.println("\nWEIGHT MATRIX\n-------------------------------");
			for ( double[] weight : weights ) {
				for ( int w = 0; w < weight.length; w++ )
				{
					if ( w == ( weight.length - 1 ) ) {
						out.printf( "%10.3f\n", weight[w] );	
					} else {
						out.printf( "%10.3f", weight[w] );					
					}
				}
			}
			
			int[][] roots = oBst.getRoots();
			out.println("\nROOT MATRIX\n-------------------------------");
			for ( int[] root : roots ) {
				for ( int r = 0; r < root.length; r++ )
				{
					if ( r == ( root.length - 1 ) ) {
						out.printf( "%5d\n", root[ r ] );
					} else {
						out.printf( "%5d", root[ r ] );					
					}
				}
			}
			
			if (preorderOutput) {
				if (traversalOrder == 1) {
					// print the tree in a preorder format
					out.println("\nBeginning Preorder Traversal\n-----------------------------------");
					oBst.preOrderToFile( out );
					out.println("\nEnd of Preorder Traversal\n");
				} else if (traversalOrder == 2) {					
					// print the tree in a preorder format
					out.println("\nBeginning inOrder Traversal\n-----------------------------------");
					oBst.inOrderToFile( out );
					out.println("\nEnd of inOrder Traversal\n");	
				}
			}
		} catch (IOException e) {		// catch block 
		System.out.println(e);
	        System.out.printf("Problem writing to the file %s");
	        System.exit(0);
		}
		out.close();
	}
}
